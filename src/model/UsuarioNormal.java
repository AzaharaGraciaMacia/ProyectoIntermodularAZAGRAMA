package model;

import java.sql.Date;
import java.sql.Timestamp;

public class UsuarioNormal extends Usuario {
    private String direccion;
    private String tlfMovil;
    private String fotografia;

    public UsuarioNormal(String dni, String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario, Timestamp fechaRegistro, String direccion, String tlfMovil, String fotografia) {
        super(dni, nombre, email, contrasena, tipoUsuario, fechaNacimiento, idUsuario, fechaRegistro);
        this.direccion = direccion;
        this.tlfMovil = tlfMovil;
        this.fotografia = fotografia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTlfMovil() {
        return tlfMovil;
    }

    public void setTlfMovil(String tlfMovil) {
        this.tlfMovil = tlfMovil;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }
}