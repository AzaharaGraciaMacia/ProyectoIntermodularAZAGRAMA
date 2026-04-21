package dao;

import model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private static ReservaDAO instance;
    private Connection connection;

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

    private static final String INSERT_QUERY = "INSERT INTO reserva (idRecurso, idUsuario, fecha, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM reserva";
    private static final String SELECT_BY_DNI_USUARIO_QUERY = "SELECT * FROM reserva r INNER JOIN usuario u ON r.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String SELECT_BY_ID_RESERVA_QUERY = "SELECT * FROM reserva r WHERE idReservaLocal = ?";
    private static final String UPDATE_QUERY = "UPDATE reserva SET idRecurso = ?, idUsuario = ?,fecha = ?, horaInicio = ?, horaFin = ?, coste = ?, numeroPlazas = ?, motivo = ?, observaciones = ? WHERE idReservaLocal = ?";
    private static final String DELETE_QUERY = "DELETE FROM reserva WHERE idReservaLocal = ?";
    private static final String TOTAL_RESERVAS_QUERY = "SELECT COUNT(*) FROM reserva";

    private ReservaDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized ReservaDAO getInstance() {
        if (instance == null) {
            instance = new ReservaDAO();
        }
        return instance;
    }

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

    public void deleteReservaById(int idReserva) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idReserva);
            statement.executeUpdate();
        }
    }

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