package asignaciones;

import java.util.Calendar;

public class Tareas extends Asignacion {

    byte importancia;

    public Tareas(String nombre, String descripcion, Calendar fecha, byte importancia) {
        super(nombre, descripcion, fecha);
        this.importancia = importancia;
    }



}
