/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miniproyecto2_parte2;

/**
 *
 * @author HoriAndréFrancoRuiz
 */
public class Arbolavl {
    private Nodo raiz;
    
    // Rotaciones
    
    private StringBuilder avisos = new StringBuilder();
    public Nodo getRaiz(){
        return raiz;
    }
    
    public boolean estaVacio(){
        return raiz == null;
    }
    
    public void vaciar(){
        raiz = null;
        avisos.setLength(0);
    }
    
    // Ultimas rotaciones
    public String obtenerAvisos(){
        String texto = avisos.toString();
        avisos.setLength(0);
        return texto;
    }
    
    // Calculo de la altura y el fe
    public int alturaNodo(Nodo nodo){
        if (nodo == null) {
            return 0;
        }
        int alturaInzquierda = alturaNodo(nodo.izquierdo);
        int alturaDerecha = alturaNodo(nodo.derecho);
        return 1 +Math.max(alturaInzquierda, alturaDerecha);
        
    }
    // La altura del arbol en general sera la misma que la de la raiz
    public int altura(){
        return alturaNodo(raiz);
        
    }
    // EL factor de equilibro es la resta
    public int factorEquilibrio(Nodo nodo){
        if (nodo == null) {
            return 0;    
        }
        int alturaIzquierda =alturaNodo(nodo.izquierdo);
        int alturaDerecha =alturaNodo(nodo.derecho);
        return alturaIzquierda - alturaDerecha;
        
    }
    
    // Rotaciones, cada nodo recibe el nodo ddesbalanceado y devuelve el nodo que queda en su lugar
    
    // 1. Rotacion simple a la DERECHA
    private Nodo rotacionSimpleDerecha(Nodo nodoDesbalanceado){
        Nodo hijoIzquierdo = nodoDesbalanceado.izquierdo;
        // el hijo derecho pasa a ser hijo izquierdo del desbalanceado
        nodoDesbalanceado.izquierdo = hijoIzquierdo.derecho;
        // el desbalanceado baja y queda a la derecha
        hijoIzquierdo.derecho = nodoDesbalanceado;
        // el hijo izq sube y ocupa el lugar del desbalanceado
        return hijoIzquierdo;
        
    }
    // 2. Rotacion simple izquierda
    
    private Nodo rotacionSimpleIzquierda(Nodo nodoDesbalanceado) {
        Nodo hijoDerecho = nodoDesbalanceado.derecho;
 
        // el hijo izquierdo del hijoDerecho pasa a ser hijo derecho del nodoDesbalanceado
        nodoDesbalanceado.derecho = hijoDerecho.izquierdo;
        // el nodoDesbalanceado baja y queda a la izquierda del hijoDerecho
        hijoDerecho.izquierdo = nodoDesbalanceado;
        // el hijoDerecho sube y ocupa el lugar del nodoDesbalanceado
        return hijoDerecho;
    }
    
    
    
    // Rotacion doble Izquierda-derecha
    private Nodo rotacionDobleIzquierdaDerecha(Nodo nodoDesbalanceado) {
        nodoDesbalanceado.izquierdo = rotacionSimpleIzquierda(nodoDesbalanceado.izquierdo);
        return rotacionSimpleDerecha(nodoDesbalanceado);
    }
    // Rotacion doble Derecha-Izquierda
    private Nodo rotacionDobleDerechaIzquierda(Nodo nodoDesbalanceado){
        nodoDesbalanceado.derecho = rotacionSimpleDerecha(nodoDesbalanceado.derecho);
        return rotacionDobleDerechaIzquierda(nodoDesbalanceado);
    }
    
    // Hacemos el Balance
    private Nodo balancear(Nodo nodo) {
        int factorDelNodo = factorEquilibrio(nodo);
 
        if (factorDelNodo > 1) {                          // pesado hacia la IZQUIERDA
            int factorDelHijo = factorEquilibrio(nodo.izquierdo);
            if (factorDelHijo < 0) {                      // el hijo izquierdo pesa a la derecha
                avisos.append("  - Rotacion doble izquierda-derecha sobre el nodo ")
                      .append(nodo.valor).append(" (factor de equilibrio = ")
                      .append(factorDelNodo).append(")\n");
                return rotacionDobleIzquierdaDerecha(nodo);
            }
            avisos.append("  - Rotacion simple a la derecha sobre el nodo ")
                  .append(nodo.valor).append(" (factor de equilibrio = ")
                  .append(factorDelNodo).append(")\n");
            return rotacionSimpleDerecha(nodo);
        }
 
        if (factorDelNodo < -1) {                         // pesado hacia la DERECHA
            int factorDelHijo = factorEquilibrio(nodo.derecho);
            if (factorDelHijo > 0) {                      // el hijo derecho pesa a la izquierda
                avisos.append("Rotacion doble derecha-izquierda sobre el nodo ")
                      .append(nodo.valor).append(" (factor de equilibrio = ")
                      .append(factorDelNodo).append(")\n");
                return rotacionDobleDerechaIzquierda(nodo);
            }
            avisos.append("Rotacion simple a la izquierda sobre el nodo ")
                  .append(nodo.valor).append(" (factor de equilibrio = ")
                  .append(factorDelNodo).append(")\n");
            return rotacionSimpleIzquierda(nodo);
        }
 
        return nodo;                                      
    }
    
    // Funcion Insertar 
    public boolean insertar(int valor) {
        if (buscarNodo(valor) != null) {
            return false;
        }
        raiz = insertarRecursivo(raiz, valor);
        return true;
    }
 
    private Nodo insertarRecursivo(Nodo actual, int valor) {
        if (actual == null) {
            return new Nodo(valor);
        }
        if (valor < actual.valor) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, valor);
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, valor);
        }
        // Al regresar de la recursion (de abajo hacia arriba) se revisa cada nodo
        return balancear(actual);
    }
    
    // Eliminar
    
    public boolean eliminar(int valor) {
        if (buscarNodo(valor) == null) {
            return false;
        }
        raiz = eliminarRecursivo(raiz, valor);
        return true;
    }
 
    private Nodo eliminarRecursivo(Nodo actual, int valor) {
        if (actual == null) {
            return null;
        }
        if (valor < actual.valor) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, valor);
        } else if (valor > actual.valor) {
            actual.derecho = eliminarRecursivo(actual.derecho, valor);
        } else {
            // Nodo encontrado. Mismos 3 casos que en el BST:
            if (actual.izquierdo == null) {
                return actual.derecho;       // hoja o un solo hijo
            }
            if (actual.derecho == null) {
                return actual.izquierdo;
            }
            // Dos hijos: se copia el menor del subarbol derecho y se elimina ese menor
            Nodo nodoMenor = actual.derecho;
            while (nodoMenor.izquierdo != null) {
                nodoMenor = nodoMenor.izquierdo;
            }
            actual.valor = nodoMenor.valor;
            actual.derecho = eliminarRecursivo(actual.derecho, nodoMenor.valor);
        }
        // Despues de eliminar, tambien se revisa el balance de cada nodo del camino
        return balancear(actual);
    }
    
    // Buscar 
    public Nodo buscarNodo(int valor) {
        Nodo actual = raiz;
        while (actual != null) {
            if (valor == actual.valor) {
                return actual;
            }
            if (valor < actual.valor) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
        }
        return null;
    }
 
    /** Devuelve el nivel donde esta el valor (raiz = nivel 0), o -1 si no existe. */
    public int buscarNivel(int valor) {
        Nodo actual = raiz;
        int nivel = 0;
        while (actual != null) {
            if (valor == actual.valor) {
                return nivel;
            }
            if (valor < actual.valor) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
            nivel++;
        }
        return -1;
    }
    
    // Recorridos 
     /** PreOrden: Raiz, Izquierdo, Derecho */
    public String preOrden() {
        StringBuilder texto = new StringBuilder();
        preOrdenRecursivo(raiz, texto);
        return texto.toString().trim();
    }
 
    private void preOrdenRecursivo(Nodo nodo, StringBuilder texto) {
        if (nodo == null) {
            return;
        }
        texto.append(nodo.valor).append(" ");
        preOrdenRecursivo(nodo.izquierdo, texto);
        preOrdenRecursivo(nodo.derecho, texto);
    }
 
    /** InOrden: Izquierdo, Raiz, Derecho (sale ordenado) */
    public String inOrden() {
        StringBuilder texto = new StringBuilder();
        inOrdenRecursivo(raiz, texto);
        return texto.toString().trim();
    }
 
    private void inOrdenRecursivo(Nodo nodo, StringBuilder texto) {
        if (nodo == null) {
            return;
        }
        inOrdenRecursivo(nodo.izquierdo, texto);
        texto.append(nodo.valor).append(" ");
        inOrdenRecursivo(nodo.derecho, texto);
    }
 
    /** PostOrden: Izquierdo, Derecho, Raiz */
    public String postOrden() {
        StringBuilder texto = new StringBuilder();
        postOrdenRecursivo(raiz, texto);
        return texto.toString().trim();
    }
 
    private void postOrdenRecursivo(Nodo nodo, StringBuilder texto) {
        if (nodo == null) {
            return;
        }
        postOrdenRecursivo(nodo.izquierdo, texto);
        postOrdenRecursivo(nodo.derecho, texto);
        texto.append(nodo.valor).append(" ");
    }
    
    // DIBUJO
     public String dibujar() {
        StringBuilder texto = new StringBuilder();
        dibujarRecursivo(raiz, "", 0, texto);
        return texto.toString();
    }
 
    // tipoDeHijo: 0 = raiz, 1 = hijo derecho (va arriba), 2 = hijo izquierdo (va abajo)
    private void dibujarRecursivo(Nodo nodo, String prefijo, int tipoDeHijo, StringBuilder texto) {
        if (nodo == null) {
            return;
        }
        // Lo que hay arriba del nodo lleva "|" solo si el nodo es hijo izquierdo
        String prefijoArriba = (tipoDeHijo == 2) ? "|   " : "    ";
        // Lo que hay abajo del nodo lleva "|" solo si el nodo es hijo derecho
        String prefijoAbajo = (tipoDeHijo == 1) ? "|   " : "    ";
 
        dibujarRecursivo(nodo.derecho, prefijo + prefijoArriba, 1, texto);
 
        String conector = "";
        if (tipoDeHijo == 1) {
            conector = "/-- ";
        } else if (tipoDeHijo == 2) {
            conector = "\\-- ";
        }
        texto.append(prefijo).append(conector).append(nodo.valor)
             .append("  (FE ").append(factorEquilibrio(nodo)).append(")\n");
 
        dibujarRecursivo(nodo.izquierdo, prefijo + prefijoAbajo, 2, texto);
    }
    
    //Contadores
    public int contarNodos() {
        return contarRecursivo(raiz);
    }
 
    private int contarRecursivo(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarRecursivo(nodo.izquierdo) + contarRecursivo(nodo.derecho);
    }
 
    /** Verifica que TODOS los nodos tengan factor de equilibrio entre -1 y 1. */
    public boolean estaBalanceado() {
        return balanceadoRecursivo(raiz);
    }
 
    private boolean balanceadoRecursivo(Nodo nodo) {
        if (nodo == null) {
            return true;
        }
        int factor = factorEquilibrio(nodo);
        if (factor < -1 || factor > 1) {
            return false;
        }
        return balanceadoRecursivo(nodo.izquierdo) && balanceadoRecursivo(nodo.derecho);
    }
    
    
    
}
