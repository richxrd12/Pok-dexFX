package pokedex.org.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader Login = new FXMLLoader(App.class.getResource("/pokedex/org/pokedex/Login.fxml"));

        //Le damos una altura al login y lo seteamos a que no se pueda hacer resizable
        Scene scene = new Scene(Login.load(), 1000, 600);
        stage.setResizable(false);

        //Le indicamos el icono
        Image pokedexIcon = new Image(
                    getClass().getResource("/pokedex/org/pokedex/img/pokedex-icon.png").toExternalForm());
        stage.getIcons().add(pokedexIcon);

        //Le indicamos la hoja de estilos
        scene.getStylesheets().add(
                getClass().getResource("/pokedex/org/pokedex/styles/styles.css").toExternalForm()
        );

        //Seteamos el título y mostramos el contenido
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
