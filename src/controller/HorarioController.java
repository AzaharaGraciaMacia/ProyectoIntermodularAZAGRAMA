package controller;

import dao.HorarioDAO;
import model.Horario;
import view.ConsolaView;
import view.HorarioView;

import java.sql.SQLException;
import java.util.List;

public class HorarioController {

    private HorarioDAO horarioDAO;
    private HorarioView horarioView;
    private ConsolaView consolaView;

    public HorarioController() {
        // Crear conexión a la base de datos

        horarioDAO = HorarioDAO.getInstance();
        horarioView = new HorarioView();
        consolaView = new ConsolaView();
    }

    public void mostrarTodosLosHorarios() {
        try {
            List<Horario> horarios = horarioDAO.getAllHorarios();
            horarioView.mostrarHorarios(horarios);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void mostrarHorarioID() {
        try {
            int idHorario = horarioView.obtenerIdHorario();
            Horario horario = horarioDAO.getHorarioById(idHorario);
            horarioView.mostrarHorario(horario);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void mostrarHorariosDia() {
        try {
            String diaSemana = horarioView.obtenerDiaHorario();
            List<Horario> horarios = horarioDAO.getHorarioByDia(diaSemana);
            horarioView.mostrarHorarios(horarios);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void crearHorario() {
        try {
            Horario horario = horarioView.crearHorario();
            horarioDAO.insertHorario(horario);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

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

    public void eliminarHorario() {
        try {
            int idHorario = horarioView.obtenerIdAEliminar();
            horarioDAO.deleteHorario(idHorario);
            System.out.println("Horario eliminado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

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
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }
}
