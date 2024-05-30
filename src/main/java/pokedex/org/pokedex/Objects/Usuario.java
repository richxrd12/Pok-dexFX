package pokedex.org.pokedex.Objects;

public class Usuario {
    private String ID;
    private String nombre;
    private String password;
    private String sexo;
    private String telefono;
    private String email;
    private DireccionUsuario direccion = new DireccionUsuario();

    public Usuario(String nombre){
        this.nombre = nombre;
    }

    public Usuario(){

    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getSexo(){
        return this.sexo;
    }

    public void setSexo(String sexo){
        this.sexo = sexo;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getID(){
        return this.ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public String getCalle(){
        return direccion.getCalle();
    }

    public String getCiudad(){
        return direccion.getCiudad();
    }

    public String getCodPostal(){
        return direccion.getCodPostal();
    }

    public void setCalle(String calle){
        direccion.setCalle(calle);
    }

    public void setCiudad(String ciudad){
        direccion.setCiudad(ciudad);
    }

    public void setCodPostal(String codPostal){
        direccion.setCodPostal(codPostal);
    }

    public DireccionUsuario getDireccion(){
        return direccion;
    }

    public void setDireccion(DireccionUsuario direccion){
        this.direccion = direccion;
    }
}
