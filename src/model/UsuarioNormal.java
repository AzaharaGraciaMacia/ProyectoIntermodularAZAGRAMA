package model;

import java.util.Date;

public class UsuarioNormal extends Usuario {
    private String direccion;
    private String tlfMovil;
    private String fotografia;

    public UsuarioNormal(String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario, Builder builder) {
        super(nombre, email, contrasena, tipoUsuario, fechaNacimiento, idUsuario);
        this.direccion = builder.direccion;
        this.tlfMovil = builder.tlfMovil;
        this.fotografia = builder.fotografia;
    }

    public UsuarioNormal(String nombre, String email, String contrasena, TipoUsuario tipoUsuario, int idUsuario, Builder builder) {
        super(nombre, email, contrasena, tipoUsuario, idUsuario);
        this.direccion = builder.direccion;
        this.tlfMovil = builder.tlfMovil;
        this.fotografia = builder.fotografia;
    }
    public static class Builder {
        private String direccion;
        private String tlfMovil;
        private String fotografia;
    }
    public Builder direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }
    public Builder tlfMovil(String tlfMovil) {
        this.tlfMovil = tlfMovil;
        return this;
    }
    public Builder fotografia(String fotografia) {
        this.fotografia = fotografia;
        return this;
    }

    public UsuarioNormal build() {
        return new UsuarioNormal(this);
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