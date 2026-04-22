package dao;

import model.Recurso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link Recurso}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code recurso}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class RecursoDAO {
    /**
     * Instancia única del DAO
     */
    private static RecursoDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;

    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_RECURSO = """
    CREATE TABLE IF NOT EXISTS `recurso`(
    `idRecurso` VARCHAR(10) NOT NULL,
    `nombre` VARCHAR(100) NOT NULL,
    `descripcion` TEXT NOT NULL,
    `ubicacion` VARCHAR(200) NOT NULL,
    `capacidad` INT NOT NULL,
                    primary key(`idRecurso`),
    );
""";

    /**
     * Consulta SQL para insertar un recurso
     */
    private static final String INSERT_QUERY = "INSERT INTO recurso (idRecurso, nombre, descripcion, ubicacion, capacidad) VALUES (?, ?, ?, ?, ?)";

    /**
     * Consulta SQL para recuperar todos los recursos
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM recurso";

    /**
     * Consulta SQL para recuperar un recurso por su ID
     */
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM recurso WHERE idRecurso = ?";

    /**
     * Consulta SQL para recuperar un recurso por su nombre
     */
    private static final String SELECT_BY_NOMBRE_QUERY = "SELECT * FROM recurso WHERE nombre = ?";

    /**
     * Consulta SQL para actualizar un recurso
     */
    private static final String UPDATE_QUERY = "UPDATE recurso SET nombre = ?, descripcion = ?, ubicacion = ?, capacidad = ? WHERE idRecurso = ?";

    /**
     * Consulta SQL para eliminar un recurso
     */
    private static final String DELETE_QUERY = "DELETE FROM recurso WHERE idRecurso = ?";

    /**
     * Consulta SQL para sumar el total de recursos
     */
    private static final String TOTAL_RECURSOS_QUERY = "SELECT COUNT(*) FROM recurso";

    /**
     * Constructor privado para aplicar Singleton.
     * @throws SQLException si ocurre un error en la conexión
     */
    private RecursoDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     * Devuelve la instancia única del DAO.
     * @return instancia de {@code RecursoDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized RecursoDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new RecursoDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de recurso en la BD
     * @param recurso recurso a insertar
     * @throws SQLException si ocurre un error en la inserción
     */
    public void insertRecurso(Recurso recurso) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, recurso.getIdRecurso());
            statement.setString(2, recurso.getNombreRecurso());
            statement.setString(3, recurso.getDescripcion());
            statement.setString(4, recurso.getUbicacionRecurso());
            statement.setInt(5, recurso.getCapacidadRecurso());
            statement.executeUpdate();
        }
    }

    /**
     * Recibe los datos de todos los recursos de la BD
     * @return ArrayList de administradores
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Recurso> getAllRecursos() throws SQLException {
        List<Recurso> recursos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recursos.add(resultSetToRecurso(resultSet));
            }
        }
        return recursos;
    }

    /**
     * Recupera los datos de un administrador por su DNI de la BD, uniendo la tabla con la tabla usuario
     * @param idRecurso id del recurso a encontrar
     * @return Recurso encontrado o {@code null}
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public Recurso getRecursoById(String idRecurso) throws SQLException {
        Recurso recurso = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setString(1, idRecurso);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                recurso = resultSetToRecurso(resultSet);
            }
        }
        return recurso;
    }

    /**
     * Recupera los datos de un administrador por su DNI de la BD, uniendo la tabla con la tabla usuario
     * @param nombre nombre del recurso a encontrar
     * @return Recurso encontrado o {@code null}
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public Recurso getRecursoByNombre(String nombre) throws SQLException {
        Recurso recurso = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NOMBRE_QUERY)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                recurso = resultSetToRecurso(resultSet);
            }
        }
        return recurso;
    }

    /**
     * Actualiza los datos de un administrador en la BD
     * @param recurso administrador con los nuevos datos
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateRecurso(Recurso recurso) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, recurso.getNombreRecurso());
            statement.setString(2, recurso.getDescripcion());
            statement.setString(3, recurso.getUbicacionRecurso());
            statement.setInt(4, recurso.getCapacidadRecurso());
            statement.setString(5, recurso.getIdRecurso());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD un recurso
     * @param idRecurso id del recurso a buscar para eliminar
     * @throws SQLException si ocurre un error en la eliminación
     */
    public void deleteRecursoById(String idRecurso) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, idRecurso);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Recurso}.
     *
     * @param resultSet fila actual del result set
     * @return objeto recurso construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private Recurso resultSetToRecurso(ResultSet resultSet) throws SQLException {
        return new Recurso(
                resultSet.getString("idRecurso"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getString("ubicacion"),
                resultSet.getInt("capacidad")
        );
    }

    /**
     * Hace un recuento de los recursos registrados en la BD
     * @return total de recursos
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalRecursos() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_RECURSOS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }
}