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
import pokedex.org.pokedex.Models.PokedexGUIModel;
import pokedex.org.pokedex.Objects.Pokemon;
import pokedex.org.pokedex.Objects.Usuario;

public class PokedexGUIController {
    PokedexGUIModel pokedexGUIModel = new PokedexGUIModel();

    @FXML
    Label nombreUsuario;

    @FXML
    private TextField buscadorPokemon;

    @FXML
    private TableView<Pokemon> tablaPokemon;

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
    public void initialize(){
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
        TableColumn<Pokemon, Void> saveButtonColumn = new TableColumn<>("Guardar");
        saveButtonColumn.setId("guardarColumn");
        saveButtonColumn.setCellFactory(x -> new TableCell<Pokemon, Void>() {
            Image image = new Image(getClass().getResourceAsStream("/pokedex/org/pokedex/img/favorito.png"));
            ImageView imageView = new ImageView(image);
            Button saveButton = new Button();


            {
                saveButton.setOnMouseClicked(event -> {
                    Pokemon pokemon = getTableView().getItems().get(getIndex());
                    pokedexGUIModel.guardarPokemon(pokemon, getNombreUsuario());
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

        pokedexGUIModel.getAllPokes(tablaPokemon);

        buscadorPokemon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                buscarPor(newValue);
            }
        });
    }

    @FXML
    void logOut(MouseEvent event) {
        final String LoginFXML = "/pokedex/org/pokedex/Login.fxml";
        try{
            Parent newRoot = FXMLLoader.load(getClass().getResource(LoginFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(newRoot);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            pokedexGUIModel.endConnection();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void editProfile(MouseEvent event) {
        final String profileURL = "/pokedex/org/pokedex/Pokedex/EditProfile.fxml";
        try{
            FXMLLoader profileGUI = new FXMLLoader(getClass().getResource(profileURL));
            Parent root = profileGUI.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Editar perfil");
            stage.setScene(scene);

            Usuario usuario = new Usuario();
            usuario.setNombre(getNombreUsuario());

            EditProfileController editProfileController = profileGUI.getController();
            editProfileController.setNombreUsuario(getNombreUsuario());

            editProfileController.setData(pokedexGUIModel.getUser(getNombreUsuario()));

            stage.centerOnScreen();
            stage.show();

            pokedexGUIModel.endConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //-----------------------------------Hay que hacer esta funcionalidad --------------------------------------------!
    @FXML
    void goToTeams(MouseEvent event) {

    }

    @FXML
    void goMegasAndMoves(MouseEvent event) {
        final String megasMovesURL = "/pokedex/org/pokedex/Pokedex/Megas_Movimientos.fxml";
        try{
            FXMLLoader megasMovGUI = new FXMLLoader(getClass().getResource(megasMovesURL));
            Parent root = megasMovGUI.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Movimientos y Megas");
            stage.setScene(scene);

            Usuario usuario = new Usuario();
            usuario.setNombre(getNombreUsuario());

            MegasMovimientosController megasMovimientosController = megasMovGUI.getController();
            megasMovimientosController.setNombreUsuario(usuario);

            stage.centerOnScreen();
            stage.show();

            pokedexGUIModel.endConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para ir a la pestaña favoritos
    @FXML
    void goToFavs(MouseEvent event) {
        final String FavsURL = "/pokedex/org/pokedex/Pokedex/FavPokemons.fxml";
        try{
            FXMLLoader favGUI = new FXMLLoader(getClass().getResource(FavsURL));
            Parent root = favGUI.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setTitle("Pokémon Favoritos");
            stage.setScene(scene);

            FavPokemonsController favPokemonsController = favGUI.getController();
            favPokemonsController.setNombreUsuario(getNombreUsuario());
            favPokemonsController.initialize();

            stage.centerOnScreen();
            stage.show();

            pokedexGUIModel.endConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para ver si es entero o es una String
    public boolean isInt(String busqueda){
        try{
            Integer.parseInt(busqueda);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //Para buscar por nombre o por id
    public void buscarPor(String busqueda){
        if (busqueda.equals("")){
            pokedexGUIModel.getAllPokes(tablaPokemon);
        }
        if (isInt(busqueda)){

            pokedexGUIModel.searchPokemonByID(tablaPokemon, Integer.parseInt(busqueda));
        }else {
            pokedexGUIModel.searchPokemonByName(tablaPokemon, busqueda);
        }
    }

    //Para ponerle el nombre del usuario a la Label
    public void setNombreUsuario(Usuario usuario) {
        nombreUsuario.setText(usuario.getNombre());
    }

    //Para obtener el nombre de la Label
    public String getNombreUsuario(){
        return nombreUsuario.getText();
    }


}
