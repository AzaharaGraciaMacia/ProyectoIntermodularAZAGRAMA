package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * La clase {@code Usuario} representa a un usuario "padre" en el sistema.
 * Contiene información personal del usuario.
 */
public class Usuario
{
    private String dni;
    private String nombre;
    private String email;
    private String contrasena;
    private TipoUsuario tipoUsuario;
    private Date fechaNacimiento;
    private int idUsuario;
    private Timestamp fechaRegistro;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-5s %-30s %-30s %-25s %-25s %-20s %-12s %-50s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID","Nombre", "Email", "Fecha de Nacimiento", "Tipo de Usuario","Contraseña", "DNI", "Fecha de Registro" +
                    "\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code Usuario}.
     * Inicializa el dni, nombre, email, la contraseña, el tipo de usuario, la fecha de nacimiento, el ID del usuario y la fecha de registro..
     *
     * @param dni el DNI de usuario, parámetro único.
     * @param nombre el nombre completo del usuario
     * @param email el email del usuario
     * @param contrasena la contraseña del usuario
     * @param tipoUsuario el tipo de usuario, dentro del enum {@link TipoUsuario}
     * @param fechaNacimiento fecha de nacimiento del usuario
     * @param idUsuario código único del usuario, autogenerado por la BD
     * @param fechaRegistro fecha de registro del usuario, autogenerado en forma de Timestamp por la BD
     */
    public Usuario(String dni, String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario,  Timestamp fechaRegistro) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.idUsuario = idUsuario;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Obtiene el DNI del usuario
     *
     * @return texto con DNI
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del usuario
     *
     * @param dni texto con el nuevo DNI
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre completo del usuario
     *
     * @return texto con el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario
     *
     * @param nombre texto con el nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de usuario
     *
     * @return Enum {@link TipoUsuario} con el tipo de usuario
     */
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Establece el tipo de usuario
     *
     * @param tipoUsuario Enum {@link TipoUsuario} con el nuevo tipo de usuario
     */
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Obtiene el email del usuario
     *
     * @return texto con el email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario
     *
     * @param email texto con el nuevo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario
     *
     * @return texto con la contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del usuario
     *
     * @param contrasena texto con la nueva contraseña
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario
     *
     * @return fecha formato SQL de nacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario
     *
     * @param fechaNacimiento nueva fecha formato SQL de nacimiento
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el ID del usuario
     *
     * @return ID entero autogenerado
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del usuario
     *
     * @param idUsuario nuevo ID entero autogenerado
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene la fecha de registro del usuario
     *
     * @return Timestamp con la fecha de registro
     */
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro del usuario
     *
     * @param fechaRegistro Timestamp con la fecha de registro
     */
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Usuario}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa el usuario.
     */
    @Override
public String toString() {
    return String.format(FORMATO_IMPRESION, idUsuario, nombre, email, fechaNacimiento, tipoUsuario, contrasena, dni , fechaRegistro);
}
}