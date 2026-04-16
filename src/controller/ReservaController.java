package controller;

import dao.ReservaDAO;
import model.Reserva;
import view.ConsolaView;
import view.ReservaView;

import java.sql.SQLException;
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

        public void crearReserva() {
            try {
                Reserva reserva = reservaView.crearReserva();
                reservaDAO.insertReserva(reserva);
            } catch (SQLException e) {
                consolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        }

        public void actualizarReserva() {
            try {
                int idReserva = reservaView.obtenerIdReserva();
                Reserva reserva = reservaDAO.getReservaById(idReserva);
                Reserva reservaActualizada = reservaView.obtenerDatosActualizados(reserva);
                reservaDAO.updateReserva(reservaActualizada);
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