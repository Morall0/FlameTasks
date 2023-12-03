package usuarios;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


import asignaciones.*;
import asignaciones.Proyecto;

public class Usuario implements Serializable{
    private int puntaje;
    private String nombre, apellido, usuario;
    private Administrador admin;
    private HashMap<String, Asignacion> asignaciones;

    public Usuario(String nombre, String apellido, String usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.admin = null;
        asignaciones = new HashMap<String, Asignacion>();
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
        return asignaciones.containsKey(nombre);
    }

    public void addAsignacion(Asignacion asignacion) {
        asignaciones.put(asignacion.getNombre(), asignacion);
    }

    public void listarProyectos() {
        Iterator<Map.Entry<String, Asignacion>> iterator = asignaciones.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Asignacion> elemento = iterator.next();
            Asignacion asignacion = elemento.getValue();
            
            if(asignacion.getClass().getSimpleName().equals("Proyecto")) {
                System.out.println("Nombre: " + asignacion.getNombre());
                System.out.println("Fecha: " + asignacion.getStringFecha());
                System.out.println();
            } 
        }
    }

    public void listarTareas() {
        Iterator<Map.Entry<String, Asignacion>> iterator = asignaciones.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Asignacion> elemento = iterator.next();
            Asignacion asignacion = elemento.getValue();
            String nombre;
            String fecha;
            
            if(asignacion.getClass().getSimpleName().equals("Tarea")) {
                nombre = asignacion.getNombre();
                fecha = asignacion.getStringFecha();
                System.out.println(nombre+"\t"+fecha);
            } 
        }
    }

    public void listarTodo() {
        Iterator<Map.Entry<String, Asignacion>> iterator = asignaciones.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Asignacion> elemento = iterator.next();
            Asignacion asignacion = elemento.getValue();
            String nombre;
            String fecha;

            if(asignacion.getClass().getSimpleName().equals("Tarea")) {
                nombre = asignacion.getNombre();
                fecha = asignacion.getStringFecha();
                System.out.println(nombre+"\t"+fecha);
            } else {
                ArrayDeque<Tarea> tareas = ((Proyecto)asignacion).getTareas();
                for(Tarea tarea: tareas) {
                    nombre = tarea.getNombre();
                    fecha = tarea.getStringFecha();
                    System.out.println(nombre+"\t"+fecha);
                }
                System.out.println();
            }
        }
    }
}
