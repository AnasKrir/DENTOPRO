package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Acte;

import java.util.List;

public interface ActService {

    Acte createActe(Acte acte) throws DaoException;
    Acte updateActe(Acte acte) throws DaoException;
    void deleteActe(Long id) throws DaoException;
    Acte getActeById(Long id) throws DaoException;
    List<Acte> getAllActes() throws DaoException;
}
