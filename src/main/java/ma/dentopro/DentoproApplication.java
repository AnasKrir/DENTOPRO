package ma.dentopro;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.UtilisateurDao;
import ma.dentopro.presentation.vue.LoginView;
import ma.dentopro.presentation.vue.SplashScreen;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;

public class DentoproApplication {

    public static void main(String[] args) {
        try {
            // Configuration de l'apparence de l'interface utilisateur
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            // Initialisation de AuthService
            UtilisateurDao utilisateurDao = new UtilisateurDao();
            AuthService authService = new AuthService(utilisateurDao);

            // Afficher le splash screen, puis lancer la vue de connexion après 3 secondes
            SwingUtilities.invokeLater(() -> {
                SplashScreen splash = new SplashScreen();

                // Timer pour fermer le splash screen et ouvrir la vue de connexion après 3 secondes
                Timer timer = new Timer(3000, e -> {
                    splash.dispose(); // Fermer le splash screen
                    new LoginView(authService); // Ouvrir la vue de connexion avec AuthService
                });

                timer.setRepeats(false); // Empêcher le Timer de se répéter
                timer.start();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}