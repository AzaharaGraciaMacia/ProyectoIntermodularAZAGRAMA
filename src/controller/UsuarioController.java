package controller;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import dao.UsuarioNormalDAO;
import model.Administrador;
import model.TipoUsuario;
import model.Usuario;
import model.UsuarioNormal;
import view.UsuarioView;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private UsuarioNormalDAO usuarioNormalDAO;
    private AdministradorDAO administradorDAO;
    private UsuarioView usuarioView;

    public UsuarioController() {
        // Crear conexión a la base de datos
        usuarioDAO = UsuarioDAO.getInstance();
        usuarioNormalDAO = UsuarioNormalDAO.getInstance();
        administradorDAO = AdministradorDAO.getInstance();
        usuarioView = new UsuarioView();
    }

    public void mostrarTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
            usuarioView.mostrarUsuarios(usuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mostrarTodosLosUsuariosNormales() {
        try {
            List<UsuarioNormal> usuariosNormales = usuarioNormalDAO.getAllUsuariosNormales();
            usuarioView.mostrarUsuariosNormales(usuariosNormales);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mostrarTodosLosAdministradores() {
        try {
            List<Administrador> administradores = administradorDAO.getAllAdministradores();
            usuarioView.mostrarAdministradores(administradores);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mostrarUsuarioDNI() {
        try {
            String dni = usuarioView.obtenerDni();
            Usuario usuario = usuarioDAO.getUsuarioByDni(dni);
            usuarioView.mostrarUsuario(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mostrarUsuarioNormalDNI() {
        try {
            String dni = usuarioView.obtenerDni();
            UsuarioNormal usuarioNormal = usuarioNormalDAO.getUsuarioNormalByDni(dni);
            usuarioView.mostrarUsuarioNormal(usuarioNormal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mostrarAdministradorDNI() {
        try {
            String dni = usuarioView.obtenerDni();
            Administrador administrador = administradorDAO.getAdministradorByDni(dni);
            usuarioView.mostrarAdministrador(administrador);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearUsuario() {
        try {
            Usuario usuario=usuarioView.crearUsuario();
            int idUsuario = usuarioDAO.insertUsuario(usuario);
            usuario.setIdUsuario(idUsuario);
            if (usuario.getTipoUsuario() == TipoUsuario.valueOf("normal")){
                crearUsuarioNormal(usuario);
            } else{
                crearAdministrador(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void crearUsuarioNormal(Usuario usuario) {
        try {
            UsuarioNormal usuarioNormal=usuarioView.crearUsuarioNormal(usuario);
            usuarioNormalDAO.insertUsuarioNormal(usuarioNormal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void crearAdministrador(Usuario usuario) {
        try {
            Administrador administrador=usuarioView.crearAdministrador(usuario);
            administradorDAO.insertAdministrador(administrador);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void actualizarUsuario() {
        try {
            String dni = usuarioView.obtenerDni();
            Usuario usuario = usuarioDAO.getUsuarioByDni(dni);
            usuarioView.obtenerDatosActualizados(usuario);
            usuarioDAO.updateUsuario(usuario);
            if (usuario.getTipoUsuario() == TipoUsuario.valueOf("normal")){
                usuarioView.obtenerDatosActualizadosUsuarioNormal(usuario);
            } else{
                usuarioView.obtenerDatosActualizadosAdministrador(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarUsuario() {
        try {
            String dni=usuarioView.obtenerDniAEliminar();
            usuarioDAO.deleteUsuarioByDni(dni);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}