package pokedex.org.pokedex.Models;

import pokedex.org.pokedex.Objects.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditProfileModel {
    private final String URL = "jdbc:postgresql://localhost:5432/pokemon_db";
    private final String username = "postgres";
    private final String password = "1234";
    private Connection conexionPerfil = null;

    public void startConnection(){
        //Para iniciar la conexión con la BBDD
        try{
            conexionPerfil = DriverManager.getConnection(
                    URL, username, password
            );
        }catch (Exception e){
            System.out.println("No se ha podido establecer conexión con la base de datos");
        }
    }

    public void endConnection(){
        //Terminamos la conexión
        try{
            conexionPerfil.close();
            conexionPerfil = null;
        }catch (Exception e){
            System.out.println("No se ha podido cerrar la conexión con la base de datos");
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

        startConnection();

        try{
            preparedQuery = conexionPerfil.prepareStatement(GET_USER_QUERY);

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

                endConnection();
                return usuario;
            }
        }catch (Exception e){
            System.out.println(e);
            endConnection();
        }
        return usuario;
    }

    public void updateUser(String ID, String nombre, String telefono, String email, String sexo, String password,
        String calle, String codPostal, String ciudad){
        final String UPDATE_QUERY = """
                UPDATE usuarios
                    SET nombre = ?,
                        telefono = ?,
                            email = ?,
                                sexo = ?,
                                    user_password = ?,
                                        direccion = row(?, ?, ?)
                                            WHERE id_usuario = ?;
                """;

        PreparedStatement preparedQuery = null;

        startConnection();

        try{
            preparedQuery = conexionPerfil.prepareStatement(UPDATE_QUERY);

            preparedQuery.setString(1, nombre);
            preparedQuery.setString(2, telefono);
            preparedQuery.setString(3, email);
            preparedQuery.setString(4, sexo);
            preparedQuery.setString(5, password);
            preparedQuery.setString(6, calle);
            preparedQuery.setString(7, ciudad);
            preparedQuery.setString(8, codPostal);
            preparedQuery.setInt(9, Integer.parseInt(ID));

            int filasAfectadas = preparedQuery.executeUpdate();

            if (filasAfectadas > 0){
                System.out.println("Se ha actualizado correctamente");
            }

            endConnection();

        }catch (Exception e){
            System.out.println(e);
            endConnection();
        }

    }
}
