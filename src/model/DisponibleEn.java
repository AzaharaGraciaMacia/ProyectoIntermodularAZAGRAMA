package model;

public class DisponibleEn {
    private String idRecurso;
    private int idHorario;

    public DisponibleEn(String idRecurso, int idHorario) {
        this.idRecurso = idRecurso;
        this.idHorario = idHorario;
    }

    public DisponibleEn(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public DisponibleEn(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    @Override
    public String toString() {
        return String.format("%-25s %-25s\n", idRecurso, idHorario);
    }
}