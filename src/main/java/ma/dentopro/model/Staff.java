package ma.dentopro.model;

import lombok.*;
import ma.dentopro.model.enums.Disponibilite;
import ma.dentopro.model.enums.StatusEmploye;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor @NoArgsConstructor @Data @Getter @Setter @ToString
public class Staff {

    Long id;
    String nom;
    Cabinet cabinetDeTravail;
    StatusEmploye statusActuel;
    Map<DayOfWeek, Disponibilite> disponibilite;
    Double salaireDebase;
    LocalDate dateRetourConge;


    public void prendreConge(LocalDate dateRetour, StatusEmploye nouveauStatut) {
        if (dateRetour != null && nouveauStatut != null) {
            this.dateRetourConge = dateRetour;
            this.statusActuel = nouveauStatut;
            System.out.println(this.nom + " est en congé jusqu'au " + dateRetour + ". Statut : " + nouveauStatut);
        } else {
            System.out.println("Erreur : La date de retour ou le statut est invalide.");
        }
    }

    public void revenirTravailler() {
        this.dateRetourConge = null;
        this.statusActuel = StatusEmploye.ACTIF;
        System.out.println(this.nom + " est de retour au travail. Statut : " + this.statusActuel);
    }

}
