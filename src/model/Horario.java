package model;

import java.sql.Time;

public class Horario {
    private int idHorario;
    private DiaSemana diaSemana;
    private Time HoraInicio;
    private Time HoraFin;

    public static final String FORMATO_IMPRESION="%-15s %-30s %-25s %-25s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Horario", "Día de la Semana", "Hora Inicio", "Hora Fin" +
            "\n-------------------------------------------------------------------------------------------------------------------");


    public Horario(int idHorario, DiaSemana diaSemana, Time horaInicio, Time horaFin) {
        this.idHorario = idHorario;
        this.diaSemana = diaSemana;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
    }

    public Horario(DiaSemana diaSemana, Time horaInicio, Time horaFin) {
        this.diaSemana = diaSemana;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Time getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        HoraInicio = horaInicio;
    }

    public Time getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(Time horaFin) {
        HoraFin = horaFin;
    }

    @Override
    public String toString() {
        return String.format(FORMATO_IMPRESION, idHorario, diaSemana, HoraInicio, HoraFin);
    }
}