package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Facture;

import java.util.List;

public interface FactureService {

    Facture createFacture(Facture facture) throws DaoException;
    Facture updateFacture(Facture facture) throws DaoException;
    void deleteFacture(Long id) throws DaoException;
    Facture getFactureById(Long id) throws DaoException;
    List<Facture> getAllFactures() throws DaoException;
}
