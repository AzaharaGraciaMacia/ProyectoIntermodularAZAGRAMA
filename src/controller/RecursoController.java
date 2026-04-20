package controller;

import dao.DisponibleEnDAO;
import dao.HorarioDAO;
import dao.RecursoDAO;
import model.DisponibleEn;
import model.Horario;
import model.Recurso;
import view.ConsolaView;
import view.RecursoView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecursoController {

    private RecursoDAO recursoDAO;
    private RecursoView recursoView;
    private ConsolaView consolaView;
    private HorarioDAO horarioDAO;
    private DisponibleEnDAO disponibleEnDAO;

    public RecursoController() {
        // Crear conexión a la base de datos

        recursoDAO = RecursoDAO.getInstance();
        recursoView = new RecursoView();
        consolaView = new ConsolaView();
        horarioDAO = HorarioDAO.getInstance();
        disponibleEnDAO = DisponibleEnDAO.getInstance();

    }

    public void mostrarTodosLosRecursos() {
        try {
            List<Recurso> recursos = recursoDAO.getAllRecursos();
            recursoView.mostrarRecursos(recursos);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void mostrarRecursoID() {
        try {
            String idRecurso = recursoView.obtenerIdRecurso();
            Recurso recurso = recursoDAO.getRecursoById(idRecurso);
            recursoView.mostrarRecurso(recurso);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void crearRecurso() {
        try {
            Recurso recurso = recursoView.crearRecurso();
            recursoDAO.insertRecurso(recurso);
            disponibleEnDAO.insertDisponibilidad(recursoView.crearDisponibilidad(recurso));
            System.out.println("Recurso creado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void actualizarRecurso() {
        try {
            String idRecurso = recursoView.obtenerIdRecurso();
            Recurso recurso = recursoDAO.getRecursoById(idRecurso);
            Recurso recursoActualizado = recursoView.obtenerDatosActualizados(recurso);
            recursoDAO.updateRecurso(recursoActualizado);
            disponibleEnDAO.insertDisponibilidad(recursoView.crearDisponibilidad(recursoActualizado));
            System.out.println("Recurso actualizado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public void eliminarRecurso() {
        try {
            String idRecurso = recursoView.obtenerIdAEliminar();
            recursoDAO.deleteRecursoById(idRecurso);
            System.out.println("Recurso eliminado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    public List<Horario> mostrarDisponibilidad(String idRecurso) {
        try{
            List<DisponibleEn> disponibilidades = disponibleEnDAO.getDisponibilidadesByRecurso(idRecurso);
            List<Horario> horarios = new ArrayList<>();
            for(DisponibleEn disponibleEn : disponibilidades){
                int idHorario = disponibleEn.getIdHorario();
                horarios.add(horarioDAO.getHorarioById(idHorario));
            }
            return horarios;
            }
        catch(SQLException e){
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return null;
    }

    public void iniciar() {
        int opcion;

        do {
            recursoView.mostrarMenu();
            opcion = consolaView.leerEntero("Opción: ");

                switch (opcion) {

                    case 1 -> crearRecurso();
                    case 2 -> eliminarRecurso();
                    case 3 -> actualizarRecurso();
                    case 4 -> mostrarTodosLosRecursos();
                    case 5 -> mostrarRecursoID();
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }
}

