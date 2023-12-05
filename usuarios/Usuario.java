package usuarios;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Comparator;
import java.util.LinkedList;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Image;

import asignaciones.*;

public class Usuario extends Thread implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private int puntaje;
    private String nombre, apellido, usuario;
    private Administrador admin;
    private PriorityQueue<Asignacion> asignaciones;
    private PriorityQueue<Asignacion> historial;

    public Usuario(String nombre, String apellido, String usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.admin = null;
        asignaciones = new PriorityQueue<Asignacion>();
        historial = new PriorityQueue<Asignacion>();
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

    public void actualizarHistorial(Tarea tarea) {
        boolean flag = false;
        asignaciones.remove(tarea);
        for(Asignacion asig : historial)
            if(tarea.getNombre().equalsIgnoreCase(asig.getNombre()))
                flag = true;

        if(flag)
            return;

        historial.add(tarea);
    }

    public Asignacion borrarAsignacion(Asignacion asignacion) {
        if(asignacion != null) {
            asignaciones.remove(asignacion);
            asignaciones.remove(asignacion);
        } 
        return asignacion;
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

    public void listarTodo(int mode) {
        LinkedList<Asignacion> tempList;

        if(mode == 0)
            tempList = new LinkedList<Asignacion>(asignaciones);
        else 
            tempList = new LinkedList<Asignacion>(historial);


        Comparator<Asignacion> customComparator = Comparator.comparing(Asignacion::getFecha);
        Collections.sort(tempList, customComparator);

        for(Asignacion asignacion : tempList)
            if(asignacion.getClass().getSimpleName().equals("Tarea"))
                System.out.println(((Tarea)asignacion).toString());
            //} else {
            //    LinkedList<Tarea> tareas = new LinkedList<Tarea>(((Proyecto)asignacion).getTareas());
            //    Collections.sort(tareas, customComparator);
            //    for(Tarea tarea: tareas)
            //        System.out.println(tarea.toString()+"\t"+asignacion.getNombre());
            //}
        System.out.println();
    }

    public void listarPorImportancia() {
        Comparator<Asignacion> customComparator = Comparator.comparing(Asignacion::getImportancia).reversed();
        LinkedList<Asignacion> tempList = new LinkedList<Asignacion>(asignaciones);
        Collections.sort(tempList, customComparator);

        for (Asignacion asignacion : tempList)
            if(asignacion.getClass().getSimpleName().equals("Tarea"))
                System.out.println(((Tarea)asignacion).toString());
            //else
            //    for (Tarea tarea : ((Proyecto)asignacion).getTareas()) {
            //        tempList.add(tarea);
            //    }
        
        System.out.println();
    }
    
    @Override
    public void run() {
        
        if (!SystemTray.isSupported()) {
            System.out.println("Sistema de notificaciones imcompatible");
            return;
        } 

        Calendar fechaActual = Calendar.getInstance();
        for(Asignacion asignacion : asignaciones) {
            Calendar fechaAsig = asignacion.getFecha(); 
            boolean verif = fechaActual.DATE == fechaAsig.DATE;
            verif = verif && fechaActual.MONTH == fechaAsig.MONTH;
            verif = verif && fechaActual.YEAR == fechaAsig.YEAR;
            if(verif)
                mostrarNotificacion(asignacion);
            
        }
    }

    public void mostrarNotificacion(Asignacion asignacion) {

        SystemTray systemTray = SystemTray.getSystemTray();
        Image icono = Toolkit.getDefaultToolkit().getImage("./icono.png"); 

        TrayIcon trayIcon = new TrayIcon(icono, "Notificación");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("FlameTasks");

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }

        trayIcon.displayMessage("Asignación pendiente: " + asignacion.getNombre(), asignacion.getDescripcion(), TrayIcon.MessageType.INFO);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                systemTray.remove(trayIcon);
                timer.cancel();
            }
        }, 5000);
    }

}