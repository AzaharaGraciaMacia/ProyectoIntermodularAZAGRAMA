package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * La clase {@code Administrador} representa a un usuario de tipo administrador en el sistema.
 * Contiene información personal del administrador y su perfil básico de usuario.
 */
public class Administrador extends Usuario{
    private String telefono;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-5s %-30s %-30s %-25s %-20s %-12s %-25s %-12s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Teléfono de Guardia" +
                    "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code Administrador}.
     * Inicializa el teléfono de guardia y recibe de la súperclase el resto de parámetros.
     *
     * @param dni de {@link Usuario}
     * @param nombre de {@link Usuario}
     * @param email de {@link Usuario}
     * @param contrasena de {@link Usuario}
     * @param tipoUsuario de {@link Usuario}
     * @param fechaNacimiento de {@link Usuario}
     * @param idUsuario de {@link Usuario}
     * @param fechaRegistro de {@link Usuario}
     * @param telefono teléfono de guardia del administrador
     */
    public Administrador(String dni, String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario, Timestamp fechaRegistro, String telefono) {
        super(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento,  idUsuario, fechaRegistro);
        this.telefono = telefono;
    }

    /**
     * Obtiene el teléfono de guardia del usuario administrador
     *
     * @return texto con el número teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de guardia
     *
     * @param telefono el nuevo teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Administrador}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa el administrador.
     */
    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, super.getIdUsuario(), super.getNombre(), super.getEmail(), super.getFechaNacimiento(), super.getContrasena(), super.getDni(), super.getFechaRegistro(), telefono);
    }
}