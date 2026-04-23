package model;

public class Recurso {
    private String idRecurso;
    private String nombreRecurso;
    private String descripcion;
    private String ubicacionRecurso;
    private int capacidadRecurso;

    public static final String FORMATO_IMPRESION="%-25s %-30s %-50s %-30s %-20s\n";
    public static final String CABECERA_LISTADO= String.format(FORMATO_IMPRESION,
            "ID de Recurso", "Nombre", "Descripción", "Ubicación", "Capacidad" +
                    "\n------------------------------------------------------------------------------------------------------------------------------------------------------");

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

    @Override
    public String toString() {
       return String.format(FORMATO_IMPRESION, idRecurso, nombreRecurso, descripcion, ubicacionRecurso, capacidadRecurso);
    }
}