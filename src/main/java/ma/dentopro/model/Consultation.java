package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.TypeConsultation;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Consultation {

    private Long idConsultation;
    private List<InterventionMedecin> interventions ;
    private Long iddossierMedicale ;
    private LocalDate dateConsultation;
    private TypeConsultation typeConsultation;
    private List<Facture>  facture ;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID Consultation: ").append(idConsultation).append("\n");
        sb.append("Date de consultation: ").append(dateConsultation).append("\n");
        sb.append("Type de consultation: ").append(typeConsultation).append("\n");
        sb.append("ID Dossier Médical: ").append(iddossierMedicale).append("\n");

        sb.append("Interventions: ");
        if (interventions != null && !interventions.isEmpty()) {
            for (InterventionMedecin intervention : interventions) {
                sb.append("\n  - ").append(intervention.toString());
            }
        } else {
            sb.append("Aucune intervention");
        }

        sb.append("\nFactures: ");
        if (facture != null && !facture.isEmpty()) {
            for (Facture f : facture) {
                sb.append("\n  - ").append(f.toString());
            }
        } else {
            sb.append("Aucune facture");
        }

        return sb.toString();
    }



}
