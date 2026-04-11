package model;

import java.sql.Date;

public class Administrador extends Usuario{
    private String telefono;

    public Administrador(String dni, String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, String telefono) {
        super(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}