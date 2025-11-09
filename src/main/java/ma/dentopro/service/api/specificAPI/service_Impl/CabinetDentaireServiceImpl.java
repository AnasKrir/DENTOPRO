package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.CabinetRepo;
import ma.dentopro.model.Cabinet;
import ma.dentopro.service.api.specificAPI.CabinetDentaireService;

import java.util.List;

public class CabinetDentaireServiceImpl implements CabinetDentaireService {

    private final CabinetRepo cabinetDentaireRepo;


    public CabinetDentaireServiceImpl(CabinetRepo cabinetDentaireRepo) {
        this.cabinetDentaireRepo = cabinetDentaireRepo;
    }

    @Override
    public Cabinet createCabinetDentaire(Cabinet cabinetDentaire) throws DaoException {
        return cabinetDentaireRepo.save(cabinetDentaire);
    }

    @Override
    public Cabinet updateCabinetDentaire(Cabinet cabinetDentaire) throws DaoException {
        cabinetDentaireRepo.update(cabinetDentaire);
        return cabinetDentaire;
    }

    @Override
    public void deleteCabinetDentaire(Long id) throws DaoException {
        cabinetDentaireRepo.deleteById(id);
    }

    @Override
    public Cabinet getCabinetDentaireById(Long id) throws DaoException {
        return cabinetDentaireRepo.findById(id);
    }

    @Override
    public List<Cabinet> getAllCabinetDentaires() throws DaoException {
        return cabinetDentaireRepo.findAll();
    }


}
