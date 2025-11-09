package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.CategorieActe;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Acte {

    Long idActe;
    String libelle;
    Double prixDeBase;
    List<InterventionMedecin> interventions;
    @Setter
    CategorieActe categorie;

}
