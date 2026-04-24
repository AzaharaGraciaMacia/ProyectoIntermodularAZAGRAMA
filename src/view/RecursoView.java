package view;

import model.DisponibleEn;
import model.Horario;
import model.Recurso;

import java.util.List;

/**
 * Vista de consola para la gestión de recursos.
 */
public class RecursoView {

    /**
     * Muestra una tabla con los recursos
     *
     * @param recursos Lista de recursos a mostrar
     */
    public void mostrarRecursos(List<Recurso> recursos) {
        System.out.println("Lista de Recursos:\n");

        ConsolaView.mostrar(Recurso.CABECERA_LISTADO);

        recursos.forEach(System.out::println);

    }

    /**
     * Crea un objeto {@link Recurso} a partir de la lectura de datos por consola
     *
     * @return objeto recurso
     */
    public Recurso crearRecurso() {

        String idRecurso = ConsolaView.leerTexto("Introduce el código del recurso:");
        String nombreRecurso = ConsolaView.leerTexto("Introduce el nombre del recurso:");
        String descripcion = ConsolaView.leerTexto("Introduce la descripción del recurso:");
        String ubicacionRecurso = ConsolaView.leerTexto("Introduce la ubicación del recurso:");
        int capacidadRecurso = ConsolaView.leerEntero("Introduce la capacidad del recurso:");

        return new Recurso(idRecurso, nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }

    /**
     * Pide al usuario que introduzca por consola el ID del recurso que desea eliminar
     *
     * @return String ID de recurso
     */
    public String obtenerIdAEliminar() {
        return ConsolaView.leerTexto("Introduce el ID del recurso a eliminar:");
    }

    /**
     * Crea un nuevo objeto {@link Recurso} a partir de otro con los datos actualizados leídos por consola
     *
     * @param recurso recurso que se desea actualizar
     * @return objeto recurso actualizado
     */
    public Recurso obtenerDatosActualizados(Recurso recurso) {
        String nombreRecurso = ConsolaView.leerTexto("Introduce el nuevo nombre del recurso:");
        String descripcion = ConsolaView.leerTexto("Introduce la nueva descripción del recurso:");
        String ubicacionRecurso = ConsolaView.leerTexto("Introduce la nueva ubicación del recurso:");
        int capacidadRecurso = ConsolaView.leerEntero("Introduce la nueva capacidad del recurso:");

        return new Recurso(recurso.getIdRecurso(), nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }

    /**
     * Pide al usuario que introduzca por consola el ID del recurso que desea recuperar
     *
     * @return String ID de horario
     */
    public String obtenerIdRecurso() {
        return ConsolaView.leerTexto("Introduce el ID del recurso:");
    }

    /**
     * Muestra una tabla con el recurso solicitado
     *
     * @param recurso horario que se desea visualizar
     */
    public void mostrarRecurso(Recurso recurso) {
        if (recurso != null){
            ConsolaView.mostrar(Recurso.CABECERA_LISTADO);
            System.out.println(recurso);}
        else
            System.out.println("El recurso no existe");
    }

    /**
     * Crea un objeto {@link DisponibleEn} para asociar un recurso a un horario
     *
     * @param recurso recurso al que se le quiere crear una disponibilidad
     * @return objeto disponibleEn
     */
    public DisponibleEn crearDisponibilidad(Recurso recurso) {
        int horario = ConsolaView.leerEntero("Introduce el id del horario a asignar:");
        return new DisponibleEn(recurso.getIdRecurso(), horario);
    }

    /**
     * Muestra una tabla con los horarios en los que está disponible un recurso
     *
     * @param horarios Lista de horarios disponibles
     */
    public void mostrarDisponiblesEn(List<Horario> horarios) {
        System.out.println("Lista de horarios disponibles en el recurso:\n");

        ConsolaView.mostrar(Horario.CABECERA_LISTADO);

        horarios.forEach(System.out::println);
    }

    /**
     * Muestra el menú de recursos
     */
    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE RECURSOS");
        System.out.println("1. Alta recurso");
        System.out.println("2. Baja recurso");
        System.out.println("3. Modificar recurso");
        System.out.println("4. Listar todos los recursos");
        System.out.println("5. Mostrar recurso por id");
        System.out.println("6. Mostrar cantidad de recursos.");
        System.out.println("0. Volver");
    }

}