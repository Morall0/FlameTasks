package usuarios;

class Usuario{
    private String nombre, apellido, contrase�a, pseudonimo;
    
    
    
    
    // getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrase�a() {
        return contrase�a;
    }
    
    

    public String getPseudonimo() {
        return pseudonimo;
    }
    
    // setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContrase�a(String contrase�a) {
        this.contrase�a = contrase�a;
    }

    
    
    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }
    
    
}