package ma.dentopro.model;


import lombok.*;
import ma.dentopro.model.enums.CategorieAntecedentsMedicaux;
import ma.dentopro.model.enums.GroupeSanguin;
import ma.dentopro.model.enums.Mutuelle;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString @EqualsAndHashCode
public class Patient extends Personne {

    private LocalDate dateNaissance;
    private Mutuelle mutuelle;
    private GroupeSanguin groupeSanguin;
    private List<CategorieAntecedentsMedicaux> AntecedentsMedicaux;

    private String profession ;


    public Patient(String nom, String prenom, String adresse, Long id, String telephone, String email, String CIN, LocalDate dateNaissance, Mutuelle mutuelle, GroupeSanguin groupeSanguin, List<CategorieAntecedentsMedicaux> antecedentsMedicaux, String profession) {
        super(nom, prenom, adresse, id, telephone, email, CIN);
        this.dateNaissance = dateNaissance;
        this.mutuelle = mutuelle;
        this.groupeSanguin = groupeSanguin;
        AntecedentsMedicaux = antecedentsMedicaux;
        this.profession = profession;
    }


}



