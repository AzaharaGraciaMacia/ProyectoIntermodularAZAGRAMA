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

public class ReservaController{

        private ReservaDAO reservaDAO;
        private ReservaView reservaView;
        private ConsolaView consolaView;
        private RecursoDAO recursoDAO;

        public ReservaController() {
            // Crear conexión a la base de datos

            reservaDAO = ReservaDAO.getInstance();
            reservaView = new ReservaView();
            consolaView = new ConsolaView();
            recursoDAO = RecursoDAO.getInstance();
        }

        public void mostrarTodasLasReservas() {
            try {
                List<Reserva> reservas = reservaDAO.getAllReservas();
                reservaView.mostrarReservas(reservas);
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    public void mostrarReservasUsuario() {
        try {
            String dni = reservaView.obtenerDNIUsuario();
            List<Reserva> reservas = reservaDAO.getReservasByDni(dni);
            reservaView.mostrarReservas(reservas);
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
    }

        public void mostrarReservaID() {
            try {
                int idReserva = reservaView.obtenerIdReserva();
                Reserva reserva = reservaDAO.getReservaById(idReserva);
                reservaView.mostrarReserva(reserva);
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

    private boolean horasSolapadas(Time ini1, Time fin1,
                                   Time ini2, Time fin2) {
            LocalTime horaInicio1 = ini1.toLocalTime();
            LocalTime horaInicio2 = ini2.toLocalTime();
            LocalTime horaFin1 = fin1.toLocalTime();
            LocalTime horaFin2 = fin2.toLocalTime();
        return horaInicio1.isBefore(horaFin2) && horaInicio2.isBefore(horaFin1);
    }

    public boolean reservaDuplicada(List<Reserva> reservas, Reserva nuevaReserva) {
        return reservas.stream()
                .anyMatch(r -> r.getIdRecurso().equals(nuevaReserva.getIdRecurso())
                        && r.getFechaReserva().equals(nuevaReserva.getFechaReserva())
                        && horasSolapadas(r.getHoraInicio(), r.getHoraFin(),
                        nuevaReserva.getHoraInicio(), nuevaReserva.getHoraFin()));
    }

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

        public void eliminarReserva() {
            try {
                int idReserva = reservaView.obtenerIdAEliminar();
                reservaDAO.deleteReservaById(idReserva);
                System.out.println("Reserva eliminada correctamente.");
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }


    public int cantidadReservas() {
        try{
            int cantidad = reservaDAO.totalReservas();
            return cantidad;
        } catch (SQLException e) {
            consolaView.mostrar("Error de base de datos: " + e.getMessage());
        }
        return 0;
    }

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