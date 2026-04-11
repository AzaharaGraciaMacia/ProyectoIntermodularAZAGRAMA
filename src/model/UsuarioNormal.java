package model;

import java.sql.Date;

public class UsuarioNormal extends Usuario {
    private String direccion;
    private String tlfMovil;
    private String fotografia;

    private UsuarioNormal(UsuarioNormalBuilder builder) {
        super(builder.dni, builder.nombre, builder.email, builder.contrasena,
                builder.tipoUsuario, builder.fechaNacimiento);
        this.direccion  = builder.direccion;
        this.tlfMovil   = builder.tlfMovil;
        this.fotografia = builder.fotografia;
    }

    public static class UsuarioNormalBuilder {

        private final String dni;
        private final String nombre;
        private final String email;
        private final String contrasena;
        private final TipoUsuario tipoUsuario;
        private final Date   fechaNacimiento;
        private String direccion;
        private String tlfMovil;
        private String fotografia;

        public UsuarioNormalBuilder(String dni, String nombre, String email, String contrasena,
                       TipoUsuario tipoUsuario, int idUsuario, Date fechaNacimiento) {
            this.dni          = dni;
            this.nombre       = nombre;
            this.email        = email;
            this.contrasena   = contrasena;
            this.tipoUsuario  = tipoUsuario;
            this.fechaNacimiento = fechaNacimiento;
        }

        public UsuarioNormalBuilder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }
        public UsuarioNormalBuilder tlfMovil(String tlfMovil) {
            this.tlfMovil = tlfMovil;
            return this;
        }
        public UsuarioNormalBuilder fotografia(String fotografia) {
            this.fotografia = fotografia;
            return this;
        }

        public UsuarioNormal build() {
            return new UsuarioNormal(this);
        }
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