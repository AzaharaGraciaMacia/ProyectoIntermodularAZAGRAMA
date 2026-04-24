package model;

import java.sql.Time;

/**
 * La clase {@code Horario} representa un horario reservable en el sistema.
 * Contiene información sobre los horarios disponibles para reservar.
 */
public class Horario {
    private int idHorario;
    private DiaSemana diaSemana;
    private Time HoraInicio;
    private Time HoraFin;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-15s %-30s %-25s %-25s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Horario", "Día de la Semana", "Hora Inicio", "Hora Fin" +
            "\n-------------------------------------------------------------------------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code Horario}.
     * Inicializa el ID del {@code Horario}, día de la semana, horas de inicio y fin.
     * Utilizado para actualizar reservas ya existentes.
     *
     * @param idHorario entero con ID del {@code Horario}, autogenerado por la BD
     * @param diaSemana el día de la semana, dentro del enum {@link DiaSemana}
     * @param horaInicio hora de inicio
     * @param horaFin hora de fin
     */
    public Horario(int idHorario, DiaSemana diaSemana, Time horaInicio, Time horaFin) {
        this.idHorario = idHorario;
        this.diaSemana = diaSemana;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
    }

    /**
     * Constructor para crear una nueva instancia de la clase {@code Horario}.
     * Inicializa el día de la semana, horas de inicio y fin.
     * Utilizado para crear un horario de cero.
     *
     * @param diaSemana el día de la semana, dentro del enum {@link DiaSemana}
     * @param horaInicio hora de inicio
     * @param horaFin hora de fin
     */
    public Horario(DiaSemana diaSemana, Time horaInicio, Time horaFin) {
        this.diaSemana = diaSemana;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
    }

    /**
     * Obtiene el ID del {@code Horario}
     *
     * @return entero con el ID de horario
     */
    public int getIdHorario() {
        return idHorario;
    }

    /**
     * Establece el ID del {@code Horario}
     *
     * @param idHorario entero con el nuevo ID de horario
     */
    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    /**
     * Obtiene el día de la semana del horario
     *
     * @return Enum {@link DiaSemana} con el día de la semana
     */
    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    /**
     * Establece el día de la semana del horario
     *
     * @param diaSemana Enum {@link DiaSemana} con el nuevo día de la semana
     */
    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * Obtiene la hora de inicio
     *
     * @return hora de inicio formato SQL
     */
    public Time getHoraInicio() {
        return HoraInicio;
    }

    /**
     * Establece la hora de inicio
     *
     * @param horaInicio nueva hora de inicio formato SQL
     */
    public void setHoraInicio(Time horaInicio) {
        HoraInicio = horaInicio;
    }

    /**
     * Obtiene la hora de fin
     *
     * @return hora de fin formato SQL
     */
    public Time getHoraFin() {
        return HoraFin;
    }

    /**
     * Establece la hora de fin
     *
     * @param horaFin nueva hora de fin formato SQL
     */
    public void setHoraFin(Time horaFin) {
        HoraFin = horaFin;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Horario}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa el horario.
     */
    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, idHorario, diaSemana, HoraInicio, HoraFin);
    }
}