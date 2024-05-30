package pokedex.org.pokedex.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterModel {
    private final String URL = "jdbc:postgresql://localhost:5432/pokemon_db";
    private final String user = "postgres";
    private final String pass = "1234";
    Connection conexionRegister = null;

    void startConnection(){
        try{
            conexionRegister = DriverManager.getConnection(
                    URL, user, pass
            );
        }catch (Exception e){
            System.out.println(e);
        }
    }

    void endConnection(){
        try{
            conexionRegister.close();
            conexionRegister = null;
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void registerUser(
            String usuario, String telefono, String email, String sexo, String pass, String calle,
            String ciudad, String codPostal
    ){
        final String REGISTER_QUERY = """
                INSERT INTO usuarios (nombre, telefono, email, sexo, user_password, direccion) VALUES
                    (?, ?, ?, ?, ?, row(?, ?, ?));
                """;
        PreparedStatement preparedQuery = null;
        int rowsAffected;

        startConnection();

        try{
            preparedQuery = conexionRegister.prepareStatement(REGISTER_QUERY);

            preparedQuery.setString(1, usuario);
            preparedQuery.setString(2, telefono);
            preparedQuery.setString(3, email);
            preparedQuery.setString(4, sexo);
            preparedQuery.setString(5, pass);
            preparedQuery.setString(6, calle);
            preparedQuery.setString(7, ciudad);
            preparedQuery.setString(8, codPostal);

            rowsAffected = preparedQuery.executeUpdate();

            if (rowsAffected > 0){
                System.out.println("Se ha registrado correctamente el usuario");
            } else {
                System.out.println("No se ha podido registrar el usuario");
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            endConnection();
        }

    }

}
