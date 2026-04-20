package controller;

import dao.ReservaDAO;
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

        public ReservaController() {
            // Crear conexión a la base de datos

            reservaDAO = ReservaDAO.getInstance();
            reservaView = new ReservaView();
            consolaView = new ConsolaView();
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
                if(!reservaDuplicada(reservaDAO.getAllReservas(), reserva)) {
                reservaDAO.insertReserva(reserva);}
                else  {
                    consolaView.mostrar("Ya existe una reserva para esa fecha.");
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
                if(!reservaDuplicada(reservaDAO.getAllReservas(), reserva)) {
                reservaDAO.updateReserva(reservaActualizada);}
                else  {
                    consolaView.mostrar("Ya existe otra reserva para esa fecha.");
                }
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

        public void eliminarReserva() {
            try {
                int idReserva = reservaView.obtenerIdAEliminar();
                reservaDAO.deleteReservaById(idReserva);
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
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
                    case 0 -> consolaView.mostrar("Volviendo...");
                    default -> consolaView.mostrar("Opción no válida.");
                }

        } while (opcion != 0);
    }

}