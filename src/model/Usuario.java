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
     *
     * @param dni
     * @param nombre
     * @param email
     * @param contrasena
     * @param tipoUsuario
     * @param fechaNacimiento
     * @param idUsuario
     * @param fechaRegistro
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

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