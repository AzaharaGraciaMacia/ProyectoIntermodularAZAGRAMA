package dao;

import model.DisponibleEn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisponibleEnDAO {
    private static DisponibleEnDAO instance;
    private Connection connection;

    public static final String CREATE_TABLE_DISPONIBLE_EN = """
    CREATE TABLE IF NOT EXISTS `disponibleEn`(
    `idRecurso` VARCHAR(10) NOT NULL,
    `idHorario` INT UNSIGNED NOT NULL,
                    primary key(`idRecurso`, `idHorario`)`,
                    foreign key(`idRecurso`) references `recurso`(`idRecurso`)
                    foreign key(`idHorario`) references `horario`(`idHorario`)
);
""";

    private static final String INSERT_QUERY = "INSERT INTO disponibleEn (idRecurso, idHorario) VALUES (?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM disponibleEn";
    private static final String SELECT_BY_RECURSO_QUERY = "SELECT * FROM disponibleEn WHERE idRecurso = ?";
    private static final String SELECT_BY_HORARIO_QUERY = "SELECT * FROM disponibleEn WHERE idHorario = ?";
    private static final String UPDATE_QUERY = "UPDATE disponibleEn SET idHorario = ? FROM disponibleEn WHERE idRecurso = ?";
    private static final String DELETE_QUERY = "DELETE FROM disponibleEn WHERE idRecurso = ? AND idHorario = ?";
    private static final String TOTAL_DISPONIBILIDADES_QUERY = "SELECT COUNT(*) FROM disponibleEn";

    private DisponibleEnDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized DisponibleEnDAO getInstance() {
        if (instance == null) {
            instance = new DisponibleEnDAO();
        }
        return instance;
    }

    public void insertDisponibilidad(DisponibleEn disponibleEn) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, disponibleEn.getIdRecurso());
            statement.setInt(2, disponibleEn.getIdHorario());
            statement.executeUpdate();
        }
    }

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

    public DisponibleEn getDisponibilidadByRecurso(String idRecurso) throws SQLException {
        DisponibleEn disponibleEn = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_RECURSO_QUERY)) {
            statement.setString(1, idRecurso);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                disponibleEn = resultSetToDisponibleEn(resultSet);
            }
        }
        return disponibleEn;
    }
    public DisponibleEn getDisponibilidadByHorario(int idHorario) throws SQLException {
        DisponibleEn disponibleEn = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_HORARIO_QUERY)) {
            statement.setInt(1, idHorario);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                disponibleEn = resultSetToDisponibleEn(resultSet);
            }
        }
        return disponibleEn;
    }

    public void updateDisponibilidad(DisponibleEn disponibleEn) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, disponibleEn.getIdRecurso());
            statement.setInt(2, disponibleEn.getIdHorario());
            statement.executeUpdate();
        }
    }

    public void deleteDisponibilidad(String idRecurso, int idHorario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, idRecurso);
            statement.setInt(2, idHorario);
            statement.executeUpdate();
        }
    }

    private DisponibleEn resultSetToDisponibleEn(ResultSet resultSet) throws SQLException {
        return new DisponibleEn(
                resultSet.getString("idRecurso"),
                resultSet.getInt("idHorario")
        );
    }

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