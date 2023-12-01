package usuarios;

import java.util.LinkedList;

import asignaciones.*;

public class Administrador {
    private Proyecto proyecto;
    private LinkedList<Usuario> colaboradores;
    
    public Administrador(Proyecto proyecto, LinkedList<Usuario> colaboradores) {
        this.proyecto = proyecto;
        this.colaboradores = colaboradores;
    }

    public Administrador(Proyecto proyecto) {
        this.proyecto = proyecto;
        colaboradores = new LinkedList<Usuario>();
    }
}
