package dao;

import model.Recurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAO {
    private static RecursoDAO instance;
    private Connection connection;

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

    private static final String INSERT_QUERY = "INSERT INTO recurso (idRecurso, nombre, descripcion, ubicacion, capacidad) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM recurso";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM Horario WHERE idRecurso = ?";
    private static final String SELECT_BY_NOMBRE_QUERY = "SELECT * FROM Horario WHERE nombre = ?";
    private static final String UPDATE_QUERY = "UPDATE recurso SET nombre = ?, descripcion = ?, ubicacion = ?, capacidad = ? FROM recurso WHERE idRecurso = ?";
    private static final String DELETE_QUERY = "DELETE FROM recurso WHERE idRecurso = ?";
    private static final String TOTAL_RECURSOS_QUERY = "SELECT COUNT(*) FROM recurso";

    private RecursoDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized RecursoDAO getInstance() {
        if (instance == null) {
            instance = new RecursoDAO();
        }
        return instance;
    }

    public void insertRecurso(Recurso recurso) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, recurso.getIdRecurso());
            statement.setString(2, recurso.getNombreRecurso());
            statement.setString(3, recurso.getDescripcion());
            statement.setString(4, recurso.getUbicacionRecurso());
            statement.setInt(5, recurso.getCapacidadRecurso());
            statement.executeUpdate();
        }
    }

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

    public void deleteRecursoById(String idRecurso) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, idRecurso);
            statement.executeUpdate();
        }
    }

    private Recurso resultSetToRecurso(ResultSet resultSet) throws SQLException {
        return new Recurso(
                resultSet.getString("idRecurso"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getString("ubicacion"),
                resultSet.getInt("capacidad")
        );
    }

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