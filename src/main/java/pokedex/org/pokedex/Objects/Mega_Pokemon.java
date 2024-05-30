package pokedex.org.pokedex.Objects;

public class Mega_Pokemon extends Pokemon {
    private String megaPiedra;

    public Mega_Pokemon() {
        super();
    }

    public Mega_Pokemon(
            int id, String nombre, int vida, int ataque, int defensa, int ataqueEsp, int defensaEsp, int velocidad,
            String tipos, String megaPiedra) {
        super(id, nombre, vida, ataque, defensa, ataqueEsp, defensaEsp, velocidad, tipos);
        this.megaPiedra = megaPiedra;
    }

    public String getMegaPiedra() {
        return megaPiedra;
    }

    public void setMegaPiedra(String megaPiedra) {
        this.megaPiedra = megaPiedra;
    }

}
