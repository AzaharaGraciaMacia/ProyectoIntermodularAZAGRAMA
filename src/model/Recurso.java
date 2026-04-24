package model;

/**
 * La clase {@code Recurso} representa un recurso reservable en el sistema.
 * Contiene información sobre los recursos disponibles para reservar.
 */
public class Recurso {
    private String idRecurso;
    private String nombreRecurso;
    private String descripcion;
    private String ubicacionRecurso;
    private int capacidadRecurso;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-25s %-30s %-50s %-30s %-20s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Recurso", "Nombre", "Descripción", "Ubicación", "Capacidad" +
                    "\n------------------------------------------------------------------------------------------------------------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code Recurso}.
     * Inicializa el ID del recurso, su nombre, la descripción, ubicación y capacidad del recurso
     *
     * @param idRecurso texto con el ID del {@code Recurso}
     * @param nombreRecurso texto con el nombre del recurso
     * @param descripcion texto con la descripción del recurso
     * @param ubicacionRecurso texto con la ubicación del recurso
     * @param capacidadRecurso entero con la capacidad máxima del recurso
     */
    public Recurso(String idRecurso, String nombreRecurso, String descripcion, String ubicacionRecurso, int capacidadRecurso) {
        this.idRecurso = idRecurso;
        this.nombreRecurso = nombreRecurso;
        this.descripcion = descripcion;
        this.ubicacionRecurso = ubicacionRecurso;
        this.capacidadRecurso = capacidadRecurso;
    }

    /**
     * Obtiene el ID del {@code Recurso}
     *
     * @return texto con el ID de recurso
     */
    public String getIdRecurso() {
        return idRecurso;
    }

    /**
     * Establece el ID del {@code Recurso}
     *
     * @param idRecurso texto con el nuevo ID de recurso
     */
    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    /**
     * Obtiene el nombre del {@code Recurso}
     *
     * @return texto con el nombre del recurso
     */
    public String getNombreRecurso() {
        return nombreRecurso;
    }

    /**
     * Establece el nombre del {@code Recurso}
     *
     * @param nombreRecurso texto con el nuevo nombre del recurso
     */
    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    /**
     * Obtiene la descripción del {@code Recurso}
     *
     * @return texto con la descripción del recurso
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del {@code Recurso}
     *
     * @param descripcion texto con la nueva descripción del recurso
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la ubicación del {@code Recurso}
     *
     * @return texto la ubicación del recurso
     */
    public String getUbicacionRecurso() {
        return ubicacionRecurso;
    }

    /**
     * Establece la ubicación del{@code Recurso}
     *
     * @param ubicacionRecurso texto con la nueva ubicación del recurso
     */
    public void setUbicacionRecurso(String ubicacionRecurso) {
        this.ubicacionRecurso = ubicacionRecurso;
    }

    /**
     * Obtiene la capacidad máxima del {@code Recurso}
     *
     * @return entero con la capacidad del recurso
     */
    public int getCapacidadRecurso() {
        return capacidadRecurso;
    }

    /**
     * Establece la capacidad máxima del {@code Recurso}
     *
     * @param capacidadRecurso entero con la nueva capacidad del recurso
     */
    public void setCapacidadRecurso(int capacidadRecurso) {
        this.capacidadRecurso = capacidadRecurso;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Recurso}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa el recurso.
     */
    @Override
    public String toString() {
       return String.format(FORMATO_IMPRESION, idRecurso, nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }
}