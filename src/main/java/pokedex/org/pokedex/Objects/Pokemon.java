package pokedex.org.pokedex.Objects;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class Pokemon {
    public int id;

    public String nombre;
    public int vida;
    public int ataque;
    public int defensa;
    public int ataqueEsp;
    public int defensaEsp;
    public int velocidad;
    public int stats;
    public String tipos;

    public Pokemon(){

    }

    public Pokemon(
            int id, String nombre, int vida, int ataque, int defensa, int ataqueEsp, int defensaEsp, int velocidad,
            String tipos) {
            this.id = id;
            this.nombre = nombre;
            this.vida = vida;
            this.ataque = ataque;
            this.defensa = defensa;
            this.ataqueEsp = ataqueEsp;
            this.defensaEsp = defensaEsp;
            this.velocidad = velocidad;
            this.stats = this.vida + this.ataque + this.defensa + this.ataqueEsp + this.defensaEsp + this.velocidad;
            this.tipos = tipos;
    }
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getAtaqueEsp() {
        return ataqueEsp;
    }
    public int getDefensaEsp() {
        return defensaEsp;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getStats(){
        return stats;
    }

    public String getTipos(){
        return this.tipos;
    }
    public void setTipos(String tipos){
        this.tipos = tipos;
    }

}
