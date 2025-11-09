package ma.dentopro.dao.api.specificAPI.repository;

import ma.dentopro.exceptions.AuthenticationException;
import ma.dentopro.exceptions.DaoException;
import ma.dentopro.exceptions.UserNotFoundException;
import ma.dentopro.model.Utilisateur;

import java.util.List;

public interface UtilisateurRepo {

    public List<Utilisateur> findAll() throws DaoException;
    public Utilisateur findById(Long identity) throws DaoException;
    public Utilisateur findByUsername(String username) throws AuthenticationException;
    public Utilisateur save(Utilisateur newElement) throws DaoException;
    public void update(Utilisateur newValuesElement) throws DaoException;
    public void delete(Utilisateur element) throws DaoException;
    public void deleteById(Long identity) throws DaoException;

}
