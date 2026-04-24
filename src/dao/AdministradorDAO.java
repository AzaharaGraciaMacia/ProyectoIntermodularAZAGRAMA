package dao;

import model.Administrador;
import model.TipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link Administrador}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code administrador}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class AdministradorDAO {

    /**
     * Instancia única del DAO
     */
    private static AdministradorDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;

    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_ADMINISTRADOR = """
    CREATE TABLE IF NOT EXISTS `administrador`(
    `idUsuario` INT UNSIGNED NOT NULL,
    `telefonoGuardia` VARCHAR(20),
                    primary key(`idUsuario`),
                    foreign key(`idUsuario`) references `usuario`(`idUsuario`)
);
""";

    /**
     * Consulta SQL para insertar un administrador
     */
    private static final String INSERT_QUERY = "INSERT INTO administrador (idUsuario, telefonoGuardia) VALUES (?, ?)";

    /**
     * Consulta SQL para recuperar todos los administradores
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario";

    /**
     * Consulta SQL para recuperar un administrador por su DNI en usuario
     */
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario WHERE dni = ?";

    /**
     * Consulta SQL para actualizar un administrador
     */
    private static final String UPDATE_QUERY = "UPDATE administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario  SET telefonoGuardia = ? WHERE dni = ?";

    /**
     * Consulta SQL para eliminar un administrador
     */
    private static final String DELETE_QUERY = "DELETE FROM administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario WHERE dni = ?";

    /**
     * Consulta SQL para sumar el total de administradores
     */
    private static final String TOTAL_ADMINISTRADORES_QUERY = "SELECT COUNT(*) FROM administrador";

    /**
     * Constructor privado para aplicar Singleton.
     *
     * @throws SQLException si ocurre un error en la conexión
     */
    private AdministradorDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     * Devuelve la instancia única del DAO.
     *
     * @return instancia de {@code AdministradorDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized AdministradorDAO getInstance() throws  SQLException {
        if (instance == null) {
            instance = new AdministradorDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de administrador en la BD
     *
     * @param administrador administrador a insertar
     * @throws SQLException si ocurre un error en la inserción
     */
    public void insertAdministrador(Administrador administrador) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, administrador.getIdUsuario());
            statement.setString(2, administrador.getTelefono());
            statement.executeUpdate();
        }
    }

    /**
     * Recibe los datos de todos los administradores de la BD
     *
     * @return ArrayList de administradores
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Administrador> getAllAdministradores() throws SQLException {
        List<Administrador> administradores = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                administradores.add(resultSetToAdministrador(resultSet));
            }
        }
        return administradores;
    }

    /**
     * Recupera los datos de un administrador por su DNI de la BD, uniendo la tabla con la tabla usuario
     *
     * @param dni dni a buscar para recibir los datos
     * @return Administrador encontrado o {@code null}
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public Administrador getAdministradorByDni(String dni) throws SQLException {
        Administrador administrador = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_QUERY)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                administrador = resultSetToAdministrador(resultSet);
            }
        }
        return administrador;
    }

    /**
     * Actualiza los datos de un administrador en la BD
     *
     * @param administrador administrador con los nuevos datos
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateAdministrador(Administrador administrador) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, administrador.getTelefono());
            statement.setString(2, administrador.getDni());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD un administrador relacionado por DNI
     *
     * @param dni dni a buscar para eliminar
     * @throws SQLException si ocurre un error en la eliminación
     */
    public void deleteAdministradorByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Administrador}.
     *
     * @param resultSet fila actual del result set
     * @return objeto administrador construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private Administrador resultSetToAdministrador(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("tipoUsuario");
        TipoUsuario tipoUsuario = (valorDB != null) ? TipoUsuario.valueOf(valorDB) : null;
        return new Administrador(
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("contrasena"),
                tipoUsuario,
                resultSet.getDate("fechaNacimiento"),
                resultSet.getInt("idUsuario"),
                resultSet.getTimestamp("fechaRegistro"),
                resultSet.getString("telefonoGuardia")
        );
    }

    /**
     * Hace un recuento de los administradores registrados en la BD
     *
     * @return total de administradores
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalAdministradores() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_ADMINISTRADORES_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }

}