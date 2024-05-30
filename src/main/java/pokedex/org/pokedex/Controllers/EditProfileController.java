package pokedex.org.pokedex.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pokedex.org.pokedex.Models.EditProfileModel;
import pokedex.org.pokedex.Objects.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileController {
    EditProfileModel editProfileModel = new EditProfileModel();
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ImageView imageSex;

    @FXML
    private Label nombreUsuario;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtCodPostal;

    @FXML
    private TextField txtCiudad;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtRepeatedPass;

    @FXML
    public void initialize(){
        comboBoxValues();


        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old, String newValue) {
                if (newValue.equalsIgnoreCase("hombre")){
                    Image imageHombre = new Image(getClass().getResource("/pokedex/org/pokedex/img/hombre.jpg")
                            .toExternalForm());
                    imageSex.setFitHeight(400);
                    imageSex.setFitWidth(200);
                    imageSex.setImage(imageHombre);
                } else if (newValue.equalsIgnoreCase("mujer")){
                    Image imageMujer = new Image(getClass().getResource("/pokedex/org/pokedex/img/mujer.jpg")
                            .toExternalForm());
                    imageSex.setFitHeight(400);
                    imageSex.setFitWidth(200);
                    imageSex.setImage(imageMujer);
                } else if (newValue.equalsIgnoreCase("helicóptero de combate")) {
                    Image imageHelicopter = new Image(getClass()
                            .getResource("/pokedex/org/pokedex/img/helicoptero-de-combate.png")
                            .toExternalForm());
                    imageSex.setFitHeight(400);
                    imageSex.setFitWidth(200);
                    imageSex.setImage(imageHelicopter);
                } else{
                    Image imageIdk = new Image(getClass()
                            .getResource("/pokedex/org/pokedex/img/no-lo-se.jpg")
                            .toExternalForm());
                    imageSex.setFitHeight(400);
                    imageSex.setFitWidth(200);
                    imageSex.setImage(imageIdk);
                }
            }
        });
    }

    //Para salir al menú de iniciar sesión
    @FXML
    public void logOut(MouseEvent mouseEvent) {
        final String LoginFXML = "/pokedex/org/pokedex/Login.fxml";
        try{
            Parent newRoot = FXMLLoader.load(getClass().getResource(LoginFXML));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(newRoot);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para volver al menú de inicio
    @FXML
    void goToHome(MouseEvent event) {
        final String homeURL = "/pokedex/org/pokedex/Pokedex/PokedexGUI.fxml";
        try{
            FXMLLoader homeGUI = new FXMLLoader(getClass().getResource(homeURL));
            Parent root = homeGUI.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Pokémon Favoritos");
            stage.setScene(scene);

            Usuario usuario = new Usuario();
            usuario.setNombre(getNombreUsuario());

            PokedexGUIController pokedexGUIController = homeGUI.getController();
            pokedexGUIController.setNombreUsuario(usuario);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para ir a la pestaña de favoritos
    @FXML
    public void goToFavs(MouseEvent mouseEvent){
        final String FavsURL = "/pokedex/org/pokedex/Pokedex/FavPokemons.fxml";
        try{
            FXMLLoader favGUI = new FXMLLoader(getClass().getResource(FavsURL));
            Parent root = favGUI.load();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Pokémon Favoritos");
            stage.setScene(scene);

            FavPokemonsController favPokemonsController = favGUI.getController();
            favPokemonsController.setNombreUsuario(getNombreUsuario());
            favPokemonsController.initialize();

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Todavía queda hacerlo ------------------------------------------------------------------------------------------
    @FXML
    public void goToTeams(MouseEvent mouseEvent) {

    }

    @FXML
    public void goMegasAndMoves(MouseEvent mouseEvent) {
        final String megasMovesURL = "/pokedex/org/pokedex/Pokedex/Megas_Movimientos.fxml";
        try{
            FXMLLoader megasMovGUI = new FXMLLoader(getClass().getResource(megasMovesURL));
            Parent root = megasMovGUI.load();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Movimientos y Megas");
            stage.setScene(scene);

            Usuario usuario = new Usuario();
            usuario.setNombre(getNombreUsuario());

            MegasMovimientosController megasMovimientosController = megasMovGUI.getController();
            megasMovimientosController.setNombreUsuario(usuario);

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para ponerle los valores a la ComboBox
    public void comboBoxValues(){
        //Le agregamos las opciones y luegos las setteamos conforme a lo que haya puesto el usuario
        comboBox.getItems().remove(comboBox.getItems());
        comboBox.getItems().addAll("Hombre", "Mujer", "Helicóptero de combate", "No lo sé");
    }

    //Para dejar establecido el valor de la ComboBox depende del sexo
    public void setComboBoxValue(Usuario usuario){
        if (usuario.getSexo().equalsIgnoreCase("hombre")){
            comboBox.getSelectionModel().select(0);
        } else if (usuario.getSexo().equalsIgnoreCase("mujer")) {
            comboBox.getSelectionModel().select(1);
        } else if (usuario.getSexo().equalsIgnoreCase("helicóptero de combate")) {
            comboBox.getSelectionModel().select(2);
        } else{
            comboBox.getSelectionModel().select(3);
        }
    }

    //Para poner el nombre de la Label
    public void setNombreUsuario(String usuario){
        nombreUsuario.setText(usuario);
    }

    //Para obtener el nombre de la label
    public String getNombreUsuario(){
        return nombreUsuario.getText();
    }

    //Para poner los datos en los TextField cuando carga el editProfile
    public void setData(Usuario usuario){
        txtNombre.setText(usuario.getNombre());
        txtEmail.setText(usuario.getEmail());
        txtTelefono.setText(usuario.getTelefono());
        txtPassword.setText(usuario.getPassword());
        txtCalle.setText(usuario.getCalle());
        txtCodPostal.setText(usuario.getCodPostal());
        txtCiudad.setText(usuario.getCiudad());

        setComboBoxValue(usuario);
    }

    //Para editar los campos del usuario
    public void onClickEditProfile(ActionEvent event) {
        String username = txtNombre.getText();
        String email = txtEmail.getText();
        String tlf = txtTelefono.getText();
        String sexo = comboBox.getValue();
        String pass = txtPassword.getText();
        String repeatedPass = txtRepeatedPass.getText();
        String calle = txtCalle.getText();
        String codPostal = txtCodPostal.getText();
        String ciudad = txtCiudad.getText();

        if (pass.equals(repeatedPass) && comprobarMail(email)){
            Usuario usuario = editProfileModel.getUser(getNombreUsuario());
            editProfileModel.updateUser(usuario.getID(), username, tlf, email, sexo, pass, calle, codPostal, ciudad);
            setNombreUsuario(username);
        }else{
            errorLabel.setStyle("-fx-text-fill: #b81414");
        }
    }

    public boolean comprobarMail(String email){
        final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
