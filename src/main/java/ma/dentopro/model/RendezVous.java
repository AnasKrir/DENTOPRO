package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.TypeRDV;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
public class RendezVous {

    private Long idRDV;
    private TypeRDV typeRDV;
    private LocalDate dateRDV;
    private String motif;
    private LocalTime temps;
    private Patient patient; // Référence au patient

    // Constructeur pour créer un rendez-vous à partir du fichier .txt
    public RendezVous(Long idRDV, TypeRDV typeRDV, LocalDate dateRDV, String motif, LocalTime temps) {
        this.idRDV = idRDV;
        this.typeRDV = typeRDV;
        this.dateRDV = dateRDV;
        this.motif = motif;
        this.temps = temps;
    }

    // Constructeur pour créer un rendez-vous à partir des entrées de l'utilisateur
    public RendezVous(Patient patient, LocalDate dateRDV, LocalTime temps, String motif) {
        this.patient = patient;
        this.dateRDV = dateRDV;
        this.temps = temps;
        this.motif = motif;
    }
}