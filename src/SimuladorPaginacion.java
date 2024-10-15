
import java.util.List;

class SimuladorPaginacion extends Thread {
    List<Pagina> marcos;  // La lista es compartida con otros hilos
    List<Referencia> referencias; // Lista de referencias desde el archivo
    int marcosMaximos;
    int fallas = 0;
    int hits = 0;
    int totalReferencias;  // Número total de referencias a procesar (NR)
    int totalPaginas;  // Número total de páginas necesarias (NP)

    // Constructor que recibe la lista compartida de marcos
    SimuladorPaginacion(List<Pagina> marcos, int marcosMaximos, List<Referencia> referencias) {
        this.marcos = marcos; // Referencia compartida
        this.marcosMaximos = marcosMaximos;
        this.referencias = referencias;
    }

    @Override
    public void run() {
        for (Referencia ref : referencias) {
            int numeroPagina = ref.numeroPagina;  // Usar el número de página del archivo

            synchronized (marcos) {
                if (!paginaEnMemoria(numeroPagina)) {
                    fallas++;  // Falla de página
                    reemplazarPagina(numeroPagina);  // Reemplazar página si es necesario
                } else {
                    hits++;  // Hit, la página ya estaba en memoria
                }
            }

            // try {
            //     Thread.sleep(1);  // Simulación de acceso cada milisegundo
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }

        // Mostrar estadísticas al finalizar
        System.out.println("Hits: " + hits + ", Fallas: " + fallas);
    }

    private boolean paginaEnMemoria(int numeroPagina) {
        // Verificación si la página está en memoria
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
