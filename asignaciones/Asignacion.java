package asignaciones;

import java.util.Calendar;

public abstract class Asignacion {
    private byte puntaje;
    private String nombre, descripcion;
    private Calendar fecha;

    public Asignacion(String nombre, String descripcion, Calendar fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        puntaje = 0;
    }

    // getters

    public String getNombre() {
        return nombre;
    } 

    public String getDescripcion() {
        return descripcion;
    }

    public Calendar getFecha() {
        return fecha;
    }

    // setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public void addPuntaje(byte puntaje) {
        this.puntaje += puntaje > 0 ? puntaje : 0;
    }
}
