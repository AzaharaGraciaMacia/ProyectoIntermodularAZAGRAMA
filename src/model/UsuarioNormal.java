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

    @Override
    public String toString() {
        return String.format("%-5s %-30s %-30s %-25s %-30s %-12s %-25s %-50s %-15s %-15s\n", super.getIdUsuario(), super.getNombre(), super.getEmail(), super.getFechaNacimiento(), super.getContrasena(), super.getDni(), super.getFechaRegistro(), direccion, tlfMovil, fotografia);
    }
}