package model;

public class Recurso {
    private String idRecurso;
    private String nombreRecurso;
    private String descripcion;
    private String ubicacionRecurso;
    private int capacidadRecurso;

    public Recurso(String idRecurso, String nombreRecurso, String descripcion, String ubicacionRecurso, int capacidadRecurso) {
        this.idRecurso = idRecurso;
        this.nombreRecurso = nombreRecurso;
        this.descripcion = descripcion;
        this.ubicacionRecurso = ubicacionRecurso;
        this.capacidadRecurso = capacidadRecurso;
    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacionRecurso() {
        return ubicacionRecurso;
    }

    public void setUbicacionRecurso(String ubicacionRecurso) {
        this.ubicacionRecurso = ubicacionRecurso;
    }

    public int getCapacidadRecurso() {
        return capacidadRecurso;
    }

    public void setCapacidadRecurso(int capacidadRecurso) {
        this.capacidadRecurso = capacidadRecurso;
    }
}