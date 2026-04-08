package model;

import java.util.Date;

public class Usuario
{
    private String nombre;
    private String email;
    private String contrasena;
    private TipoUsuario tipoUsuario;
    private Date fechaNacimiento;
    private int idUsuario;

    public Usuario(String nombre, String email, String contrasena, TipoUsuario tipoUsuario, Date fechaNacimiento, int idUsuario) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.idUsuario = idUsuario;
    }

    public Usuario(String nombre, String email, String contrasena, TipoUsuario tipoUsuario, int idUsuario) {
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.idUsuario = idUsuario;
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

}