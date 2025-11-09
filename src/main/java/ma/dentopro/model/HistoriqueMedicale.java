package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.CategorieHistoriqueMedicale;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class HistoriqueMedicale {

    Long idAntecedent;
    List<Patient> patientsAvecHistoriqueMedicale;
    String libelle;
    CategorieHistoriqueMedicale categorie;

}
