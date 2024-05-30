package pokedex.org.pokedex.Objects;

public class DireccionUsuario {
    private String calle;
    private String ciudad;
    private String codPostal;

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
}
