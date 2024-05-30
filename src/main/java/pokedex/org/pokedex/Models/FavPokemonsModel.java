package pokedex.org.pokedex.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import pokedex.org.pokedex.Objects.DireccionUsuario;
import pokedex.org.pokedex.Objects.Mega_Pokemon;
import pokedex.org.pokedex.Objects.Pokemon;
import pokedex.org.pokedex.Objects.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class FavPokemonsModel {
    private final String URL = "jdbc:postgresql://localhost:5432/pokemon_db";
    private final String username = "postgres";
    private final String password = "1234";
    private Connection conexionFavs = null;

    public void startConnection(){
        //Para iniciar la conexión con la BBDD
        try{
            conexionFavs = DriverManager.getConnection(
                    URL, username, password
            );
        }catch (Exception e){
            System.out.println("No se ha podido establecer conexión con la base de datos");
        }
    }

    public void endConnection(){
        //Terminamos la conexión
        try{
            conexionFavs.close();
            conexionFavs = null;
        }catch (Exception e){
            System.out.println("No se ha podido cerrar la conexión con la base de datos");
        }
    }

    public String tipoAsString(ArrayList<String> tipos){
        if (tipos.size() != 1){
            return tipos.get(0)  + " / " +  tipos.get(1);
        }else{
            return tipos.get(0);
        }
    }

    public void getFavPokes(TableView<Pokemon> tablaPokemon, String usuario){
        final String QUERY = """
                SELECT pokemons.id_pokemon, pokemons.nombre, pokemons.tipos, stats_pokemon.vida, stats_pokemon.ataque,
                    stats_pokemon.defensa, stats_pokemon.ataque_esp, stats_pokemon.defensa_esp,
                		stats_pokemon.velocidad FROM usuarios
                			INNER JOIN pokemon_favs ON usuarios.id_usuario = pokemon_favs.id_usuario
                				INNER JOIN pokemons ON pokemon_favs.id_pokemon = pokemons.id_pokemon
                					INNER JOIN stats_pokemon ON pokemons.id_pokemon = stats_pokemon.id_pokemon
                						WHERE usuarios.nombre = ? AND pokemons.nombre NOT LIKE 'Mega-%';
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();

        startConnection();

        try{
            preparedQuery = conexionFavs.prepareStatement(QUERY);
            preparedQuery.setString(1, usuario);
            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaPokemon.add(new Pokemon(resultados.getInt("id_pokemon"),
                        resultados.getString("nombre"),
                        resultados.getInt("vida"),
                        resultados.getInt("ataque"),
                        resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"),
                        resultados.getInt("velocidad"),
                        tipoString
                ));
            }
            tablaPokemon.setItems(listaPokemon);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void getFavMegas(TableView<Mega_Pokemon> tablaMega, String usuario){
        final String QUERY = """
                SELECT mega_pokes.id_mega, mega_pokes.nombre, mega_pokes.tipos, mega_pokes.mega_piedra,
                    stats_megas.vida, stats_megas.ataque, stats_megas.defensa, stats_megas.ataque_esp,\s
                    		stats_megas.defensa_esp, stats_megas.velocidad FROM stats_megas
                    			INNER JOIN mega_pokes ON stats_megas.id_mega = mega_pokes.id_mega
                    				INNER JOIN mega_favs ON mega_pokes.id_mega = mega_favs.id_mega
                    					INNER JOIN usuarios ON mega_favs.id_usuario = usuarios.id_usuario
                							WHERE usuarios.nombre = ?;
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Mega_Pokemon> listaPokemon = FXCollections.observableArrayList();

        try{
            preparedQuery = conexionFavs.prepareStatement(QUERY);
            preparedQuery.setString(1, usuario);
            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaPokemon.add(new Mega_Pokemon(resultados.getInt("id_mega"),
                        resultados.getString("nombre"),
                        resultados.getInt("vida"),
                        resultados.getInt("ataque"),
                        resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"),
                        resultados.getInt("velocidad"),
                        tipoString, resultados.getString("mega_piedra")
                ));
            }
            tablaMega.setItems(listaPokemon);
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void searchPokemonByID(TableView<Pokemon> tablaPokemon, String ID, String usuario){
        final String ID_QUERY = """
                SELECT pokemons.id_pokemon, pokemons.nombre, pokemons.tipos, stats_pokemon.vida, stats_pokemon.ataque,
                    stats_pokemon.defensa, stats_pokemon.ataque_esp, stats_pokemon.defensa_esp,
                		stats_pokemon.velocidad FROM usuarios
                			INNER JOIN pokemon_favs ON usuarios.id_usuario = pokemon_favs.id_usuario
                				INNER JOIN pokemons ON pokemon_favs.id_pokemon = pokemons.id_pokemon
                					INNER JOIN stats_pokemon ON pokemons.id_pokemon = stats_pokemon.id_pokemon
                						WHERE usuarios.nombre = ? AND pokemons.id_pokemon = ?
                """;
        PreparedStatement statementQuery = null;
        ResultSet resultados = null;

        ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();

        try{
            statementQuery = conexionFavs.prepareStatement(ID_QUERY);
            statementQuery.setString(1, usuario);
            statementQuery.setInt(2, Integer.parseInt(ID));

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

    public void searchPokemonByName(TableView<Pokemon> tablaPokemon, String name, String usuario){
        final String NAME_QUERY = """
                SELECT pokemons.id_pokemon, pokemons.nombre, pokemons.tipos, stats_pokemon.vida, stats_pokemon.ataque,
                    stats_pokemon.defensa, stats_pokemon.ataque_esp, stats_pokemon.defensa_esp,
                		stats_pokemon.velocidad FROM usuarios
                			INNER JOIN pokemon_favs ON usuarios.id_usuario = pokemon_favs.id_usuario
                				INNER JOIN pokemons ON pokemon_favs.id_pokemon = pokemons.id_pokemon
                					INNER JOIN stats_pokemon ON pokemons.id_pokemon = stats_pokemon.id_pokemon
                						WHERE usuarios.nombre = ? AND pokemons.nombre LIKE ?;
                """;
        PreparedStatement statementQuery = null;
        ResultSet resultados = null;

        ObservableList<Pokemon> listaPokemon = FXCollections.observableArrayList();

        try{
            statementQuery = conexionFavs.prepareStatement(NAME_QUERY);
            statementQuery.setString(1, usuario);
            statementQuery.setString(2, "%" + name + "%");

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

    public void searchMegaByID(TableView<Mega_Pokemon> tablaMega, String ID, String usuario){
        final String QUERY = """
                SELECT mega_pokes.id_mega, mega_pokes.nombre, mega_pokes.tipos, mega_pokes.mega_piedra,
                    stats_megas.vida, stats_megas.ataque, stats_megas.defensa, stats_megas.ataque_esp,
                    		stats_megas.defensa_esp, stats_megas.velocidad FROM stats_megas
                    			INNER JOIN mega_pokes ON stats_megas.id_mega = mega_pokes.id_mega
                    				INNER JOIN mega_favs ON mega_pokes.id_mega = mega_favs.id_mega
                    					INNER JOIN usuarios ON mega_favs.id_usuario = usuarios.id_usuario
                							WHERE usuarios.nombre = ? AND mega_pokes.id_mega = ?;
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Mega_Pokemon> listaMega = FXCollections.observableArrayList();

        try{
            preparedQuery = conexionFavs.prepareStatement(QUERY);
            preparedQuery.setString(1, usuario);
            preparedQuery.setInt(2, Integer.parseInt(ID));

            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaMega.add(new Mega_Pokemon(resultados.getInt("id_mega"),
                        resultados.getString("nombre"),
                        resultados.getInt("vida"),
                        resultados.getInt("ataque"),
                        resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"),
                        resultados.getInt("velocidad"),
                        tipoString, resultados.getString("mega_piedra")
                ));
            }
            tablaMega.setItems(listaMega);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void searchMegaByName(TableView<Mega_Pokemon> tablaMega, String name, String usuario){
        final String NAME_QUERY = """
                SELECT mega_pokes.id_mega, mega_pokes.nombre, mega_pokes.tipos, mega_pokes.mega_piedra,
                    stats_megas.vida, stats_megas.ataque, stats_megas.defensa, stats_megas.ataque_esp,
                    		stats_megas.defensa_esp, stats_megas.velocidad FROM stats_megas
                    			INNER JOIN mega_pokes ON stats_megas.id_mega = mega_pokes.id_mega
                    				INNER JOIN mega_favs ON mega_pokes.id_mega = mega_favs.id_mega
                    					INNER JOIN usuarios ON mega_favs.id_usuario = usuarios.id_usuario
                							WHERE usuarios.nombre = ? AND mega_pokes.nombre LIKE ?;
                """;
        PreparedStatement statementQuery = null;
        ResultSet resultados = null;

        ObservableList<Mega_Pokemon> listaMega = FXCollections.observableArrayList();

        try{
            statementQuery = conexionFavs.prepareStatement(NAME_QUERY);
            statementQuery.setString(1, usuario);
            statementQuery.setString(2, "Mega-" + name + "%");

            resultados = statementQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaMega.add(new Mega_Pokemon(resultados.getInt("id_mega"),
                        resultados.getString("nombre"),
                        resultados.getInt("vida"),
                        resultados.getInt("ataque"),
                        resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"),
                        resultados.getInt("velocidad"),
                        tipoString, resultados.getString("mega_piedra")
                ));
                tablaMega.setItems(listaMega);
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
            statementComprobar = conexionFavs.prepareStatement(IS_REGISTERED);

            statementComprobar.setInt(1, ID_POKEMON);
            statementComprobar.setString(2, usuario);

            resultadosComprobar = statementComprobar.executeQuery();

            if (resultadosComprobar.next()){
                isRegistered = true;
                idUsuario = resultadosComprobar.getInt("id_usuario");
            }else{
                statementIdUsuario = conexionFavs.prepareStatement(OBTENER_ID);

                statementIdUsuario.setString(1, usuario);
                resultadoIdUsuario = statementIdUsuario.executeQuery();

                if (resultadoIdUsuario.next()){
                    idUsuario = resultadoIdUsuario.getInt("id_usuario");
                }else {
                    System.out.println("No se ha podido obtener el id del usuario");
                }
            }

            if (isRegistered){
                lastStatement = conexionFavs.prepareStatement(DELETE_FAV);

                lastStatement.setInt(1, ID_POKEMON);
                lastStatement.setInt(2, idUsuario);

                lastStatement.executeUpdate();
            }else{
                lastStatement = conexionFavs.prepareStatement(INSERT_FAV);

                lastStatement.setInt(1, idUsuario);
                lastStatement.setInt(2, ID_POKEMON);

                lastStatement.executeUpdate();
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void guardarMega(Mega_Pokemon mega, String usuario){
        final String IS_REGISTERED = """
                SELECT id_mega, id_usuario FROM mega_favs
                	WHERE id_mega=? AND id_usuario=
                		(SELECT id_usuario FROM usuarios WHERE nombre=?);
                """;
        final String INSERT_FAV= """
                INSERT INTO mega_favs (id_usuario, id_mega) VALUES
                    (?, ?);
                """;
        final String DELETE_FAV = """
                DELETE FROM mega_favs
                    WHERE id_mega=? AND id_usuario=?;
                """;
        final String OBTENER_ID = """
                SELECT id_usuario FROM usuarios WHERE nombre=?;
                """;

        PreparedStatement statementComprobar = null;
        ResultSet resultadosComprobar = null;

        PreparedStatement statementIdUsuario = null;
        ResultSet resultadoIdUsuario = null;

        PreparedStatement lastStatement = null;

        final int ID_POKEMON = mega.getId();
        int idUsuario = 0;

        boolean isRegistered = false;

        try{
            statementComprobar = conexionFavs.prepareStatement(IS_REGISTERED);

            statementComprobar.setInt(1, ID_POKEMON);
            statementComprobar.setString(2, usuario);

            resultadosComprobar = statementComprobar.executeQuery();

            if (resultadosComprobar.next()){
                isRegistered = true;
                idUsuario = resultadosComprobar.getInt("id_usuario");
            }else{
                statementIdUsuario = conexionFavs.prepareStatement(OBTENER_ID);

                statementIdUsuario.setString(1, usuario);
                resultadoIdUsuario = statementIdUsuario.executeQuery();

                if (resultadoIdUsuario.next()){
                    idUsuario = resultadoIdUsuario.getInt("id_usuario");
                }else {
                    System.out.println("No se ha podido obtener el id del usuario");
                }
            }

            if (isRegistered){
                lastStatement = conexionFavs.prepareStatement(DELETE_FAV);

                lastStatement.setInt(1, ID_POKEMON);
                lastStatement.setInt(2, idUsuario);

                lastStatement.executeUpdate();
            }else{
                lastStatement = conexionFavs.prepareStatement(INSERT_FAV);

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
            preparedQuery = conexionFavs.prepareStatement(GET_USER_QUERY);

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
