package arbolesavl; 
/**
 *
 * @author jcarrenoe
 */
public class Arbol {

    private Nodo raiz; // raíz del árbol

    public Nodo getRaiz() { // getter de la raíz
        return raiz;
    }

    public void setRaiz(Nodo raiz) { // setter de la raíz
        this.raiz = raiz;
    }

    public int alturaNodo(Nodo nodo) { // altura almacenada en el nodo (fe); si es null, 0
        if (nodo == null) {
            return 0;
        } else {
            return nodo.getFe();
        }
    }

    public static int alturaArbol(Nodo n1) { // altura real del árbol (para impresión)
        if (n1 == null) {
            return 0;
        }
        return Math.max(alturaArbol(n1.getIzq()), alturaArbol(n1.getDer())) + 1;
    }

    public static int getcol(int h) { // ancho de la matriz usada para dibujar
        if (h == 1) {
            return 1;
        }
        return getcol(h - 1) + getcol(h - 1) + 1; // 2^(h)-1
    }

    public static void printTree(int[][] M, Nodo root, int col, int row, int height) {
        // coloca root en (row, col) y recurre a izq/der con separación horizontal decreciente
        if (root == null) {
            return;
        }
        M[row][col] = root.getDato();
        printTree(M, root.getIzq(), col - (int) Math.pow(2, height - 2), row + 1, height - 1);
        printTree(M, root.getDer(), col + (int) Math.pow(2, height - 2), row + 1, height - 1);
    }

    public void TreePrinter() {
        int h = alturaArbol(this.raiz);  
        int col = getcol(h);
        int[][] M = new int[h][col];
        printTree(M, this.raiz, col / 2, 0, h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < col; j++) {
                if (M[i][j] == 0) { 
                    System.out.print("  ");
                } else {
                    if (M[i][j] >= 'A' && M[i][j] <= 'Z') {
                        System.out.print((char) M[i][j] + " ");
                    } else {
                        System.out.print(M[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }
    }   

    public int factorEquilibrio(Nodo nodo) { // balance = altura(izq) - altura(der)
        if (nodo == null) {
            return 0;
        }
        return alturaNodo(nodo.getIzq()) - alturaNodo(nodo.getDer());
    }

    public Nodo rotacionDerecha(Nodo y) { // rotación simple a la derecha (LL o parte de LR)
        Nodo x = y.getIzq();
        Nodo temp = x.getDer();

        x.setDer(y);
        y.setIzq(temp);

        // actualizar alturas de abajo hacia arriba
        y.setFe(Math.max(alturaNodo(y.getIzq()), alturaNodo(y.getDer())) + 1);
        x.setFe(Math.max(alturaNodo(x.getIzq()), alturaNodo(x.getDer())) + 1);

        System.out.println("Rotacion Derecha sobre nodo " + y.getDato());
        return x; // nueva raíz del subárbol
    }

    public Nodo rotacionIzquierda(Nodo y) { // rotación simple a la izquierda (RR o parte de RL)
        Nodo x = y.getDer();
        Nodo temp = x.getIzq();

        x.setIzq(y);
        y.setDer(temp);

        // actualizar alturas
        y.setFe(Math.max(alturaNodo(y.getIzq()), alturaNodo(y.getDer())) + 1);
        x.setFe(Math.max(alturaNodo(x.getIzq()), alturaNodo(x.getDer())) + 1);

        System.out.println("Rotacion Izquierda sobre nodo " + y.getDato());
        return x; // nueva raíz del subárbol
    }

    public void insertar(int valor) { // API pública de inserción
        this.raiz = insertar(valor, this.raiz); // puede cambiar la raíz
    }

    public Nodo insertar(int valor, Nodo nodo) { // inserta como BST y rebalancea tipo AVL
        if (nodo == null) { // caso base: nueva hoja
            Nodo new_nodo = new Nodo(valor);
            return new_nodo;
        }
        if (valor < nodo.getDato()) {
            nodo.setIzq(insertar(valor, nodo.getIzq()));
        } else if (valor > nodo.getDato()) {
            nodo.setDer(insertar(valor, nodo.getDer()));
        } else {
            // duplicado: no inserta
            return nodo;
        }
        // recalcula altura (fe = 1 + max(altura izq, altura der))
        nodo.setFe(Math.max(alturaNodo(nodo.getIzq()), alturaNodo(nodo.getDer())) + 1);
        int equilibrio = factorEquilibrio(nodo); // balance del nodo actual
        // Casos de rotación:
        // LL: pesado a la izq y el valor cayó en sub-izq
        if (equilibrio > 1 && valor < nodo.getIzq().getDato()) {
            return rotacionDerecha(nodo);
        }
        // RR: pesado a la der y el valor cayó en sub-der
        if (equilibrio < -1 && valor > nodo.getDer().getDato()) {
            return rotacionIzquierda(nodo);
        }
        // LR: pesado a la izq pero el valor cayó en sub-der de izq
        if (equilibrio > 1 && valor > nodo.getIzq().getDato()) {
            nodo.setIzq(rotacionIzquierda(nodo.getIzq()));
            return rotacionDerecha(nodo);
        }
        // RL: pesado a la der pero el valor cayó en sub-izq de der
        if (equilibrio < -1 && valor < nodo.getDer().getDato()) {
            nodo.setDer(rotacionDerecha(nodo.getDer()));
            return rotacionIzquierda(nodo);
        }

        return nodo; // ya balanceado
    }

    public Nodo delete(Nodo root, int valor) { // elimina como BST y rebalancea tipo AVL
        if (root == null) {
            return root;
        }

        if (valor < root.getDato()) {
            root.setIzq(delete(root.getIzq(), valor));
        } else if (valor > root.getDato()) {
            root.setDer(delete(root.getDer(), valor));
        } else {
            // encontrado: manejar 0, 1 o 2 hijos
            if (root.getIzq() == null) { // 0 o 1 hijo (solo der)
                return root.getDer();
            } else if (root.getDer() == null) { // 0 o 1 hijo (solo izq)
                return root.getIzq();
            }

            // 2 hijos: reemplazar por sucesor en-orden (mínimo del subárbol derecho)
            root.setDato(minValue(root.getDer()));

            // eliminar el sucesor en el subárbol derecho
            root.setDer(delete(root.getDer(), root.getDato()));
        }

        // actualizar altura y rebalancear
        root.setFe(Math.max(alturaNodo(root.getIzq()), alturaNodo(root.getDer())) + 1);
        int balance = factorEquilibrio(root);

        // LL
        if (balance > 1 && factorEquilibrio(root.getIzq()) >= 0) {
            return rotacionDerecha(root);
        }

        // LR
        if (balance > 1 && factorEquilibrio(root.getIzq()) < 0) {
            root.setIzq(rotacionIzquierda(root.getIzq()));
            return rotacionDerecha(root);
        }

        // RR
        if (balance < -1 && factorEquilibrio(root.getDer()) <= 0) {
            return rotacionIzquierda(root);
        }

        // RL
        if (balance < -1 && factorEquilibrio(root.getDer()) > 0) {
            root.setDer(rotacionDerecha(root.getDer()));
            return rotacionIzquierda(root);
        }

        return root; // nodo ya balanceado
    }

    //bonus usar raiz A
    public int minValue(Nodo node) { // mínimo de un subárbol (ir completamente a la izquierda)
        int minValue = node.getDato();
        while (node.getIzq() != null) {
            minValue = node.getIzq().getDato();
            node = node.getIzq();
        }
        return minValue;
    }

    private int prePos = 0;  // posición actual en el preorden

    public void construirDesdePreIn(String[] preOrden, String[] inOrden) {
        prePos = 0; //Empezar a leer desde el primer elemento del preOrden
        this.raiz = construirDesdePreIn(preOrden, inOrden, 0, inOrden.length - 1);
    }

    public Nodo construirDesdePreIn(String[] preOrden, String[] inOrden, int limiteIzq, int limiteDer) {
        if (limiteIzq > limiteDer) {
            return null; //Si lo encontramos vacio, nos dice que no hay nodo 
        }
        char raizSubarbol = preOrden[prePos].charAt(0); //Se guarda la raiz del sub arbol que estamos construyendo
        prePos = prePos + 1; //Avanzar el p en preOrden 
        Nodo nodo = new Nodo(raizSubarbol); 
        int k = -1; //si K sigue siento -1 al final del recorrido, el valor de la raiz no pudo ser encontrado en inOrden
        for (int i = limiteIzq; i <= limiteDer; i++) {
            if (inOrden[i].charAt(0) == raizSubarbol) { // buscar la raíz en el inorden
                k = i; //si es igual a i significa que si pudimos encontrar el valor de la raiz en inOrden
                break;
            }
        }
        nodo.setIzq(construirDesdePreIn(preOrden, inOrden, limiteIzq, k - 1)); //Constructores para subárbol izquierdo usando la izquierda de K en inOrden
        nodo.setDer(construirDesdePreIn(preOrden, inOrden, k + 1, limiteDer)); //Constructores para subárbol derecho usando la derecha de K en inOrden
        /*
        K toma el valor de la posicion de la raiz en preOrden (1) => K = 1, para marcar el limite entre los subárboles
        izquierdo y derecho, se ubica a k = 1 = A en el recorrido inOrden, una vez ubicado, todo lo que esta a la izquierda
        de A será el subárbol izquierdo y asi respectivamente con el derecho, entonces esto nos lleva a marcar un rango en el 
        inOrden en donde subárbol izquierdo va desde [LimiteIzq , K - 1] y el subárbol derecho va desde [K + 1 , LimiteDer] 
        */

        return nodo;
    }

}
