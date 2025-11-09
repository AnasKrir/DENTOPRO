package ma.dentopro.service.api.specificAPI;

import ma.dentopro.exceptions.*;
import ma.dentopro.model.Utilisateur;

public interface AuthInterface {
    boolean login(String username, String password) throws AuthenticationException;
    void logout(String username);
    void Register(Utilisateur utilisateur) throws UserRegistrationException, InvalidPasswordException, InvalidUsernameException;
    void deleteUser(String username) throws UserNotFoundException;


}
