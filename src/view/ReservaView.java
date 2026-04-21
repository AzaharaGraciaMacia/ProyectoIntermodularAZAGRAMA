package view;


import controller.RecursoController;
import dao.HorarioDAO;
import model.DiaSemana;
import model.Horario;
import model.Reserva;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class ReservaView {
    RecursoView recursoView = new RecursoView();
    RecursoController recursoController = new RecursoController();
    HorarioDAO horarioDAO = HorarioDAO.getInstance();

    public void mostrarReservas(List<Reserva> reservas) {
        System.out.println("Lista de Reservas:");

        System.out.printf("%-15s %-15s %-15s %-15s %-10s %-10s %-10s %-15s %-50s %-100s\n","ID de Reserva" , "ID de Recurso" , "ID de Usuario" , "Fecha de Reserva" , "Hora de Inicio", "Hora de Fin", "Coste (€)", "Número de Plazas", "Motivo", "Observaciones");

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        reservas.forEach(System.out::println);

    }

    public Reserva crearReserva () {

        String idRecurso = ConsolaView.leerTexto("Introduce el código del recurso:");
        int idUsuario = ConsolaView.leerEntero("Introduce el id del usuario:");

        List<Horario> horarios = recursoController.mostrarDisponibilidad(idRecurso);
        recursoView.mostrarDisponiblesEn(horarios);
        int idHorario = ConsolaView.leerEntero("Seleccione un horario disponible de los anteriores:");
        Horario horario;
        DiaSemana diaSemanaDisponible;

        try {
            horario = horarioDAO.getHorarioById(idHorario);
            diaSemanaDisponible = horario.getDiaSemana();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        LocalDate fechaDia;
        DayOfWeek diaSemana;
        String diaEspanol;
        String diaLimpio;
        Date fechaReserva = null;
        do {
            boolean fechaValida = false;
            while (!fechaValida) {
                String fecha = ConsolaView.leerTexto("Introduce una fecha de reserva válida (dd/MM/yyyy) dentro de la disponibilidad del recurso:");
                fechaReserva = ConsolaView.validarYFormatearFecha(fecha);
                if (fechaReserva != null)
                    fechaValida = true;
                else
                    System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
            }
            fechaDia = fechaReserva.toLocalDate();
            diaSemana = fechaDia.getDayOfWeek();
            diaEspanol = diaSemana.getDisplayName(TextStyle.FULL, new Locale("es"));
            diaLimpio = Normalizer.normalize(diaEspanol, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();
        } while (DiaSemana.valueOf(diaLimpio.toLowerCase())!=diaSemanaDisponible);

        Time horaInicio = horario.getHoraInicio();
        Time horaFin = horario.getHoraFin();

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

        List<Horario> horarios = recursoController.mostrarDisponibilidad(idRecurso);
        recursoView.mostrarDisponiblesEn(horarios);
        int idHorario = ConsolaView.leerEntero("Seleccione un horario disponible de los anteriores:");
        Horario horario;
        DiaSemana diaSemanaDisponible;

        try {
            horario = horarioDAO.getHorarioById(idHorario);
            diaSemanaDisponible = horario.getDiaSemana();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String diaEspanol;
        String diaLimpio;
        LocalDate fechaDia;
        DayOfWeek diaSemana;
        Date fechaReserva = null;
        do {
            boolean fechaValida = false;
            while (!fechaValida) {
                String fecha = ConsolaView.leerTexto("Introduce una fecha de reserva válida (dd/MM/yyyy) dentro de la disponibilidad del recurso:");
                fechaReserva = ConsolaView.validarYFormatearFecha(fecha);
                if (fechaReserva != null)
                    fechaValida = true;
                else
                    System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
            }
            fechaDia = fechaReserva.toLocalDate();
            diaSemana = fechaDia.getDayOfWeek();
            diaEspanol = diaSemana.getDisplayName(TextStyle.FULL, new Locale("es"));
            diaLimpio = Normalizer.normalize(diaEspanol, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();
        } while (DiaSemana.valueOf(diaLimpio)!=diaSemanaDisponible);

        Time horaInicio = horario.getHoraInicio();
        Time horaFin = horario.getHoraFin();

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

        return new Reserva(idRecurso, reserva.getIdReserva(), reserva.getIdUsuario(), fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }

    public int obtenerIdReserva() {
        return ConsolaView.leerEntero("Introduce el ID de la reserva:");
    }
    public String obtenerDNIUsuario() {
        String dni;
        do {
            dni = ConsolaView.leerTexto("Introduce el DNI del usuario:").toUpperCase();
            if (!ConsolaView.comprobarDNI(dni)) System.out.println("Error: DNI incorrecto.");
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
        System.out.println("7. Cantidad de reservas registradas");
        System.out.println("0. Volver");
    }


}