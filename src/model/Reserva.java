package model;

import java.sql.Time;
import java.sql.Date;

/**
 * La clase {@code Reserva} representa una reserva en el sistema.
 * Contiene información sobre el {@link Recurso} reservado, el {@link Usuario} que lo reserva y los detalles de la reserva.
 */
public class Reserva {
    private String idRecurso;
    private int idReserva;
    private int idUsuario;
    private Date fechaReserva;
    private Time horaInicio;
    private Time horaFin;
    private double coste;
    private int numeroPlazas;
    private String motivo;
    private String observaciones;

    /**
     * Cabecera para imprimir listados
     */
    public static final String FORMATO_IMPRESION="%-15s %-15s %-15s %-20s %-20s %-20s %-20s %-20s %-40s %-100s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Reserva" , "ID de Recurso" , "ID de Usuario" , "Fecha de Reserva" , "Hora de Inicio", "Hora de Fin", "Coste (€)", "Número de Plazas", "Motivo", "Observaciones" +
                    "\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    /**
     * Constructor para crear una nueva instancia de la clase {@code Reserva}.
     * Inicializa el ID del {@link Recurso}, ID de la {@code Reserva}, ID del {@link Usuario}, fecha de la reserva, horas de inicio y fin, coste, número de personas, motivo y observaciones.
     * Utilizado para actualizar reservas ya existentes.
     *
     * @param idRecurso texto con ID del {@link Recurso} a reservar
     * @param idReserva entero con el ID de la {@code Reserva}, autogenerado por la BD
     * @param idUsuario entero con el ID del {@link Usuario} que reserva
     * @param fechaReserva fecha de la reserva
     * @param horaInicio hora de inicio de la reserva
     * @param horaFin hora de fin de la reserva
     * @param coste número en formato decimal con el coste de la reserva
     * @param numeroPlazas entero positivo con el número de asistentes
     * @param motivo texto con el motivo de la reserva
     * @param observaciones texto con las observaciones, puede ser {@code null}
     */
    public Reserva(String idRecurso, int idReserva, int idUsuario, Date fechaReserva, Time horaInicio, Time horaFin, double coste, int numeroPlazas, String motivo, String observaciones) {
        this.idRecurso = idRecurso;
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.coste = coste;
        this.numeroPlazas = numeroPlazas;
        this.motivo = motivo;
        this.observaciones = observaciones;
    }

    /**
     * Constructor para crear una nueva instancia de la clase {@code Reserva}.
     * Inicializa el ID del {@link Recurso}, ID del {@link Usuario}, fecha de la reserva, horas de inicio y fin, coste, número de personas, motivo y observaciones.
     * Utilizado para crear reservas de cero
     *
     * @param idRecurso texto con ID del {@link Recurso} a reservar
     * @param idUsuario entero con el ID del {@link Usuario} que reserva
     * @param fechaReserva fecha de la reserva
     * @param horaInicio hora de inicio de la reserva
     * @param horaFin hora de fin de la reserva
     * @param coste número en formato decimal con el coste de la reserva
     * @param numeroPlazas entero positivo con el número de asistentes
     * @param motivo texto con el motivo de la reserva
     * @param observaciones texto con las observaciones, puede ser {@code null}
     */
    public Reserva(String idRecurso, int idUsuario, Date fechaReserva, Time horaInicio, Time horaFin, double coste, int numeroPlazas, String motivo, String observaciones) {
        this.idRecurso = idRecurso;
        this.idUsuario = idUsuario;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.coste = coste;
        this.numeroPlazas = numeroPlazas;
        this.motivo = motivo;
        this.observaciones = observaciones;
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
     * Obtiene el ID de la {@code Reserva}
     *
     * @return entero con el ID de la reserva, autogenerado por la BD
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Establece el ID de la {@code Reserva}
     *
     * @param idReserva entero con el nuevo ID de la reserva, autogenerado por la BD
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Obtiene el ID del {@link Usuario}
     *
     * @return entero con el ID del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el ID del {@link Usuario}
     *
     * @param idUsuario entero con el nuevo ID del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene la fecha de la reserva
     *
     * @return fecha formato SQL de reserva
     */
    public Date getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Establece la fecha de la reserva
     *
     * @param fechaReserva nueva fecha formato SQL de la reserva
     */
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * Obtiene la hora de inicio de la reserva
     *
     * @return hora de inicio formato SQL de reserva
     */
    public Time getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece la hora de inicio de la reserva
     *
     * @param horaInicio nueva hora de inicio formato SQL de la reserva
     */
    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de fin de la reserva
     *
     * @return hora de fin formato SQL de reserva
     */
    public Time getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de fin de la reserva
     *
     * @param horaFin nueva hora de fin formato SQL de la reserva
     */
    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el coste de la reserva
     *
     * @return número decimal con el importe de la reserva
     */
    public double getCoste() {
        return coste;
    }

    /**
     * Establece el coste de la reserva
     *
     * @param coste número decimal con el importe de la reserva
     */
    public void setCoste(double coste) {
        this.coste = coste;
    }

    /**
     * Obtiene el número de personas que incluye la reserva
     *
     * @return número entero con el número de personas que incluye la reserva
     */
    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    /**
     * Establece el número de personas que incluye la reserva
     *
     * @param numeroPlazas número entero con el nuevo número de personas que incluye la reserva
     */
    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    /**
     * Obtiene el motivo de la reserva
     *
     * @return texto con el motivo de la reserva
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Establece el motivo de la reserva
     *
     * @param motivo texto con el nuevo motivo de reserva
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * Obtiene las observaciones de la reserva
     *
     * @return texto con las observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones de la reserva
     *
     * @param observaciones texto con las nuevas observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Reserva}.
     * Actualmente, devuelve una cadena con formato de tabla.
     *
     * @return Una cadena que representa la reserva.
     */
    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, idReserva, idRecurso, idUsuario, fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }
}