package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.*;
import ma.dentopro.model.DossierMedicale;
import ma.dentopro.model.Patient;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SituationFinanciereView {
    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JPanel topBarPanel;
    DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();

    AuthService serviceauthentif;

    public SituationFinanciereView(Patient patient) {
        mainFrame = new JFrame("Situation Financière de " + patient.getNom() + " " + patient.getPrenom());
        mainFrame.setSize(1200, 800);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(236, 246, 248));

        createTopBar();
        mainFrame.add(topBarPanel, BorderLayout.NORTH);

        createSideNavBar();
        mainFrame.add(sideNavBarPanel, BorderLayout.WEST);

        mainFrame.add(createDetailsPanel(patient), BorderLayout.CENTER);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private JPanel createDetailsPanel(Patient patient) {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(Color.WHITE);

        List<DossierMedicale> liste = dossierMedicaleDao.findAll();
        DossierMedicale dossier = null;
        for (DossierMedicale dossierMedicale : liste) {
            if (dossierMedicale.getPatient().equals(patient)) {
                dossier = dossierMedicale;
                break;
            }
        }

        String[] columnNames = {"Détail", "Valeur"};
        Object[][] data = {
                {"ID", patient.getId()},
                {"Nom", patient.getNom()},
                {"Prénom", patient.getPrenom()}
        };

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non éditables
            }
        };
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(34, 187, 209));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Ajouter la table au panneau principal
        detailsPanel.add(tableScrollPane, BorderLayout.NORTH);

        // Ajouter une zone de texte pour afficher le dossier médical
        JTextArea dossierTextArea = new JTextArea((dossier != null) ? dossier.toString() : "Aucun dossier médical trouvé");
        dossierTextArea.setLineWrap(true);
        dossierTextArea.setWrapStyleWord(true);
        dossierTextArea.setEditable(false);

        JScrollPane dossierScrollPane = new JScrollPane(dossierTextArea);
        dossierScrollPane.setBorder(BorderFactory.createTitledBorder("Dossier Médical"));

        // Ajouter la zone de texte au panneau principal
        detailsPanel.add(dossierScrollPane, BorderLayout.CENTER);

        return detailsPanel;
    }

    private void createTopBar() {
        topBarPanel = new JPanel();
        topBarPanel.setBackground(new Color(236, 246, 248));
        topBarPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 70));
        topBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon originalLogoIcon = new ImageIcon("src/main/resources/lightTheme/icons/buttons/logo_Dentopro.png");
        Image originalImage = originalLogoIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(70, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JLabel textLabel = new JLabel("DENTOPRO");
        textLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        textLabel.setForeground(new Color(34, 187, 209));

        topBarPanel.add(logoLabel);
        topBarPanel.add(textLabel);
        topBarPanel.add(Box.createHorizontalStrut(800));

        ImageIcon iconWithText = new ImageIcon("src/main/resources/lightTheme/icons/buttons/Dentiste.png");
        Image iconedntst = iconWithText.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(iconedntst));
        JLabel textLabel2 = new JLabel("Dentiste");
        textLabel2.setFont(new Font("Georgia", Font.PLAIN, 16));

        topBarPanel.add(iconLabel);
        topBarPanel.add(textLabel2);
    }

    private void createSideNavBar() {
        sideNavBarPanel = new JPanel();
        sideNavBarPanel.setBackground(new Color(34, 187, 209));
        sideNavBarPanel.setLayout(new BoxLayout(sideNavBarPanel, BoxLayout.Y_AXIS));
        sideNavBarPanel.setPreferredSize(new Dimension(130, 80));
        sideNavBarPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        String[] navItems = {"Accueil", "Mes Patients", "Caisse", "Dossier Médical"};
        String[] iconPaths = {"src/main/resources/lightTheme/icons/buttons/Mon_Profile.png", "src/main/resources/lightTheme/icons/buttons/Patient.png", "src/main/resources/lightTheme/icons/buttons/caisse.png", "src/main/resources/lightTheme/icons/buttons/dossier_medical.png"};

        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            String iconPath = iconPaths[i];

            JButton navButton = new JButton(item);
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            navButton.setIcon(new ImageIcon(scaledIcon));
            navButton.setPreferredSize(new Dimension(100, 60));
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(34, 187, 209));
            navButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            navButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

            sideNavBarPanel.add(navButton);
            sideNavBarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        sideNavBarPanel.add(Box.createVerticalGlue());

        JButton logoutButton = new JButton();
        ImageIcon logoutIcon = new ImageIcon("src/main/resources/lightTheme/icons/buttons/log_out.png");
        Image scaledIcon = logoutIcon.getImage().getScaledInstance(28, 25, Image.SCALE_SMOOTH);
        logoutButton.setIcon(new ImageIcon(scaledIcon));
        logoutButton.setText("Logout");
        logoutButton.setForeground(Color.GRAY);
        logoutButton.setBackground(Color.DARK_GRAY);
        logoutButton.setFocusPainted(false);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        logoutButton.setPreferredSize(new Dimension(100, 60));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new LoginView(serviceauthentif);
            }
        });
        sideNavBarPanel.add(logoutButton);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            PatientDao patientDao = new PatientDao();
//
//            List<Patient> patients = patientDao.findAll();
//            if (!patients.isEmpty()) {
//                Patient patient = patients.get(0);
//                new SituationFinanciereView(patient);
//            } else {
//                JOptionPane.showMessageDialog(null, "Aucun patient trouvé dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//    }
}
