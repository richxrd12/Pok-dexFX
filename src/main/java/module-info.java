module pokedex.org.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.sql;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens pokedex.org.pokedex to javafx.fxml;
    exports pokedex.org.pokedex;
    exports pokedex.org.pokedex.Controllers;
    opens pokedex.org.pokedex.Controllers to javafx.fxml;
    exports pokedex.org.pokedex.Models;
    opens pokedex.org.pokedex.Models to javafx.fxml;
    exports pokedex.org.pokedex.Objects;
    opens pokedex.org.pokedex.Objects to javafx.fxml;
}