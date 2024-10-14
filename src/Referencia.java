class Referencia {
    String descripcion;
    int numeroPagina;
    int desplazamiento;
    char tipoOperacion;

    public Referencia(String descripcion, int numeroPagina, int desplazamiento, char tipoOperacion) {
        this.descripcion = descripcion;
        this.numeroPagina = numeroPagina;
        this.desplazamiento = desplazamiento;
        this.tipoOperacion = tipoOperacion;
    }
}