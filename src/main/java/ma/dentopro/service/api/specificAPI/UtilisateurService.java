package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.*;
import ma.dentopro.model.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    Utilisateur createUtilisateur(Utilisateur utilisateur) throws DaoException;
    Utilisateur updateUtilisateur(Utilisateur utilisateur) throws DaoException;
    void deleteUtilisateur(Long id) throws DaoException;
    Utilisateur getUtilisateurById(Long id) throws DaoException;
    List<Utilisateur> getAllUtilisateurs() throws DaoException;
    Utilisateur authenticate(String username, String password) throws AuthenticationException;
    void Register(Utilisateur utilisateur) throws UserRegistrationException, InvalidPasswordException, InvalidUsernameException;
    void deleteUser(String username) throws UserNotFoundException, AuthenticationException;
}
