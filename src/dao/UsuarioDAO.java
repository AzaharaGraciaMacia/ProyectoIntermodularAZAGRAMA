package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static UsuarioDAO instance;
    private Connection connection;

    //Sentencia SQL para crear la tabla si no existe
    public static final String CREATE_TABLE_USUARIO = """
    CREATE TABLE IF NOT EXISTS `Usuario`(
    `idUsuario` INT NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `fechaNacimiento` DATE,
    `tipoUsuario` ENUM NOT NULL,
    `contrasena` VARCHAR(50) NOT NULL,
    `dni` VARCHAR(9) NOT NULL,
                    primary key(`idUsuario`),
                    unique key(`dni`)
);
""";
    // Consultas SQL predefinidas para operaciones CRUD
    private static final String INSERT_QUERY = "INSERT INTO Usuario (idUsuario, nombre, email, fechaNacimiento, tipoUsuario, contrasena, dni) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Usuario";
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM Usuario WHERE dni = ?";
    private static final String UPDATE_QUERY = "UPDATE Usuario SET nombre = ?, email = ?, fechaNacimiento = ?, contrasena = ? WHERE dni = ?";
    private static final String DELETE_QUERY = "DELETE FROM Usuario WHERE dni = ?";
    private static final String TOTAL_PERSONAS_QUERY = "SELECT COUNT(*) FROM Usuario";

    private UsuarioDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public void insertUsuario(Usuario usuario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, usuario.getIdUsuario());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getContrasena());
            statement.setDate(5, usuario.getFechaNacimiento());
            statement.setString(6, usuario.getTipoUsuario().name());
            statement.setString(7, usuario.getDni());
            statement.executeUpdate();
        }
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuarios.add(resultSetToUsuario(resultSet));
            }
        }
        return usuarios;
    }

    public Usuario getUsuarioByDni(String dni) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_QUERY)) {
            statement.setString(7, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuario = resultSetToUsuario(resultSet);
            }
        }
        return usuario;
    }

    public void updateUsuario(Usuario usuario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, usuario.getIdUsuario());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getContrasena());
            statement.setDate(5, usuario.getFechaNacimiento());
            statement.setString(6, usuario.getTipoUsuario().name());
            statement.setString(7, usuario.getDni());
            statement.executeUpdate();
        }
    }

    public void deleteUsuarioByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(7, dni);
            statement.executeUpdate();
        }
    }

    private Usuario resultSetToUsuario(ResultSet resultSet) throws SQLException {
        return new Usuario(
                resultSet.getInt("idUsuario"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("contrasena"),
                resultSet.getDate("fechaNacimiento"),
                resultSet.getString("tipoUsuario"),
                resultSet.getString("dni")
        );
    }

    public int totalUsuarios() throws SQLException {
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