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
        Tarea tarea;
        String nombre;

        if (usuario == null)
            return;

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
            System.out.println("9. Salir");
            System.out.print("> ");
            opt = scan.nextInt();
            scan.nextLine();

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
                    usuario.listarTodo();
                    System.out.print("\nNombre de la tarea: ");
                    nombre = scan.nextLine();
                    if (usuario.existeAsignacion(nombre)) {
                        tarea = (Tarea) usuario.buscarAsignacion(nombre);
                        tarea.setCompletada(true);
                        System.out.println("La tarea '" + nombre + "' se marcó como completada!\n");
                    } else
                        System.out.println("\nNo existe la tarea indicada!\n");
                    break;

                case 5:
                    crearProyecto(usuario);
                    break;

                case 6:
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
                    Autenticacion.registrarAtributos(usuario);
                    System.out.println("\nSaliendo...");
                    break;

                default:
                    System.out.println("\nSelecciona una opción válida!\n");
                    break;
            }
        } while (opt != 9);

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

        System.out.print("Puntaje: ");
        puntaje = scan.nextInt();
        scan.nextLine();

        System.out.print("Importancia: ");
        importancia = scan.nextByte();
        scan.nextLine();

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
        opt = scan.nextInt();
        scan.nextLine();
        
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
            opcion = scan.nextInt();
            scan.nextLine();

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
            System.out.println();
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

        if (dif < 0) {
            System.out.println("Fecha inválida! Ingrese una fecha posterior a la actual");
            return scanFecha();
        }

        return fechaAsignacion;
    }

    private static void elegirOrdenamiento(Usuario usuario){
        System.out.println("\nIntroduzca la forma en que quiere"
                + " ordenar la información en cuestión: ");
        System.out.println("1. Por fecha");
        System.out.println("2. Por importancia");
        System.out.print("> ");
        int opcion = scan.nextInt();
        scan.nextLine();

        switch(opcion){
            case 1:
                usuario.listarTodo();
                break;
            case 2:
                usuario.listarPorImportancia();
                break;
            default:
                System.out.println("Introduzca una opción válida.");
        }

    }

}
