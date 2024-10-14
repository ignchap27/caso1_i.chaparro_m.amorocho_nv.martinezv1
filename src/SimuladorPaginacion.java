
import java.util.List;

class SimuladorPaginacion extends Thread {
    List<Pagina> marcos;  // La lista es pasada desde afuera, es compartida
    List<Integer> referencias; // Lista de referencias desde el archivo
    int marcosMaximos;
    int fallas = 0;
    int hits = 0;

    // Constructor que recibe la lista compartida de marcos
    SimuladorPaginacion(List<Pagina> marcos, int marcosMaximos, List<Integer> referencias) {
        this.marcos = marcos; // Referencia compartida
        this.marcosMaximos = marcosMaximos;
        this.referencias = referencias;
    }

    @Override
    public void run() {
        for (Integer pagina : referencias) {
            synchronized (marcos) {
                if (!paginaEnMemoria(pagina)) {
                    fallas++;
                    reemplazarPagina(pagina);
                } else {
                    hits++;
                }
            }

            try {
                Thread.sleep(1); // Simulación de acceso cada milisegundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Hits: " + hits + ", Fallas: " + fallas);
    }

    private boolean paginaEnMemoria(int numeroPagina) {
        for (Pagina p : marcos) {
            if (p.numero == numeroPagina) {
                p.bitR = true; // Página accedida, activar bit de referencia
                return true;
            }
        }
        return false;
    }

    private void reemplazarPagina(int numeroPagina) {
        synchronized (marcos) {
            if (marcos.size() < marcosMaximos) {
                marcos.add(new Pagina(numeroPagina)); // Añadir nueva página si hay espacio
            } else {
                Pagina aReemplazar = marcos.get(0); // Buscar la página con menor "edad"
                for (Pagina p : marcos) {
                    if (p.edad < aReemplazar.edad) {
                        aReemplazar = p;
                    }
                }
                marcos.remove(aReemplazar); // Reemplazar la página más vieja
                marcos.add(new Pagina(numeroPagina));
            }
        }
    }
}
