import java.util.Scanner;

import java.util.Base64;

import java.io.File;
import java.io.FileNotFoundException; import java.nio.charset.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Autenticacion { private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int opt;
        String usuario;
        String contrasena;

        System.out.println("--- FlameTasks ---");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Crear cuenta");
        System.out.print("> ");
        opt = scan.nextInt();
        scan.nextLine();

        switch(opt) {
            case 1:
                System.out.println("Inicio de sesión");
                System.out.print("Nombre de usuario: "); 
                usuario = scan.nextLine(); 

                System.out.print("Contraseña: "); 
                contrasena = hashContra(scan.nextLine());

                iniciarSesion(usuario, contrasena);
                break;

            case 2:
                System.out.println("Creación de cuenta");
                break;

            default:
                System.out.println("Introduce una opción correcta.\n");
                break;
        }
    }
    
    private static String hashContra(String contrasena) {
        String hash = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(contrasena.getBytes(StandardCharsets.UTF_8));
            hash = Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("El algoritmo de hash no fue encontrado\n");            
        }
        return hash;
    }

    private static boolean iniciarSesion(String usuario, String contrasena){
        File archivo = new File("./users.txt");

        try {
            Scanner scan = new Scanner(archivo);
            String[] datos;

            while (scan.hasNext()) {
                datos = scan.nextLine().split(" ");  
                if (datos[0].equals(usuario) && datos[1].equals(contrasena))
                    return true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo de los usuarios no existe.\n");
        }
        return false;
    } 
}
