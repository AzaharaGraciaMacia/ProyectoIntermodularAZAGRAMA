package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión única a la base de datos y la inicialización del esquema.
 *
 * <p>La clase implementa un acceso centralizado a MySQL/MariaDB usando un patrón similar a
 * Singleton. Además, crea las tablas necesarias y carga datos de ejemplo únicamente
 * cuando la tabla {@code Departamento} está vacía.</p>
 */
public class DBConnection {

    /**
     * URL JDBC de la base de datos.
     *
     * <p>Se puede modificar este valor según el servidor MySQL.</p>
     */
    private static final String URL = "jdbc:mysql://localhost:3308/proyintud5azaharagracia";

    /**
     * Usuario de la base de datos.
     */
    private static final String USERNAME = "root";

    /**
     * Contraseña de la base de datos.
     */
    private static final String PASSWORD = "1968";

    /**
     * Conexión compartida de la aplicación.
     */
    private static Connection connection;

    /**
     * Constructor privado para impedir la creación de instancias.
     */
    private DBConnection() {}

    /**
     * Devuelve la conexión activa a la base de datos.
     * @return conexión JDBC compartida
     */
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                    try {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch ( SQLException e) {
                        throw new RuntimeException("No se ha podido conectar con la base de datos.", e);
                    }
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión activa si existe.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("No se ha podido cerrar la conexión: " + e.getMessage());
            }
        }
    }

}