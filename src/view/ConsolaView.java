package view;

import java.util.Scanner;

/**
 * Utilidad de entrada y salida por consola.
 *
 * <p>Centraliza la lectura segura de datos desde teclado y la impresión de mensajes,
 * de modo que la vista y los controladores no tengan que repetir lógica de validación.</p>
 */
public class ConsolaView {

    /**
     * Escáner compartido para leer datos del teclado.
     */
    static private final Scanner scanner = new Scanner(System.in);

    /**
     * Muestra un mensaje y lee una línea de texto.
     *
     * @param mensaje texto que se mostrará al usuario
     * @return texto introducido por el usuario
     */
    static public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un número entero desde consola con validación.
     *
     * @param mensaje texto que se mostrará al usuario
     * @return número entero válido
     */
    static public int leerEntero(String mensaje) {
        boolean valido = false;
        int numero = 0;

        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= 0) {
                valido = true;}
                else {
                    System.out.println("El número debe ser mayor o igual a 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debes introducir un número entero.");
            }
        }
        return numero;
    }

    /**
     * Lee una opción de sí o no.
     *
     * @param mensaje pregunta a mostrar
     * @return {@code true} si la respuesta es s, si o sí; {@code false} en otro caso
     */
    static public boolean leerSiNo(String mensaje) {
        String respuesta = leerTexto(mensaje + " (s/n): ");
        return respuesta.equalsIgnoreCase("s")
                || respuesta.equalsIgnoreCase("si")
                || respuesta.equalsIgnoreCase("sí");
    }

    /**
     * Imprime un mensaje con salto de línea.
     *
     * @param mensaje texto a mostrar
     */
    static public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Imprime una línea vacía.
     */
    static public void saltoLinea() {
        System.out.println();
    }
    /**
     * Pausa la ejecución hasta que el usuario pulse Enter.
     *
     * @param mensaje mensaje que se mostrará antes de esperar
     */
    static public void pausar(String mensaje) {
        System.out.println();
        System.out.print(mensaje);
        scanner.nextLine();
    }
    /**
     * Muestra una cabecera visual en consola.
     *
     * @param titulo título a mostrar
     */
    static public void mostrarTitulo(String titulo) {
        int ancho = 48; // ancho total de la línea
        String linea = "=".repeat(ancho);

        int espacios = (ancho - titulo.length()) / 2;
        String tituloCentrado = " ".repeat(Math.max(0, espacios)) + titulo;

        System.out.println();
        System.out.println(linea);
        System.out.println(tituloCentrado);
        System.out.println(linea);
    }

    /**
     * Limpia la pantalla
     */
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}