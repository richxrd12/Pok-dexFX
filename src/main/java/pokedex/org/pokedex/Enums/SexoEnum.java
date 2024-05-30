package pokedex.org.pokedex.Enums;

//No la estoy usando porque Postgres es un mierdón
public enum SexoEnum {
    HOMBRE,
    MUJER,
    NO_SE,
    HELICOPTERO_DE_COMBATE;

    public String getValue(){
        switch (this){
            default:
                return "Hombre";
            case HELICOPTERO_DE_COMBATE:
                return "Helicóptero de combate";
            case NO_SE:
                return "No se";
            case MUJER:
                return "Mujer";
            case HOMBRE:
                return "Hombre";
        }
    }
}
