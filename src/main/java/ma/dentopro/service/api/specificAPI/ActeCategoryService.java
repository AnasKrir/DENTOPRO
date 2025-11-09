package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.model.CategorieActe;

import java.util.List;

public interface ActeCategoryService {

    CategorieActe createActeCategory(CategorieActe acteCategory) throws DaoException;
    CategorieActe updateActeCategory(CategorieActe acteCategory) throws DaoException;
    void deleteActeCategory(Long id) throws DaoException;
    CategorieActe getActeCategoryById(Long id) throws DaoException;
    List<CategorieActe> getAllActeCategories() throws DaoException;
}
