package arbolavl;

/**
 *
 * @author jcarrenoe
 */
public class ArbolAVL {

    public static void main(String[] args) {
        // TODO code application logic here
        Arbol arbol = new Arbol();
        int[] valores = {20,30,40,35,15,50,55,56,57,58};
        //int[] valoresE = {21, 14, 9, 10, 19, 5, 16};
        for (int i : valores) {
            System.out.println("se agrega el valor "+i);
            arbol.insertar(i);
            arbol.TreePrinter();
        }
        System.out.println("-------->ARBOL FINAL<--------");
        arbol.TreePrinter();
        System.out.println("ELIMINACIÓN");
        arbol.delete(arbol.getRaiz(), 57);
        arbol.delete(arbol.getRaiz(), 30);
        arbol.delete(arbol.getRaiz(), 50);
        arbol.delete(arbol.getRaiz(), 40);
        arbol.TreePrinter();
        
        /*for(int i:valoresE){
            System.out.println("se elimino el valor "+i);
            arbol.delete(arbol.getRaiz(),i);
            arbol.TreePrinter();
        }*/
    }
    
}
