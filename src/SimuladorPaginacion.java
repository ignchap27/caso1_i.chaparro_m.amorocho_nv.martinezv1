
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
            char tipoOperacion = ref.tipoOperacion;  // Usar el tipo de operación del archivo

            synchronized (marcos) {
                if (!paginaEnMemoria(numeroPagina, tipoOperacion)) {
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

    private boolean paginaEnMemoria(int numeroPagina, char tipoOperacion) {
        // Verificación si la página está en memoria
        for (Pagina p : marcos) {
            if (p.numero == numeroPagina) {
                p.bitR = true; // Página accedida, activar bit de referencia

                if (tipoOperacion == 'W') {
                    p.bitM = true;
                }

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
                Pagina paginaAReemplazar = null;
                int claseMinima = 4;  // Inicializar con un valor mayor a cualquier clase posible

                // Buscar la página con la clase NRU más baja
                for (Pagina p : marcos) {
                    int clase = p.getClaseNRU();
                    if (clase < claseMinima) {
                        claseMinima = clase;
                        paginaAReemplazar = p;
                    }
                }

                if (paginaAReemplazar != null) {
                    marcos.remove(paginaAReemplazar);  // Eliminar la página de la memoria
                }
                
                marcos.add(new Pagina(numeroPagina));
            }
        }
    }
}
