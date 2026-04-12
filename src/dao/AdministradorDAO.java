package dao;

import model.Administrador;
import model.TipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    private static AdministradorDAO instance;
    private Connection connection;

    public static final String CREATE_TABLE_ADMINISTRADOR = """
    CREATE TABLE IF NOT EXISTS `usuarioNormal`(
    `idUsuario` INT UNSIGNED NOT NULL,
    `telefonoGuardia` VARCHAR(20),
                    primary key(`idUsuario`),
                    foreign key(`idUsuario`) references `usuario`(`idUsuario`)
);
""";

    private static final String INSERT_QUERY = "INSERT INTO Administrador (idUsuario, telefonoGuardia) VALUES (?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario";
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM Administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String UPDATE_QUERY = "UPDATE Administrador SET telefonoGuardia = ? FROM Administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String DELETE_QUERY = "DELETE FROM Administrador a INNER JOIN usuario u ON a.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String TOTAL_PERSONAS_QUERY = "SELECT COUNT(*) FROM Administrador";

    private AdministradorDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized AdministradorDAO getInstance() {
        if (instance == null) {
            instance = new AdministradorDAO();
        }
        return instance;
    }

    public void insertAdministrador(Administrador administrador) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, administrador.getIdUsuario());
            statement.setString(2, administrador.getTelefono());
            statement.executeUpdate();
        }
    }

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

    public void updateAdministrador(Administrador administrador) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, administrador.getTelefono());
            statement.setString(2, administrador.getDni());
            statement.executeUpdate();
        }
    }

    public void deleteAdministradorByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

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

    public int totalAdministradores() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_PERSONAS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }

}