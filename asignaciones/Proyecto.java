package asignaciones;

import java.util.ArrayDeque;
import java.util.Calendar;

public class Proyecto extends Asignacion {
    
    private ArrayDeque<Tarea> tareas;

    public Proyecto(String nombre, String descripcion, byte puntaje, Calendar fecha) {
        super(nombre, descripcion, puntaje, fecha);
        tareas = new ArrayDeque<Tarea>();
    }

    public void addTarea(Tarea nuevaTarea) {
        tareas.add(nuevaTarea);
    }
        
}
