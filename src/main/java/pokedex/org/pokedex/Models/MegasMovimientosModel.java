package pokedex.org.pokedex.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import pokedex.org.pokedex.Enums.Categoria;
import pokedex.org.pokedex.Objects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class MegasMovimientosModel {
    private final String URL = "jdbc:postgresql://localhost:5432/pokemon_db";
    private final String username = "postgres";
    private final String password = "1234";
    private Connection conexionMegas = null;

    public void startConnection(){
        //Para iniciar la conexión con la BBDD
        try{
            conexionMegas = DriverManager.getConnection(
                    URL, username, password
            );
        }catch (Exception e){
            System.out.println("No se ha podido establecer conexión con la base de datos");
        }
    }

    public void endConnection(){
        //Terminamos la conexión
        try{
            conexionMegas.close();
            conexionMegas = null;
        }catch (Exception e){
            System.out.println("No se ha podido cerrar la conexión con la base de datos");
        }
    }

    public void getAllMegas(TableView<Mega_Pokemon> tablaPokemon, String usuario){
        final String QUERY = """
                SELECT mega_pokes.id_mega, mega_pokes.nombre, mega_pokes.tipos,
                		mega_pokes.mega_piedra, stats_megas.vida, stats_megas.ataque,
                			stats_megas.defensa, stats_megas.ataque_esp, stats_megas.defensa_esp,
                				stats_megas.velocidad FROM mega_pokes
                					INNER JOIN stats_megas on mega_pokes.id_mega=stats_megas.id_mega;
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Mega_Pokemon> listaPokemon = FXCollections.observableArrayList();

        startConnection();

        try{
            preparedQuery = conexionMegas.prepareStatement(QUERY);
            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaPokemon.add(new Mega_Pokemon(resultados.getInt("id_mega"),
                        resultados.getString("nombre"), resultados.getInt("vida"),
                        resultados.getInt("ataque"), resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"), resultados.getInt("velocidad"),
                        tipoString, resultados.getString("mega_piedra")

                ));
                tablaPokemon.setItems(listaPokemon);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void getAllMovements(TableView<Movimientos> tablaMovimiento){
        final String QUERY = """
                SELECT nombre, (movimiento).poder, (movimiento).categoria, (movimiento).tipo FROM movimientos;
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Movimientos> listaMovimientos = FXCollections.observableArrayList();

        startConnection();

        try{
            preparedQuery = conexionMegas.prepareStatement(QUERY);
            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                listaMovimientos.add(new Movimientos(resultados.getString("nombre"),
                        resultados.getInt("poder"),
                        resultados.getString("categoria"),
                        resultados.getString("tipo")

                ));
                tablaMovimiento.setItems(listaMovimientos);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String tipoAsString(ArrayList<String> tipos){
        if (tipos.size() != 1){
            return tipos.get(0)  + " / " +  tipos.get(1);
        }else{
            return tipos.get(0);
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
            preparedQuery = conexionMegas.prepareStatement(GET_USER_QUERY);

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
                usuario.setCodPostal(resultados.getString("cod_postal"));
                usuario.setCiudad(resultados.getString("ciudad"));


                return usuario;
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return usuario;
    }

    public void searchMegaByName(TableView<Mega_Pokemon> tablaPokemon, String name, String usuario){
        final String NAME_QUERY = """
                SELECT mega_pokes.id_mega, mega_pokes.nombre, mega_pokes.tipos,
                		mega_pokes.mega_piedra, stats_megas.vida, stats_megas.ataque,
                			stats_megas.defensa, stats_megas.ataque_esp, stats_megas.defensa_esp,
                				stats_megas.velocidad FROM mega_pokes
                					INNER JOIN stats_megas on mega_pokes.id_mega=stats_megas.id_mega
                					    WHERE mega_pokes.nombre LIKE ?;
                """;
        PreparedStatement statementQuery = null;
        ResultSet resultados = null;

        ObservableList<Mega_Pokemon> listaPokemon = FXCollections.observableArrayList();

        startConnection();

        try{
            statementQuery = conexionMegas.prepareStatement(NAME_QUERY);
            statementQuery.setString(1, "Mega-" + name + "%");

            resultados = statementQuery.executeQuery();

            while(resultados.next()){
                String[] tiposArray = (String[]) resultados.getArray("tipos").getArray();
                ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tiposArray));
                String tipoString = tipoAsString(tipos);

                listaPokemon.add(new Mega_Pokemon(resultados.getInt("id_mega"),
                        resultados.getString("nombre"), resultados.getInt("vida"),
                        resultados.getInt("ataque"), resultados.getInt("defensa"),
                        resultados.getInt("ataque_esp"),
                        resultados.getInt("defensa_esp"), resultados.getInt("velocidad"),
                        tipoString, resultados.getString("mega_piedra"))
                );
                tablaPokemon.setItems(listaPokemon);
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void searchMovement(TableView<Movimientos> tablaMovimiento, String movimiento){
        final String QUERY = """
                SELECT nombre, (movimiento).poder, (movimiento).categoria, (movimiento).tipo FROM movimientos
                    WHERE nombre LIKE ?;
                   """;
        PreparedStatement preparedQuery = null;
        ResultSet resultados = null;

        ObservableList<Movimientos> listaMovimientos = FXCollections.observableArrayList();

        startConnection();

        try{
            preparedQuery = conexionMegas.prepareStatement(QUERY);

            preparedQuery.setString(1, "%" + movimiento + "%");

            resultados = preparedQuery.executeQuery();

            while(resultados.next()){
                listaMovimientos.add(new Movimientos(resultados.getString("nombre"),
                        resultados.getInt("poder"),
                        resultados.getString("categoria"),
                        resultados.getString("tipo")

                ));
                tablaMovimiento.setItems(listaMovimientos);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void guardarPokemon(Pokemon pokemon, String nombreUsuario) {
        //Las queries para buscar en la base de datos e insertar
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

        final int ID_POKEMON = pokemon.getId();
        int idUsuario = 0;

        boolean isRegistered = false;

        try{
            statementComprobar = conexionMegas.prepareStatement(IS_REGISTERED);

            statementComprobar.setInt(1, ID_POKEMON);
            statementComprobar.setString(2, nombreUsuario);

            resultadosComprobar = statementComprobar.executeQuery();

            if (resultadosComprobar.next()){
                isRegistered = true;
                idUsuario = resultadosComprobar.getInt("id_usuario");
            }else{
                statementIdUsuario = conexionMegas.prepareStatement(OBTENER_ID);

                statementIdUsuario.setString(1, nombreUsuario);
                resultadoIdUsuario = statementIdUsuario.executeQuery();

                if (resultadoIdUsuario.next()){
                    idUsuario = resultadoIdUsuario.getInt("id_usuario");
                }else {
                    System.out.println("No se ha podido obtener el id del usuario");
                }
            }

            if (isRegistered){
                lastStatement = conexionMegas.prepareStatement(DELETE_FAV);

                lastStatement.setInt(1, ID_POKEMON);
                lastStatement.setInt(2, idUsuario);

                lastStatement.executeUpdate();
            }else{
                lastStatement = conexionMegas.prepareStatement(INSERT_FAV);

                lastStatement.setInt(1, idUsuario);
                lastStatement.setInt(2, ID_POKEMON);

                lastStatement.executeUpdate();
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
