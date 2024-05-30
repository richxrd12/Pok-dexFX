package pokedex.org.pokedex.Objects;

import pokedex.org.pokedex.Enums.Categoria;

public class Movimientos {
    public String nombre;
    public int poder;
    public String categoria;
    public String tipo;

    public Movimientos(String nombre, int poder, String categoria, String tipo){
        this.nombre = nombre;
        this.poder = poder;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPoder() {
        return poder;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
