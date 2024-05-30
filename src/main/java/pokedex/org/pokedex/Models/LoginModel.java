package pokedex.org.pokedex.Models;

import pokedex.org.pokedex.Objects.Usuario;

import java.sql.*;

public class LoginModel {
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
        }catch (SQLException e){
            System.out.println(e);
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

    public boolean checkLogin(String username, String password){
        //Inicializamos variables
        String loginQuery = "SELECT nombre, user_password FROM usuarios WHERE nombre=? AND user_password=?;";
        PreparedStatement statementSentencia = null;
        ResultSet setResultados = null;

        //Iniciamos la conexión con la BBDD
        startConnection();

        //Hacemos la consulta
        if (conexionLogin != null){
            try{
                statementSentencia = conexionLogin.prepareStatement(loginQuery);

                statementSentencia.setString(1, username);
                statementSentencia.setString(2, password);

                setResultados = statementSentencia.executeQuery();

                if (setResultados.next()){
                    System.out.println("Usuario " + setResultados.getString("nombre") + " ha iniciado sesión.");
                    //Creamos el objeto al que vamos a acceder luego para ponerle nombre a la label
                    Usuario usuario = new Usuario();
                    usuario.setNombre(username);
                    endConnection();
                    return true;
                }

            }catch (Exception e){
                System.out.println(e);
            }
        }else{
            System.out.println("No se puede realizar el check debido a que no se ha conectado la BBDD");
        }
        endConnection();
        return false;
    }
}
