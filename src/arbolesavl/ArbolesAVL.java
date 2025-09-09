package arbolesavl;

/**
 *
 * @author jcarrenoe
 */
public class ArbolesAVL {

    public static void main(String[] args) {
        int[] arbol1 = {20, 30, 40, 35, 15, 50, 55, 56, 57};
        int[] delete = {57, 55, 30, 20};
        int[] arbol2 = {10, 60, 17, 58};
        String[] preOrden = {"A", "B", "D", "G", "H", "K", "E", "C", "F", "I", "J"};
        String[] inOrden = {"G", "D", "K", "H", "B", "E", "A", "C", "I", "F", "J"};
        Arbol arbolAVL = new Arbol();
        Arbol arbol = new Arbol();

        for (int i : arbol1) {
            System.out.println("Se agrego " + i);
            arbolAVL.insertar(i);
            arbolAVL.TreePrinter();
        }

        for (int i : arbol2) {
            System.out.println("Se agrego " + i);
            arbolAVL.insertar(i);
            arbolAVL.TreePrinter();
        }

        for (int d : delete) {
            System.out.println("Se elimina " + d);
            arbolAVL.setRaiz(arbolAVL.delete(arbolAVL.getRaiz(), d));
            arbolAVL.TreePrinter();
        }

        arbol.construirDesdePreIn(preOrden, inOrden);
        System.out.println("Arbol reconstruido:");
        arbol.TreePrinter();

    }
}
