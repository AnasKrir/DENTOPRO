package ma.dentopro.model.enums;

public enum Role {

    Administateur,Secretaire;

    public String getNomRole() {
        return this.name();
    }
}
