package model;

import java.util.Date;

public class Administrador extends Usuario{
    private String telefono;

    public Administrador(String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario, String telefono) {
        super(nombre, email, contrasena, tipoUsuario, fechaNacimiento, idUsuario);
        this.telefono = telefono;
    }

    public Administrador(String nombre, String email, String contrasena, TipoUsuario tipoUsuario, int idUsuario, String telefono) {
        super(nombre, email, contrasena, tipoUsuario, idUsuario);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}