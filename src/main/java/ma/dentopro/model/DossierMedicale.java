package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.StatutPaiement;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class DossierMedicale {

    private Long numeroDossier;
    private Patient patient;
    private Dentiste medecin;
    private List<Consultation> consultations;
    private LocalDate dateCreation;
    private StatutPaiement statutPaiement ;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numéro de dossier: ").append(numeroDossier).append("\n");
        sb.append("Patient: ").append(patient.getNom()).append(" ").append(patient.getPrenom()).append("\n");
        sb.append("Médecin: ").append(medecin.getNom()).append(" ").append(medecin.getPrenom()).append("\n");
        sb.append("Date de création: ").append(dateCreation).append("\n");
        sb.append("Statut de paiement: ").append(statutPaiement).append("\n");
        sb.append("Consultations: ");
        if (consultations != null && !consultations.isEmpty()) {
            for (Consultation consultation : consultations) {
                sb.append("\n  - ").append(consultation.toString());
            }
        } else {
            sb.append("Aucune consultation");
        }
        return sb.toString();
    }


}
