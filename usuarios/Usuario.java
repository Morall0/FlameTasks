package usuarios;

import java.io.Serializable;
import java.util.LinkedList;

import asignaciones.*;

public class Usuario implements Serializable{
    private int puntaje;
    private String nombre, apellido, usuario;
    private Administrador admin;
    private LinkedList<Asignacion> asignaciones;

    public Usuario(String nombre, String apellido, String usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.admin = null;
        asignaciones = new LinkedList<Asignacion>();
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
}
