import usuarios.Usuario;
import java.util.Scanner;
import asignaciones.*;
import java.util.Calendar;
import java.util.InputMismatchException;

import autent.Autenticacion;

public class Main {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int opt;
        Usuario usuario = Autenticacion.autenticacion();
        Tarea tarea;
        String nombre;

        if (usuario == null)
            return;

        ((Thread) usuario).start();

        do {
            System.out.println("--- FlameTask ---");
            System.out.println("1. Crear tarea");
            System.out.println("2. Editar tarea");
            System.out.println("3. Listar tareas");
            System.out.println("4. Marcar tarea como completada");
            System.out.println("5. Crear proyecto");
            System.out.println("6. Editar proyecto");
            System.out.println("7. Listar proyectos");
            System.out.println("8. Ver proyecto");
            System.out.println("9. Eliminar tarea o proyecto");
            System.out.println("10. Salir");
            System.out.print("> ");
            try {
                opt = scan.nextInt();
                scan.nextLine();
            } catch(InputMismatchException e){
                scan.nextLine();
                System.out.println("El valor introducido no es válido.");
                opt = -1;
            }

            switch (opt) {
                case 1:
                    System.out.println("\nCrear una tarea");
                    usuario.addAsignacion(crearTarea());
                    break;

                case 2:
                    System.out.println("\nEditar una tarea");
                    menuEditarTarea(usuario);
                    break;

                case 3:
                    if (usuario.hayAsignaciones()) {
                        elegirOrdenamiento(usuario);
                    } else
                        System.out.println("\nNo hay asignaciones por el momento\n");
                    break;

                case 4:
                    System.out.println("\nMarcar como completada");
                    usuario.listarTodo(0);
                    System.out.print("Nombre de la tarea: ");
                    nombre = scan.nextLine();
                    if (usuario.existeAsignacion(nombre)) {
                        tarea = (Tarea) usuario.buscarAsignacion(nombre);
                        tarea.setCompletada(true);
                        usuario.actualizarHistorial(tarea);
                        System.out.println("La tarea '" + nombre + "' se marcó como completada!\n");
                    } else
                        System.out.println("\nNo existe la tarea indicada!\n");
                    break;

                case 5:
                    crearProyecto(usuario);
                    break;

                case 6:
                    if(usuario.hayProyectos()){
                        System.out.println("\nProyectos actuales:");
                        usuario.listarProyectos();
                        System.out.print("Nombre del proyecto: ");
                        nombre = scan.nextLine();
                        if(usuario.existeAsignacion(nombre)){
                            menuEditarProyectos((Proyecto) usuario.buscarAsignacion(nombre));
                        } else {
                            System.out.println("\nEl proyecto buscado no existe.\n");
                        }

                    } else{
                        System.out.println("\nNo hay proyectos que mostrar por el momento.\n");
                    }
                    break;

                case 7:
                    System.out.println("\nProyectos");
                    if (usuario.hayProyectos())
                        usuario.listarProyectos();
                    else {
                        System.out.println("\nNo hay proyectos por el momento\n");
                    }
                    break;

                case 8:
                    System.out.print("\nNombre del proyecto: ");
                    nombre = scan.nextLine();
                    if (usuario.existeAsignacion(nombre)) {
                        Proyecto proyecto = (Proyecto) usuario.buscarAsignacion(nombre);
                        proyecto.showProyecto();
                    } else
                        System.out.println("\nNo existe el proyecto indicado!\n");
                    break;

                case 9: 
                    System.out.print("\nNombre de la asignación: ");
                    nombre = scan.nextLine();
                    if (usuario.existeAsignacion(nombre)) {
                        Asignacion asig = usuario.borrarAsignacion(usuario.buscarAsignacion(nombre));
                        System.out.println("Se ha borrado la asignación siguiente: " + asig.getNombre() + "\n");
                    } else
                        System.out.println("\nNo existe la tarea indicada!\n");
                    break;

                case 10:
                    Autenticacion.registrarAtributos(usuario);
                    System.out.println("\nSaliendo...");
                    break;

                default:
                    System.out.println("\nSelecciona una opción válida!\n");
                    break;
            }
        } while (opt != 10);

        scan.close();
    }

    public static Tarea crearTarea() {
        String nombre, descripcion;
        int puntaje;
        byte importancia;
        Calendar fecha;

        System.out.print("Nombre: ");
        nombre = scan.nextLine();

        System.out.print("Descripción: ");
        descripcion = scan.nextLine();

        do {
            try {
                System.out.print("Puntaje: ");
                puntaje = scan.nextInt();
                scan.nextLine();
            } catch(InputMismatchException e){
                scan.nextLine();
                System.out.println("Este puntaje no es válido.");
                puntaje = -1;
            }
        } while(puntaje == -1);


        do {
            System.out.print("Importancia: ");
            try{
                importancia = scan.nextByte();
                scan.nextLine();
            }catch(InputMismatchException e){
                scan.nextLine();
                System.out.println("Este valor no es válido.");
                importancia = -1;
            }
        } while(importancia == -1);

        System.out.println("Fecha");
        fecha = scanFecha();

        return new Tarea(nombre, descripcion, puntaje, fecha, importancia);
    }

    private static void menuEditarTarea(Usuario usuario) {
        int opt; 
        String nombre;
        Proyecto proyecto = null;
        Tarea tarea = null;

        System.out.println("1. Editar tarea");
        System.out.println("2. Editar tarea de un proyecto");
        System.out.println("3. Cancelar edición");
        System.out.print("> ");
        try {
            opt = scan.nextInt();
            scan.nextLine();
        } catch(InputMismatchException e){
            scan.nextLine();
            opt = 0;
        }

        do {
            switch(opt) {
                case 1:
                    System.out.print("\nNombre de la tarea: ");
                    nombre = scan.nextLine();
                    System.out.println();
                    tarea = (Tarea) usuario.buscarAsignacion(nombre);
                    if (tarea != null) {
                        editarTarea(tarea);
                    } else
                        System.out.println("\nLa tarea no existe\n");
                    break;

                case 2:
                    System.out.print("\nNombre del proyecto: ");
                    nombre = scan.nextLine(); 
                    proyecto = (Proyecto) usuario.buscarAsignacion(nombre);
                    if (proyecto != null) {
                        System.out.print("Nombre de la tarea: ");
                        nombre = scan.nextLine();
                        System.out.println();
                        tarea = proyecto.buscarTarea(nombre);
                        if (tarea != null)
                            editarTarea(tarea);
                        else
                            System.out.println("\nLa tarea no existe\n");
                    } else
                        System.out.println("\nEl proyecto no existe\n");
                    break;

                case 3:
                    System.out.println();
                    break;
                default:
                    System.out.println("\nSelecciona una opción válida!\n");
                    break;
            }
        } while (opt < 1 || opt > 3);
    }

    private static void editarTarea(Tarea tarea) {
        int opcion;

        do {
            System.out.println("\nEdición de tareas");
            System.out.println("1. Cambiar nombre");
            System.out.println("2. Cambiar descripción");
            System.out.println("3. Cambiar fecha");
            System.out.println("4. Cambiar puntaje");
            System.out.println("5. Cambiar importancia");
            System.out.println("6. Regresar al menú principal");
            System.out.print("> ");

            try {
                opcion = scan.nextInt();
                scan.nextLine();
            } catch(InputMismatchException e){
                scan.nextLine();
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nuevo nombre de la asignación: ");
                    tarea.setNombre(scan.nextLine());
                    break;

                case 2:
                    System.out.print("Ingrese la nueva descripción: ");
                    tarea.setDescripcion(scan.nextLine());
                    break;

                case 3:
                    System.out.println("Ingrese la nueva fecha: ");
                    Calendar fecha = scanFecha();
                    tarea.setFecha(fecha);
                    break;

                case 4:
                    System.out.print("Ingrese el nuevo puntaje: ");
                    tarea.setPuntaje(scan.nextInt());
                    scan.nextLine();
                    break;

                case 5:
                    System.out.print("Ingrese la nueva importancia: ");
                    tarea.setImportancia(scan.nextByte());
                    scan.nextLine();
                    break;

                case 6:
                    System.out.println();
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        } while(opcion != 6);
    }

    public static void crearProyecto(Usuario usuario) {
        String nombre, descripcion;
        Calendar fecha;

        System.out.println("\nCrear un proyecto");
        System.out.print("Nombre del proyecto: ");
        nombre = scan.nextLine();
        while (usuario.existeAsignacion(nombre)) {
            System.out.println("El nombre del proyecto ya se encuentra en uso!");
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
        rep = (scan.nextLine().equalsIgnoreCase("y")) ? 0 : 1;

        while (rep == 0) {
            Tarea tarea = crearTarea();
            proyecto.addTarea(tarea);
            System.out.println("¿Seguir agregando?");
            System.out.println("[Y] Sí, [N] No");
            System.out.print("> ");
            rep = (scan.nextLine().equalsIgnoreCase("y")) ? 0 : 1;
        }

        System.out.println();
    }

    public static Calendar scanFecha() {
        int dia, mes, anio;
        Calendar fechaAsignacion = Calendar.getInstance();
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.set(Calendar.HOUR_OF_DAY, 0);

        try{
            System.out.print("Día: ");
            dia = scan.nextInt();
            scan.nextLine();
            if(dia > 31 || dia < 1){
                throw new IllegalArgumentException();
            }
            System.out.print("Mes: ");
            mes = scan.nextInt();
            scan.nextLine();
            if(mes > 12 || mes < 0){
                throw new IllegalArgumentException();
            }
            System.out.print("Año: ");
            anio = scan.nextInt();
            scan.nextLine();
        }catch(IllegalArgumentException e){
            System.out.println("El valor introducido es erróneo. Intente de nuevo.");
            return scanFecha();
        }catch(InputMismatchException e){
            System.out.println("El tipo de valor introducido es erróneo. Intente de nuevo.");
            return scanFecha();
        }

        fechaAsignacion.set(anio, mes - 1, dia);
        System.out.println();
        int dif = fechaAsignacion.compareTo(fechaActual);

        if (dif < 0) {
            System.out.println("Fecha inválida! Ingrese una fecha posterior a la actual");
            return scanFecha();
        }

        return fechaAsignacion;
    }

    private static void elegirOrdenamiento(Usuario usuario){
        int opcion;
        System.out.println("\nIntroduzca la forma en que quiere"
                + " ordenar la información en cuestión: ");
        System.out.println("1. Por fecha");
        System.out.println("2. Por importancia");
        System.out.print("> ");
        try {
            opcion = scan.nextInt();
            scan.nextLine();
        } catch(InputMismatchException e){
            scan.nextLine();
            opcion = 0;
        }
        System.out.println(); 

        switch(opcion){
            case 1:
                usuario.listarTodo(0);
                break;
            case 2:
                usuario.listarPorImportancia();
                break;
            default:
                System.out.println("\nIntroduzca una opción válida!\n");
        }

    }

    public static void menuEditarProyectos(Proyecto proyecto){
        int opcion;
        String var;
        do{

            System.out.println("\nEditor de proyectos");
            System.out.println("1. Editar información del proyecto.");
            System.out.println("2. Editar información de alguna tarea del proyecto.");
            System.out.println("3. Salir del menú.");
            System.out.print("> ");
            try{
                opcion = scan.nextInt();
                scan.nextLine();
            }catch(InputMismatchException e){opcion = 0; scan.nextLine(); System.out.println();}

            switch(opcion){
                case 1:
                    System.out.println();
                    subMenuEditarProyectos(proyecto);
                    break;
                case 2:
                    proyecto.showProyecto();
                    System.out.print("Tarea a modificar: ");
                    var = scan.nextLine();
                    if(proyecto.buscarTarea(var) != null){
                        editarTarea(proyecto.buscarTarea(var));
                    }else{
                        System.out.println("\nEsa tarea no existe.\n");
                    }
                    break;
                case 3:
                    System.out.println();
                    break;
                default:
                    System.out.println("\nIntroduzca una opción válida!\n");
            }
        }while(opcion != 3);
    }

    public static void subMenuEditarProyectos(Proyecto proyecto){
        int opcion;
        String var;
        do{
            System.out.println("Edición de un proyecto especifico");
            System.out.println("1. Cambiar nombre");
            System.out.println("2. Cambiar fecha");
            System.out.println("3. Cambiar descripcion");
            System.out.println("4. Regresar");
            System.out.print("> ");
            try{
                opcion = scan.nextInt();
            }catch(InputMismatchException e){opcion = 0;}
            scan.nextLine();
            switch(opcion){
                case 1:
                    System.out.print("\nNuevo nombre del proyecto: ");
                    var = scan.nextLine();
                    proyecto.setNombre(var);
                    System.out.println();
                    break;
                case 2:
                    System.out.println("");
                    proyecto.setFecha(scanFecha());
                    break;
                case 3:
                    System.out.print("\nNueva descripcion del proyecto: ");
                    var = scan.nextLine();
                    proyecto.setDescripcion(var);
                    System.out.println();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\nIntroduzca una opción válida!\n");
            }
        }while(opcion != 4);
    }
}
