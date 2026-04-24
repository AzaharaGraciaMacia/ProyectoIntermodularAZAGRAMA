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

/**
 * Controlador encargado de la lógica de negocio relacionada con los recursos.
 *
 * <p>Coordina la interacción entre la vista de recursos y la capa DAO del modelo.</p>
 */
public class RecursoController {

    /**
     * DAO de recursos
     */
    private RecursoDAO recursoDAO;

    /**
     * Vista de recursos
     */
    private RecursoView recursoView;

    /**
     * Utilidad de consola.
     */
    private ConsolaView consolaView;

    /**
     * DAO de horarios
     */
    private HorarioDAO horarioDAO;

    /**
     * DAO de disponibilidades
     */
    private DisponibleEnDAO disponibleEnDAO;

    /**
     * Construye el controlador con sus dependencias.
     */
    public RecursoController() throws SQLException {
        recursoDAO = RecursoDAO.getInstance();
        recursoView = new RecursoView();
        consolaView = new ConsolaView();
        horarioDAO = HorarioDAO.getInstance();
        disponibleEnDAO = DisponibleEnDAO.getInstance();
    }

    /**
     * Muestra todos los recursos
     */
    public void mostrarTodosLosRecursos() {
        try {
            List<Recurso> recursos = recursoDAO.getAllRecursos();
            recursoView.mostrarRecursos(recursos);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra un recurso por ID
     */
    public void mostrarRecursoID() {
        try {
            String idRecurso = recursoView.obtenerIdRecurso();
            Recurso recurso = recursoDAO.getRecursoById(idRecurso);
            recursoView.mostrarRecurso(recurso);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Crea un recurso
     */
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

    /**
     * Actualiza un recurso existente
     */
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

    /**
     * Elimina un recurso existente
     */
    public void eliminarRecurso() {
        try {
            String idRecurso = recursoView.obtenerIdAEliminar();
            recursoDAO.deleteRecursoById(idRecurso);
            System.out.println("Recurso eliminado correctamente.");
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los horarios en los que está disponible un recurso, recuperado por su ID
     *
     * @param idRecurso
     * @return ArrayList de todos los horarios disponibles
     */
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

    /**
     * Suma la cantidad de recursos en el momento de la llamada
     *
     * @return int cantidad de recursos
     */
    public int cantidadRecursos() {
        try{
            int cantidad = recursoDAO.totalRecursos();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Inicia el menú de recursos
     */
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
                    case 6 -> System.out.println("En estos momentos hay registrados "+ cantidadRecursos() + " recursos.");
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }
}

