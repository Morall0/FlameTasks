package asignaciones;

import java.util.Calendar;

public class Tarea extends Asignacion {

    public Tarea(String nombre, String descripcion, int puntaje, Calendar fecha, byte importancia) {
        super(nombre, descripcion, puntaje, fecha, importancia);
    }

    @Override
    public String toString(){
        return getNombre()+"\t"+getStringFecha();
    }


}
