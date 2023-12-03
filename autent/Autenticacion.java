package autent;

import java.util.Scanner;

import java.util.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import usuarios.Usuario;

public class Autenticacion { 
    private static Scanner scan = new Scanner(System.in);

    public static Usuario autenticacion() {
        int opt;
        Usuario nuevoUsuario = null;
        String usuario;
        String contrasena;

        do {
            System.out.println("--- FlameTasks ---");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Crear cuenta");
            System.out.println("3. Salir");
            System.out.print("> ");
            opt = scan.nextInt();
            scan.nextLine();

            switch(opt) {
                case 1:
                    System.out.println("\nInicio de sesión");

                    System.out.print("Nombre de usuario: "); 
                    usuario = scan.nextLine(); 

                    System.out.print("Contraseña: "); 
                    contrasena = hashContra(scan.nextLine());

                    nuevoUsuario = iniciarSesion(usuario, contrasena);

                    if (nuevoUsuario != null) {
                        System.out.println("\nSe ha inciado sesión con éxito.\n");
                        return nuevoUsuario;
                    } else
                        System.out.println("\nLas credenciales no coindicen.\n");

                    break;

                case 2:
                    String nombre, apellido;
                    System.out.println("\nCreación de cuenta");

                    System.out.print("Nombre(s): ");
                    nombre = scan.nextLine();

                    System.out.print("Apellidos: ");
                    apellido = scan.nextLine();

                    do {
                        System.out.print("Nombre de usuario: ");
                        usuario = scan.nextLine();
                    } while (usuarioDisponible(usuario) == false);

                    System.out.print("Contrasena: ");
                    contrasena = hashContra(scan.nextLine());

                    nuevoUsuario = new Usuario(nombre, apellido, usuario); 

                    if (crearCuenta(nuevoUsuario, contrasena) == true) {
                        System.out.println("\nRegistro exitoso!\n");
                        return nuevoUsuario; 
                    } else
                        System.out.println("Ha ocurrido un error en el registro!\n");

                    break;

                case 3:
                    System.out.println("\nSaliendo...");
                    break;

                default:
                    System.out.println("\nIntroduce una opción correcta.\n");
                    break;
            }
        } while ((opt < 1 || opt > 3) || opt != 3);

        scan.close();
        return nuevoUsuario;
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

    private static Usuario iniciarSesion(String usuario, String contrasena){
        File archivo = new File("./credenciales.txt");
        Usuario usuarioSesion = null;

        try {
            Scanner scanFile = new Scanner(archivo);
            String[] datos;

            while (scanFile.hasNext()) {
                datos = scanFile.nextLine().split(" ");  
                if (datos[0].equals(usuario) && datos[1].equals(contrasena))
                    usuarioSesion = getInstanciaUsuario(usuario);
            }

            scanFile.close();
        } catch (FileNotFoundException e) {
            //System.out.println("El archivo de los usuarios no existe.\n");
        }

        return usuarioSesion;
    } 

    private static Usuario getInstanciaUsuario(String usuario) {
        File archivo = new File("./registros/"+usuario);
        Usuario instancia = null;

        try {
            ObjectInputStream arch_usuario = new ObjectInputStream(new FileInputStream(archivo)); 
            instancia = (Usuario) arch_usuario.readObject();
            arch_usuario.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo del usuario\n");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró la clase Usuario\n");
        } catch (IOException e) {
            System.out.println("Hubo un error al obtener los datos del usuario.\n");
        }

        return instancia;
    }

    private static boolean crearCuenta(Usuario usuario, String contrasena) {
        if (!registrarCredenciales(usuario.getUsuario(), contrasena)) {
            System.out.println("No se han podido registrar las credenciales del usuario.\n");
            return false;
        }

        if (!registrarAtributos(usuario)) {
            System.out.println("No se han podido registrar los atributos del usuario.\n");
            return false;
        }

        return true; 
    }

    private static boolean registrarCredenciales(String usuario, String contrasena) {
        File archivo = new File("./credenciales.txt");
        String credenciales = usuario + " " +contrasena+" \n";

        try {
            FileWriter usuarios = new FileWriter(archivo, true);
            usuarios.write(credenciales);
            usuarios.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error en el registro de credenciales.\n");
            return false;
        }
    }

    private static boolean usuarioDisponible(String usuario) {
        File archivo = new File("./credenciales.txt");

        try {
            Scanner scanFile = new Scanner(archivo);
            String[] datos;

            while (scanFile.hasNext()) {
                datos = scanFile.nextLine().split(" ");  
                if (datos[0].equals(usuario)) {
                    System.out.println("\nEl nombre de usuario ya está ocupado!");
                    scanFile.close();
                    return false; 
                }
            }

            scanFile.close();
        } catch (FileNotFoundException e) {
        }

        return true;
    }

    public static boolean registrarAtributos(Usuario usuario) {
        File archivo = new File("./registros/"+usuario.getUsuario());

        try {
            ObjectOutputStream arch_usuario = new ObjectOutputStream(new FileOutputStream(archivo));
            arch_usuario.writeObject(usuario);
            arch_usuario.close();
            return true;
        } catch (IOException e){
            System.out.println("Error en el registro de atributos del usuario.\n");
            return false;
        }
    }
}
