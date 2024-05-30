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
import pokedex.org.pokedex.Models.FavPokemonsModel;
import pokedex.org.pokedex.Objects.Mega_Pokemon;
import pokedex.org.pokedex.Objects.Pokemon;
import pokedex.org.pokedex.Objects.Usuario;

public class FavPokemonsController {

    FavPokemonsModel favPokemonsModel = new FavPokemonsModel();

    @FXML
    private Label nombreUsuario;

    @FXML
    private TextField buscadorPokemon;

    //Label datos (para cambiar el color depende del valor de los stats)

    @FXML
    private Label nombrePoke;

    @FXML
    private Label vidaLabel;

    @FXML
    private Label ataqueLabel;

    @FXML
    private Label defensaLabel;

    @FXML
    private Label ataqEspLabel;

    @FXML
    private Label defEspLabel;

    @FXML
    private Label velocidadLabel;

    //Estos son los stats que van a cambiar

    @FXML
    private Label vidaPoke;

    @FXML
    private Label ataquePoke;

    @FXML
    private Label defensaPoke;

    @FXML
    private Label atEspPoke;

    @FXML
    private Label defEspPoke;

    @FXML
    private Label velocidadPoke;

    @FXML
    private Label tipoPoke;

    //Tableview Poke

    @FXML
    private TableView<Pokemon> tablaPokemon;

    @FXML
    private TableColumn<?, ?> idPokemon;

    @FXML
    private TableColumn<?, ?> nombrePokemon;

    @FXML
    private TableColumn<?, ?> totalPokemon;

    //Tableview MegaPoke
    @FXML
    private TableView<Mega_Pokemon> tablaMegas;

    @FXML
    private TableColumn<?, ?> idMegaPokemon;

    @FXML
    private TableColumn<?, ?> nombreMegaPokemon;

    @FXML
    private TableColumn<?, ?> totalMegaPokemon;

    private boolean isButtonAdded = false;

    @FXML
    public void initialize(){
        idPokemon.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombrePokemon.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        totalPokemon.setCellValueFactory(new PropertyValueFactory<>("stats"));

        idMegaPokemon.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreMegaPokemon.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        totalMegaPokemon.setCellValueFactory(new PropertyValueFactory<>("stats"));


        if (!isButtonAdded){
            addSaveButtonPokemon();
            addSaveButtonMega();
            isButtonAdded = true;
        }

        //Cambiar el método por getAllFavs
        favPokemonsModel.getFavPokes(tablaPokemon, getNombreUsuario());
        favPokemonsModel.getFavMegas(tablaMegas, getNombreUsuario());

        buscadorPokemon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                buscarPor(newValue);
            }
        });
    }

    //Para editar el perfil, hay que crearlo más tarde, cuando se tenga el FXML
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

            editProfileController.setData(favPokemonsModel.getUser(getNombreUsuario()));

            stage.centerOnScreen();
            stage.show();

            favPokemonsModel.endConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para volver a la pantalla principal
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

            favPokemonsModel.endConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para hacer equipos, todavía hay que crearlo
    @FXML
    void goToTeams(MouseEvent event) {

    }

    //Para volver al menú del login
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

            favPokemonsModel.endConnection();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para ir a Megas y movimientos
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

            favPokemonsModel.endConnection();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    //Para buscar por nombre o por id
    public void buscarPor(String busqueda){
        if (busqueda.equals("")){
            favPokemonsModel.getFavPokes(tablaPokemon, getNombreUsuario());
        }
        if (isInt(busqueda)){
            favPokemonsModel.searchPokemonByID(tablaPokemon, busqueda, getNombreUsuario());
            favPokemonsModel.searchMegaByID(tablaMegas, busqueda, getNombreUsuario());
        }else {
            favPokemonsModel.searchPokemonByName(tablaPokemon, busqueda, getNombreUsuario());
            favPokemonsModel.searchMegaByName(tablaMegas, busqueda, getNombreUsuario());
        }
    }

    //Para comprobar si el campo del buscador es texto o numérico
    public boolean isInt(String busqueda){
        try{
            Integer.parseInt(busqueda);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //Para añadir el botón de guardado a la tableview
    public void addSaveButtonPokemon(){
        TableColumn<Pokemon, Void> saveButtonColumn = new TableColumn<>("Guardar");
        saveButtonColumn.setId("guardarColumn");
        saveButtonColumn.setCellFactory(x -> new TableCell<Pokemon, Void>() {
            Image image = new Image(getClass().getResourceAsStream("/pokedex/org/pokedex/img/favorito.png"));
            ImageView imageView = new ImageView(image);
            Button saveButton = new Button();

            {
                saveButton.setOnMouseClicked(event -> {
                    Pokemon pokemon = getTableView().getItems().get(getIndex());
                    favPokemonsModel.guardarPokemon(pokemon, getNombreUsuario());
                    favPokemonsModel.getFavPokes(tablaPokemon, getNombreUsuario());
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

        tablaPokemon.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)->{
                if (newVal != null){
                    showPokemon(newVal);
                }
        });

        tablaMegas.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)->{
            if (newVal != null){
                showMega(newVal);
            }
        });
    }

    public void addSaveButtonMega(){
        TableColumn<Mega_Pokemon, Void> saveButtonColumn = new TableColumn<>("Guardar");
        saveButtonColumn.setId("guardarColumn");
        saveButtonColumn.setCellFactory(x -> new TableCell<Mega_Pokemon, Void>() {
            Image image = new Image(getClass().getResourceAsStream("/pokedex/org/pokedex/img/favorito.png"));
            ImageView imageView = new ImageView(image);
            Button saveButton = new Button();

            {
                saveButton.setOnMouseClicked(event -> {
                    Mega_Pokemon mega = getTableView().getItems().get(getIndex());
                    favPokemonsModel.guardarMega(mega, getNombreUsuario());
                    favPokemonsModel.getFavMegas(tablaMegas, getNombreUsuario());
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
        tablaMegas.getColumns().add(saveButtonColumn);

        tablaPokemon.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal)->{
            if (newVal != null){
                showPokemon(newVal);
            }
        });
    }

    //Para obtener el nombre de usuario de la label
    public String getNombreUsuario(){
        return nombreUsuario.getText();
    }

    //Para poner el nombre de usuario en la label
    public void setNombreUsuario(String usuario){
        nombreUsuario.setText(usuario);
    }

    //Para ver el Pokemon cuando seleccionamos alguno
    public void showPokemon(Pokemon pokemon){
        //Le establecemos los datos a los label
        nombrePoke.setText(pokemon.getNombre());
        vidaPoke.setText(String.valueOf(pokemon.getVida()));
        ataquePoke.setText(String.valueOf(pokemon.getAtaque()));
        defensaPoke.setText(String.valueOf(pokemon.getDefensa()));
        atEspPoke.setText(String.valueOf(pokemon.getAtaqueEsp()));
        defEspPoke.setText(String.valueOf(pokemon.getDefensaEsp()));
        velocidadPoke.setText(String.valueOf(pokemon.getVelocidad()));
        tipoPoke.setText(pokemon.getTipos());

        changeColours(pokemon);
    }

    public void showMega(Mega_Pokemon mega){
        //Le establecemos los datos a los label
        nombrePoke.setText(mega.getNombre());
        vidaPoke.setText(String.valueOf(mega.getVida()));
        ataquePoke.setText(String.valueOf(mega.getAtaque()));
        defensaPoke.setText(String.valueOf(mega.getDefensa()));
        atEspPoke.setText(String.valueOf(mega.getAtaqueEsp()));
        defEspPoke.setText(String.valueOf(mega.getDefensaEsp()));
        velocidadPoke.setText(String.valueOf(mega.getVelocidad()));
        tipoPoke.setText(mega.getTipos());

        changeColours(mega);
    }

    //Para cambiar el color de los stats cuando seleccionamos al Pokemon
    public void changeColours(Pokemon pokemon){
        if (pokemon.getVida() <= 30){
            vidaPoke.setStyle("-fx-text-fill: #b81414");
        } else if (pokemon.getVida() < 60 && pokemon.getVida() > 30) {
            vidaPoke.setStyle("-fx-text-fill: #FFD34F");
        } else if (pokemon.getVida() >= 60){
            vidaPoke.setStyle("-fx-text-fill: #03F934");
        }

        if (pokemon.getAtaque() <= 30){
            ataquePoke.setStyle("-fx-text-fill: #b81414");
        } else if (pokemon.getAtaque() < 60 && pokemon.getAtaque() > 30) {
            ataquePoke.setStyle("-fx-text-fill: #FFD34F");
        } else if (pokemon.getAtaque() >= 60){
            ataquePoke.setStyle("-fx-text-fill: #03F934");
        }

        if (pokemon.getDefensa() <= 30){
            defensaPoke.setStyle("-fx-text-fill: #b81414");
        } else if (pokemon.getDefensa() < 60 && pokemon.getDefensa() > 30) {
            defensaPoke.setStyle("-fx-text-fill: #FFD34F");
        } else if (pokemon.getDefensa() >= 60){
            defensaPoke.setStyle("-fx-text-fill: #03F934");
        }

        if (pokemon.getAtaqueEsp() <= 30){
            atEspPoke.setStyle("-fx-text-fill: #b81414");
        } else if (pokemon.getAtaqueEsp() < 60 && pokemon.getAtaqueEsp() > 30) {
            atEspPoke.setStyle("-fx-text-fill: #FFD34F");
        } else if (pokemon.getAtaqueEsp() >= 60){
            atEspPoke.setStyle("-fx-text-fill: #03F934");
        }

        if (pokemon.getDefensaEsp() <= 30){
            defEspPoke.setStyle("-fx-text-fill: #b81414");
        } else if (pokemon.getDefensaEsp() < 60 && pokemon.getDefensaEsp() > 30) {
            defEspPoke.setStyle("-fx-text-fill: #FFD34F");
        } else if (pokemon.getDefensaEsp() >= 60){
            defEspPoke.setStyle("-fx-text-fill: #03F934");
        }

        if (pokemon.getVelocidad() <= 30){
            velocidadPoke.setStyle("-fx-text-fill: #b81414");
        } else if (pokemon.getVelocidad() < 60 && pokemon.getVelocidad() > 30) {
            velocidadPoke.setStyle("-fx-text-fill: #FFD34F");
        } else if (pokemon.getVelocidad() >= 60){
            velocidadPoke.setStyle("-fx-text-fill: #03F934");
        }
    }


}