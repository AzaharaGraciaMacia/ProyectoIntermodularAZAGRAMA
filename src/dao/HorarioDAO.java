package dao;

import model.DiaSemana;
import model.Horario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link Horario}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code horario}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class HorarioDAO {
    /**
     * Instancia única del DAO
     */
    private static HorarioDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;

    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_HORARIO = """
    CREATE TABLE IF NOT EXISTS `horario`(
    `idHorario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `diaSemana` ENUM(`Lunes`,`Martes`,`Miércoles`,`Jueves`,`Viernes`,`Sábado`,`Domingo`) NOT NULL,
    `horaInicio` TIME NOT NULL,
    `horaFin` TIME NOT NULL,
                    primary key(`idHorario`)
);
""";

    /**
     * Consulta SQL para insertar un horario
     */
    private static final String INSERT_QUERY = "INSERT INTO horario (diaSemana, horaInicio, horaFin) VALUES (?, ?, ?)";

    /**
     * Consulta SQL para recuperar todos los horarios
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM horario";

    /**
     * Consulta SQL para recuperar los horarios de un día de la semana
     */
    private static final String SELECT_BY_DIA_QUERY = "SELECT * FROM horario WHERE diaSemana = ?";

    /**
     * Consulta SQL para recuperar los horarios por su ID
     */
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM horario WHERE idHorario = ?";

    /**
     * Consulta SQL para actualizar un horario
     */
    private static final String UPDATE_QUERY = "UPDATE horario SET diaSemana = ?, horaInicio = ?, horaFin = ? WHERE idHorario = ?";

    /**
     * Consulta SQL para eliminar un horario
     */
    private static final String DELETE_QUERY = "DELETE FROM horario WHERE idHorario = ?";

    /**
     * Consulta SQL para sumar el total de horarios
     */
    private static final String TOTAL_HORARIOS_QUERY = "SELECT COUNT(*) FROM horario";

    /**
     * Constructor privado para aplicar Singleton.
     * @throws SQLException si ocurre un error en la conexión
     */
    private HorarioDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     *Devuelve la instancia única del DAO.
     * @return instancia de {@code HorarioDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized HorarioDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new HorarioDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de horario en la BD
     * @param horario horario a insertar
     * @return int del id autogenerado (auto_increment)
     * @throws SQLException si ocurre un error en la inserción
     */
    public int insertHorario(Horario horario) throws SQLException {
        int id = -1;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,  Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, horario.getDiaSemana().toString());
            statement.setTime(2, horario.getHoraInicio());
            statement.setTime(3, horario.getHoraFin());
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
     * Recibe los datos de todas los horarios de la BD
     * @return ArrayList de horarios
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Horario> getAllHorarios() throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                horarios.add(resultSetToHorario(resultSet));
            }
        }
        return horarios;
    }

    /**
     * Recibe los datos de todas los horarios disponibles por día de la semana de la BD
     * @param diaSemana día de la semana a encontrar
     * @return ArrayList de horarios
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Horario>  getHorarioByDia(String diaSemana) throws SQLException {
        List<Horario> horarios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DIA_QUERY)) {
            statement.setString(1, diaSemana);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                horarios.add(resultSetToHorario(resultSet));
            }
        }
        return horarios;
    }

    /**
     * Recibe los datos de todas los horarios disponibles por día de la semana de la BD
     * @param idHorario id del horario concreto
     * @return objeto horario con los datos recibidos
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public Horario getHorarioById(int idHorario) throws SQLException {
        Horario horario = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setInt(1, idHorario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                horario = resultSetToHorario(resultSet);
            }
        }
        return horario;
    }

    /**
     * Actualiza los datos de horario en la BD
     * @param horario horario a actualizar
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateHorario(Horario horario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, horario.getDiaSemana().toString());
            statement.setTime(2, horario.getHoraInicio());
            statement.setTime(3, horario.getHoraFin());
            statement.setInt(4, horario.getIdHorario());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD un horario concreto
     * @param idHorario id del horario a eliminar
     * @throws SQLException si ocurre un error en la eliminación
     */
    public void deleteHorario(int idHorario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idHorario);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Horario}.
     *
     * @param resultSet fila actual del result set
     * @return objeto horario construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private Horario resultSetToHorario(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("diaSemana");
        DiaSemana diaSemana = (valorDB != null) ? DiaSemana.valueOf(valorDB) : null;
        return new Horario(
                resultSet.getInt("idHorario"),
                diaSemana,
                resultSet.getTime("horaInicio"),
                resultSet.getTime("horaFin")
        );
    }

    /**
     * Hace un recuento de los horarios registrados en la BD
     * @return total de horarios
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalHorarios() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_HORARIOS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }
}