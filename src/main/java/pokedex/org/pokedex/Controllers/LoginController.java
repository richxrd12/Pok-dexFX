package pokedex.org.pokedex.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pokedex.org.pokedex.Models.LoginModel;
import pokedex.org.pokedex.Objects.Usuario;

public class LoginController {
    LoginModel loginModel = new LoginModel();
    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label errorLabel;

    @FXML
    void onClickLogin(ActionEvent event) {
        String username = txtUser.getText();
        String password = txtPassword.getText();

        boolean isLogged = loginModel.checkLogin(username, password);

        Usuario usuario = new Usuario();
        usuario.setNombre(username);

        if (isLogged){
            userLoginSuccess(event, usuario);
        }else{
            failedLogin();
        }
    }

    @FXML
    void onClickRegister(MouseEvent event){
        final String registerURL = "/pokedex/org/pokedex/Register.fxml";

        try{
            FXMLLoader register = new FXMLLoader(getClass().getResource(registerURL));

            Parent root = register.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Registrarse");

            RegisterController registerController = register.getController();
            registerController.populateComboBox();

            stage.setScene(scene);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void userLoginSuccess(ActionEvent event, Usuario usuario){
        final String pokedexURL = "/pokedex/org/pokedex/Pokedex/PokedexGUI.fxml";
        try{
            FXMLLoader userGUI = new FXMLLoader(getClass().getResource(pokedexURL));
            Parent root = userGUI.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Pokédex");
            stage.setScene(scene);

            PokedexGUIController pokedexGUIController =  userGUI.getController();
            pokedexGUIController.setNombreUsuario(usuario);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void failedLogin(){
        errorLabel.setStyle("-fx-text-fill: blue;");
    }

}
