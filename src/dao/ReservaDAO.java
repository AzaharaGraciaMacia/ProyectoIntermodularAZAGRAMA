package dao;

import model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de la entidad {@link ReservaDAO}.
 * <p>La clase encapsula las operaciones CRUD y consultas complementarias de la tabla
 * {@code reserva}. La clase utiliza un patrón Singleton para trabajar con una única
 * instancia y una única conexión compartida.</p>
 */
public class ReservaDAO {
    /**
     * Instancia única del DAO
     */
    private static ReservaDAO instance;

    /**
     * Conexión compartida con la BD
     */
    private Connection connection;

    /**
     * Crea la tabla en la BD si no existe
     */
    public static final String CREATE_TABLE_RESERVA = """
    CREATE TABLE IF NOT EXISTS `reserva`(
    `idRecurso VARCHAR(10),
    `idReservaLocal` INT UNSIGNED AUTO_INCREMENT,
    `idUsuario` INT UNSIGNED NOT NULL,
    `fecha` DATE NOT NULL,
    `horaInicio` TIME NOT NULL,
    `horaFin` TIME NOT NULL,
    `coste` DECIMAL (10,2) DEFAULT 0.00 NOT NULL,
    `numeroPlazas` INT NOT NULL,
    `motivo` TEXT DEFAULT 'Cumpleaños' NOT NULL,
    `observaciones` TEXT,
                    primary key(`idReservaLocal`),
                    foreign key(`idUsuario`) references `usuario`(`idUsuario`),
                    foreign key(`idRecurso`) references `recurso`(`idRecurso`),`
);
""";

    /**
     * Consulta SQL para insertar una reserva
     */
    private static final String INSERT_QUERY = "INSERT INTO reserva (idRecurso, idUsuario, fecha, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Consulta SQL para recuperar todas las reservas
     */
    private static final String SELECT_ALL_QUERY = "SELECT * FROM reserva";

    /**
     * Consulta SQL para recuperar las reservas de un usuario
     */
    private static final String SELECT_BY_DNI_USUARIO_QUERY = "SELECT * FROM reserva r INNER JOIN usuario u ON r.idUsuario = u.idUsuario WHERE dni = ?";

    /**
     * Consulta SQL para recuperar una reserva por su ID
     */
    private static final String SELECT_BY_ID_RESERVA_QUERY = "SELECT * FROM reserva r WHERE idReservaLocal = ?";

    /**
     * Consulta SQL para actualizar una reserva por su ID
     */
    private static final String UPDATE_QUERY = "UPDATE reserva SET idRecurso = ?, idUsuario = ?,fecha = ?, horaInicio = ?, horaFin = ?, coste = ?, numeroPlazas = ?, motivo = ?, observaciones = ? WHERE idReservaLocal = ?";

    /**
     * Consulta SQL para eliminar una reserva por su ID
     */
    private static final String DELETE_QUERY = "DELETE FROM reserva WHERE idReservaLocal = ?";

    /**
     * Consulta SQL para sumar el total de reservas
     */
    private static final String TOTAL_RESERVAS_QUERY = "SELECT COUNT(*) FROM reserva";

    /**
     * Constructor privado para aplicar Singleton.
     * @throws SQLException si ocurre un error en la conexión
     */
    private ReservaDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    /**
     *Devuelve la instancia única del DAO.
     * @return instancia de {@code ReservaDAO}
     * @throws SQLException si ocurre un error en la conexión
     */
    public static synchronized ReservaDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReservaDAO();
        }
        return instance;
    }

    /**
     * Inserta los datos de disponibilidades en la BD
     * @param reserva reserva  a insertar
     * @throws SQLException si ocurre un error en la inserción
     */
    public void insertReserva(Reserva reserva) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, reserva.getIdRecurso());
            statement.setInt(2, reserva.getIdUsuario());
            statement.setDate(3, reserva.getFechaReserva());
            statement.setTime(4, reserva.getHoraInicio());
            statement.setTime(5, reserva.getHoraFin());
            statement.setDouble(6, reserva.getCoste());
            statement.setInt(7, reserva.getNumeroPlazas());
            statement.setString(8, reserva.getMotivo());
            statement.setString(9, reserva.getObservaciones());
            statement.executeUpdate();
        }
    }

    /**
     * Recibe los datos de todas las reservas de la BD
     * @return ArrayList de reservas
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Reserva> getAllReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reservas.add(resultSetToReserva(resultSet));
            }
        }
        return reservas;
    }

    /**
     * Recibe los datos de todas las reservas de un usuario
     * @param dni dni del usuario solicitado
     * @return ArrayList de reservas
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public List<Reserva> getReservasByDni(String dni) throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_USUARIO_QUERY)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                reservas.add(resultSetToReserva(resultSet));
            }
        }
        return reservas;
    }

    /**
     * Recibe los datos de una reserva concreta recuperada por su ID de la BD
     * @param idReserva ID de la reserva solicitada
     * @return objeto reserva con los datos recibidos
     * @throws SQLException si ocurre un error en la búsqueda
     */
    public Reserva getReservaById(int idReserva) throws SQLException {
        Reserva reserva = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_RESERVA_QUERY)) {
            statement.setInt(1, idReserva);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reserva = resultSetToReserva(resultSet);
            }
        }
        return reserva;
    }

    /**
     * Actualiza los datos de disponibilidades en la BD
     * @param reserva reserva actualizada
     * @throws SQLException si ocurre un error en la actualización
     */
    public void updateReserva(Reserva reserva) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, reserva.getIdRecurso());
            statement.setInt(2, reserva.getIdUsuario());
            statement.setDate(3, reserva.getFechaReserva());
            statement.setTime(4, reserva.getHoraInicio());
            statement.setTime(5, reserva.getHoraFin());
            statement.setDouble(6, reserva.getCoste());
            statement.setInt(7, reserva.getNumeroPlazas());
            statement.setString(8, reserva.getMotivo());
            statement.setString(9, reserva.getObservaciones());
            statement.setInt(10, reserva.getIdReserva());
            statement.executeUpdate();
        }
    }

    /**
     * Elimina de la BD una reserva concreta recuperada por su ID
     * @param idReserva ID de la reserva a eliminar
     * @throws SQLException si ocurre un error en la eliminación
     */
    public void deleteReservaById(int idReserva) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idReserva);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte una fila del {@link ResultSet} en un objeto {@link Reserva}.
     *
     * @param resultSet fila actual del result set
     * @return objeto reserva construido a partir de la fila actual
     * @throws SQLException si ocurre un error al leer columnas
     */
    private Reserva resultSetToReserva(ResultSet resultSet) throws SQLException {
        return new Reserva(

                resultSet.getString("idRecurso"),
                resultSet.getInt("idReservaLocal"),
                resultSet.getInt("idUsuario"),
                resultSet.getDate("fecha"),
                resultSet.getTime("horaInicio"),
                resultSet.getTime("horaFin"),
                resultSet.getDouble("coste"),
                resultSet.getInt("numeroPlazas"),
                resultSet.getString("motivo"),
                resultSet.getString("observaciones")
        );
    }

    /**
     * Hace un recuento de las reservas registrados en la BD
     * @return total de reservas
     * @throws SQLException si ocurre un error en la recuperación de datos
     */
    public int totalReservas() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_RESERVAS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }
}