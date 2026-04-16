package view;

import model.Recurso;

import java.util.List;
import java.util.Scanner;

public class RecursoView {
    public Scanner scanner;

    public RecursoView() {
        scanner = new Scanner(System.in);
    }

    public void mostrarRecursos(List<Recurso> recursos) {
        System.out.println("Lista de Recursos:");

        System.out.printf("%-25s %-30s %-50s %-30s %-20s\n", "ID de Recurso" , "Nombre de Recurso" , "Descripción" , "Ubicación", "Capacidad");

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        recursos.forEach(System.out::println);

    }

    public Recurso crearRecurso() {

        System.out.println("Introduce el código del recurso:");
        String idRecurso = scanner.nextLine();
        System.out.println("Introduce el nombre del recurso:");
        String nombreRecurso = scanner.nextLine();
        System.out.println("Introduce la descripción del recurso:");
        String descripcion = scanner.nextLine();
        System.out.println("Introduce la ubicación del recurso:");
        String ubicacionRecurso = scanner.nextLine();
        System.out.println("Introduce la capacidad del recurso:");
        int capacidadRecurso = scanner.nextInt();

        return new Recurso(idRecurso, nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }

    public String obtenerIdAEliminar() {
        System.out.println("Introduce el ID del recurso a eliminar:");
        return scanner.nextLine();
    }

    public Recurso obtenerDatosActualizados(Recurso recurso) {
        System.out.println("Introduce el nuevo nombre del recurso:");
        String nombreRecurso = scanner.nextLine();
        System.out.println("Introduce la nueva descripción del recurso:");
        String descripcion = scanner.nextLine();
        System.out.println("Introduce la nueva ubicación del recurso:");
        String ubicacionRecurso = scanner.nextLine();
        System.out.println("Introduce la nueva capacidad del recurso:");
        int capacidadRecurso = scanner.nextInt();

        return new Recurso(recurso.getIdRecurso(), nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String obtenerIdRecurso() {
        System.out.println("Introduce el ID del recurso:");
        return scanner.nextLine();
    }


    public void mostrarRecurso(Recurso recurso) {
        if (recurso != null)
            System.out.println(recurso.toString());
        else
            System.out.println("El recurso no existe");
    }

    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE RECURSOS");
        System.out.println("1. Alta recurso");
        System.out.println("2. Baja recurso");
        System.out.println("3. Modificar recurso");
        System.out.println("4. Listar todos los recursos");
        System.out.println("5. Mostrar recurso por id");
        System.out.println("0. Volver");
    }

}