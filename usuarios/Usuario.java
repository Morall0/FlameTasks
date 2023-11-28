package usuarios;

class Usuario{
    private String nombre, apellido, contraseña, pseudonimo;
    
    
    
    
    // getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContraseña() {
        return contraseña;
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

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    
    
    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }
    
    
}