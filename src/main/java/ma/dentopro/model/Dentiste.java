package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.Specialite;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Dentiste extends Utilisateur{

    private Double salaireDeBase;
    private Specialite specialite;


}
