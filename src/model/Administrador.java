package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Administrador extends Usuario{
    private String telefono;

    public static final String FORMATO_IMPRESION="%-5s %-30s %-30s %-25s %-20s %-12s %-25s %-12s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID","Nombre", "Email", "Fecha de Nacimiento", "Contraseña", "DNI", "Fecha de Registro", "Teléfono de Guardia" +
                    "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    public Administrador(String dni, String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario, Timestamp fechaRegistro, String telefono) {
        super(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento,  idUsuario, fechaRegistro);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, super.getIdUsuario(), super.getNombre(), super.getEmail(), super.getFechaNacimiento(), super.getContrasena(), super.getDni(), super.getFechaRegistro(), telefono);
    }
}