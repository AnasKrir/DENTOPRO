package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Ordonnance;

import java.util.List;

public interface OrdonnanceService {

    Ordonnance createOrdonnance(Ordonnance ordonnance) throws DaoException;
    Ordonnance updateOrdonnance(Ordonnance ordonnance) throws DaoException;
    void deleteOrdonnance(Long id) throws DaoException;
    Ordonnance getOrdonnanceById(Long id) throws DaoException;
    List<Ordonnance> getAllOrdonnances() throws DaoException;
}
