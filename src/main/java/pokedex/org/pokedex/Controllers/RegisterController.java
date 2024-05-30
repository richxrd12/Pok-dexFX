package pokedex.org.pokedex.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pokedex.org.pokedex.Models.RegisterModel;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterController {
    RegisterModel registerModel = new RegisterModel();
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRepeatPassword;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtCodPostal;

    @FXML
    private ComboBox<String> sexSelector;

    @FXML
    void registrarUsuario(ActionEvent event) {
        String usuario = txtUser.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String sexo = sexSelector.getValue();
        String password = txtPassword.getText();
        String repeatedPass = txtRepeatPassword.getText();
        String calle = txtCalle.getText();
        String ciudad = txtCiudad.getText();
        String codPostal = txtCodPostal.getText();

        if (comprobarMail(email) && samePassword(password, repeatedPass)){
            errorLabel.setStyle("-fx-text-fill: #b81414;");
            successLabel.setStyle("-fx-text-fill: #008000;");
            registerModel.registerUser(usuario, telefono, email, sexo, password, calle, ciudad, codPostal);
        }else{
            errorLabel.setStyle("-fx-text-fill: blue;");
        }

    }

    public boolean comprobarMail(String email){
        final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean samePassword(String password, String repeatedPass){
        if (password.equals(repeatedPass)){
            return true;
        }
        return false;
    }

    public void populateComboBox(){
        sexSelector.getItems().remove(sexSelector.getItems());
        sexSelector.getItems().addAll("Hombre", "Mujer", "No lo sé", "Helicóptero de combate");
    }

    @FXML
    void returnLogin(MouseEvent event){
        final String loginFXML = "/pokedex/org/pokedex/Login.fxml";

        try{
            FXMLLoader login = new FXMLLoader(getClass().getResource(loginFXML));
            Parent root = login.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setTitle("Login");
            stage.setScene(scene);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
