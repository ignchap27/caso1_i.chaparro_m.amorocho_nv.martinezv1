import java.util.List;

class AlgoritmoEnvejecimiento extends Thread {
    List<Pagina> marcos;

    AlgoritmoEnvejecimiento(List<Pagina> marcos) {
        this.marcos = marcos; // Referencia compartida
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (marcos) {
                for (Pagina p : marcos) {
                    p.incrementarEdad(); // Actualizar la edad de la página
                }
            }
            try {
                Thread.sleep(2); // Corre cada dos milisegundos
            } catch (InterruptedException e) {
                break; // Salir del ciclo si es interrumpido
            }
        }
    }
}

