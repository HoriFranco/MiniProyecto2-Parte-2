/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package miniproyecto2_parte2;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author HoriAndréFrancoRuiz
 */
public class Miniproyecto2_parte2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner teclado = new Scanner(System.in);
        Arbolavl arbol = new Arbolavl();
        int opcion;
 
        do {
            System.out.println("\n========== ARBOL AVL ==========");
            System.out.println(" 1. Insertar nodo");
            System.out.println(" 2. Eliminar nodo");
            System.out.println(" 3. Buscar nodo (indica el nivel)");
            System.out.println(" 4. Mostrar arbol");
            System.out.println(" 5. Recorrido PreOrden");
            System.out.println(" 6. Recorrido InOrden");
            System.out.println(" 7. Recorrido PostOrden");
            System.out.println(" 8. Altura del arbol");
            System.out.println(" 9. Altura de un nodo especifico");
            System.out.println("10. Factor de equilibrio de un nodo");
            System.out.println("11. Verificar que el arbol esta balanceado");
            System.out.println("12. Cargar arbol desde archivo");
            System.out.println("13. Guardar recorridos en archivo");
            System.out.println(" 0. Salir");
            opcion = leerEntero(teclado, "Elige una opcion: ");
 
            switch (opcion) {
                case 1:
                    int valorNuevo = leerEntero(teclado, "Valor a insertar: ");
                    if (arbol.insertar(valorNuevo)) {
                        System.out.println("Se inserto el " + valorNuevo);
                        mostrarAvisos(arbol);
                        mostrarArbol(arbol);
                    } else {
                        System.out.println("El " + valorNuevo + " ya existe, no se inserto.");
                    }
                    break;
 
                case 2:
                    int valorParaEliminar = leerEntero(teclado, "Valor a eliminar: ");
                    if (arbol.eliminar(valorParaEliminar)) {
                        System.out.println("Se elimino el " + valorParaEliminar);
                        mostrarAvisos(arbol);
                        mostrarArbol(arbol);
                    } else {
                        System.out.println("El " + valorParaEliminar + " no existe en el arbol.");
                    }
                    break;
 
                case 3:
                    int valorBuscado = leerEntero(teclado, "Valor a buscar: ");
                    int nivel = arbol.buscarNivel(valorBuscado);
                    if (nivel == -1) {
                        System.out.println("El " + valorBuscado + " no se encontro.");
                    } else {
                        System.out.println("El " + valorBuscado + " esta en el nivel " + nivel
                                + " (la raiz es el nivel 0).");
                    }
                    break;
 
                case 4:
                    mostrarArbol(arbol);
                    break;
 
                case 5:
                    mostrarRecorrido("PreOrden", arbol.preOrden(), arbol);
                    break;
 
                case 6:
                    mostrarRecorrido("InOrden", arbol.inOrden(), arbol);
                    break;
 
                case 7:
                    mostrarRecorrido("PostOrden", arbol.postOrden(), arbol);
                    break;
 
                case 8:
                    if (arbol.estaVacio()) {
                        System.out.println("El arbol esta vacio.");
                    } else {
                        System.out.println("La altura del arbol es " + arbol.altura()
                                + " (una hoja mide 1).");
                    }
                    break;
 
                case 9:
                    int valorParaAltura = leerEntero(teclado, "Valor del nodo: ");
                    Nodo nodoParaAltura = arbol.buscarNodo(valorParaAltura);
                    if (nodoParaAltura == null) {
                        System.out.println("El " + valorParaAltura + " no existe en el arbol.");
                    } else {
                        System.out.println("La altura del nodo " + valorParaAltura + " es "
                                + arbol.alturaNodo(nodoParaAltura) + " (una hoja mide 1).");
                    }
                    break;
 
                case 10:
                    int valorParaFactor = leerEntero(teclado, "Valor del nodo: ");
                    Nodo nodoParaFactor = arbol.buscarNodo(valorParaFactor);
                    if (nodoParaFactor == null) {
                        System.out.println("El " + valorParaFactor + " no existe en el arbol.");
                    } else {
                        System.out.println("Altura izquierda = " + arbol.alturaNodo(nodoParaFactor.izquierdo)
                                + ", altura derecha = " + arbol.alturaNodo(nodoParaFactor.derecho));
                        System.out.println("Factor de equilibrio del " + valorParaFactor + " = "
                                + arbol.factorEquilibrio(nodoParaFactor));
                    }
                    break;
 
                case 11:
                    if (arbol.estaBalanceado()) {
                        System.out.println("Correcto: todos los nodos tienen factor de equilibrio entre -1 y 1.");
                    } else {
                        System.out.println("El arbol NO esta balanceado.");
                    }
                    break;
 
                case 12:
                    cargarArchivo(teclado, arbol);
                    break;
 
                case 13:
                    guardarResultados(teclado, arbol);
                    break;
 
                case 0:
                    System.out.println("Hasta luego.");
                    break;
 
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }
 
    
 
    private static void mostrarArbol(Arbolavl arbol) {
        if (arbol.estaVacio()) {
            System.out.println("El arbol esta vacio.");
            return;
        }
        System.out.println("\nArbol (raiz a la izquierda, /-- hijo mayor arriba, \\-- hijo menor abajo):\n");
        System.out.println(arbol.dibujar());
    }
 
    
    private static void mostrarAvisos(Arbolavl arbol) {
        String avisos = arbol.obtenerAvisos();
        if (avisos.isEmpty()) {
            System.out.println("No hizo falta rotar: el arbol sigue balanceado.");
        } else {
            System.out.println("El arbol se desbalanceo y se corrigio con:");
            System.out.print(avisos);
        }
    }
 
    private static void mostrarRecorrido(String nombre, String resultado, Arbolavl arbol) {
        if (arbol.estaVacio()) {
            System.out.println("El arbol esta vacio.");
        } else {
            System.out.println(nombre + ": " + resultado);
        }
    }
 
    private static int leerEntero(Scanner teclado, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("Eso no es un numero entero, intenta de nuevo.");
            }
        }
    }
 
    
    private static void cargarArchivo(Scanner teclado, Arbolavl arbol) {
        System.out.print("Nombre o ruta del archivo (ej. datos.txt): ");
        String ruta = teclado.nextLine().trim();
 
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append(",");
            }
 
            arbol.vaciar();                       // empezamos con un arbol nuevo
            for (String parte : contenido.toString().split(",")) {
                parte = parte.trim();
                if (parte.isEmpty()) {
                    continue;
                }
                try {
                    arbol.insertar(Integer.parseInt(parte));
                } catch (NumberFormatException error) {
                    System.out.println("Se ignoro \"" + parte + "\" porque no es un numero.");
                }
            }
            System.out.println("Arbol creado con " + arbol.contarNodos() + " nodos.");
            mostrarAvisos(arbol);
            mostrarArbol(arbol);
            System.out.println("Ahora puedes agregar mas con la opcion 1.");
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo. Revisa el nombre o la ruta.");
        }
    }
 
    
    private static void guardarResultados(Scanner teclado, Arbolavl arbol) {
        if (arbol.estaVacio()) {
            System.out.println("El arbol esta vacio, no hay nada que guardar.");
            return;
        }
        System.out.print("Nombre del archivo de salida (ej. resultados.txt): ");
        String ruta = teclado.nextLine().trim();
 
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ruta))) {
            escritor.println("Resultados de los recorridos del arbol AVL");
            escritor.println("PreOrden:  " + arbol.preOrden());
            escritor.println("InOrden:   " + arbol.inOrden());
            escritor.println("PostOrden: " + arbol.postOrden());
            System.out.println("Resultados guardados en " + ruta);
        } catch (IOException error) {
            System.out.println("No se pudo guardar el archivo.");
        }
    }
    
}
