package arbolavl;

/**
 *
 * @author jcarrenoe
 */
public class Arbol {
    private Nodo raiz;
    
    public Arbol(){
        this.raiz = null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
    
    public int alturaNodo (Nodo nodo){
        if (nodo == null){
            return 0;
        }
        return nodo.getAlturaNodo();
    }
    
    public int factorDeEquilibrio(Nodo nodo){
        if (nodo == null){
            return 0;
        }
        return alturaNodo(nodo.getIzquierdo())-alturaNodo(nodo.getDerecho());
    }
    
    public Nodo rotacionDerecha (Nodo y){
        Nodo x = y.getIzquierdo();
        Nodo temp = x.getDerecho();
        x.setDerecho(y);
        y.setIzquierdo(temp);
        
        y.setAlturaNodo(Math.max(alturaNodo(y.getIzquierdo()),
               alturaNodo(y.getDerecho())) + 1); 
        
        x.setAlturaNodo(Math.max(alturaNodo(x.getIzquierdo()),
                alturaNodo(x.getDerecho())) + 1); 
        
        return x;
    }
    
    public Nodo rotacionIzquierda (Nodo y) {
        Nodo x = y.getDerecho();
        Nodo temp = x.getIzquierdo();
        x.setIzquierdo(y);
        y.setDerecho(temp);
        
        y.setAlturaNodo(Math.max(alturaNodo(y.getIzquierdo()),
               alturaNodo(y.getDerecho())) + 1); 
        
        x.setAlturaNodo(Math.max(alturaNodo(x.getIzquierdo()),
                alturaNodo(x.getDerecho())) + 1); 
        
        return x;
        
    }
    
    public Nodo insertar (Nodo nodo, int valor){
        if (nodo == null){
            Nodo nuevoNodo = new Nodo(valor);
            return nuevoNodo;
        }
        if (valor < nodo.getDato()){
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), valor));
        } else if (valor > nodo.getDato()) {
            nodo.setDerecho(insertar(nodo.getDerecho(), valor));
        } else {
            return nodo;
        }
        nodo.setAlturaNodo(Math.max(alturaNodo(nodo.getIzquierdo()),
                alturaNodo(nodo.getDerecho())) + 1);
        
        int fe = factorDeEquilibrio(nodo);
        
        int a = nodo.getIzquierdo() != null? nodo.getIzquierdo().getDato(): 0;
        
        int b = nodo.getDerecho()!= null? nodo.getDerecho().getDato(): 0;
     
        if (fe > 1 && valor < a){
            System.out.println("Rotación Der");
            return rotacionDerecha(nodo);
        } 
        if (fe < -1 && valor > b){
            System.out.println("Rotación Izq");
            return rotacionIzquierda(nodo);
        } 
        if (fe > 1 && factorDeEquilibrio(nodo.getIzquierdo()) < 0) {
            System.out.println("Se realizó rotación doble de izquierda a derecha ");
            nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            return rotacionDerecha(nodo);
        }

        if (fe < -1 && factorDeEquilibrio(nodo.getDerecho()) > 0) {
            System.out.println("Se realizó rotación doble de derecha a izquierda ");
            nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }
    public void insertar(int valor){
        this.raiz = insertar(raiz, valor);
    }
    public int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho())) + 1;
    } 
    public static int getcol(int h) {
        if (h == 1) {
            return 1;
        }
        return getcol(h - 1) + getcol(h - 1) + 1;
    }
    public static void printTree(int[][] M, Nodo root, int col, int row, int height) {
        if (root == null) {
            return;
        }
        M[row][col] = root.getDato();
        printTree(M, root.getIzquierdo(), col - (int) Math.pow(2, height - 2), row + 1, height - 1);
        printTree(M, root.getDerecho(), col + (int) Math.pow(2, height - 2), row + 1, height - 1);
    }
    public void TreePrinter() {
        int h = altura(this.raiz);
        int col = getcol(h);
        int[][] M = new int[h][col];
        printTree(M, this.raiz, col / 2, 0, h);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < col; j++) {
                if (M[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(M[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
    public void imprimir(Nodo nodo, int nivel) {
        if (nodo != null) {
            this.imprimir(nodo.getDerecho(), nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("   ");
            }
            System.out.println(nodo.getDato());
            this.imprimir(nodo.getIzquierdo(), nivel + 1);
        }
    }
    
    public Nodo delete(Nodo root, int valor) {
        if (root == null) {
            return root;
        }

        if (valor < root.getDato()) {
            root.setIzquierdo(delete(root.getIzquierdo(), valor));
        } else if (valor > root.getDato()) {
            root.setDerecho(delete(root.getDerecho(), valor));
        } else {
            // Caso 1: Nodo con un solo hijo o sin hijos
            if (root.getIzquierdo() == null) {
                return root.getDerecho();
            } else if (root.getDerecho() == null) {
                return root.getIzquierdo();
            }

            // Caso 2: Nodo con dos hijos
            root.setDato(minValue(root.getDerecho()));
            root.setDerecho(delete(root.getDerecho(), root.getDato()));
        }

        // Si el árbol tiene solo un nodo, se retorna
        if (root == null) {
            return root;
        }


        root.setAlturaNodo(Math.max(alturaNodo(root.getIzquierdo()), alturaNodo(root.getDerecho())) + 1);
        int fe = factorDeEquilibrio(root);

        if (fe > 1 && factorDeEquilibrio(root.getIzquierdo()) >= 0) {
            System.out.println("Se realizó rotación simple a la derecha ");
            return rotacionDerecha(root);
        }


        if (fe < -1 && factorDeEquilibrio(root.getDerecho()) <= 0) {
            System.out.println("Se realizó rotación simple a la izquierda ");
            return rotacionIzquierda(root);
        }

        if (fe > 1 && factorDeEquilibrio(root.getIzquierdo()) < 0) {
            System.out.println("Se realizó rotación doble de izquierda a derecha ");
            root.setIzquierdo(rotacionIzquierda(root.getIzquierdo()));
            return rotacionDerecha(root);
        }

        if (fe < -1 && factorDeEquilibrio(root.getDerecho()) > 0) {
            System.out.println("Se realizó rotación doble de derecha a izquierda ");
            root.setDerecho(rotacionDerecha(root.getDerecho()));
            return rotacionIzquierda(root);
        }

        return root;
    }
    public void eliminar(int valor) {
        this.raiz = delete(this.raiz, valor);
    }
    public int minValue(Nodo node) {
        int minValue = node.getDato();
        while (node.getIzquierdo() != null ) {
            minValue = node.getIzquierdo().getDato();
            node = node.getIzquierdo();
        }
        return minValue;
    }
}
