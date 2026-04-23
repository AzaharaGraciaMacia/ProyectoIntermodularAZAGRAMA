package model;

import java.sql.Time;
import java.sql.Date;

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

    public static final String FORMATO_IMPRESION="%-15s %-15s %-15s %-20s %-20s %-20s %-20s %-20s %-40s %-100s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Reserva" , "ID de Recurso" , "ID de Usuario" , "Fecha de Reserva" , "Hora de Inicio", "Hora de Fin", "Coste (€)", "Número de Plazas", "Motivo", "Observaciones" +
                    "\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


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

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, idReserva, idRecurso, idUsuario, fechaReserva, horaInicio, horaFin, coste, numeroPlazas, motivo, observaciones);
    }
}