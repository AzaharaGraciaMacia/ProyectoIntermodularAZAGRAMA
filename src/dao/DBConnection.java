

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mariadb://localhost:3308/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1968";

    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DBConnection.class) {
                    try {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        /*crearTabla(connection);
                        crearDatosEjemplo();*/
                    } catch ( SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /*//creamos la tabla si no existe
    private static void crearTabla(Connection conexion) throws SQLException {
        try (Statement statement = conexion.createStatement()) {
            statement.executeUpdate(
                    //Sentencia SQL para crear la tabla si no existe
                    PersonaDAO.CREATE_TABLE_PERSONA
            );
            // System.out.println("Tabla Persona creada correctamente.");
        }
    }
    //Crea unos datos de ejemplo
    public static void crearDatosEjemplo() throws SQLException{
        PersonaDAO personaDAO = PersonaDAO.getInstance();
        try {
            //si no tenemos datos, creamos unos ejemplos
            if(personaDAO.totalPersonas()==0) {
                personaDAO.insertPersona(new Persona("12345678A", "Juan", "Pérez", 25));
                personaDAO.insertPersona(new Persona("98765432B", "María", "Gómez", 30));
                personaDAO.insertPersona(new Persona("55555555C", "Carlos", "López", 22));
                personaDAO.insertPersona(new Persona("11111111D", "Ana", "Martínez", 28));
                personaDAO.insertPersona(new Persona("99999999E", "Pedro", "Sánchez", 35));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
}