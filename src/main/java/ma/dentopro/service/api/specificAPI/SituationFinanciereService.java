package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.SituationFinanciere;

import java.util.List;

public interface SituationFinanciereService {

    SituationFinanciere createSituationFinanciere(SituationFinanciere situationFinanciere) throws DaoException;
    SituationFinanciere updateSituationFinanciere(SituationFinanciere situationFinanciere) throws DaoException;
    void deleteSituationFinanciere(Long id) throws DaoException;
    SituationFinanciere getSituationFinanciereById(Long id) throws DaoException;
    List<SituationFinanciere> getAllSituationFinancieres() throws DaoException;
}
