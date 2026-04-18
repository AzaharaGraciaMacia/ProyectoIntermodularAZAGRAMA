package dao;

import model.TipoUsuario;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static UsuarioDAO instance;
    private Connection connection;

    public static final String CREATE_TABLE_USUARIO = """
    CREATE TABLE IF NOT EXISTS `usuario`(
    `idUsuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `nombre` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `fechaNacimiento` DATE,
    `tipoUsuario` ENUM(`Administrador`, `Normal`) NOT NULL,
    `contrasena` VARCHAR(100) NOT NULL,
    `dni` VARCHAR(9) NOT NULL,
    `fechaRegistro` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    primary key(`idUsuario`),
                    unique key(`dni`,`email`)
);
""";

    private static final String INSERT_QUERY = "INSERT INTO usuario (nombre, email, fechaNacimiento, tipoUsuario, contrasena, dni) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM usuario";
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM usuario WHERE dni = ?";
    private static final String UPDATE_QUERY = "UPDATE usuario SET nombre = ?, email = ?, fechaNacimiento = ?, contrasena = ? WHERE dni = ?";
    private static final String DELETE_QUERY = "DELETE FROM usuario WHERE dni = ?";
    private static final String TOTAL_USUARIOS_QUERY = "SELECT COUNT(*) FROM usuario";

    private UsuarioDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public int insertUsuario(Usuario usuario) throws SQLException {
        int id = -1;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,  Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(5, usuario.getContrasena());
            statement.setDate(3, usuario.getFechaNacimiento());
            statement.setString(4, usuario.getTipoUsuario().toString());
            statement.setString(6, usuario.getDni());
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
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuario = resultSetToUsuario(resultSet);
            }
        }
        return usuario;
    }

    public void updateUsuario(Usuario usuario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(4, usuario.getContrasena());
            statement.setDate(3, usuario.getFechaNacimiento());
            statement.setString(5, usuario.getDni());
            statement.executeUpdate();
        }
    }

    public void deleteUsuarioByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

    private Usuario resultSetToUsuario(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("tipoUsuario");
        TipoUsuario tipoUsuario = (valorDB != null) ? TipoUsuario.valueOf(valorDB) : null;
        return new Usuario(
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("contrasena"),
                tipoUsuario,
                resultSet.getDate("fechaNacimiento"),
                resultSet.getInt("idUsuario"),
                resultSet.getTimestamp("fechaRegistro")

        );
    }

    public int totalUsuarios() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_USUARIOS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }
}