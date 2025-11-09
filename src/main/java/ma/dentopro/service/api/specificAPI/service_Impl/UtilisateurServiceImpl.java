package ma.dentopro.service.api.specificAPI.service_Impl;

import lombok.Getter;
import ma.dentopro.exceptions.*;
import ma.dentopro.dao.api.specificAPI.repository.UtilisateurRepo;
import ma.dentopro.model.Utilisateur;
import ma.dentopro.service.api.specificAPI.UtilisateurService;

import java.util.List;

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepo utilisateurRepo;
    private Utilisateur loggedInUser;


    public Utilisateur getLoggedInUser() {
        return loggedInUser;
    }

    public UtilisateurServiceImpl(UtilisateurRepo utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) throws DaoException {
        return utilisateurRepo.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) throws DaoException {
        utilisateurRepo.update(utilisateur);
        return utilisateur;
    }

    @Override
    public void deleteUtilisateur(Long id) throws DaoException {
        utilisateurRepo.deleteById(id);
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) throws DaoException {
        return utilisateurRepo.findById(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() throws DaoException {
        return utilisateurRepo.findAll();
    }

    @Override
    public Utilisateur authenticate(String username, String password) throws AuthenticationException {
        Utilisateur utilisateur = utilisateurRepo.findByUsername(username);
        if (utilisateur != null && utilisateur.getPassword().equals(password)) {
            loggedInUser = utilisateur; // Mettre à jour l'utilisateur connecté
            return utilisateur;
        } else {
            throw new AuthenticationException("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    @Override
    public void Register(Utilisateur utilisateur) throws UserRegistrationException {
        try {
            if (utilisateurRepo.findById(utilisateur.getId()) != null) {
                throw new UserRegistrationException("L'utilisateur avec le nom d'utilisateur '" + utilisateur.getUsername() + "' existe déjà.");
            }
            if (utilisateur.getPassword().length() <= 5) {
                throw new InvalidPasswordException("Le mot de passe doit contenir plus de 5 caractères.");
            }

            if (utilisateur.getUsername().length() <= 8 || Character.isDigit(utilisateur.getUsername().charAt(0))) {
                throw new InvalidUsernameException("Le nom d'utilisateur doit dépasser 8 caractères et ne doit pas commencer par un chiffre.");
            }

            utilisateurRepo.save(utilisateur);
        } catch (InvalidPasswordException | InvalidUsernameException e) {
            throw new UserRegistrationException("Erreur lors de l'ajout de l'utilisateur : " + utilisateur.getUsername());
        } catch (DaoException e) {
            throw new UserRegistrationException("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(String username) throws UserNotFoundException, AuthenticationException {
        try {
            Utilisateur utilisateur = utilisateurRepo.findByUsername(username);
            if (utilisateur == null) {
                throw new UserNotFoundException("Utilisateur avec le nom d'utilisateur '" + username + "' introuvable.");
            }
            utilisateurRepo.deleteById(utilisateur.getId());
        } catch (DaoException e) {
            throw new UserNotFoundException("Une erreur s'est produite lors de la suppression de l'utilisateur : " + username);
        }
    }
}