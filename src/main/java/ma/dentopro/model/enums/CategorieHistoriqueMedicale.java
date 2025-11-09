package ma.dentopro.model.enums;

public enum CategorieHistoriqueMedicale {

    CONTRE_INDICATION,
    MALADIE_CHRONIQUE,
    MALADIE_HEREDITAIRE,
    ALLERGIE,
    AUTRE;

    String description;
    Risque risqueAssocie;
}
