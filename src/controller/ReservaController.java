package controller;

import dao.RecursoDAO;
import dao.ReservaDAO;
import model.Recurso;
import model.Reserva;
import view.ConsolaView;
import view.ReservaView;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador encargado de la lógica de negocio relacionada con las reservas.
 *
 * <p>Coordina la interacción entre la vista de reservas y la capa DAO del modelo.</p>
 */
public class ReservaController{

    /**
     * DAO de reservas
     */
    private ReservaDAO reservaDAO;

    /**
     * Vista de reservas
     */
    private ReservaView reservaView;

    /**
     * Utilidad de consola.
     */
    private ConsolaView consolaView;

    /**
     * DAO de recursos
     */
    private RecursoDAO recursoDAO;

    /**
     * Construye el controlador con sus dependencias.
     */
    public ReservaController() throws SQLException {
            reservaDAO = ReservaDAO.getInstance();
            reservaView = new ReservaView();
            consolaView = new ConsolaView();
            recursoDAO = RecursoDAO.getInstance();
        }

    /**
     * Muestra todas las reservas
     */
    public void mostrarTodasLasReservas() {
            try {
                List<Reserva> reservas = reservaDAO.getAllReservas();
                reservaView.mostrarReservas(reservas);
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    /**
     * Muestra todas las reservas de un usuario por DNI
     */
    public void mostrarReservasUsuario() {
        try {
            String dni = reservaView.obtenerDNIUsuario();
            List<Reserva> reservas = reservaDAO.getReservasByDni(dni);
            reservaView.mostrarReservas(reservas);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

    /**
     * Muestra una reserva por su ID
     */
    public void mostrarReservaID() {
            try {
                int idReserva = reservaView.obtenerIdReserva();
                Reserva reserva = reservaDAO.getReservaById(idReserva);
                reservaView.mostrarReserva(reserva);
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    /**
     * Comprueba si hay solapamiento en los horarios, o si se salen de la franja horaria establecida
     *
     * @param ini1 hora inicial de la reserva registrada en la BD
     * @param fin1 hora final de la reserva registrada en la BD
     * @param ini2 hora inicial de la nueva reserva a comprobar
     * @param fin2 hora final de la nueva reserva a comprobar
     * @return true si hay solapamiento
     */
    private boolean horasSolapadas(Time ini1, Time fin1,
                                   Time ini2, Time fin2) {
            //Convertimos formato de hora SQL a formato LocalTime para poder comparar
            LocalTime horaInicio1 = ini1.toLocalTime();
            LocalTime horaInicio2 = ini2.toLocalTime();
            LocalTime horaFin1 = fin1.toLocalTime();
            LocalTime horaFin2 = fin2.toLocalTime();
        return horaInicio1.isBefore(horaFin2) && horaInicio2.isBefore(horaFin1);
    }

    /**
     * Comprueba si hay reservas duplicadas
     *
     * @param reservas Listado de las reservas registradas en la BD
     * @param nuevaReserva reserva de la que comprobamos la duplicidad
     * @return true si ya existe una reserva en misma fecha y horario
     */
    public boolean reservaDuplicada(List<Reserva> reservas, Reserva nuevaReserva) {
        return reservas.stream()
                .anyMatch(r -> r.getIdRecurso().equals(nuevaReserva.getIdRecurso())
                        && r.getFechaReserva().equals(nuevaReserva.getFechaReserva())
                        && horasSolapadas(r.getHoraInicio(), r.getHoraFin(),
                        nuevaReserva.getHoraInicio(), nuevaReserva.getHoraFin()));
    }

    /**
     * Crea una nueva reserva
     */
    public void crearReserva() {
            try {
                Reserva reserva = reservaView.crearReserva();
                String idRecurso = reserva.getIdRecurso();
                Recurso recurso = recursoDAO.getRecursoById(idRecurso);
                if(!reservaDuplicada(reservaDAO.getAllReservas(), reserva) && reserva.getNumeroPlazas() <= recurso.getCapacidadRecurso()) {
                reservaDAO.insertReserva(reserva);
                    System.out.println("Reserva creada correctamente.");
                }
                else  {
                    consolaView.mostrar("Ya existe una reserva para esa fecha o el número de plazas es mayor que la capacidad del recurso.");
                }
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    /**
     * Actualiza una reserva existente
     */
    public void actualizarReserva() {
            try {
                int idReserva = reservaView.obtenerIdReserva();
                Reserva reserva = reservaDAO.getReservaById(idReserva);
                Reserva reservaActualizada = reservaView.obtenerDatosActualizados(reserva);
                String idRecurso = reservaActualizada.getIdRecurso();
                Recurso recurso = recursoDAO.getRecursoById(idRecurso);
                if(!reservaDuplicada(reservaDAO.getAllReservas(), reservaActualizada) && reserva.getNumeroPlazas() <= recurso.getCapacidadRecurso()) {
                reservaDAO.updateReserva(reservaActualizada);
                    System.out.println("Reserva actualizada correctamente.");
                }
                else  {
                    consolaView.mostrar("Ya existe otra reserva para esa fecha o el número de plazas es mayor que la capacidad del recurso.");
                }
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    /**
     * Elimina una reserva existente
     */
    public void eliminarReserva() {
            try {
                int idReserva = reservaView.obtenerIdAEliminar();
                reservaDAO.deleteReservaById(idReserva);
                System.out.println("Reserva eliminada correctamente.");
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    /**
     * Suma la cantidad de reservas en el momento de la llamada
     *
     * @return int cantidad de reservas
     */
    public int cantidadReservas() {
        try{
            int cantidad = reservaDAO.totalReservas();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Inicia el menú de reservas
     */
    public void iniciar() {
        int opcion;

        do {
            reservaView.mostrarMenu();
            opcion = consolaView.leerEntero("Opción: ");

                switch (opcion) {

                    case 1 -> crearReserva();
                    case 2 -> eliminarReserva();
                    case 3 -> actualizarReserva();
                    case 4 -> mostrarTodasLasReservas();
                    case 5 -> mostrarReservaID();
                    case 6 -> mostrarReservasUsuario();
                    case 7 -> System.out.println("En estos momentos hay registradas "+ cantidadReservas() + " reservas.");
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }

}