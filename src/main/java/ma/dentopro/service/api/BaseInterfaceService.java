package ma.dentopro.service.api;

import ma.dentopro.exceptions.DaoException;

import java.util.List;

public interface BaseInterfaceService<T>  {

    T create(T entity) throws DaoException;
    T update(T entity) throws DaoException;
    void delete(Long id) throws DaoException;
    T getById(Long id) throws DaoException;
    List<T> getAll() throws DaoException;
}
