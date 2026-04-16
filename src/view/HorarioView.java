package view;

import model.DiaSemana;
import model.Horario;

import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class HorarioView {
    public Scanner scanner;

    public HorarioView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarHorarios(List<Horario> horarios) {
        System.out.println("Lista de Horarios:");

        System.out.printf("%-15s %-30s %-25s %-25s\n", "ID del Horario" , "Día de la semana" , "Hora de Inicio" , "Hora de Fin");

        System.out.println("---------------------------------------------------------------------------------------------------");

        horarios.forEach(System.out::println);

    }

    public Horario crearHorario() {

        System.out.println("Introduce el día de la semana:");
        DiaSemana diaSemana = DiaSemana.valueOf(scanner.nextLine().toLowerCase());
        System.out.println("Introduce la hora de inicio:");
        Time horaInicio = Time.valueOf(scanner.nextLine());
        System.out.println("Introduce la hora de fin:");
        Time horaFin = Time.valueOf(scanner.nextLine());

        return new Horario(diaSemana, horaInicio, horaFin);
    }

    public int obtenerIdAEliminar() {
        System.out.println("Introduce el ID del horario a eliminar:");
        return scanner.nextInt();
    }

    public Horario obtenerDatosActualizados(Horario horario) {
        System.out.println("Introduce el nuevo día de la semana:");
        DiaSemana diaSemana = DiaSemana.valueOf(scanner.nextLine().toLowerCase());
        System.out.println("Introduce la nueva hora de inicio:");
        Time horaInicio = Time.valueOf(scanner.nextLine());
        System.out.println("Introduce la nueva hora de fin:");
        Time horaFin = Time.valueOf(scanner.nextLine());

        return new Horario(horario.getIdHorario(), diaSemana, horaInicio, horaFin);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public int obtenerIdHorario() {
        System.out.println("Introduce el ID del recurso:");
        return scanner.nextInt();
    }

    public String obtenerDiaHorario() {
        System.out.println("Introduce el día de la semana:");
        return scanner.nextLine().toLowerCase();
    }

    public void mostrarHorario(Horario horario) {
        if (horario != null)
            System.out.println(horario.toString());
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
        System.out.println("0. Volver");
    }
}