package pokedex.org.pokedex.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pokedex.org.pokedex.Models.MegasMovimientosModel;
import pokedex.org.pokedex.Objects.Mega_Pokemon;
import pokedex.org.pokedex.Objects.Movimientos;
import pokedex.org.pokedex.Objects.Pokemon;
import pokedex.org.pokedex.Objects.Usuario;

public class MegasMovimientosController {
    MegasMovimientosModel megasMovimientosModel = new MegasMovimientosModel();
    @FXML
    private Label nombreUsuario;

    @FXML
    private TextField buscadorPokemon;

    //Tabla poke
    @FXML
    private TableView<Mega_Pokemon> tablaPokemon;

    @FXML
    private TableColumn<?, ?> idPokemon;

    @FXML
    private TableColumn<?, ?> nombrePokemon;

    @FXML
    private TableColumn<?, ?> tiposPokemon;

    @FXML
    private TableColumn<?, ?> vidaPokemon;

    @FXML
    private TableColumn<?, ?> ataquePokemon;

    @FXML
    private TableColumn<?, ?> defensaPokemon;

    @FXML
    private TableColumn<?, ?> ataqueSpPokemon;

    @FXML
    private TableColumn<?, ?> defensaSpPokemon;

    @FXML
    private TableColumn<?, ?> velocidadPokemon;

    @FXML
    private TableColumn<?, ?> totalPokemon;

    @FXML
    private TableColumn<?, ?> piedraPokemon;

    //La tabla movs
    @FXML
    private TableView<Movimientos> tablaMovs;

    @FXML
    private TableColumn<?, ?> nombreMov;

    @FXML
    private TableColumn<?, ?> powerMov;

    @FXML
    private TableColumn<?, ?> tipoMov;

    @FXML
    private TableColumn<?, ?> categoriaMov;

    private boolean isButtonAdded = false;

    @FXML
    void initialize(){
        //Tabla Pokémon
        idPokemon.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombrePokemon.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tiposPokemon.setCellValueFactory(new PropertyValueFactory<>("tipos"));
        vidaPokemon.setCellValueFactory(new PropertyValueFactory<>("vida"));
        ataquePokemon.setCellValueFactory(new PropertyValueFactory<>("ataque"));
        defensaPokemon.setCellValueFactory(new PropertyValueFactory<>("defensa"));
        ataqueSpPokemon.setCellValueFactory(new PropertyValueFactory<>("ataqueEsp"));
        defensaSpPokemon.setCellValueFactory(new PropertyValueFactory<>("defensaEsp"));
        velocidadPokemon.setCellValueFactory(new PropertyValueFactory<>("velocidad"));
        totalPokemon.setCellValueFactory(new PropertyValueFactory<>("stats"));
        piedraPokemon.setCellValueFactory(new PropertyValueFactory<>("megaPiedra"));

        //Tabla movimientos
        nombreMov.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        powerMov.setCellValueFactory(new PropertyValueFactory<>("poder"));
        categoriaMov.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tipoMov.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        if (!isButtonAdded){
            addSaveButton();
            isButtonAdded = true;
        }

        //Cambiar el método por getAllFavs
        megasMovimientosModel.getAllMegas(tablaPokemon, getNombreUsuario());
        megasMovimientosModel.getAllMovements(tablaMovs);

        buscadorPokemon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                buscarPor(newValue);
            }
        });
    }

    //Este hay que hacerlo aún ---------------------------------------------------------------------------------------
    @FXML
    public void goToTeams(MouseEvent mouseEvent) {
    }

    @FXML
    public void editProfile(MouseEvent mouseEvent) {
        final String profileURL = "/pokedex/org/pokedex/Pokedex/EditProfile.fxml";
        try{
            FXMLLoader profileGUI = new FXMLLoader(getClass().getResource(profileURL));
            Parent root = profileGUI.load();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Editar perfil");
            stage.setScene(scene);

            Usuario usuario = new Usuario();
            usuario.setNombre(getNombreUsuario());

            EditProfileController editProfileController = profileGUI.getController();
            editProfileController.setNombreUsuario(getNombreUsuario());

            editProfileController.setData(megasMovimientosModel.getUser(getNombreUsuario()));

            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

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

    @FXML
    public void goToFavs(MouseEvent mouseEvent) {
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

    public String getNombreUsuario(){
        return nombreUsuario.getText();
    }

    public void setNombreUsuario(Usuario usuario) {
        nombreUsuario.setText(usuario.getNombre());
    }

    public void buscarPor(String busqueda){
        if (busqueda.equals("")){
            megasMovimientosModel.getAllMegas(tablaPokemon, getNombreUsuario());
            megasMovimientosModel.getAllMovements(tablaMovs);
        }else{
            megasMovimientosModel.searchMegaByName(tablaPokemon, busqueda, getNombreUsuario());
            megasMovimientosModel.searchMovement(tablaMovs, busqueda);
        }
    }

    public void addSaveButton(){
        TableColumn<Mega_Pokemon, Void> saveButtonColumn = new TableColumn<>("Guardar");
        saveButtonColumn.setId("guardarColumn");
        saveButtonColumn.setCellFactory(x -> new TableCell<Mega_Pokemon, Void>() {
            Image image = new Image(getClass().getResourceAsStream("/pokedex/org/pokedex/img/favorito.png"));
            ImageView imageView = new ImageView(image);
            Button saveButton = new Button();

            {
                saveButton.setOnMouseClicked(event -> {
                    Pokemon pokemon = getTableView().getItems().get(getIndex());
                    megasMovimientosModel.guardarPokemon(pokemon, getNombreUsuario());
                    megasMovimientosModel.getAllMegas(tablaPokemon, getNombreUsuario());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                }else {
                    imageView.setFitWidth(25);
                    imageView.setFitHeight(25);
                    saveButton.setGraphic(imageView);
                    setGraphic(saveButton);
                }
            }
        });
        tablaPokemon.getColumns().add(saveButtonColumn);

    }
}
