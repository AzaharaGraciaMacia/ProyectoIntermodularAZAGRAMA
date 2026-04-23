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

/**
 * Controlador de la tabla usuarios
 */
public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private UsuarioNormalDAO usuarioNormalDAO;
    private AdministradorDAO administradorDAO;
    private UsuarioView usuarioView;
    private ConsolaView consolaView;

    /**
     * Crea las conexiones con el resto de clases
     */
    public UsuarioController() throws SQLException {
        usuarioDAO = UsuarioDAO.getInstance();
        usuarioNormalDAO = UsuarioNormalDAO.getInstance();
        administradorDAO = AdministradorDAO.getInstance();
        usuarioView = new UsuarioView();
        consolaView = new ConsolaView();
    }

    /**
     * Muestra todos los usuarios
     */
    public void mostrarTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
            usuarioView.mostrarUsuarios(usuarios);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los usuarios normales
     */
    public void mostrarTodosLosUsuariosNormales() {
        try {
            List<UsuarioNormal> usuariosNormales = usuarioNormalDAO.getAllUsuariosNormales();
            usuarioView.mostrarUsuariosNormales(usuariosNormales);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los usuarios administradores
     */
    public void mostrarTodosLosAdministradores() {
        try {
            List<Administrador> administradores = administradorDAO.getAllAdministradores();
            usuarioView.mostrarAdministradores(administradores);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra usuario por DNI
     */
    public void mostrarUsuarioDNI() {
        try {
            String dni = usuarioView.obtenerDni();
            Usuario usuario = usuarioDAO.getUsuarioByDni(dni);
            if (usuario.getTipoUsuario() == TipoUsuario.normal) {
                UsuarioNormal usuarioNormal = usuarioNormalDAO.getUsuarioNormalByDni(dni);
                usuarioView.mostrarUsuarioNormal(usuarioNormal);
            } else  {
                Administrador administrador = administradorDAO.getAdministradorByDni(dni);
                usuarioView.mostrarAdministrador(administrador);
            }
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Crea un usuario y según su tipo lo divide en usuario normal o administrador
     */
    public void crearUsuario() {
        try {
            Usuario usuario=usuarioView.crearUsuario();
            int idUsuario = usuarioDAO.insertUsuario(usuario);
            usuario.setIdUsuario(idUsuario);
            System.out.println("Usuario creado correctamente.");
            if (usuario.getTipoUsuario() == TipoUsuario.valueOf("normal")){
                crearUsuarioNormal(usuario);
            } else{
                crearAdministrador(usuario);
            }
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }

    }

    /**
     * Crea el usuario normal a partir de un usuario cuyo tipo haya sido marcado como normal
     * @param usuario
     */
    public void crearUsuarioNormal(Usuario usuario) {
        try {
            UsuarioNormal usuarioNormal=usuarioView.crearUsuarioNormal(usuario);
            usuarioNormalDAO.insertUsuarioNormal(usuarioNormal);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Crea el administrador a partir de un usuario cuyo tipo haya sido marcado como administrador
     * @param usuario
     */
    public void crearAdministrador(Usuario usuario) {
        try {
            Administrador administrador=usuarioView.crearAdministrador(usuario);
            administradorDAO.insertAdministrador(administrador);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un usuario y de las tablas usuario normal o administrador según su tipo
     */
    public void actualizarUsuario() {
        try {
            String dni = usuarioView.obtenerDni();
            Usuario usuario = usuarioDAO.getUsuarioByDni(dni);
            Usuario usuarioActualizado = usuarioView.obtenerDatosActualizados(usuario);
            usuarioDAO.updateUsuario(usuarioActualizado);
            System.out.println("Usuario actualizado correctamente.");
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

    /**
     * Elimina un usuario (y por ende su referencia en usuario normal o administrador)
     */
    public void eliminarUsuario () {
        try {
            String dni=usuarioView.obtenerDniAEliminar();
            usuarioDAO.deleteUsuarioByDni(dni);
            System.out.println("Usuario eliminado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Suma la cantidad de usuarios en el momento de la llamada
     * @return int cantidad de usuarios
     */
    public int cantidadUsuarios() {
        try{
            int cantidad = usuarioDAO.totalUsuarios();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Suma la cantidad de usuarios administradores en el momento de la llamada
     * @return int cantidad de administradores
     */
    public int cantidadAdministradores() {
        try{
            int cantidad = administradorDAO.totalAdministradores();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Suma la cantidad de usuarios normales en el momento de la llamada
     * @return int cantidad de usuarios normales
     */
    public int cantidadUsuariosNormales() {
        try{
            int cantidad = usuarioNormalDAO.totalUsuariosNormales();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Inicia el menú de usuarios
     */
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
                    case 8 -> System.out.println("En estos momentos hay registrados "+ cantidadUsuarios() + " usuarios.");
                    case 9 -> System.out.println("En estos momentos hay registrados "+ cantidadAdministradores() + " administradores.");
                    case 10 -> System.out.println("En estos momentos hay registrados "+ cantidadUsuariosNormales() + " usuarios.");
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }

}