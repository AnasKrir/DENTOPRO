package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.PatientDao;
import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.RendezVousDao;
import ma.dentopro.model.Patient;
import ma.dentopro.model.RendezVous;
import ma.dentopro.model.enums.TypeRDV;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AccueilSecretaireView extends JFrame {
    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JPanel topBarPanel;
    private JPanel containerPanel = new JPanel();
    private JTable rendezVousTable;
    private RendezVousDao rendezVousDao = new RendezVousDao();
    private PatientDao patientDao = new PatientDao();
    AuthService serviceauthentif;

    public AccueilSecretaireView() {
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
        createRendezVousTable();
    }

    private void createRendezVousTable() {
        ArrayList<RendezVous> rendezVousList = new ArrayList<>(rendezVousDao.findAll());
        CustomTableModel tableModel = new CustomTableModel(rendezVousList);
        rendezVousTable = new JTable(tableModel);
        JLabel tableLabel = new JLabel("Liste des Rendez-vous");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 22));
        tableLabel.setForeground(new Color(34, 187, 209)); // Couleur du texte du titre
        rendezVousTable.setRowHeight(30);
        JTableHeader header = rendezVousTable.getTableHeader();
        header.setPreferredSize(new Dimension(100, 40));
        header.setBackground(new Color(34, 187, 209)); // Couleur de l'en-tête du tableau
        header.setForeground(Color.WHITE);
        rendezVousTable.setGridColor(Color.GRAY);
        rendezVousTable.setSelectionBackground(new Color(0, 128, 255));
        rendezVousTable.setSelectionForeground(Color.WHITE);
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        header.setFont(headerFont);
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        rendezVousTable.setFont(tableFont);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tableLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(rendezVousTable), BorderLayout.CENTER);

        // Formulaire réduit
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10)); // Réduire le nombre de lignes et de colonnes
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer un Rendez-vous"));

        JTextField patientIdField = new JTextField(15);
        JComboBox<TypeRDV> typeRDVComboBox = new JComboBox<>(TypeRDV.values());
        JTextField dateField = new JTextField(10);
        JTextField timeField = new JTextField(10);
        JTextArea notesArea = new JTextArea(5, 20);

        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(Color.WHITE); // Couleur du bouton en blanc
        addButton.setForeground(Color.BLACK); // Texte en noir pour contraster
        addButton.setPreferredSize(new Dimension(90, 30)); // Réduire la taille du bouton
        addButton.addActionListener(e -> {
            try {
                long patientId = Long.parseLong(patientIdField.getText());
                TypeRDV typeRDV = (TypeRDV) typeRDVComboBox.getSelectedItem();
                String date = dateField.getText();
                String time = timeField.getText();
                String notes = notesArea.getText();

                if (date.isEmpty() || time.isEmpty() || notes.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Veuillez remplir tous les champs obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Patient patient = patientDao.findById(patientId);
                if (patient == null) {
                    JOptionPane.showMessageDialog(mainFrame, "Patient non trouvé avec l'ID spécifié.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate dateRDV = LocalDate.parse(date);
                LocalTime temps = LocalTime.parse(time);
                RendezVous rendezVous = new RendezVous(patient, dateRDV, temps, notes);
                rendezVous.setTypeRDV(typeRDV);
                rendezVousDao.save(rendezVous);

                JOptionPane.showMessageDialog(mainFrame, "Rendez-vous créé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);

                // Réinitialiser les champs
                patientIdField.setText("");
                typeRDVComboBox.setSelectedIndex(0);
                dateField.setText("");
                timeField.setText("");
                notesArea.setText("");

                // Rafraîchir la table
                refreshTable(new ArrayList<>(rendezVousDao.findAll())); // Mettre à jour la liste des rendez-vous
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "ID patient invalide. Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Format de date ou d'heure invalide. Utilisez YYYY-MM-DD pour la date et HH:MM pour l'heure.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        formPanel.add(new JLabel("ID Patient: "));
        formPanel.add(patientIdField);
        formPanel.add(new JLabel("Type de RDV: "));
        formPanel.add(typeRDVComboBox);
        formPanel.add(new JLabel("Date (YYYY-MM-DD): "));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Motif: "));
        formPanel.add(new JScrollPane(notesArea));
        formPanel.add(new JLabel("Heure (HH:MM): "));
        formPanel.add(timeField);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(Color.WHITE); // Couleur du bouton en blanc
        deleteButton.setForeground(Color.BLACK); // Texte en noir pour contraster
        deleteButton.setPreferredSize(new Dimension(90, 30)); // Réduire la taille du bouton
        deleteButton.addActionListener(e -> {
            int selectedRow = rendezVousTable.getSelectedRow();
            if (selectedRow != -1) {
                // Récupérer l'IDRDV de la ligne sélectionnée
                Long rendezVousId = (Long) rendezVousTable.getValueAt(selectedRow, 0); // La colonne 0 contient l'IDRDV

                int option = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Voulez-vous vraiment supprimer ce rendez-vous ?",
                        "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Supprimer le rendez-vous par son ID
                    boolean isRemoved = rendezVousDao.deleteById(rendezVousId);

                    if (isRemoved) {
                        JOptionPane.showMessageDialog(mainFrame, "Rendez-vous supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        // Rafraîchir la table
                        refreshTable(new ArrayList<>(rendezVousDao.findAll()));
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Erreur lors de la suppression du rendez-vous.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        "Veuillez sélectionner un rendez-vous à supprimer.",
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

    private void refreshTable(ArrayList<RendezVous> rendezVousList) {
        CustomTableModel model = new CustomTableModel(rendezVousList);
        rendezVousTable.setModel(model);
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

        // Ajouter l'icône et le texte de la secrétaire
        ImageIcon iconWithText = new ImageIcon("src/main/resources/lightTheme/icons/buttons/Secretary.png");
        Image iconedntst = iconWithText.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon finaliconedntst = new ImageIcon(iconedntst);
        JLabel iconLabel = new JLabel(finaliconedntst);
        JLabel secretaryTextLabel = new JLabel("Secrétaire");
        secretaryTextLabel.setFont(new Font("Georgia", Font.PLAIN, 16)); // Définir la police et la taille

        topBarPanel.add(iconLabel);
        topBarPanel.add(secretaryTextLabel);
    }

    private void createSideNavBar() {
        sideNavBarPanel = new JPanel();
        sideNavBarPanel.setBackground(new Color(34, 187, 209)); // Couleur de fond de la barre latérale
        sideNavBarPanel.setLayout(new BoxLayout(sideNavBarPanel, BoxLayout.Y_AXIS));

        String[] navItems = {"Accueil", "Patients", "Factures"};
        String[] iconPaths = {"src/main/resources/lightTheme/icons/buttons/Mon_Profile.png", "src/main/resources/lightTheme/icons/buttons/Patient.png", "src/main/resources/lightTheme/icons/buttons/facture.png"};

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
                        new AccueilSecretaireView();
                    } else if (item.equals("Patients")) {
                        mainFrame.dispose();
                        new PatientsView();
                    } else if (item.equals("Factures")) {
                        mainFrame.dispose();
                        new FacturesView();
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
        private final String[] columns = {"ID", "Patient ID", "Type RDV", "Date", "Motifs", "Heure"};
        private final ArrayList<RendezVous> rendezVousList;

        public CustomTableModel(ArrayList<RendezVous> rendezVousList) {
            this.rendezVousList = rendezVousList;
            setDataVector(createData(), columns);
        }

        private Object[][] createData() {
            Object[][] data = new Object[rendezVousList.size()][6];
            for (int i = 0; i < rendezVousList.size(); i++) {
                RendezVous rdv = rendezVousList.get(i);
                data[i] = new Object[]{
                        rdv.getIdRDV(),
                        rdv.getPatient().getId(),
                        rdv.getTypeRDV(),
                        rdv.getDateRDV(),
                        rdv.getMotif(),
                        rdv.getTemps()
                };
            }
            return data;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public RendezVous getRendezVousAt(int row) {
            return rendezVousList.get(row);
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AccueilSecretaireView());
//    }
}