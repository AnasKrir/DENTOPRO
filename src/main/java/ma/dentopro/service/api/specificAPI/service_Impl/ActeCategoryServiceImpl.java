package ma.dentopro.service.api.specificAPI.service_Impl;

import ma.dentopro.exceptions.DaoException;
import ma.dentopro.dao.api.specificAPI.repository.CategorieActeRepo;
import ma.dentopro.model.CategorieActe;
import ma.dentopro.service.api.specificAPI.ActeCategoryService;

import java.util.List;

public class ActeCategoryServiceImpl implements ActeCategoryService {

    private final CategorieActeRepo acteCategoryRepo;

    public ActeCategoryServiceImpl(CategorieActeRepo acteCategoryRepo) {
        this.acteCategoryRepo = acteCategoryRepo;
    }

    @Override
    public CategorieActe createActeCategory(CategorieActe acteCategory) throws DaoException {
        return acteCategoryRepo.save(acteCategory);
    }

    @Override
    public CategorieActe updateActeCategory(CategorieActe acteCategory) throws DaoException {
        acteCategoryRepo.update(acteCategory);
        return acteCategory;
    }

    @Override
    public void deleteActeCategory(Long id) throws DaoException {
        acteCategoryRepo.deleteById(id);
    }

    @Override
    public CategorieActe getActeCategoryById(Long id) throws DaoException {
        return acteCategoryRepo.findById(id);
    }

    @Override
    public List<CategorieActe> getAllActeCategories() throws DaoException {
        return acteCategoryRepo.findAll();
    }
}
