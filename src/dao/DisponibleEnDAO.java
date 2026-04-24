package dao;

import model.DisponibleEn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link DisponibleEn}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code disponibleEn}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class DisponibleEnDAO {
    /**
     * Instancia única del DAO
     */
    private static DisponibleEnDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;

    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_DISPONIBLE_EN = """
    CREATE TABLE IF NOT EXISTS `disponibleEn`(
    `idRecurso` VARCHAR(10) NOT NULL,
    `idHorario` INT UNSIGNED NOT NULL,
                    primary key(`idRecurso`, `idHorario`)`,
                    foreign key(`idRecurso`) references `recurso`(`idRecurso`)
                    foreign key(`idHorario`) references `horario`(`idHorario`)
);
""";

    /**
     * Consulta SQL para insertar una disponibilidad
     */
    private static final String INSERT_QUERY = "INSERT INTO disponibleEn (idRecurso, idHorario) VALUES (?, ?)";

    /**
     * Consulta SQL para recuperar todas las disponibilidades
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM disponibleEn";

    /**
     * Consulta SQL para recuperar las disponibilidades de un recurso
     */
    private static final String SELECT_BY_RECURSO_QUERY = "SELECT * FROM disponibleEn d INNER JOIN horario h ON d.idHorario = h.idHorario WHERE idRecurso = ?";

    /**
     * Consulta SQL para recuperar las disponibilidades en un horario concreto
     */
    private static final String SELECT_BY_HORARIO_QUERY = "SELECT * FROM disponibleEn d INNER JOIN recurso r ON d.idRecurso = r.idRecurso  WHERE idHorario = ?";

    /**
     * Consulta SQL para actualizar una disponibilidad
     */
    private static final String UPDATE_QUERY = "UPDATE disponibleEn SET idHorario = ? WHERE idRecurso = ?";

    /**
     * Consulta SQL para eliminar una disponibilidad
     */
    private static final String DELETE_QUERY = "DELETE FROM disponibleEn WHERE idRecurso = ? AND idHorario = ?";

    /**
     * Consulta SQL para sumar el total de disponibilidades
     */
    private static final String TOTAL_DISPONIBILIDADES_QUERY = "SELECT COUNT(*) FROM disponibleEn";

    /**
     * Constructor privado para aplicar Singleton.
     *
     * @throws SQLException si ocurre un error en la conexión
     */
    private DisponibleEnDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     *Devuelve la instancia única del DAO.
     *
     * @return instancia de {@code DisponibleEnDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized DisponibleEnDAO getInstance() throws  SQLException {
        if (instance == null) {
            instance = new DisponibleEnDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de disponibilidades en la BD
     *
     * @param disponibleEn disponibilidad a insertar
     * @throws SQLException si ocurre un error en la inserción
     */
    public void insertDisponibilidad(DisponibleEn disponibleEn) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, disponibleEn.getIdRecurso());
            statement.setInt(2, disponibleEn.getIdHorario());
            statement.executeUpdate();
        }
    }

    /**
     * Recibe los datos de todas las disponibilidades de la BD
     *
     * @return ArrayList de disponibilidades
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<DisponibleEn> getAllDisponibilidades() throws SQLException {
        List<DisponibleEn> disponiblesEn = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                disponiblesEn.add(resultSetToDisponibleEn(resultSet));
            }
        }
        return disponiblesEn;
    }

    /**
     * Recibe los datos de todas las disponibilidades de un recurso en la BD
     *
     * @param idRecurso id del recurso a buscar
     * @return ArrayList de disponibilidades
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<DisponibleEn> getDisponibilidadesByRecurso(String idRecurso) throws SQLException {
        List<DisponibleEn> disponiblesEn = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_RECURSO_QUERY)) {
            statement.setString(1, idRecurso);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                disponiblesEn.add(resultSetToDisponibleEn(resultSet));
            }
        }
        return disponiblesEn;
    }

    /**
     * Recibe los datos de todas las disponibilidades en un horario concreto de la BD
     *
     * @param idHorario id del horario a buscar
     * @return ArrayList de disponibilidades
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<DisponibleEn> getDisponibilidadByHorario(int idHorario) throws SQLException {
        List<DisponibleEn> disponiblesEn = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_HORARIO_QUERY)) {
            statement.setInt(1, idHorario);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                disponiblesEn.add(resultSetToDisponibleEn(resultSet));
            }
        }
        return disponiblesEn;
    }

    /**
     * Actualiza los datos de disponibilidades en la BD
     *
     * @param disponibleEn disponibilidad actualizada
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateDisponibilidad(DisponibleEn disponibleEn) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, disponibleEn.getIdRecurso());
            statement.setInt(2, disponibleEn.getIdHorario());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD una disponibilidad concreta, pk compuesta de:
     *
     * @param idRecurso id del recurso asociado a la disponibilidad
     * @param idHorario id del horario asociado a la disponibilidad
     * @throws SQLException si ocurre un error en la eliminación
     */
    public void deleteDisponibilidad(String idRecurso, int idHorario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, idRecurso);
            statement.setInt(2, idHorario);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link DisponibleEn}.
     *
     * @param resultSet fila actual del result set
     * @return objeto disponibleEn construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private DisponibleEn resultSetToDisponibleEn(ResultSet resultSet) throws SQLException {
        return new DisponibleEn(
                resultSet.getString("idRecurso"),
                resultSet.getInt("idHorario")
        );
    }

    /**
     * Hace un recuento de las disponibilidades registrados en la BD
     *
     * @return total de disponibilidades
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalDisponibilidades() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_DISPONIBILIDADES_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }
}