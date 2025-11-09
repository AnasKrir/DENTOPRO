package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.*;
import ma.dentopro.model.DossierMedicale;
import ma.dentopro.model.Patient;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DossierMedicalView extends JFrame {
    private JTable dossierTable;
    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JPanel topBarPanel;
    AuthService serviceauthentif;
    DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();

    public DossierMedicalView(List<DossierMedicale> dossiers) {
        mainFrame = new JFrame("DENTOPRO");
        mainFrame.setSize(1225, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(236, 246, 248)); // Couleur de fond du panneau principal

        createTopBar();
        mainFrame.add(topBarPanel, BorderLayout.NORTH);

        createSideNavBar();
        mainFrame.add(sideNavBarPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(236, 246, 248)); // Couleur de fond du panneau de contenu

        JLabel titleLabel = new JLabel("Liste des Dossiers Médicaux", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 187, 209)); // Couleur du texte du titre
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Numéro de Dossier", "Nom du Patient", "Prénom du Patient", "Date de Création", "Statut de Paiement", "Consultations"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == columns.length - 1 ? JButton.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (DossierMedicale dossier : dossiers) {
            String nomPatient = "N/A";
            String prenomPatient = "N/A";

            Patient patient = dossier.getPatient();
            if (patient != null) {
                nomPatient = patient.getNom();
                prenomPatient = patient.getPrenom();
            }

            Object[] rowData = {dossier.getNumeroDossier(), nomPatient, prenomPatient, dossier.getDateCreation(), dossier.getStatutPaiement(), "Details"};
            model.addRow(rowData);
        }

        JTable medicalRecordsTable = new JTable(model);
        customizeTableHeader(medicalRecordsTable);
        medicalRecordsTable.setRowHeight(30);
        TableCellRenderer customRenderer = new CustomRenderer();
        for (int i = 0; i < medicalRecordsTable.getColumnCount(); i++) {
            medicalRecordsTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        medicalRecordsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = medicalRecordsTable.getColumnModel().getColumnIndex("Consultations");
                int row = medicalRecordsTable.rowAtPoint(e.getPoint());

                if (column == -1 || row == -1 || column != medicalRecordsTable.getColumnCount() - 1) {
                    return;
                }

                Long idDossier = (Long) medicalRecordsTable.getValueAt(row, 0);
                mainFrame.dispose();
                new ConsultationsView(idDossier);
            }
        });

        JScrollPane scrollPane = new JScrollPane(medicalRecordsTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(contentPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private void customizeTableHeader(JTable table) {
        JTableHeader header = table.getTableHeader();

        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(34, 187, 209)); // Couleur de l'en-tête du tableau

        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new Color(34, 187, 209)); // Couleur de l'en-tête du tableau
                c.setForeground(Color.WHITE);
                return c;
            }
        });
    }

    public class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row % 2 == 0) {
                c.setBackground(new Color(66, 107, 120)); // Couleur des lignes paires
            } else {
                c.setBackground(new Color(66, 107, 120)); // Couleur des lignes impaires
            }

            c.setForeground(Color.WHITE); // Couleur du texte
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.PLAIN, 18));

            if (column == table.getColumnCount() - 1) {
                c.setForeground(Color.BLUE);
            } else {
                c.setForeground(Color.WHITE);
            }

            return c;
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
//        DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();
//        List<DossierMedicale> dossiers = dossierMedicaleDao.findAll();
//        SwingUtilities.invokeLater(() -> new DossierMedicalView(dossiers));
//    }
}