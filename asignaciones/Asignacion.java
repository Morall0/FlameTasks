package asignaciones;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public abstract class Asignacion implements Serializable {
    private int puntaje;
    private byte importancia;
    private byte progreso;
    private String nombre, descripcion;
    private Calendar fecha;
    private boolean completada;

    public Asignacion(String nombre, String descripcion, int puntaje, Calendar fecha, byte importancia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.puntaje = puntaje;
        this.importancia = importancia;
        progreso = 0;
        completada = false;
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

    public String getStringFecha() {
        SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return formato.format(this.fecha.getTime());
    }

    public int getPuntaje() {
        return puntaje;
    }

    public byte getImportancia() {
        return importancia;
    }

    public byte getProgreso() {
        return progreso;
    }

    public boolean getCompletada() {
        return completada;
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

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public void setImportancia(byte importancia) {
        this.importancia = importancia;
    }

    public void setProgreso(byte progreso) {
        this.progreso = progreso;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void addPuntaje(int puntaje) {
        this.puntaje += puntaje > 0 ? puntaje : 0;
    }
}
