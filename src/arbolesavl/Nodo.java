package arbolesavl;
/**
 *
 * @author jcarrenoe
 */
public class Nodo {

    private int dato;
    private Nodo der;
    private Nodo izq;
    private int fe;

    public Nodo(int dato) {
        this.dato = dato;
        der = null;
        izq = null;
        this.fe = 1;
    }

    public void setFe(int fe) {
        this.fe = fe;
    }

    public int getFe() {
        return fe;
    }

    public int getDato() {
        return dato;
    }

    public Nodo getDer() {
        return der;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

}
