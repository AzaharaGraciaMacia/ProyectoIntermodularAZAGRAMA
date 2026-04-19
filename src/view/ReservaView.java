package view;

import model.Reserva;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ReservaView {

    public void mostrarReservas(List<Reserva> reservas) {
        System.out.println("Lista de Reservas:");

        System.out.printf("%-15s %-15s %-15s %-15s %-10s %-10s %-10s %-15s %-50s %-100s\n","ID de Reserva" , "ID de Recurso" , "ID de Usuario" , "Fecha de Reserva" , "Hora de Inicio", "Hora de Fin", "Coste (€)", "Número de Plazas", "Motivo", "Observaciones");

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        reservas.forEach(System.out::println);

    }

    public Reserva crearReserva() {

        String idRecurso = ConsolaView.leerTexto("Introduce el código del recurso:");
        int idUsuario = ConsolaView.leerEntero("Introduce el id del usuario:");

        Date fechaReserva = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            String fecha = ConsolaView.leerTexto("Introduce la fecha de la reserva (dd/MM/yyyy):");
            fechaReserva = ConsolaView.validarYFormatearFecha(fecha);
            if (fechaReserva != null)
                fechaValida = true;
            else
                System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
        }

        Time horaInicio = null;
        Time horaFin = null;

        do {
            System.out.println("Recuerda que la hora de fin debe ser posterior a la hora de inicio");
            boolean horaInicioValida = false;
            while (!horaInicioValida) {
                String hora = ConsolaView.leerTexto("Introduce la hora de inicio (HH:mm 24h):");
                horaInicio = ConsolaView.validarYFormatearHora(hora);
                if (horaInicio != null)
                    horaInicioValida = true;
                else
                    System.out.println("Hora no válida. Usa el formato HH:mm 24h.");
            }

            boolean horaFinValida = false;
            while (!horaFinValida) {
                String hora = ConsolaView.leerTexto("Introduce la hora de fin (HH:mm 24h):");
                horaFin = ConsolaView.validarYFormatearHora(hora);
                if (horaFin != null)
                    horaFinValida = true;
                else
                    System.out.println("Hora no válida. Usa el formato HH:mm 24h.");
            }
        } while (horaFin.before(horaInicio) || horaFin.equals(horaInicio));

        double coste = ConsolaView.leerDouble("Introduce el coste de la reserva:");

        int numeroPlazas = ConsolaView.leerEntero("Introduce el número de plazas:");
        String motivo = ConsolaView.leerTexto("Introduce el motivo de la reserva:");

        String observaciones = null;
        String respuesta;
        do {
            respuesta = ConsolaView.leerTexto("Introduce las observaciones (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            observaciones = respuesta;
        }

        return new Reserva(idRecurso, idUsuario, fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }

    public int obtenerIdAEliminar() {
        return ConsolaView.leerEntero("Introduce el ID de la reserva a eliminar:");
    }

    public Reserva obtenerDatosActualizados(Reserva reserva) {

        String idRecurso = ConsolaView.leerTexto("Introduce el nuevo código del recurso:");

        Date fechaReserva = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            String fecha = ConsolaView.leerTexto("Introduce la nueva fecha de la reserva (dd/MM/yyyy):");
            fechaReserva = ConsolaView.validarYFormatearFecha(fecha);
            if (fechaReserva != null)
                fechaValida = true;
            else
                System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
        }

        Time horaInicio = null;
        Time horaFin = null;

        do {
            System.out.println("Recuerda que la hora de fin debe ser posterior a la hora de inicio");
            boolean horaInicioValida = false;
            while (!horaInicioValida) {
                String hora = ConsolaView.leerTexto("Introduce la hora de inicio (HH:mm 24h):");
                horaInicio = ConsolaView.validarYFormatearHora(hora);
                if (horaInicio != null)
                    horaInicioValida = true;
                else
                    System.out.println("Hora no válida. Usa el formato HH:mm 24h.");
            }

            boolean horaFinValida = false;
            while (!horaFinValida) {
                String hora = ConsolaView.leerTexto("Introduce la hora de fin (HH:mm 24h):");
                horaFin = ConsolaView.validarYFormatearHora(hora);
                if (horaFin != null)
                    horaFinValida = true;
                else
                    System.out.println("Hora no válida. Usa el formato HH:mm 24h.");
            }
        } while (horaFin.before(horaInicio) || horaFin.equals(horaInicio));

        double coste = ConsolaView.leerDouble("Introduce el coste de la reserva:");

        int numeroPlazas = ConsolaView.leerEntero("Introduce el número de plazas:");
        String motivo = ConsolaView.leerTexto("Introduce el motivo de la reserva:");

        String observaciones = null;
        String respuesta;
        do {
            respuesta = ConsolaView.leerTexto("Introduce las observaciones (escribe OMITIR si no deseas aportar el dato): ");
        } while (respuesta.isBlank());
        if (!ConsolaView.OmitirRespuesta(respuesta.toUpperCase())) {
            observaciones = respuesta;
        }

        return new Reserva(idRecurso, reserva.getIdUsuario(), fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }

    public int obtenerIdReserva() {
        return ConsolaView.leerEntero("Introduce el ID de la reserva:");
    }
    public String obtenerDNIUsuario() {
        String dni;
        do {
            dni = ConsolaView.leerTexto("Introduce el DNI del usuario:").toUpperCase();
            System.out.println("Error: DNI incorrecto.");
        } while(!ConsolaView.comprobarDNI(dni));
        return dni;
    }

    public void mostrarReserva(Reserva reserva) {
        if (reserva != null)
            System.out.println(reserva);
        else
            System.out.println("La reserva no existe");
    }

    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE RESERVAS");
        System.out.println("1. Alta reserva");
        System.out.println("2. Baja reserva");
        System.out.println("3. Modificar reserva");
        System.out.println("4. Listar todas las reservas");
        System.out.println("5. Mostrar reserva por id");
        System.out.println("6. Listar reservas por DNI de Usuario");
        System.out.println("0. Volver");
    }

}