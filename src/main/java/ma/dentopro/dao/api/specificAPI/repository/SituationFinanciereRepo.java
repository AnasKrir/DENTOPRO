package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.SituationFinanciere;

import java.util.List;

public interface SituationFinanciereRepo {

    public List<SituationFinanciere> findAll() throws DaoException;
    public SituationFinanciere findById(Long identity) throws DaoException;
    public SituationFinanciere save(SituationFinanciere newElement) throws DaoException;
    public void update(SituationFinanciere newValuesElement) throws DaoException;
    public void delete(SituationFinanciere element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;
}
