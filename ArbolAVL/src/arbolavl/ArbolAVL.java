package arbolavl;

/**
 *
 * @author jcarrenoe
 */
public class ArbolAVL {

    public static void main(String[] args) {
        // TODO code application logic here
        Arbol arbol = new Arbol();
        int[] inOrden = {15,20,30,35,40,50,55,56,57,58};
        arbol.construirDesdeInorden(inOrden);
        System.out.println("----> Árbol construido desde recorrido inOrden <----");
        arbol.TreePrinter();
    }

}
