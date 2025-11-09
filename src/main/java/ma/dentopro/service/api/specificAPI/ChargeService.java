package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.Charge;

import java.util.List;

public interface ChargeService {

    Charge createCharge(Charge charge) throws DaoException;
    Charge updateCharge(Charge charge) throws DaoException;
    void deleteCharge(Long id) throws DaoException;
    Charge getChargeById(Long id) throws DaoException;
    List<Charge> getAllCharges() throws DaoException;
}
