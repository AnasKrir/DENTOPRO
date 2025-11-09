package ma.dentopro.presentation.vue;



import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {

    JPanel pnl_splash;
    JLabel lbl_splash;

    private void initPannels() {
        initLabels();

        pnl_splash = new JPanel();
        pnl_splash.setPreferredSize(new Dimension(712, 506));
        pnl_splash.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
        pnl_splash.setLayout(new GridLayout(1, 1));
        pnl_splash.add(lbl_splash);

        setContentPane(pnl_splash);
    }

    private void initLabels() {
        // Charger l'image
        ImageIcon splashIcon = new ImageIcon("src/main/resources/darkTheme/images/Splash_Screen.png");

        // Recuperer l'image et ajuster à la taille de l'ecran
        Image image = splashIcon.getImage().getScaledInstance(712, 506, Image.SCALE_SMOOTH);
        lbl_splash = new JLabel(new ImageIcon(image));
    }

    public SplashScreen() {
        initPannels();

        setSize(new Dimension(712, 506));
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        setVisible(true);
    }
}
