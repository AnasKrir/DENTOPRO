package ma.dentopro.service.api.specificAPI;

import ma.dentopro.model.InterventionMedecin;
import ma.dentopro.exceptions.FormulaireException;

public interface DentisteInterface {

    void creerConsultation() throws FormulaireException;
    void mettreAJourConsultation() throws FormulaireException;
    void supprimerConsultation(long consultationId) throws FormulaireException;

    void creerInterventionMedecin() throws FormulaireException;
    void mettreAJourInterventionMedecin(InterventionMedecin intervention) throws FormulaireException;
    void supprimerInterventionMedecin(long interventionId) throws FormulaireException;
}
