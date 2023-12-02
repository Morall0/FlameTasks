import usuarios.Usuario;

import java.util.Scanner;

import autent.Autenticacion;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        int opt;
        Usuario usuario = Autenticacion.autenticacion();

        do {
            
            System.out.println("--- FlameTask ---");
            System.out.println("1. Crear Tarea");
            System.out.println("2. Ver tareas ");
            System.out.println("3. Crear proyecto");
            System.out.println("4. Ver proyecto");
            System.out.println("5. Salir");
            System.out.print("> ");
            opt = scan.nextInt();
            scan.nextLine();
    
            switch (opt) {
                case 1:
                    
                    break;

                case 2:
                    
                    break;

                case 3:
                    
                    break;

                case 4:
                    
                    break;

                case 5:
                    
                    break;

                default:
                    System.out.println("\nSelecciona una opción válida!\n");
                    break;
            }
        } while (opt != 5);

        scan.close();
    }
}
