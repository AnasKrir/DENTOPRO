package ma.dentopro.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Medicament {

    Long idMedicament;
    String nom;
    String description;
    Double prix;
    List<PrescriptionDeMedicament> prescriptionDeCesMedicaments;
    List<HistoriqueMedicale> precautionsEnCasde;
}

