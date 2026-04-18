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


public class ConsolaView {

    static private final Scanner scanner = new Scanner(System.in);

    static public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }


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

    static public boolean comprobarDNI(String dni) {

        String LETRAS_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";

        if (dni == null || dni.isBlank()) return false;

        String d = dni.toUpperCase();

        if (!d.matches("[0-9]{8}[A-Z]")) return false;

        int numeroDNI = Integer.parseInt(d.substring(0, 8));
        char letraEsperada = LETRAS_CONTROL.charAt(numeroDNI % 23);

        return d.charAt(8) == letraEsperada;
    }

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

    private static final DateTimeFormatter FORMATO_ENTRADA_HORA = DateTimeFormatter.ofPattern("HH:mm");

    static public Time validarYFormatearHora(String hora) {
        if (hora == null || hora.isBlank()) return null;

        try {
            LocalTime localTime = LocalTime.parse(hora.trim(), FORMATO_ENTRADA_HORA);
            return Time.valueOf(localTime);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    static public boolean OmitirRespuesta(String respuesta) {
        boolean omitirRespuesta = false;
        if (respuesta.equals("OMITIR")) {
            omitirRespuesta = true;
        }
        return omitirRespuesta;
    }

    static public TipoUsuario ComprobarTipoUsuario(String respuesta) {
        if (respuesta == null || respuesta.isBlank()) return null;

        for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
            if (tipoUsuario.name().equalsIgnoreCase(respuesta.trim())) {
                return tipoUsuario;
            }
        }
        return null;
    }

    static public DiaSemana ComprobarDiaSemana(String respuesta) {
        if (respuesta == null || respuesta.isBlank()) return null;

        for (DiaSemana diaSemana : DiaSemana.values()) {
            if (diaSemana.name().equalsIgnoreCase(respuesta.trim())) {
                return diaSemana;
            }
        }
        return null;
    }

    private static final Pattern PATRON_EMAIL = Pattern.compile(
            "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"
    ); // Patrón validador de email

    public static boolean validarEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return PATRON_EMAIL.matcher(email.trim()).matches();
    }

    static public void mostrar(String mensaje) {
        System.out.println(mensaje);
    }


    static public void saltoLinea() {
        System.out.println();
    }

    static public void pausar(String mensaje) {
        System.out.println();
        System.out.print(mensaje);
        scanner.nextLine();
    }

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

    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}