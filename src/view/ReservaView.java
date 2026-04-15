package view;

import model.Reserva;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class ReservaView {
    public Scanner scanner;

    public ReservaView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarReservas(List<Reserva> reservas) {
        System.out.println("Lista de Reservas:");
        // Cabecera
        System.out.printf("%-15s %-15s %-15s %-15s %-10s %-10s %-10s %-15s %-50s %-100s\n","ID de Reserva" , "ID de Recurso" , "ID de Usuario" , "Fecha de Reserva" , "Hora de Inicio", "Hora de Fin", "Coste (€)", "Número de Plazas", "Motivo", "Observaciones");

        System.out.println("---------------------------------------------------------------------");

        reservas.forEach(System.out::println);

    }

    public Reserva crearReserva() {

        System.out.println("Introduce el código del recurso:");
        String idRecurso = scanner.nextLine();
        System.out.println("Introduce el id del usuario:");
        int idUsuario = scanner.nextInt();
        System.out.println("Introduce la fecha de la reserva (AAAA-MM-DD):");
        Date fechaReserva = Date.valueOf(scanner.nextLine());
        System.out.println("Introduce la hora de inicio:");
        Time horaInicio = Time.valueOf(scanner.nextLine());
        System.out.println("Introduce la hora final:");
        Time horaFin = Time.valueOf(scanner.nextLine());
        System.out.println("Introduce el coste de la reserva:");
        double coste = scanner.nextDouble();
        System.out.println("Introduce el número de plazas:");
        int numeroPlazas = scanner.nextInt();
        System.out.println("Introduce el motivo de la reserva:");
        String motivo = scanner.nextLine();
        System.out.println("Introduce las observaciones:");
        String observaciones = scanner.nextLine();

        return new Reserva(idRecurso, idUsuario, fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }

    public int obtenerIdAEliminar() {
        System.out.println("Introduce el ID de la reserva a eliminar:");
        return scanner.nextInt();
    }

    public Reserva obtenerDatosActualizados(Reserva reserva) {
        System.out.println("Introduce el nuevo código del recurso:");
        String idRecurso = scanner.nextLine();
        System.out.println("Introduce la nueva fecha de la reserva (AAAA-MM-DD):");
        Date fechaReserva = Date.valueOf(scanner.nextLine());
        System.out.println("Introduce la nueva hora de inicio:");
        Time horaInicio = Time.valueOf(scanner.nextLine());
        System.out.println("Introduce la nueva hora final:");
        Time horaFin = Time.valueOf(scanner.nextLine());
        System.out.println("Introduce el nuevo coste de la reserva:");
        double coste = scanner.nextDouble();
        System.out.println("Introduce el nuevo número de plazas:");
        int numeroPlazas = scanner.nextInt();
        System.out.println("Introduce el motivo de la reserva:");
        String motivo = scanner.nextLine();
        System.out.println("Introduce las observaciones:");
        String observaciones = scanner.nextLine();

        return new Reserva(idRecurso, reserva.getIdUsuario(), fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String obtenerIdReserva() {
        System.out.println("Introduce el ID de la reserva:");
        return scanner.nextLine();
    }
    public String obtenerIdUsuario() {
        System.out.println("Introduce el DNI del usuario:");
        return scanner.nextLine();
    }

    //muestra los datos de la persona
    public void mostrarReserva(Reserva reserva) {
        if (reserva != null)
            System.out.println(reserva.toString());
        else
            System.out.println("La persona no existe");
    }

    // Otros métodos de la vista...
}