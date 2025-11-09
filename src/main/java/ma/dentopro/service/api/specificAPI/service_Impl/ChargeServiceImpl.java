package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.ChargeRepo;
import ma.dentopro.model.Charge;
import ma.dentopro.service.api.specificAPI.ChargeService;

import java.util.List;

public class ChargeServiceImpl implements ChargeService {

    private final ChargeRepo chargeRepo;

    public ChargeServiceImpl(ChargeRepo chargeRepo) {
        this.chargeRepo = chargeRepo;
    }

    @Override
    public Charge createCharge(Charge charge) throws DaoException {
        return chargeRepo.save(charge);
    }

    @Override
    public Charge updateCharge(Charge charge) throws DaoException {
        chargeRepo.update(charge);
        return charge;
    }

    @Override
    public void deleteCharge(Long id) throws DaoException {
        chargeRepo.deleteById(id);
    }

    @Override
    public Charge getChargeById(Long id) throws DaoException {
        return chargeRepo.findById(id);
    }

    @Override
    public List<Charge> getAllCharges() throws DaoException {
        return chargeRepo.findAll();
    }
}
