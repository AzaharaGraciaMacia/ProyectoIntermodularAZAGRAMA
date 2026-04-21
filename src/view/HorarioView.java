package view;

import model.DiaSemana;
import model.Horario;

import java.sql.Time;
import java.util.List;

public class HorarioView {

    public void mostrarHorarios(List<Horario> horarios) {
        System.out.println("Lista de Horarios:");

        System.out.printf("%-15s %-30s %-25s %-25s\n", "ID del Horario" , "Día de la semana" , "Hora de Inicio" , "Hora de Fin");

        System.out.println("---------------------------------------------------------------------------------------------------");

        horarios.forEach(System.out::println);

    }

    public Horario crearHorario() {

        DiaSemana diaSemana = null;
        boolean diaValido = false;
        while (!diaValido) {
            String respuestaDia = ConsolaView.leerTexto("Introduce el día de la semana:").toLowerCase();
            diaSemana = ConsolaView.ComprobarDiaSemana(respuestaDia);
            if (diaSemana != null)
                diaValido = true;
            else
                System.out.println("Día de la semana no válido.");
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


        return new Horario(diaSemana, horaInicio, horaFin);
    }

    public int obtenerIdAEliminar() {
        return ConsolaView.leerEntero("Introduce el ID del horario a eliminar:");
    }

    public Horario obtenerDatosActualizados(Horario horario) {
        DiaSemana diaSemana = null;
        boolean diaValido = false;
        while (!diaValido) {
            String respuestaDia = ConsolaView.leerTexto("Introduce el nuevo día de la semana:").toLowerCase();
            diaSemana = ConsolaView.ComprobarDiaSemana(respuestaDia);
            if (diaSemana != null)
                diaValido = true;
            else
                System.out.println("Día de la semana no válido.");
        }
        Time horaInicio = null;
        Time horaFin = null;

        do {
            System.out.println("Recuerda que la hora de fin debe ser posterior a la hora de inicio");
            boolean horaInicioValida = false;
            while (!horaInicioValida) {
                String hora = ConsolaView.leerTexto("Introduce la nueva hora de inicio (HH:mm 24h):");
                horaInicio = ConsolaView.validarYFormatearHora(hora);
                if (horaInicio != null)
                    horaInicioValida = true;
                else
                    System.out.println("Hora no válida. Usa el formato HH:mm 24h.");
            }

            boolean horaFinValida = false;
            while (!horaFinValida) {
                String hora = ConsolaView.leerTexto("Introduce la nueva hora de fin (HH:mm 24h):");
                horaFin = ConsolaView.validarYFormatearHora(hora);
                if (horaFin != null)
                    horaFinValida = true;
                else
                    System.out.println("Hora no válida. Usa el formato HH:mm 24h.");
            }
        } while (horaFin.before(horaInicio) || horaFin.equals(horaInicio));

        return new Horario(horario.getIdHorario(), diaSemana, horaInicio, horaFin);
    }

    public int obtenerIdHorario() {
        return ConsolaView.leerEntero("Introduce el ID del horario:");
    }

    public String obtenerDiaHorario() {
        DiaSemana diaSemana = null;
        boolean diaValido = false;
        while (!diaValido) {
            String respuestaDia = ConsolaView.leerTexto("Introduce el día de la semana para obtener los horarios:").toLowerCase();
            diaSemana = ConsolaView.ComprobarDiaSemana(respuestaDia);
            if (diaSemana != null)
                diaValido = true;
            else
                System.out.println("Día de la semana no válido.");
        }
        return String.valueOf(diaSemana);
    }

    public void mostrarHorario(Horario horario) {
        if (horario != null)
            System.out.println(horario);
        else
            System.out.println("El horario no existe");
    }

    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE HORARIOS");
        System.out.println("1. Alta horario");
        System.out.println("2. Baja horario");
        System.out.println("3. Modificar horario");
        System.out.println("4. Listar todos los horarios");
        System.out.println("5. Mostrar horario por id");
        System.out.println("6. Listar horarios por día");
        System.out.println("7. Mostrar número de horarios registrados");
        System.out.println("0. Volver");
    }
}