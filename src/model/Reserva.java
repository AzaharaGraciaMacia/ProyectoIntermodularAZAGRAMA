package model;

import java.sql.Time;
import java.util.Date;

public class Reserva {
    private int idRecurso;
    private int idReserva;
    private int idUsuario;
    private Date fechaReserva;
    private Time horaInicio;
    private Time horaFin;
    private double coste;
    private int numeroPlazas;
    private String motivo;
    private String observaciones;

    private Reserva (ReservaBuilder builder) {
        this.idRecurso = builder.idRecurso;
        this.idReserva = builder.idReserva;
        this.idUsuario = builder.idUsuario;
        this.fechaReserva = builder.fechaReserva;
        this.horaInicio = builder.horaInicio;
        this.horaFin = builder.horaFin;
        this.coste = builder.coste;
        this.numeroPlazas = builder.numeroPlazas;
        this.motivo = builder.motivo;
        this.observaciones = builder.observaciones;
    }
    public static class ReservaBuilder{
        private final int idRecurso;
        private final int idReserva;
        private final int idUsuario;
        private final Date fechaReserva;
        private final Time horaInicio;
        private final Time horaFin;
        private double coste;
        private int numeroPlazas;
        private String motivo;
        private String observaciones;

        public ReservaBuilder(int idRecurso, int idReserva, int idUsuario, Date fechaReserva,  Time horaInicio, Time horaFin) {
            this.idRecurso = idRecurso;
            this.idReserva = idReserva;
            this.idUsuario = idUsuario;
            this.fechaReserva = fechaReserva;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
        }
        public ReservaBuilder coste(double coste) {
            this.coste = coste;
            return this;
        }
        public ReservaBuilder numeroPlazas(int numeroPlazas) {
            this.numeroPlazas = numeroPlazas;
            return this;
        }
        public ReservaBuilder motivo(String motivo) {
            this.motivo = motivo;
            return this;
        }
        public ReservaBuilder observaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public Reserva build() {
            return new Reserva(this);
        }
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
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
}