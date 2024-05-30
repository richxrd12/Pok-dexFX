package pokedex.org.pokedex.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import pokedex.org.pokedex.Objects.DireccionUsuario;
import pokedex.org.pokedex.Objects.Pokemon;
import pokedex.org.pokedex.Objects.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PokedexGUIModel {
    private final String URL = "jdbc:postgresql://localhost:5432/pokemon_db";
    private final String username = "postgres";
    private final String password = "1234";
    private Connection conexionLogin = null;

    public void startConnection(){
        //Para iniciar la conexión con la BBDD
        try{
            conexionLogin = DriverManager.getConnection(
                    URL, username, password
            );
        }catch (Exception e){
            System.out.println("No se ha podido establecer conexión con la base de datos");
        }
    }

    public void endConnection(){
        //Terminamos la conexión
        try{
            conexionLogin.close();
            conexionLogin = null;
        }catch (Exception e){
            System.out.println("No se ha podido cerrar la conexión con la base de datos");
        }
    }

    public String tipoAsString(ArrayList<String> tipos){
        if (tipos.size() != 1){
            return tipos.get(0) + " / " + tipos.get(1);
        }else{
            return tipos.get(0);
        }
    }

    public void getAllPokes(TableView<Pokemon> tablaPokemon){
        final String QUERY = """
                SELECT id_pokemon,
                	(SELECT nombre FROM pokemons WHERE pokemons.id_pokemon=stats_pokemon.id_pokemon limit 1) AS nombre,
                		(SELECT tipos FROM pokemons WHERE pokemons.id_pokemon=stats_pokemon.id_pokemon limit 1) as tipos,
                			vida, ataque, defensa, ataque_esp, defensa_esp, velocidad FROM stats_pokemon;
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();

        startConnection();

        try{
            preparedQuery = conexionLogin.prepareStatement(QUERY);
            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaPokemon.add(new Pokemon(resultados.getInt("id_pokemon"),
                        resultados.getString("nombre"), resultados.getInt("vida"),
                        resultados.getInt("ataque"), resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"), resultados.getInt("velocidad"),
                        tipoString

                ));
                tablaPokemon.setItems(listaPokemon);
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void searchPokemonByID(TableView<Pokemon> tablaPokemon, int ID){
        final String ID_QUERY = """
                SELECT id_pokemon,
                	(SELECT nombre FROM pokemons WHERE pokemons.id_pokemon=stats_pokemon.id_pokemon limit 1) AS nombre,
                		(SELECT tipos FROM pokemons WHERE pokemons.id_pokemon=stats_pokemon.id_pokemon limit 1) as tipos,
                			vida, ataque, defensa, ataque_esp, defensa_esp, velocidad FROM stats_pokemon
                				WHERE id_pokemon= AND pokemons.nombre NOT LIKE 'Mega-%';?;
                """;
        PreparedStatement statementQuery = null;
        ResultSet resultados = null;

        ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();

        try{
            statementQuery = conexionLogin.prepareStatement(ID_QUERY);
            statementQuery.setInt(1, ID);

            resultados = statementQuery.executeQuery();


            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                Pokemon pokemon = new Pokemon(resultados.getInt("id_pokemon"),
                        resultados.getString("nombre"), resultados.getInt("vida"),
                        resultados.getInt("ataque"), resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"), resultados.getInt("velocidad"),
                        tipoString
                );
                listaPokemon.add(pokemon);
                tablaPokemon.setItems(listaPokemon);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void searchPokemonByName(TableView<Pokemon> tablaPokemon, String name){
        final String NAME_QUERY = """
                SELECT stats_pokemon.id_pokemon, pokemons.nombre AS nombre,
                    pokemons.tipos AS tipos, stats_pokemon.vida, stats_pokemon.ataque, stats_pokemon.defensa,
                    	stats_pokemon.ataque_esp, stats_pokemon.defensa_esp, stats_pokemon.velocidad\s
                			FROM stats_pokemon INNER JOIN pokemons ON stats_pokemon.id_pokemon =pokemons.id_pokemon\s
                				WHERE pokemons.nombre LIKE ? AND pokemons.nombre NOT LIKE 'Mega-%';
                """;
        PreparedStatement statementQuery = null;
        ResultSet resultados = null;

        ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();

        try{
            statementQuery = conexionLogin.prepareStatement(NAME_QUERY);
            statementQuery.setString(1, "%" + name + "%");

            resultados = statementQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaPokemon.add(new Pokemon(resultados.getInt("id_pokemon"),
                        resultados.getString("nombre"), resultados.getInt("vida"),
                        resultados.getInt("ataque"), resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"), resultados.getInt("velocidad"),
                        tipoString
                ));
                tablaPokemon.setItems(listaPokemon);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void guardarPokemon(Pokemon pokemon, String usuario){
        //Las queries para buscar en la base de datos e insertar
        final String IS_REGISTERED = """
                SELECT id_pokemon, id_usuario FROM pokemon_favs
                	WHERE id_pokemon=? AND id_usuario=
                		(SELECT id_usuario FROM usuarios WHERE nombre=?);
                """;
        final String INSERT_FAV= """
                INSERT INTO pokemon_favs (id_usuario, id_pokemon) VALUES
                    (?, ?);
                """;
        final String DELETE_FAV = """
                DELETE FROM pokemon_favs
                    WHERE id_pokemon=? AND id_usuario=?;
                """;
        final String OBTENER_ID = """
                SELECT id_usuario FROM usuarios WHERE nombre=?;
                """;

        PreparedStatement statementComprobar = null;
        ResultSet resultadosComprobar = null;

        PreparedStatement statementIdUsuario = null;
        ResultSet resultadoIdUsuario = null;

        PreparedStatement lastStatement = null;

        final int ID_POKEMON = pokemon.getId();
        int idUsuario = 0;

        boolean isRegistered = false;

        try{
            statementComprobar = conexionLogin.prepareStatement(IS_REGISTERED);

            statementComprobar.setInt(1, ID_POKEMON);
            statementComprobar.setString(2, usuario);

            resultadosComprobar = statementComprobar.executeQuery();

            if (resultadosComprobar.next()){
                isRegistered = true;
                idUsuario = resultadosComprobar.getInt("id_usuario");
            }else{
                statementIdUsuario = conexionLogin.prepareStatement(OBTENER_ID);

                statementIdUsuario.setString(1, usuario);
                resultadoIdUsuario = statementIdUsuario.executeQuery();

                if (resultadoIdUsuario.next()){
                   idUsuario = resultadoIdUsuario.getInt("id_usuario");
                }else {
                    System.out.println("No se ha podido obtener el id del usuario");
                }
            }

            if (isRegistered){
                lastStatement = conexionLogin.prepareStatement(DELETE_FAV);

                lastStatement.setInt(1, ID_POKEMON);
                lastStatement.setInt(2, idUsuario);

                lastStatement.executeUpdate();
            }else{
                lastStatement = conexionLogin.prepareStatement(INSERT_FAV);

                lastStatement.setInt(1, idUsuario);
                lastStatement.setInt(2, ID_POKEMON);

                lastStatement.executeUpdate();
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Usuario getUser(String username){
        Usuario usuario = new Usuario();
        final String GET_USER_QUERY = """
                SELECT id_usuario, nombre, telefono, email, sexo, user_password, (direccion).calle, (direccion).ciudad, 
                    (direccion).cod_postal FROM usuarios WHERE nombre = ?;
                """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        try{
            preparedQuery = conexionLogin.prepareStatement(GET_USER_QUERY);

            preparedQuery.setString(1, username);

            resultados = preparedQuery.executeQuery();

            if (resultados.next()){
                usuario.setID(resultados.getString("id_usuario"));
                usuario.setNombre(resultados.getString("nombre"));
                usuario.setEmail(resultados.getString("email"));
                usuario.setSexo(resultados.getString("sexo"));
                usuario.setTelefono(resultados.getString("telefono"));
                usuario.setPassword(resultados.getString("user_password"));
                usuario.setCalle(resultados.getString("calle"));
                usuario.setCiudad(resultados.getString("ciudad"));
                usuario.setCodPostal(resultados.getString("cod_postal"));

                return usuario;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return usuario;
    }
}
