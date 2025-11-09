package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Cabinet;

import java.util.List;

public interface CabinetDentaireService {

    Cabinet createCabinetDentaire(Cabinet cabinetDentaire) throws DaoException;
    Cabinet updateCabinetDentaire(Cabinet cabinetDentaire) throws DaoException;
    void deleteCabinetDentaire(Long id) throws DaoException;
    Cabinet getCabinetDentaireById(Long id) throws DaoException;
    List<Cabinet> getAllCabinetDentaires() throws DaoException;
}
