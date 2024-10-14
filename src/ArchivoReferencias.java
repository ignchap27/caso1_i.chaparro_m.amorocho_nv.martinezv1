
import java.util.List;

public class ArchivoReferencias {
    private List<Integer> referencias;
    private int numReferencias;
    private int numPaginas;

    public ArchivoReferencias(List<Integer> referencias, int numReferencias, int numPaginas) {
        this.referencias = referencias;
        this.numReferencias = numReferencias;
        this.numPaginas = numPaginas;
    }

    public List<Integer> getReferencias() {
        return referencias;
    }

    public int getNumReferencias() {
        return numReferencias;
    }

    public int getNumPaginas() {
        return numPaginas;
    }
}