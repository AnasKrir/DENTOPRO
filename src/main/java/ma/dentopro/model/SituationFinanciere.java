package ma.dentopro.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class SituationFinanciere {

    private DossierMedicale dossierMedicale;
    private LocalDate dateCreation;
    private Double montantGlobaleRestant = 0.0;
    private Long idSituationFinanciere;
    private List<Facture> factures;

    public SituationFinanciere(DossierMedicale dossierMedicale, LocalDate dateCreation,
                               Long idSituationFinanciere, List<Facture> factures) {
        this.dossierMedicale = dossierMedicale;
        this.dateCreation = dateCreation;
        this.idSituationFinanciere = idSituationFinanciere;
        this.factures = factures;
        this.montantGlobaleRestant = calculerMontantGlobaleRestant();
        calculerMontantGlobaleRestant();
    }

    private double calculerMontantGlobaleRestant() {
        for (Facture facture : factures) {
            montantGlobaleRestant += facture.getMontantRestant();
        }

        return montantGlobaleRestant;
    }

}