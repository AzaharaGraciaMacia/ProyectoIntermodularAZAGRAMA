package view;

import model.DisponibleEn;
import model.Horario;
import model.Recurso;

import java.util.List;

public class RecursoView {


    public void mostrarRecursos(List<Recurso> recursos) {
        System.out.println("Lista de Recursos:");

        System.out.printf("%-25s %-30s %-50s %-30s %-20s\n", "ID de Recurso" , "Nombre de Recurso" , "Descripción" , "Ubicación", "Capacidad");

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        recursos.forEach(System.out::println);

    }

    public Recurso crearRecurso() {

        String idRecurso = ConsolaView.leerTexto("Introduce el código del recurso:");
        String nombreRecurso = ConsolaView.leerTexto("Introduce el nombre del recurso:");
        String descripcion = ConsolaView.leerTexto("Introduce la descripción del recurso:");
        String ubicacionRecurso = ConsolaView.leerTexto("Introduce la ubicación del recurso:");
        int capacidadRecurso = ConsolaView.leerEntero("Introduce la capacidad del recurso:");

        return new Recurso(idRecurso, nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }

    public String obtenerIdAEliminar() {
        return ConsolaView.leerTexto("Introduce el ID del recurso a eliminar:");
    }

    public Recurso obtenerDatosActualizados(Recurso recurso) {
        String nombreRecurso = ConsolaView.leerTexto("Introduce el nuevo nombre del recurso:");
        String descripcion = ConsolaView.leerTexto("Introduce la nueva descripción del recurso:");
        String ubicacionRecurso = ConsolaView.leerTexto("Introduce la nueva ubicación del recurso:");
        int capacidadRecurso = ConsolaView.leerEntero("Introduce la nueva capacidad del recurso:");

        return new Recurso(recurso.getIdRecurso(), nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }

    public String obtenerIdRecurso() {
        return ConsolaView.leerTexto("Introduce el ID del recurso:");
    }


    public void mostrarRecurso(Recurso recurso) {
        if (recurso != null)
            System.out.println(recurso);
        else
            System.out.println("El recurso no existe");
    }

    public DisponibleEn crearDisponibilidad(Recurso recurso) {
        int horario = ConsolaView.leerEntero("Introduce el id del horario a asignar:");
        return new DisponibleEn(recurso.getIdRecurso(), horario);
    }

    public void mostrarDisponiblesEn(List<Horario> horarios) {
        System.out.println("Lista de horarios disponibles en el recurso:");

        System.out.printf("%-15s %-30s %-25s %-25s\n", "ID de Horario", "Día de la Semana", "Hora Inicio", "Hora Fin");

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        horarios.forEach(System.out::println);
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