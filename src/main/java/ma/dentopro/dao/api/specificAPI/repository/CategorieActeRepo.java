package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.CategorieActe;

import java.util.List;

public interface CategorieActeRepo {

    List<CategorieActe> findAll() throws DaoException;
    CategorieActe findById(Long id) throws DaoException;
    CategorieActe save(CategorieActe newElement) throws DaoException;
    void update(CategorieActe categorieActe) throws DaoException;
    void delete(CategorieActe categorieActe) throws DaoException;
    void deleteById(Long id) throws DaoException;

}