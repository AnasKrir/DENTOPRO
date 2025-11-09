package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.*;
import ma.dentopro.model.Dentiste;
import ma.dentopro.model.DossierMedicale;
import ma.dentopro.model.Patient;
import ma.dentopro.model.enums.CategorieAntecedentsMedicaux;
import ma.dentopro.model.enums.GroupeSanguin;
import ma.dentopro.model.enums.Mutuelle;
import ma.dentopro.model.enums.StatutPaiement;
import ma.dentopro.exceptions.FormulaireException;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;
import ma.dentopro.service.api.specificAPI.service_Impl.SecretaireService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class MesPatientsView {
    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JPanel topBarPanel;
    AuthService serviceauthentif;
    private JTable patientTable;
    private JPanel containerPanel = new JPanel();

    SecretaireDao s = new SecretaireDao(new UtilisateurDao());
    PatientDao p = new PatientDao();
    DentisteDao d = new DentisteDao();
    FactureDao f = new FactureDao();
    DossierMedicaleDao dm = new DossierMedicaleDao();
    ConsultationDao c = new ConsultationDao();
    SituationFinanciereDao SF = new SituationFinanciereDao(dm, f);
    CaisseDao caisse = new CaisseDao(SF);
    private JRadioButton antecedentsMedicauxRadioButton;
    private JPanel antecedentsMedicauxPanel;
    private JComboBox<CategorieAntecedentsMedicaux> antecedentsMedicauxField;

    public MesPatientsView() {
        mainFrame = new JFrame("DENTOPRO");
        mainFrame.setSize(1225, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(236, 246, 248)); // Couleur de fond du panneau principal

        createTopBar();
        mainFrame.add(topBarPanel, BorderLayout.NORTH);

        createSideNavBar();
        mainFrame.add(sideNavBarPanel, BorderLayout.WEST);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        createPatientTable();
    }

    private void createPatientTable() {
        ArrayList<Patient> patients = new ArrayList<>(p.findAll());
        CustomTableModel tableModel = new CustomTableModel(patients);
        JComboBox<CategorieAntecedentsMedicaux> antecedentsMedicauxField = new JComboBox<>(CategorieAntecedentsMedicaux.values());
        patientTable = new JTable(tableModel);
        JLabel tableLabel = new JLabel("Liste des Patients");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 22));
        tableLabel.setForeground(new Color(34, 187, 209)); // Couleur du texte du titre
        patientTable.setRowHeight(30);
        JTableHeader header = patientTable.getTableHeader();
        header.setPreferredSize(new Dimension(100, 40));
        header.setBackground(new Color(34, 187, 209)); // Couleur de l'en-tête du tableau
        header.setForeground(Color.WHITE);
        patientTable.setGridColor(Color.GRAY);
        patientTable.setSelectionBackground(new Color(0, 128, 255));
        patientTable.setSelectionForeground(Color.WHITE);
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        header.setFont(headerFont);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        patientTable.setFont(tableFont);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tableLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(patientTable), BorderLayout.CENTER);

        // Ajouter un MouseListener pour détecter les clics sur la colonne "Situation Financière"
        patientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = patientTable.columnAtPoint(e.getPoint());
                int row = patientTable.rowAtPoint(e.getPoint());

                // Vérifier si l'utilisateur a cliqué sur la colonne "Situation Financière"
                if (column == 7) { // La colonne "Situation Financière" est à l'index 7
                    // Récupérer le patient correspondant à la ligne cliquée
                    Patient patient = ((CustomTableModel) patientTable.getModel()).getPatientAt(row);

                    // Ouvrir la vue SituationFinanciereView pour ce patient
                    new SituationFinanciereView(patient);
                }
            }
        });

        // Formulaire réduit
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // Réduire le nombre de lignes et de colonnes
        formPanel.setBorder(BorderFactory.createTitledBorder("Ajouter un nouveau patient"));

        JTextField nomField = new JTextField(15);
        JTextField prenomField = new JTextField(15);
        JTextField dateNaissanceField = new JTextField(10);
        JComboBox<Mutuelle> mutuelleComboBox = new JComboBox<>(Mutuelle.values());
        JComboBox<GroupeSanguin> groupeSanguinComboBox = new JComboBox<>(GroupeSanguin.values());
        JTextField professionField = new JTextField(15);

        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(Color.WHITE); // Couleur du bouton en blanc
        addButton.setForeground(Color.BLACK); // Texte en noir pour contraster
        addButton.setPreferredSize(new Dimension(90, 30)); // Réduire la taille du bouton
        addButton.addActionListener(e -> {
            // Générer un nouvel ID automatiquement
            long newPatientId = patients.isEmpty() ? 1 : patients.get(patients.size() - 1).getId() + 1;

            Patient newPatient = new Patient(
                    nomField.getText(),
                    prenomField.getText(),
                    "",
                    newPatientId,
                    "",
                    "",
                    "",
                    LocalDate.parse(dateNaissanceField.getText()),
                    (Mutuelle) mutuelleComboBox.getSelectedItem(),
                    (GroupeSanguin) groupeSanguinComboBox.getSelectedItem(),
                    null,
                    professionField.getText()
            );
            patients.add(newPatient);
            p.save(newPatient);

            // Récupérer le dentiste
            Dentiste dentiste = d.findById(1L);
            if (dentiste == null) {
                // Si le dentiste n'existe pas, afficher un message d'erreur
                JOptionPane.showMessageDialog(mainFrame, "Aucun dentiste trouvé avec l'ID 1.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return; // Arrêter l'exécution de la méthode
            }

            // Créer le dossier médical
            DossierMedicale dossierMedicale = new DossierMedicale(
                    p.findIdByPatient(newPatient),
                    newPatient,
                    dentiste,
                    null,
                    LocalDate.now(),
                    StatutPaiement.IMPAYE
            );
            dm.writeDossierMedicaleToFile(dossierMedicale);

            // Rafraîchir la table
            refreshTable(patients);

            // Réinitialiser les champs du formulaire
            nomField.setText("");
            prenomField.setText("");
            dateNaissanceField.setText("");
            mutuelleComboBox.setSelectedIndex(0);
            groupeSanguinComboBox.setSelectedIndex(0);
            professionField.setText("");
        });

        formPanel.add(new JLabel("Nom: "));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Prénom: "));
        formPanel.add(prenomField);
        formPanel.add(new JLabel("Date de naissance: "));
        formPanel.add(dateNaissanceField);
        formPanel.add(new JLabel("Mutuelle: "));
        formPanel.add(mutuelleComboBox);
        formPanel.add(new JLabel("Groupe Sanguin: "));
        formPanel.add(groupeSanguinComboBox);
        formPanel.add(new JLabel("Profession: "));
        formPanel.add(professionField);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.WHITE); // Couleur du bouton en blanc
        deleteButton.setForeground(Color.BLACK); // Texte en noir pour contraster
        deleteButton.setPreferredSize(new Dimension(90, 30)); // Réduire la taille du bouton
        deleteButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow != -1) {
                int option = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Voulez-vous vraiment supprimer ce patient ?",
                        "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    long patientId = (long) patientTable.getValueAt(selectedRow, 0);

                    try {
                        SecretaireService service = new SecretaireService(s, p, d, SF, f, caisse, dm, c);
                        service.deletePatient(patientId);
                        p.deleteById(patientId);

                        DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();
                        DossierMedicale dossierMedicale = dossierMedicaleDao.findByPatientId(patientId);

                        if (dossierMedicale != null) {
                            dossierMedicaleDao.delete(dossierMedicale);
                        }

                        // Rafraîchir la table
                        refreshTable(patients);
                    } catch (FormulaireException ex) {
                        JOptionPane.showMessageDialog(mainFrame,
                                "Erreur lors de la suppression du patient : " + ex.getMessage(),
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Veuillez sélectionner un patient à supprimer.",
                        "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.add(panel);
        containerPanel.add(Box.createVerticalStrut(10));
        containerPanel.add(formPanel);
        containerPanel.add(Box.createVerticalStrut(10));
        containerPanel.add(buttonPanel);

        mainFrame.add(containerPanel, BorderLayout.CENTER);
    }

    private void refreshTable(ArrayList<Patient> patients) {
        CustomTableModel model = new CustomTableModel(patients);
        patientTable.setModel(model);
    }

    private void toggleAntecedentsMedicauxFields() {
        boolean showFields = antecedentsMedicauxRadioButton.isSelected();
        antecedentsMedicauxPanel.setVisible(showFields);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return (Component) value;
        }
    }

    private void createTopBar() {
        topBarPanel = new JPanel();
        topBarPanel.setBackground(new Color(236, 246, 248)); // Couleur de fond de la barre supérieure
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
        sideNavBarPanel.setBackground(new Color(34, 187, 209)); // Couleur de fond de la barre latérale
        sideNavBarPanel.setLayout(new BoxLayout(sideNavBarPanel, BoxLayout.Y_AXIS));

        String[] navItems = {"Accueil", "Mes Patients", "Caisse", "Dossier Médical"};
        String[] iconPaths = {"src/main/resources/lightTheme/icons/buttons/Mon_Profile.png", "src/main/resources/lightTheme/icons/buttons/Patient.png", "src/main/resources/lightTheme/icons/buttons/caisse.png", "src/main/resources/lightTheme/icons/buttons/dossier_medical.png"};

        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            String iconPath = iconPaths[i];

            JPanel navPanel = new JPanel();
            navPanel.setLayout(new BorderLayout());
            navPanel.setBackground(new Color(34, 187, 209)); // Couleur des boutons
            navPanel.setPreferredSize(new Dimension(40, 60));

            JButton navButton = new JButton(item);
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            navButton.setIcon(new ImageIcon(scaledIcon));
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(34, 187, 209)); // Couleur des boutons
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
                        new DossierMedicalView(dm.findAll());
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

    public class CustomTableModel extends DefaultTableModel {
        private final String[] columnsWithSituationFinanciere = {"ID", "Nom", "Prénom", "Date de Naissance", "Mutuelle", "Groupe Sanguin", "Profession", "Situation Financière"};
        private final ArrayList<Patient> patients;

        public CustomTableModel(ArrayList<Patient> patients) {
            this.patients = patients;
            setDataVector(createData(), columnsWithSituationFinanciere);
        }

        private Object[][] createData() {
            Object[][] data = new Object[patients.size()][8];
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                data[i] = new Object[]{
                        patient.getId(),
                        patient.getNom(),
                        patient.getPrenom(),
                        patient.getDateNaissance(),
                        patient.getMutuelle(),
                        patient.getGroupeSanguin(),
                        patient.getProfession(),
                        "Détails"
                };
            }
            return data;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Patient getPatientAt(int row) {
            return patients.get(row);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MesPatientsView());
//    }
}