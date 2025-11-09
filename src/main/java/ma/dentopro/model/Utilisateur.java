package ma.dentopro.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import ma.dentopro.model.enums.Role;

@NoArgsConstructor @Data @Getter @Setter @ToString
public class Utilisateur extends Personne{

    private String username;
    private String password;
    private Role roles;

    public Utilisateur(String username, String password, Role roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Utilisateur(String nom, String prenom, String adresse, Long id, String telephone, String email, String CIN, String username, String password, Role roles) {
        super(nom, prenom, adresse, id, telephone, email, CIN);
        this.username = username;
        this.password = password;
        this.roles = roles;
    }




    public void setRoles(ma.dentopro.model.enums.Role role) {
        this.roles = roles;

    }

    public Role getRoles() {
        return roles;
    }
}
