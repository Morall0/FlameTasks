package usuarios;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.PriorityQueue;

import asignaciones.*;
import java.util.Collections;

public class Usuario implements Serializable{
    private int puntaje;
    private String nombre, apellido, usuario;
    private Administrador admin;
    private PriorityQueue<Asignacion> asignaciones;

    public Usuario(String nombre, String apellido, String usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.admin = null;
        asignaciones = new PriorityQueue<Asignacion>();
    }

    // getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public Administrador getAdmin() {
        return admin;
    }

    // setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPseudonimo(String usuario) {
        this.usuario = usuario;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    // Métodos extra

    public boolean existeAsignacion(String nombre) {
        for(Asignacion asignacion: asignaciones) {
            if (asignacion.getNombre().equals(nombre))
                return true;
        }         
        return false;
    }
    
    public Asignacion buscarAsignacion(String nombre) {
        PriorityQueue<Asignacion> tempQueue = new PriorityQueue<Asignacion>(asignaciones);
        while(!tempQueue.peek().getNombre().equalsIgnoreCase(nombre)){
            tempQueue.poll();
        }
      
        return tempQueue.poll();
    } 

    public void addAsignacion(Asignacion asignacion) {
        asignaciones.add(asignacion);
    }

    public boolean hayAsignaciones() {
        return !asignaciones.isEmpty();
    }

    public boolean hayProyectos() {
        for (Asignacion asignacion : asignaciones)
            if(asignacion.getClass().getSimpleName().equals("Proyecto"))
                return true;
        return false;
    }

    public void listarProyectos() {
        Iterator<Asignacion> iterator = asignaciones.iterator();

        while (iterator.hasNext()) {
            Asignacion asignacion = iterator.next();

            if(asignacion.getClass().getSimpleName().equals("Proyecto")) {
                System.out.println("Nombre: " + asignacion.getNombre());
                System.out.println("Fecha: " + asignacion.getStringFecha());
                System.out.println();
            } 
        }
    }

    public void listarTareas() {
        Iterator<Asignacion> iterator = asignaciones.iterator();

        while (iterator.hasNext()) {
            Asignacion asignacion = iterator.next();
            
            if(asignacion.getClass().getSimpleName().equals("Tarea")) {
                System.out.println(((Tarea)asignacion).toString());
            } 
        }
    }

    public void listarTodo() {
        Iterator<Asignacion> iterator = asignaciones.iterator();

        while (iterator.hasNext()) {
            Asignacion asignacion = iterator.next();

            if(asignacion.getClass().getSimpleName().equals("Tarea")) {
                System.out.println(((Tarea)asignacion).toString());
            } else {
                ArrayDeque<Tarea> tareas = ((Proyecto)asignacion).getTareas();
                for(Tarea tarea: tareas)
                    System.out.println(tarea.toString()+"\t"+asignacion.getNombre());
                System.out.println();
            }
        }
    }
    
    //public void listarPorFecha(){
    //    DateBasedComparator DBComp = new DateBasedComparator();
    //    PriorityQueue<Asignacion> tempQueue = new PriorityQueue<Asignacion>(asignaciones);
    //    Collections.sort(tempQueue, DBComp);
    //    Iterator<Asignacion> iterator = tempQueue.iterator();
    //    
    //    while (iterator.hasNext()) {
    //        Asignacion asignacion = iterator.next();

    //        if(asignacion.getClass().getSimpleName().equals("Tarea")) {
    //            System.out.println(((Tarea)asignacion).toString());
    //        } else {
    //            ArrayDeque<Tarea> tareas = ((Proyecto)asignacion).getTareas();
    //            for(Tarea tarea: tareas)
    //                System.out.println(tarea.toString()+"\t"+asignacion.getNombre());
    //            System.out.println();
    //        }
    //    }
    //}
    
    //public void listarPorImportancia(){
    //    ImportanceBasedComparator IBComp = new ImportanceBasedComparator();
    //    PriorityQueue<Asignacion> tempQueue = new PriorityQueue<Asignacion>(asignaciones);
    //    Collections.sort(tempQueue, IBComp);
    //    
    //    Iterator<Asignacion> iterator = tempQueue.iterator();
    //    
    //    while (iterator.hasNext()) {
    //        Asignacion asignacion = iterator.next();

    //        if(asignacion.getClass().getSimpleName().equals("Tarea")) {
    //            System.out.println(((Tarea)asignacion).toString());
    //        } else {
    //            ArrayDeque<Tarea> tareas = ((Proyecto)asignacion).getTareas();
    //            for(Tarea tarea: tareas)
    //                System.out.println(tarea.toString()+"\t"+asignacion.getNombre());
    //            System.out.println();
    //        }
    //    }
    //    
    //}
}
