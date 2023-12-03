package asignaciones;

import java.util.ArrayDeque;
import java.util.Calendar;

public class Proyecto extends Asignacion {
    
    private ArrayDeque<Tarea> tareas;

    public Proyecto(String nombre, String descripcion, Calendar fecha) {
        super(nombre, descripcion, 0, fecha, (byte) 0);
        tareas = new ArrayDeque<Tarea>();
    }

    public void addTarea(Tarea nuevaTarea) {
        tareas.add(nuevaTarea);
    }

    public void updatePuntaje() {
        for(Tarea tarea : tareas) {
            if (tarea.getCompletada())
                addPuntaje(tarea.getPuntaje());
        } 
    }

    public void showProyecto() {
        System.out.println("Proyecto: "+this.getNombre());
        System.out.println(this.getDescripcion());
        System.out.println("Fecha: "+this.getStringFecha());
        System.out.println("Tareas:");
        if (!tareas.isEmpty()) {
            for(Tarea tarea: tareas) {
                String nombre = tarea.getNombre();
                String fecha = tarea.getStringFecha();
                System.out.println(nombre+"\t"+fecha);
            }
            System.out.println();
        } else {
            System.out.println("No hay tareas en el proyecto\n");
        }
    }

    public ArrayDeque<Tarea> getTareas() {
        return this.tareas;
    }
}
