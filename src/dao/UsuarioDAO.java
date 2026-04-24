package dao;

import model.TipoUsuario;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link Usuario}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code usuario}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class UsuarioDAO {

    /**
     * Instancia única del DAO
     */
    private static UsuarioDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;

    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_USUARIO = """
    CREATE TABLE IF NOT EXISTS `usuario`(
    `idUsuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `fechaNacimiento` DATE,
    `tipoUsuario` ENUM(`Administrador`, `Normal`) NOT NULL,
    `contrasena` VARCHAR(100) NOT NULL,
    `dni` VARCHAR(9) NOT NULL,
    `fechaRegistro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    primary key(`idUsuario`),
                    unique key(`dni`,`email`)
);
""";

    /**
     * Consulta SQL para insertar un usuario
     */
    private static final String INSERT_QUERY = "INSERT INTO usuario (nombre, email, fechaNacimiento, tipoUsuario, contrasena, dni) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Consulta SQL para recuperar todos los usuarios
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM usuario";

    /**
     * Consulta SQL para recuperar un usuario por su DNI
     */
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM usuario u LEFT JOIN usuarioNormal u2 ON u.idUsuario = u2.idUsuario LEFT JOIN administrador a ON u.idUsuario = a.idUsuario WHERE dni = ?";

    /**
     * Consulta SQL para actualizar un usuario
     */
    private static final String UPDATE_QUERY = "UPDATE usuario SET nombre = ?, email = ?, fechaNacimiento = ?, contrasena = ? WHERE dni = ?";

    /**
     * Consulta SQL para eliminar un usuario
     */
    private static final String DELETE_QUERY = "DELETE FROM usuario WHERE dni = ?";

    /**
     * Consulta SQL para sumar el total de usuarios
     */
    private static final String TOTAL_USUARIOS_QUERY = "SELECT COUNT(*) FROM usuario";

    /**
     * Constructor privado para aplicar Singleton.
     *
     * @throws SQLException si ocurre un error en la conexión
     */
    private UsuarioDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     * Devuelve la instancia única del DAO.
     *
     * @return instancia de {@code UsuarioDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized UsuarioDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de un usuario en la BD
     *
     * @param usuario usuario a insertar
     * @return int del id autogenerado (auto_increment)
     * @throws SQLException si ocurre un error en la inserción
     */
    public int insertUsuario(Usuario usuario) throws SQLException {
        int id = -1;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,  Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(5, usuario.getContrasena());
            statement.setDate(3, usuario.getFechaNacimiento());
            statement.setString(4, usuario.getTipoUsuario().toString());
            statement.setString(6, usuario.getDni());
            int filas = statement.executeUpdate();

            if (filas > 0) {
                try {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        id = generatedKeys.getInt(1);
                    }
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return id;
    }

    /**
     * Recibe los datos de todos los usuarios de la BD
     *
     * @return ArrayList de usuarios
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuarios.add(resultSetToUsuario(resultSet));
            }
        }
        return usuarios;
    }

    /**
     * Recupera los datos de un usuario por su DNI de la BD
     *
     * @param dni dni a buscar para recibir los datos
     * @return Usuario encontrado o {@code null}
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public Usuario getUsuarioByDni(String dni) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_QUERY)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuario = resultSetToUsuario(resultSet);
            }
        }
        return usuario;
    }

    /**
     * Actualiza los datos de un usuario en la BD
     *
     * @param usuario usuario con los nuevos datos
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateUsuario(Usuario usuario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(4, usuario.getContrasena());
            statement.setDate(3, usuario.getFechaNacimiento());
            statement.setString(5, usuario.getDni());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD un usuario concreto por DNI
     *
     * @param dni dni a buscar para eliminar
     * @throws SQLException si ocurre un error en la eliminación
     */
    public void deleteUsuarioByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Usuario}.
     *
     * @param resultSet fila actual del result set
     * @return objeto usuario construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private Usuario resultSetToUsuario(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("tipoUsuario");
        TipoUsuario tipoUsuario = (valorDB != null) ? TipoUsuario.valueOf(valorDB) : null;
        return new Usuario(
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("contrasena"),
                tipoUsuario,
                resultSet.getDate("fechaNacimiento"),
                resultSet.getInt("idUsuario"),
                resultSet.getTimestamp("fechaRegistro")

        );
    }

    /**
     * Hace un recuento de los usuarios registrados en la BD
     *
     * @return total de usuarios
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalUsuarios() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_USUARIOS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }
}