package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.TypePaiement;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Facture {

    private Double montantRestant =0.0;
    private Long idsituationFinanciere ;
    private Long idConsultation ;
    private Double montantPaye;
    private Long idFacture;
    private LocalDate dateFacturation;
    private Double montantTotal;
    private TypePaiement typePaiement ;

    public Facture( Long idsituationFinanciere, Long idConsultation, Double montantPaye, Long idFacture, LocalDate dateFacturation, Double montantTotal, TypePaiement typePaiement) {

        this.idsituationFinanciere = idsituationFinanciere;
        this.idConsultation = idConsultation;
        this.montantPaye = montantPaye;
        this.idFacture = idFacture;
        this.dateFacturation = dateFacturation;
        this.montantTotal = montantTotal;
        this.typePaiement = typePaiement;
        this.montantRestant = CalculmontantRestant();
    }

    private double CalculmontantRestant() {
        if (getMontantTotal() != null && getMontantPaye() != null) {
            return montantRestant = getMontantTotal() - getMontantPaye();
        } else {
            return montantRestant;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID Facture: ").append(idFacture).append("\n");
        sb.append("Date de facturation: ").append(dateFacturation != null ? dateFacturation : "Non spécifiée").append("\n");
        sb.append("Montant total: ").append(montantTotal != null ? montantTotal + " MAD" : "Non spécifié").append("\n");
        sb.append("Montant payé: ").append(montantPaye != null ? montantPaye + " MAD" : "Non spécifié").append("\n");
        sb.append("Montant restant: ").append(montantRestant + " MAD").append("\n");
        sb.append("Type de paiement: ").append(typePaiement != null ? typePaiement : "Non spécifié").append("\n");
        sb.append("ID Consultation: ").append(idConsultation != null ? idConsultation : "Non spécifiée").append("\n");
        sb.append("ID Situation financière: ").append(idsituationFinanciere != null ? idsituationFinanciere : "Non spécifiée");
        return sb.toString();
    }



}

