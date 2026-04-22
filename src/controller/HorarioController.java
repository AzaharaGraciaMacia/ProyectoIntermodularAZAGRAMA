package controller;

import dao.HorarioDAO;
import model.Horario;
import view.ConsolaView;
import view.HorarioView;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador de la tabla horarios
 */
public class HorarioController {

    private HorarioDAO horarioDAO;
    private HorarioView horarioView;
    private ConsolaView consolaView;

    /**
     * Crea las conexiones con el resto de clases
     */
    public HorarioController() throws SQLException {
        horarioDAO = HorarioDAO.getInstance();
        horarioView = new HorarioView();
        consolaView = new ConsolaView();
    }

    /**
     * Muestra todos los horarios
     */
    public void mostrarTodosLosHorarios() {
        try {
            List<Horario> horarios = horarioDAO.getAllHorarios();
            horarioView.mostrarHorarios(horarios);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra un horario por su ID
     */
    public void mostrarHorarioID() {
        try {
            int idHorario = horarioView.obtenerIdHorario();
            Horario horario = horarioDAO.getHorarioById(idHorario);
            horarioView.mostrarHorario(horario);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los horarios por día de la semana
     */
    public void mostrarHorariosDia() {
        try {
            String diaSemana = horarioView.obtenerDiaHorario();
            List<Horario> horarios = horarioDAO.getHorarioByDia(diaSemana);
            horarioView.mostrarHorarios(horarios);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo horario
     */
    public void crearHorario() {
        try {
            Horario horario = horarioView.crearHorario();
            horarioDAO.insertHorario(horario);
            System.out.println("Horario creado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Modifica un horario existente
     */
    public void actualizarHorario() {
        try {
            int idHorario = horarioView.obtenerIdHorario();
            Horario horario = horarioDAO.getHorarioById(idHorario);
            Horario horarioActualizado = horarioView.obtenerDatosActualizados(horario);
            horarioDAO.updateHorario(horarioActualizado);
            System.out.println("Horario actualizado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Elimina un horario existente
     */
    public void eliminarHorario() {
        try {
            int idHorario = horarioView.obtenerIdAEliminar();
            horarioDAO.deleteHorario(idHorario);
            System.out.println("Horario eliminado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Suma la cantidad de horarios en el momento de la llamada
     * @return int cantidad de horarios
     */
    public int cantidadHorarios() {
        try{
            int cantidad = horarioDAO.totalHorarios();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Inicia el menú de horarios
     */
    public void iniciar() {
        int opcion;

        do {
            horarioView.mostrarMenu();
            opcion = consolaView.leerEntero("Opción: ");

                switch (opcion) {

                    case 1 -> crearHorario();
                    case 2 -> eliminarHorario();
                    case 3 -> actualizarHorario();
                    case 4 -> mostrarTodosLosHorarios();
                    case 5 -> mostrarHorarioID();
                    case 6 -> mostrarHorariosDia();
                    case 7 -> System.out.println("En estos momentos hay registrados "+ cantidadHorarios() + " horarios.");
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }
}
