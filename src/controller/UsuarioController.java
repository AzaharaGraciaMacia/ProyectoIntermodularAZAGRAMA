package controller;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import dao.UsuarioNormalDAO;
import model.Administrador;
import model.TipoUsuario;
import model.Usuario;
import model.UsuarioNormal;
import view.ConsolaView;
import view.UsuarioView;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private UsuarioNormalDAO usuarioNormalDAO;
    private AdministradorDAO administradorDAO;
    private UsuarioView usuarioView;
    private ConsolaView consolaView;

    public UsuarioController() {
        // Crear conexión a la base de datos
        usuarioDAO = UsuarioDAO.getInstance();
        usuarioNormalDAO = UsuarioNormalDAO.getInstance();
        administradorDAO = AdministradorDAO.getInstance();
        usuarioView = new UsuarioView();
        consolaView = new ConsolaView();
    }

    public void mostrarTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
            usuarioView.mostrarUsuarios(usuarios);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }
    public void mostrarTodosLosUsuariosNormales() {
        try {
            List<UsuarioNormal> usuariosNormales = usuarioNormalDAO.getAllUsuariosNormales();
            usuarioView.mostrarUsuariosNormales(usuariosNormales);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }
    public void mostrarTodosLosAdministradores() {
        try {
            List<Administrador> administradores = administradorDAO.getAllAdministradores();
            usuarioView.mostrarAdministradores(administradores);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }
    public void mostrarUsuarioDNI() {
        try {
            String dni = usuarioView.obtenerDni();
            Usuario usuario = usuarioDAO.getUsuarioByDni(dni);
            usuarioView.mostrarUsuario(usuario);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
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
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }

    }
    public void crearUsuarioNormal(Usuario usuario) {
        try {
            UsuarioNormal usuarioNormal=usuarioView.crearUsuarioNormal(usuario);
            usuarioNormalDAO.insertUsuarioNormal(usuarioNormal);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }
    public void crearAdministrador(Usuario usuario) {
        try {
            Administrador administrador=usuarioView.crearAdministrador(usuario);
            administradorDAO.insertAdministrador(administrador);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }
    public void actualizarUsuario() {
        try {
            String dni = usuarioView.obtenerDni();
            Usuario usuario = usuarioDAO.getUsuarioByDni(dni);
            Usuario usuarioActualizado = usuarioView.obtenerDatosActualizados(usuario);
            usuarioDAO.updateUsuario(usuarioActualizado);
            if (usuario.getTipoUsuario() == TipoUsuario.valueOf("normal")){
                UsuarioNormal usuarioNormalActualizado = usuarioView.obtenerDatosActualizadosUsuarioNormal(usuarioActualizado);
                usuarioNormalDAO.updateUsuarioNormal(usuarioNormalActualizado);
            } else{
               Administrador administradorActualizado = usuarioView.obtenerDatosActualizadosAdministrador(usuarioActualizado);
               administradorDAO.updateAdministrador(administradorActualizado);
            }
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void eliminarUsuario () {
        try {
            String dni=usuarioView.obtenerDniAEliminar();
            usuarioDAO.deleteUsuarioByDni(dni);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void iniciar() {
        int opcion;

        do {
            usuarioView.mostrarMenu();
            opcion = consolaView.leerEntero("Opción: ");

                switch (opcion) {

                    case 1 -> crearUsuario();
                    case 2 -> eliminarUsuario();
                    case 3 -> actualizarUsuario();
                    case 4 -> mostrarTodosLosUsuarios();
                    case 5 -> mostrarTodosLosAdministradores();
                    case 6 -> mostrarTodosLosUsuariosNormales();
                    case 7 -> mostrarUsuarioDNI();
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }

}