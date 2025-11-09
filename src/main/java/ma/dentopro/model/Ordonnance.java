package ma.dentopro.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Ordonnance {

    Long idOrdonnance;
    LocalDate date;
    Consultation consultationConcernee;
    List<PrescriptionDeMedicament> prescriptionDeMedicaments;
}
