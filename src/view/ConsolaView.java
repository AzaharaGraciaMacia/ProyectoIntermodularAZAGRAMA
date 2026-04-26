package view;

import model.DiaSemana;
import model.TipoUsuario;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
     * @return número entero válido introducido por el usuario
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
     * Lee un número con decimales desde consola con validación.
     *
     * @param mensaje texto que se mostrará al usuario
     * @return número con decimales válido introducido por el usuario
     */
    static public double leerDouble(String mensaje) {
        boolean valido = false;
        double numero = 0.0;

        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = Double.parseDouble(scanner.nextLine().trim());
                if (numero >= 0.0) {
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
     * Valida un DNI leído previamente
     *
     * @param dni DNI a comprobar
     * @return true si el DNI es válido
     */
    static public boolean comprobarDNI(String dni) {
        try{
        String LETRAS_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";

        if (dni == null || dni.isBlank()) return false;

        String d = dni.toUpperCase();

        if (!d.matches("[0-9]{8}[A-Z]")) return false;

        int numeroDNI = Integer.parseInt(d.substring(0, 8));
        char letraEsperada = LETRAS_CONTROL.charAt(numeroDNI % 23);

        return d.charAt(8) == letraEsperada;
        }
        catch (IllegalArgumentException e) {
            System.out.println("Error: debes introducir un dni válido." + e.getMessage());
        }
        return false;
    }

    /**
     * Valida una fecha leída previamente y le da formato para MySQL/MariaDB
     *
     * @param fecha fecha que queremos validar
     * @return true si la fecha es válida
     */
    static public Date validarYFormatearFecha(String fecha) {
        if (fecha == null || fecha.isBlank()) return null;

        DateTimeFormatter fechaIntroducida = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fechaFormateada = LocalDate.parse(fecha.trim(), fechaIntroducida);
            return Date.valueOf(fechaFormateada);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Formateador para horas en formato de 24 horas (HH:mm).
     *
     * <p>
     * Se utiliza para parsear y formatear horas de entrada
     * del usuario. Ejemplo de formato aceptado: {@code 08:30}, {@code 23:45}.
     * </p>
     */
    private static final DateTimeFormatter FORMATO_ENTRADA_HORA = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Valida una hora leída previamente y le da formato para MySQL/MariaDB
     *
     * @param hora hora que queremos validar
     * @return true si la hora es válida
     */
    static public Time validarYFormatearHora(String hora) {
        if (hora == null || hora.isBlank()) return null;

        try {
            LocalTime localTime = LocalTime.parse(hora.trim(), FORMATO_ENTRADA_HORA);
            return Time.valueOf(localTime);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Comprueba si se ha introducido la palabra "omitir" leída previamente (usado para las columnas que pueden ser null)
     *
     * @param respuesta texto leído previamente
     * @return true si se ha introducido "omitir"
     */
    static public boolean OmitirRespuesta(String respuesta) {
        boolean omitirRespuesta = false;
        if (respuesta.equals("OMITIR")) {
            omitirRespuesta = true;
        }
        return omitirRespuesta;
    }

    /**
     * Valida si el texto leído previamente encaja en {@link TipoUsuario} y lo convierte de String a Enum
     *
     * @param respuesta texto proporcionado leído previamente
     * @return objeto tipoUsuario
     */
    static public TipoUsuario ComprobarTipoUsuario(String respuesta) {
        if (respuesta == null || respuesta.isBlank()) return null;

        for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
            if (tipoUsuario.name().equalsIgnoreCase(respuesta.trim())) {
                return tipoUsuario;
            }
        }
        return null;
    }

    /**
     * Valida si el texto leído previamente encaja en {@link DiaSemana} y lo convierte de String a Enum
     *
     * @param respuesta texto proporcionado leído previamente
     * @return objeto diaSemana
     */
    static public DiaSemana ComprobarDiaSemana(String respuesta) {
        if (respuesta == null || respuesta.isBlank()) return null;

        for (DiaSemana diaSemana : DiaSemana.values()) {
            if (diaSemana.name().equalsIgnoreCase(respuesta.trim())) {
                return diaSemana;
            }
        }
        return null;
    }

    /**
     * Patrón validador de email
     */
    private static final Pattern PATRON_EMAIL = Pattern.compile(
            "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"
    );

    /**
     * Valida el formato de email del String leído previamente
     *
     * @param email texto proporcionado leído previamente
     * @return true si el email es válido
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return PATRON_EMAIL.matcher(email.trim()).matches();
    }

    /**
     * Imprime un mensaje en consola
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
        int ancho = 70; // ancho total de la línea
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