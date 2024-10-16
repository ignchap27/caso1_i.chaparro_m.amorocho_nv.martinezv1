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
                    p.resetearBitReferencia();  // Resetear el bit de referencia
                }
            }

            try {
                Thread.sleep(2);  // Cada 2 milisegundos (ajusta según sea necesario)
            } catch (InterruptedException e) {
                break;  // Salir del ciclo si es interrumpido
            }
        }
    }
}

