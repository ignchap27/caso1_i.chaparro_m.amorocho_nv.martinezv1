class Pagina {
    int numero; // Número de página
    boolean bitR; // Bit de referencia 
    boolean bitM; // Bit de modificación 
    int edad; // Edad de la página, se incrementa con el tiempo

    Pagina(int numero) {
        this.numero = numero;
        this.bitR = false;
        this.bitM = false;
        this.edad = 0;
    }

    void resetearBitReferencia(){
        this.bitR = false;
    }

    // Clasificación de la página para el algoritmo NRU
    int getClaseNRU() {
        if (!bitR && !bitM) {
            return 0;  // Clase 0: No referenciada, no modificada
        } else if (!bitR && bitM) {
            return 1;  // Clase 1: No referenciada, pero modificada
        } else if (bitR && !bitM) {
            return 2;  // Clase 2: Referenciada, pero no modificada
        } else {
            return 3;  // Clase 3: Referenciada y modificada
        }
    }

    void incrementarEdad() {
        // Algoritmo de envejecimiento: desplazamos los bits y agregamos el bitR como el MSB
        this.edad = (this.edad >> 1) | (bitR ? 0x80 : 0x00); 
        this.bitR = false; // Reseteamos el bit después de usarlo
    }
}

