package dao;

import model.TipoUsuario;
import model.UsuarioNormal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioNormalDAO {

    private static UsuarioNormalDAO instance;
    private Connection connection;

    public static final String CREATE_TABLE_USUARIONORMAL = """
    CREATE TABLE IF NOT EXISTS `usuarioNormal`(
    `idUsuario` INT UNSIGNED NOT NULL,
    `direccion` VARCHAR(200),
    `telefonoMovil` VARCHAR(20),
    `fotografia` VARCHAR(255),
                    primary key(`idUsuario`),
                    foreign key(`idUsuario`) references `usuario`(`idUsuario`)
);
""";

    private static final String INSERT_QUERY = "INSERT INTO usuarioNormal (idUsuario, direccion, telefonoMovil, fotografia) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario";
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String UPDATE_QUERY = "UPDATE usuarioNormal SET direccion = ?, telefonoMovil = ?, fotografia = ? FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String DELETE_QUERY = "DELETE FROM usuarioNormal u2 INNER JOIN usuario u ON u2.idUsuario = u.idUsuario WHERE dni = ?";
    private static final String TOTAL_USUARIOS_NORMALES_QUERY = "SELECT COUNT(*) FROM usuarioNormal";

    private UsuarioNormalDAO() {
        this.connection = DBConnection.getConnection();
    }

    public static synchronized UsuarioNormalDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioNormalDAO();
        }
        return instance;
    }

    public void insertUsuarioNormal(UsuarioNormal usuarioNormal) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, usuarioNormal.getIdUsuario());
            statement.setString(2, usuarioNormal.getDireccion());
            statement.setString(3, usuarioNormal.getTlfMovil());
            statement.setString(4, usuarioNormal.getFotografia());
            statement.executeUpdate();
        }
    }

    public List<UsuarioNormal> getAllUsuariosNormales() throws SQLException {
        List<UsuarioNormal> usuariosNormales = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuariosNormales.add(resultSetToUsuarioNormal(resultSet));
            }
        }
        return usuariosNormales;
    }

    public UsuarioNormal getUsuarioNormalByDni(String dni) throws SQLException {
        UsuarioNormal usuarioNormal = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_QUERY)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                usuarioNormal = resultSetToUsuarioNormal(resultSet);
            }
        }
        return usuarioNormal;
    }

    public void updateUsuarioNormal(UsuarioNormal usuarioNormal) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, usuarioNormal.getDireccion());
            statement.setString(2, usuarioNormal.getTlfMovil());
            statement.setString(3, usuarioNormal.getFotografia());
            statement.setString(4, usuarioNormal.getDni());
            statement.executeUpdate();
        }
    }

    public void deleteUsuarioNormalByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

    private UsuarioNormal resultSetToUsuarioNormal(ResultSet resultSet) throws SQLException {
        String valorDB = resultSet.getString("tipoUsuario");
        TipoUsuario tipoUsuario = (valorDB != null) ? TipoUsuario.valueOf(valorDB) : null;
        return new UsuarioNormal(
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("contrasena"),
                tipoUsuario,
                resultSet.getDate("fechaNacimiento"),
                resultSet.getInt("idUsuario"),
                resultSet.getTimestamp("fechaRegistro"),
                resultSet.getString("direccion"),
                resultSet.getString("telefonoMovil"),
                resultSet.getString("fotografia")
        );
    }

    public int totalUsuariosNormales() throws SQLException {
        int total = 0;
        try (PreparedStatement statement = connection.prepareStatement(TOTAL_USUARIOS_NORMALES_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }

}