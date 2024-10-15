class Pagina {
    int numero; // Número de página
    boolean bitR; // Bit de referencia (true o false)
    int edad; // Edad de la página, se incrementa con el tiempo

    Pagina(int numero) {
        this.numero = numero;
        this.bitR = false;
        this.edad = 0;
    }

    void incrementarEdad() {
        // Algoritmo de envejecimiento: desplazamos los bits y agregamos el bitR como el MSB
        this.edad = (this.edad >> 1) | (bitR ? 0x80 : 0x00); 
        this.bitR = false; // Reseteamos el bit después de usarlo
    }
}

