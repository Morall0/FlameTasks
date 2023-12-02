import usuarios.Usuario;
import autent.Autenticacion;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = Autenticacion.autenticacion();
    }
}
