package ma.dentopro.model;


import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class PrescriptionDeMedicament {

    Long idPrescription;
    int unitesMinAPrendre;
    int unitesMaxAPrendre;
    String contraintesAlimentation;
    Ordonnance ordonnance;
    String contraintesTemps;
    Medicament medicamentAPrescrire;

}
