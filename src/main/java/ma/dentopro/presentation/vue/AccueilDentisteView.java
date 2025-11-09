package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.*;
import ma.dentopro.model.Caisse;
import ma.dentopro.presentation.vue.palette.panels.CirclePanel;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AccueilDentisteView extends JFrame {
    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JPanel topBarPanel;
    private JPanel mainContentPanel;
    ConsultationDao consultationDao = new ConsultationDao();
    DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();
    AuthService serviceauthentif;
    private Caisse caisse;
    SituationFinanciereDao situationFinanciereDao = new SituationFinanciereDao(new DossierMedicaleDao(), new FactureDao());
    CaisseDao caisseDao = new CaisseDao(situationFinanciereDao);

    public AccueilDentisteView() {
        mainFrame = new JFrame("DENTOPRO");
        mainFrame.setSize(1225, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        createTopBar();
        mainFrame.add(topBarPanel, BorderLayout.NORTH);

        createSideNavBar();
        mainFrame.add(sideNavBarPanel, BorderLayout.WEST);

        mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        addCircleToGrid(mainContentPanel, new Color(227, 227, 237), gbc, 0, 0, "Recette du jour : \n" + (caisseDao.SommerecetteDuJour(LocalDate.now())));
        addCircleToGrid(mainContentPanel, new Color(227, 227, 237), gbc, 2, 0, "Recette du mois : \n" + (caisseDao.SommerecetteDuMois(LocalDate.now())));
        addCircleToGrid(mainContentPanel, new Color(227, 227, 237), gbc, 1, 1, "Nombre des patients:\n" + (consultationDao.getNombreConsultationsPourMois(LocalDate.now().getMonth())));
        addCircleToGrid(mainContentPanel, new Color(227, 227, 237), gbc, 0, 2, "Recette de l'année : \n" + (caisseDao.SommerecetteDeLAnnee(LocalDate.now())));
        addCircleToGrid(mainContentPanel, new Color(227, 227, 237), gbc, 2, 2, "Nombre de consultations du mois : \n" + (consultationDao.getNombreConsultationsPourMois(LocalDate.now().getMonth())));
        mainFrame.add(mainContentPanel, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void addCircleToGrid(JPanel panel, Color color, GridBagConstraints gbc, int row, int col, String text) {
        CirclePanel circlePanel = new CirclePanel(color, text);
        gbc.gridx = col;
        gbc.gridy = row;
        mainContentPanel.add(circlePanel, gbc);
    }

    private void createTopBar() {
        topBarPanel = new JPanel();
        topBarPanel.setBackground(new Color(236, 246, 248));
        topBarPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 70));
        topBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Charger et redimensionner l'icône du logo
        ImageIcon originalLogoIcon = new ImageIcon("src/main/resources/lightTheme/icons/buttons/logo_Dentopro.png");
        Image originalImage = originalLogoIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(70, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);

        // Créer un JLabel pour l'icône du logo
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0)); // Ajouter une marge à gauche

        // Créer un JLabel pour le texte "DENTOPRO"
        JLabel textLabel = new JLabel("DENTOPRO");
        textLabel.setFont(new Font("Georgia", Font.BOLD, 24)); // Définir la police et la taille
        textLabel.setForeground(new Color(34, 187, 209)); // Définir la couleur du texte

        // Ajouter l'icône et le texte au topBarPanel
        topBarPanel.add(logoLabel);
        topBarPanel.add(textLabel);

        // Ajouter un espace flexible pour pousser les autres éléments vers la droite
        topBarPanel.add(Box.createHorizontalStrut(800));

        // Ajouter l'icône et le texte du dentiste
        ImageIcon iconWithText = new ImageIcon("src/main/resources/lightTheme/icons/buttons/Dentiste.png");
        Image iconedntst = iconWithText.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon finaliconedntst = new ImageIcon(iconedntst);
        JLabel iconLabel = new JLabel(finaliconedntst);
        JLabel dentisteTextLabel = new JLabel("Dentiste");
        dentisteTextLabel.setFont(new Font("Georgia", Font.PLAIN, 16)); // Définir la police et la taille

        topBarPanel.add(iconLabel);
        topBarPanel.add(dentisteTextLabel);
    }

    private void createSideNavBar() {
        sideNavBarPanel = new JPanel();
        sideNavBarPanel.setBackground(new Color(34, 187, 209));
        sideNavBarPanel.setLayout(new BoxLayout(sideNavBarPanel, BoxLayout.Y_AXIS));

        String[] navItems = {"Accueil", "Mes Patients", "Caisse", "Dossier Médical"};
        String[] iconPaths = {"src/main/resources/lightTheme/icons/buttons/Mon_Profile.png", "src/main/resources/lightTheme/icons/buttons/Patient.png", "src/main/resources/lightTheme/icons/buttons/caisse.png", "src/main/resources/lightTheme/icons/buttons/dossier_medical.png"};

        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            String iconPath = iconPaths[i];

            JPanel navPanel = new JPanel();
            navPanel.setLayout(new BorderLayout());
            navPanel.setBackground(new Color(34, 187, 209));
            navPanel.setPreferredSize(new Dimension(40, 60));

            JButton navButton = new JButton(item);
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            navButton.setIcon(new ImageIcon(scaledIcon));
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(34, 187, 209));
            navButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            navButton.setHorizontalAlignment(SwingConstants.LEFT);

            navButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (item.equals("Accueil")) {
                        mainFrame.dispose();
                        new AccueilDentisteView();
                    } else if (item.equals("Mes Patients")) {
                        mainFrame.dispose();
                        new MesPatientsView();
                    } else if (item.equals("Caisse")) {
                        mainFrame.dispose();
                        new CaisseView();
                    } else if (item.equals("Dossier Médical")) {
                        mainFrame.dispose();
                        new DossierMedicalView(dossierMedicaleDao.findAll());
                    }
                }
            });

            navPanel.add(navButton, BorderLayout.WEST);
            sideNavBarPanel.add(navPanel);
        }

        // Bouton Logout
        JButton logoutButton = new JButton();
        ImageIcon logoutIcon = new ImageIcon("src/main/resources/lightTheme/icons/buttons/log_out.png");
        Image scaledIcon = logoutIcon.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH);
        ImageIcon finalLogoutIcon = new ImageIcon(scaledIcon);
        logoutButton.setIcon(finalLogoutIcon);
        logoutButton.setText("Logout");
        logoutButton.setForeground(Color.GRAY);
        logoutButton.setBackground(Color.DARK_GRAY);
        logoutButton.setFocusPainted(false);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        logoutButton.setPreferredSize(new Dimension(150, 60));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new LoginView(serviceauthentif);
            }
        });
        sideNavBarPanel.add(Box.createVerticalStrut(20));
        sideNavBarPanel.add(logoutButton);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AccueilDentisteView());
//    }
}