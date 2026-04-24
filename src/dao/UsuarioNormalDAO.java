package dao;

import model.TipoUsuario;
import model.UsuarioNormal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link UsuarioNormal}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code usuarioNormal}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class UsuarioNormalDAO {

    /**
     * Instancia única del DAO
     */
    private static UsuarioNormalDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;


    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_USUARIONORMAL = """
    CREATE TABLE IF NOT EXISTS `usuarioNormal`(
    `idUsuario` INT UNSIGNED NOT NULL,
    `direccion` VARCHAR(200),
    `telefonoMovil` VARCHAR(20),
    `fotografia` VARCHAR(255),
                    primary key(`idUsuario`),
                    foreign key(`idUsuario`) references `usuario`(`idUsuario`)
);
""";

    /**
     * Consulta SQL para insertar un usuario normal
     */
    private static final String INSERT_QUERY = "INSERT INTO usuarioNormal (idUsuario, direccion, telefonoMovil, fotografia) VALUES (?, ?, ?, ?)";

    /**
     * Consulta SQL para recuperar todos los usuarios normales
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario";

    /**
     * Consulta SQL para recuperar un usuario normal por su DNI en usuario
     */
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario WHERE dni = ?";

    /**
     * Consulta SQL para actualizar un usuario normal
     */
    private static final String UPDATE_QUERY = "UPDATE usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario SET direccion = ?, telefonoMovil = ?, fotografia = ? WHERE dni = ?";

    /**
     * Consulta SQL para eliminar un usuario normal
     */
    private static final String DELETE_QUERY = "DELETE FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario WHERE dni = ?";

    /**
     * Consulta SQL para sumar el total de usuarios normales
     */
    private static final String TOTAL_USUARIOS_NORMALES_QUERY = "SELECT COUNT(*) FROM usuarioNormal";

    /**
     * Constructor privado para aplicar Singleton.
     *
     * @throws SQLException si ocurre un error en la conexión
     */
    private UsuarioNormalDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     * Devuelve la instancia única del DAO.
     *
     * @return instancia de {@code UsuarioNormalDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized UsuarioNormalDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new UsuarioNormalDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de un usuario normal en la BD
     *
     * @param usuarioNormal usuario normal a insertar
     * @throws SQLException SQLException si ocurre un error en la inserción
     */
    public void insertUsuarioNormal(UsuarioNormal usuarioNormal) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, usuarioNormal.getIdUsuario());
            statement.setString(2, usuarioNormal.getDireccion());
            statement.setString(3, usuarioNormal.getTlfMovil());
            statement.setString(4, usuarioNormal.getFotografia());
            statement.executeUpdate();
        }
    }

    /**
     * Recibe los datos de todos los usuarios normales de la BD
     *
     * @return ArrayList de usuarios normales
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<UsuarioNormal> getAllUsuariosNormales() throws SQLException {
        List<UsuarioNormal> usuariosNormales = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuariosNormales.add(resultSetToUsuarioNormal(resultSet));
            }
        }
        return usuariosNormales;
    }

    /**
     * Recupera los datos de un usuario normal por su DNI de la BD, uniendo la tabla con la tabla usuario
     *
     * @param dni dni a buscar para recibir los datos
     * @return UsuarioNormal encontrado o {@code null}
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public UsuarioNormal getUsuarioNormalByDni(String dni) throws SQLException {
        UsuarioNormal usuarioNormal = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_QUERY)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioNormal = resultSetToUsuarioNormal(resultSet);
            }
        }
        return usuarioNormal;
    }

    /**
     * Actualiza los datos de un usuario normal en la BD
     *
     * @param usuarioNormal usuario normal con los nuevos datos
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateUsuarioNormal(UsuarioNormal usuarioNormal) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, usuarioNormal.getDireccion());
            statement.setString(2, usuarioNormal.getTlfMovil());
            statement.setString(3, usuarioNormal.getFotografia());
            statement.setString(4, usuarioNormal.getDni());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD un usuario normal concreto relacionado por DNI
     *
     * @param dni dni a buscar para eliminar
     * @throws SQLException SQLException si ocurre un error en la eliminación
     */
    public void deleteUsuarioNormalByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link UsuarioNormal}.
     *
     * @param resultSet fila actual del result set
     * @return objeto usuario normal construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private UsuarioNormal resultSetToUsuarioNormal(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("tipoUsuario");
        TipoUsuario tipoUsuario = (valorDB != null) ? TipoUsuario.valueOf(valorDB) : null;
        return new UsuarioNormal(
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("contrasena"),
                tipoUsuario,
                resultSet.getDate("fechaNacimiento"),
                resultSet.getInt("idUsuario"),
                resultSet.getTimestamp("fechaRegistro"),
                resultSet.getString("direccion"),
                resultSet.getString("telefonoMovil"),
                resultSet.getString("fotografia")
        );
    }

    /**
     * Hace un recuento de los usuarios normales registrados en la BD
     *
     * @return total de usuarios normales
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalUsuariosNormales() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_USUARIOS_NORMALES_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }

}