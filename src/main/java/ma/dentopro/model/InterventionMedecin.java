package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.CategorieActe;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class InterventionMedecin {

    private String noteMedecin;
    private Double prixPatient;
    private Long dent;
    private Long idIntervention;
    private CategorieActe acte;
    private Long idConsultation;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID Intervention: ").append(idIntervention).append("\n");
        sb.append("Acte médical: ").append(acte).append("\n");
        sb.append("Dent concernée: ").append(dent != null ? "Dent n°" + dent : "Non spécifiée").append("\n");
        sb.append("Prix pour le patient: ").append(prixPatient != null ? prixPatient + " MAD" : "Non spécifié").append("\n");
        sb.append("Note du médecin: ").append(noteMedecin != null ? noteMedecin : "Aucune note").append("\n");
        sb.append("ID Consultation associée: ").append(idConsultation != null ? idConsultation : "Non spécifiée");
        return sb.toString();
    }
}

