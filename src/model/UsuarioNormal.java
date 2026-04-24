package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * La clase {@code UsuarioNormal} representa a un usuario de tipo normal en el sistema.
 * Contiene información personal del usuario normal y su perfil básico de usuario.
 */
public class UsuarioNormal extends Usuario {
    private String direccion;
    private String tlfMovil;
    private String fotografia;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-5s %-30s %-30s %-25s %-20s %-12s %-25s %-40s %-15s %-15s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Dirección", "Teléfono Móvil", "Fotografía" +
                    "\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code Administrador}.
     * Inicializa la dirección, teléfono móvil y fotografía, recibiendo de la súperclase el resto de parámetros.
     *
     * @param dni de {@link Usuario}
     * @param nombre de {@link Usuario}
     * @param email de {@link Usuario}
     * @param contrasena de {@link Usuario}
     * @param tipoUsuario de {@link Usuario}
     * @param fechaNacimiento de {@link Usuario}
     * @param idUsuario de {@link Usuario}
     * @param fechaRegistro de {@link Usuario}
     * @param direccion dirección física del usuario normal(puede ser {@code null})
     * @param tlfMovil teléfono móvil del usuario normal (puede ser {@code null})
     * @param fotografia código o link de la fotografía del usuario normal (puede ser {@code null})
     */
    public UsuarioNormal(String dni, String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario, Timestamp fechaRegistro, String direccion, String tlfMovil, String fotografia) {
        super(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, idUsuario, fechaRegistro);
        this.direccion = direccion;
        this.tlfMovil = tlfMovil;
        this.fotografia = fotografia;
    }

    /**
     * Obtiene la dirección del usuario normal
     *
     * @return texto con la dirección
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del usuario normal
     *
     * @param direccion texto con la nueva dirección
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la dirección del usuario normal
     *
     * @return texto con la dirección
     */
    public String getTlfMovil() {
        return tlfMovil;
    }

    /**
     * Establece el teléfono móvil del usuario normal
     *
     * @param tlfMovil texto con el nuevo teléfono móvil
     */
    public void setTlfMovil(String tlfMovil) {
        this.tlfMovil = tlfMovil;
    }

    /**
     * Obtiene la fotografía del usuario normal
     *
     * @return texto con la fotografía
     */
    public String getFotografia() {
        return fotografia;
    }

    /**
     * Establece la fotografía del usuario normal
     *
     * @param fotografia texto con la nueva fotografía
     */
    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code UsuarioNormal}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa el usuario normal.
     */
    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, super.getIdUsuario(), super.getNombre(), super.getEmail(), super.getFechaNacimiento(), super.getContrasena(), super.getDni(), super.getFechaRegistro(), direccion, tlfMovil, fotografia);
    }
}