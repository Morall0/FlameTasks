import usuarios.Usuario;

import java.util.Scanner;

import asignaciones.*;

import java.util.Calendar;

import autent.Autenticacion;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        int opt;
        Usuario usuario = Autenticacion.autenticacion();
        
        if (usuario == null)
            return;

        do {
            System.out.println("--- FlameTask ---");
            System.out.println("1. Crear Tarea");
            System.out.println("2. Listar tareas ");
            System.out.println("3. Crear proyecto");
            System.out.println("4. Listar proyectos");
            System.out.println("5. Salir");
            System.out.print("> ");
            opt = scan.nextInt();
            scan.nextLine();
    
            switch (opt) {
                case 1:
                    
                    break;

                case 2:
                    System.out.println("\nTareas:"); 
                    usuario.listarTodo();
                    break;

                case 3:
                    crearProyecto(usuario);
                    break;

                case 4:
                    System.out.println("\nProyectos:");
                    usuario.listarProyectos();
                    break;

                case 5:
                    Autenticacion.registrarAtributos(usuario);
                    System.out.println("\nSaliendo...");
                    break;

                default:
                    System.out.println("\nSelecciona una opción válida!\n");
                    break;
            }
        } while (opt != 5);

        scan.close();
    }
    
    public static Tarea crearTarea() {
        String nombre, descripcion;
        int puntaje;
        byte importancia;
        Calendar fecha;

        System.out.println("\nCrear una tarea");
        System.out.print("Nombre: ");
        nombre = scan.nextLine();
        System.out.print("Descripción: ");
        descripcion = scan.nextLine();
        System.out.print("Puntaje: ");
        puntaje = scan.nextInt();
        System.out.print("Importancia: ");
        importancia = scan.nextByte();
        System.out.println("Fecha");
        fecha = scanFecha();
        
        return new Tarea(nombre, descripcion, puntaje, fecha, importancia);
    }

    public static void crearProyecto(Usuario usuario) {
        String nombre, descripcion;
        Calendar fecha;

        System.out.println("\nCrear un proyecto");
        System.out.print("Nombre del proyecto: ");
        nombre = scan.nextLine();
        while(usuario.existeAsignacion(nombre)) {
            System.out.println("El nombre del proyecto ya se encuentra en uso.");
            System.out.print("Nombre del proyecto: ");
            nombre = scan.nextLine();
        }
        System.out.print("Descripción: ");
        descripcion = scan.nextLine();
        System.out.println("Fecha");
        fecha = scanFecha();
        
        Proyecto proyecto = new Proyecto(nombre, descripcion, fecha);
        usuario.addAsignacion(proyecto);

        int rep = 1;

        System.out.println("¿Agregar subtareas al proyecto?, [Y] Sí, [N] No");
        System.out.print("> ");
        rep = (scan.nextLine().equalsIgnoreCase("y"))? 0:1;
        
        while(rep == 0) {
            Tarea tarea = crearTarea();
            proyecto.addTarea(tarea);
            System.out.println("¿Seguir agregando?");
            System.out.println("[Y] Sí, [N] No");
            System.out.print("> ");
            rep = (scan.nextLine().equalsIgnoreCase("y"))? 0:1;
        }
    }

    public static Calendar scanFecha() {
        int dia, mes, anio;
        Calendar fechaAsignacion = Calendar.getInstance();
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.set(Calendar.HOUR_OF_DAY, 0);

        System.out.print("Día: ");
        dia = scan.nextInt();
        scan.nextLine();
        System.out.print("Mes: ");
        mes = scan.nextInt();
        scan.nextLine();
        System.out.print("Año: ");
        anio = scan.nextInt();
        scan.nextLine();
        fechaAsignacion.set(anio, mes - 1, dia);
        System.out.println();
        
        int dif = fechaAsignacion.compareTo(fechaActual);

        if(dif < 0) {
            System.out.println("Fecha inválida. Ingrese una fecha posterior a la actual");
            return scanFecha();
        }

        return fechaAsignacion;
    }

}
