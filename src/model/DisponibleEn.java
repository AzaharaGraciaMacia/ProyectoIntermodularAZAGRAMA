package model;

/**
 * La clase {@code DisponibleEn} representa una disponibilidad en el sistema.
 * Contiene información sobre los horarios disponibles para cada recurso.
 */
public class DisponibleEn {
    private String idRecurso;
    private int idHorario;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-25s %-25s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Recurso", "ID de Horario" +
                    "\n-----------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code DisponibleEn}.
     * Inicializa el ID del {@link Recurso} y el ID del {@link Horario}.
     *
     * @param idRecurso texto con el ID del {@link Recurso}
     * @param idHorario entero con el ID del {@link Horario}
     */
    public DisponibleEn(String idRecurso, int idHorario) {
        this.idRecurso = idRecurso;
        this.idHorario = idHorario;
    }

    /**
     * Obtiene el ID del {@link Recurso}
     *
     * @return texto con el ID de recurso
     */
    public String getIdRecurso() {
        return idRecurso;
    }

    /**
     * Establece el ID del {@link Recurso}
     *
     * @param idRecurso texto con el nuevo ID de recurso
     */
    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    /**
     * Obtiene el ID del {@link Horario}
     *
     * @return entero con el ID de horario
     */
    public int getIdHorario() {
        return idHorario;
    }

    /**
     * Establece el ID del {@link Horario}
     *
     * @param idHorario entero con el nuevo ID de horario
     */
    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code DisponibleEn}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa la disponibilidad.
     */
    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, idRecurso, idHorario);
    }
}