package ma.dentopro.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Caisse {

    private List<SituationFinanciere> situationFinanciere;
    private Double recetteDuJour ;
    private Double recetteDumois;
    private Double recetteDeLAnnee;
    private Long idCaisse;


    public Double recetteDuJour(LocalDate date) {
        recetteDuJour = 0.0;
        for (SituationFinanciere situation : situationFinanciere) {
            for (Facture facture : situation.getFactures()) {
                if (facture.getDateFacturation().equals(date)) {
                    recetteDuJour += facture.getMontantPaye();
                }
            }
        }
        return recetteDuJour;
    }

    public Double recetteDumois(int year, int month) {
        recetteDumois = 0.0;
        for (SituationFinanciere situation : situationFinanciere) {
            for (Facture facture : situation.getFactures()) {
                if (facture.getDateFacturation().getYear() == year &&
                        facture.getDateFacturation().getMonthValue() == month) {
                    recetteDumois += facture.getMontantPaye();
                }
            }
        }
        return recetteDumois;
    }

    public Double recetteDeLAnnee(int year) {
        recetteDeLAnnee = 0.0;
        for (SituationFinanciere situation : situationFinanciere) {
            for (Facture facture : situation.getFactures()) {
                if (facture.getDateFacturation().getYear() == year) {
                    recetteDeLAnnee += facture.getMontantPaye();
                }
            }
        }
        return recetteDeLAnnee;
    }

}
