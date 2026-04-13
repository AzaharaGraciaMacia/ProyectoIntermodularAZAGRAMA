package dao;

import model.DiaSemana;
import model.Horario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {
    private static HorarioDAO instance;
    private Connection connection;

    public static final String CREATE_TABLE_HORARIO = """
    CREATE TABLE IF NOT EXISTS `horario`(
    `idHorario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `diaSemana` ENUM(`Lunes`,`Martes`,`Miércoles`,`Jueves`,`Viernes`,`Sábado`,`Domingo`) NOT NULL,
    `horaInicio` TIME NOT NULL,
    `horaFin` TIME NOT NULL,
                    primary key(`idHorario`)
);
""";

    private static final String INSERT_QUERY = "INSERT INTO horario (diaSemana, horaInicio, horaFin) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM horario";
    private static final String SELECT_BY_DIA_QUERY = "SELECT * FROM horario WHERE diaSemana = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM horario WHERE idHorario = ?";
    private static final String UPDATE_QUERY = "UPDATE horario SET diaSemana = ?, horaInicio = ?, horaFin = ? FROM horario WHERE idHorario = ?";
    private static final String DELETE_QUERY = "DELETE FROM horario WHERE idHorario = ?";
    private static final String TOTAL_HORARIOS_QUERY = "SELECT COUNT(*) FROM horario";

    private HorarioDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized HorarioDAO getInstance() {
        if (instance == null) {
            instance = new HorarioDAO();
        }
        return instance;
    }

    public void insertHorario(Horario horario) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, horario.getDiaSemana().toString());
            statement.setTime(2, horario.getHoraInicio());
            statement.setTime(3, horario.getHoraFin());
            statement.executeUpdate();
        }
    }

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

    public Horario getHorarioByDia(String diaSemana) throws SQLException {
        Horario horario = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DIA_QUERY)) {
            statement.setString(1, diaSemana);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                horario = resultSetToHorario(resultSet);
            }
        }
        return horario;
    }
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

    public void updateHorario(Horario horario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, horario.getDiaSemana().toString());
            statement.setTime(2, horario.getHoraInicio());
            statement.setTime(3, horario.getHoraFin());
            statement.setInt(4, horario.getIdHorario());
            statement.executeUpdate();
        }
    }

    public void deleteHorario(int idHorario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, idHorario);
            statement.executeUpdate();
        }
    }

    private Horario resultSetToHorario(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("diaSemana");
        DiaSemana diaSemana = (valorDB != null) ? DiaSemana.valueOf(valorDB) : null;
        return new Horario(
                diaSemana,
                resultSet.getTime("horaInicio"),
                resultSet.getTime("horaFin")
        );
    }

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