package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.*;
import ma.dentopro.model.Facture;
import ma.dentopro.model.enums.TypePaiement;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturesView extends JFrame {
    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JPanel topBarPanel;
    AuthService serviceauthentif;
    private JTable patientTable;
    JButton dossierButton;
    SecretaireDao s = new SecretaireDao(new UtilisateurDao());
    PatientDao p = new PatientDao();
    DentisteDao d = new DentisteDao();
    FactureDao f = new FactureDao();
    DossierMedicaleDao dm = new DossierMedicaleDao();
    ConsultationDao c = new ConsultationDao();
    SituationFinanciereDao SF = new SituationFinanciereDao(dm, f);
    CaisseDao caisse = new CaisseDao(SF);
    FactureDao factureDao = new FactureDao();

    FacturesView() {
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
        addInvoiceListToCenter();
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

        // Ajouter l'icône et le texte du secrétaire
        ImageIcon iconWithText = new ImageIcon("src/main/resources/lightTheme/icons/buttons/Secretary.png");
        Image iconedntst = iconWithText.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon finaliconedntst = new ImageIcon(iconedntst);
        JLabel iconLabel = new JLabel(finaliconedntst);
        JLabel textLabel2 = new JLabel("Secrétaire");
        textLabel2.setFont(new Font("Georgia", Font.PLAIN, 16)); // Définir la police et la taille

        topBarPanel.add(iconLabel);
        topBarPanel.add(textLabel2);
    }

    private void createSideNavBar() {
        sideNavBarPanel = new JPanel();
        sideNavBarPanel.setBackground(new Color(34, 187, 209)); // Couleur de fond de la barre latérale
        sideNavBarPanel.setLayout(new BoxLayout(sideNavBarPanel, BoxLayout.Y_AXIS));
        sideNavBarPanel.setPreferredSize(new Dimension(130, 80));
        sideNavBarPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        String[] navItems = {"Accueil", "Patients", "Factures"};
        String[] iconPaths = {"src/main/resources/lightTheme/icons/buttons/Mon_Profile.png", "src/main/resources/lightTheme/icons/buttons/Patient.png", "src/main/resources/lightTheme/icons/buttons/facture.png"};
        for (int i = 0; i < navItems.length; i++) {
            String item = navItems[i];
            String iconPath = iconPaths[i];

            JButton navButton = new JButton(item);
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            navButton.setIcon(new ImageIcon(scaledIcon));
            navButton.setPreferredSize(new Dimension(100, 60));
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(34, 187, 209)); // Couleur des boutons
            navButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            navButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            navButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ("Accueil".equals(item)) {
                        mainFrame.dispose();
                        new AccueilSecretaireView();
                    }
                    if ("Patients".equals(item)) {
                        mainFrame.dispose();
                         new PatientsView();
                    }
                    if ("Factures".equals(item)) {
                        mainFrame.dispose();
                        new FacturesView();
                    }
                }
            });

            sideNavBarPanel.add(navButton);
            sideNavBarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        sideNavBarPanel.add(Box.createVerticalGlue());

        // Bouton Logout
        JButton logoutButton = new JButton();
        ImageIcon logoutIcon = new ImageIcon("src/main/resources/lightTheme/icons/buttons/log_out.png");
        Image scaledIcon = logoutIcon.getImage().getScaledInstance(28, 25, Image.SCALE_SMOOTH);
        ImageIcon finalLogoutIcon = new ImageIcon(scaledIcon);
        logoutButton.setIcon(finalLogoutIcon);
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

    private Object[][] getFactureData() {
        List<Facture> factures = new ArrayList<>();
        factures = factureDao.findAll();
        Object[][] data = new Object[factures.size()][5];
        for (int i = 0; i < factures.size(); i++) {
            Facture facture = factures.get(i);
            data[i] = new Object[]{
                    facture.getIdFacture(),
                    facture.getDateFacturation(),
                    facture.getMontantTotal(),
                    facture.getMontantPaye(),
                    facture.getMontantRestant()
            };
        }
        return data;
    }

    private void addInvoiceListToCenter() {
        String[] columns = {"ID Facture", "Date Facturation", "Montant Total", "Montant Payé", "Montant Restant"};
        Object[][] data = getFactureData();
        JTable factureTable = new JTable(data, columns);
        factureTable.getTableHeader().setBackground(new Color(34, 187, 209)); // Couleur de l'en-tête du tableau
        factureTable.getTableHeader().setForeground(Color.WHITE);
        factureTable.setRowHeight(30);
        factureTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        factureTable.getTableHeader().setPreferredSize(new Dimension(100, 40));
        factureTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new Font("Arial", Font.PLAIN, 14));
                if (row % 2 == 0) {
                    c.setBackground(new Color(66, 107, 120)); // Couleur des lignes paires
                } else {
                    c.setBackground(new Color(66, 107, 120)); // Couleur des lignes impaires
                }
                c.setForeground(Color.WHITE); // Couleur du texte
                return c;
            }
        });

        JButton addInvoiceButton = new JButton("Ajouter Facture");
        addInvoiceButton.setBackground(new Color(34, 187, 209)); // Couleur du bouton
        addInvoiceButton.setForeground(Color.WHITE);
        addInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajtFactureform();
            }
        });

        JPanel tableAndButtonPanel = new JPanel(new BorderLayout());
        tableAndButtonPanel.add(new JScrollPane(factureTable), BorderLayout.CENTER);
        tableAndButtonPanel.add(addInvoiceButton, BorderLayout.SOUTH);
        mainFrame.add(tableAndButtonPanel, BorderLayout.CENTER);
        mainFrame.revalidate(); // Rafraîchir le frame pour s'assurer que les composants sont visibles
        mainFrame.repaint();
    }

    private void ajtFactureform() {
        JDialog dialog = new JDialog(mainFrame, "Ajouter une facture", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10)); // Augmenter le nombre de lignes pour inclure le bouton
        Font boldFont = new Font("Arial", Font.BOLD, 14);

        JTextField idField = new JTextField();
        idField.setBorder(BorderFactory.createLineBorder(new Color(110, 232, 69)));
        JTextField dateField = new JTextField();
        dateField.setBorder(BorderFactory.createLineBorder(new Color(110, 232, 69)));
        JTextField totalField = new JTextField();
        totalField.setBorder(BorderFactory.createLineBorder(new Color(110, 232, 69)));
        JTextField payeField = new JTextField();
        payeField.setBorder(BorderFactory.createLineBorder(new Color(110, 232, 69)));

        JLabel restantField = new JLabel();
        restantField.setBorder(BorderFactory.createLineBorder(new Color(110, 232, 69)));

        dialog.add(new JLabel("ID Facture:")).setFont(boldFont);
        dialog.add(idField);
        dialog.add(new JLabel("Date Facturation (YYYY-MM-DD):")).setFont(boldFont);
        dialog.add(dateField);
        dialog.add(new JLabel("Montant Total:")).setFont(boldFont);
        dialog.add(totalField);
        dialog.add(new JLabel("Montant Payé:")).setFont(boldFont);
        dialog.add(payeField);

        JComboBox<TypePaiement> typePaiementComboBox = new JComboBox<>(TypePaiement.values());
        typePaiementComboBox.setBorder(BorderFactory.createLineBorder(new Color(110, 232, 69)));
        dialog.add(new JLabel("Type de Paiement:")).setFont(boldFont);
        dialog.add(typePaiementComboBox);

        // Ajouter un espace vide pour aligner le bouton
        dialog.add(new JLabel());

        JButton submitButton = new JButton("Valider");
        submitButton.setBackground(new Color(27, 28, 28)); // Couleur du bouton
        submitButton.setForeground(Color.WHITE); // Changer la couleur du texte pour qu'il soit visible
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Long idsituationFinanciere = 0L;
                    Long idConsultation = Long.parseLong(idField.getText());
                    Double montantPaye = Double.parseDouble(payeField.getText());
                    Long idFacture = Long.parseLong(idField.getText());
                    LocalDate dateFacturation = LocalDate.parse(dateField.getText());
                    Double montantTotal = Double.parseDouble(totalField.getText());
                    TypePaiement typePaiement = (TypePaiement) typePaiementComboBox.getSelectedItem();
                    Double montantRestant = montantTotal - montantPaye;
                    restantField.setText(String.valueOf(montantRestant));

                    Facture newFacture = new Facture(idsituationFinanciere, idConsultation, montantPaye, idFacture, dateFacturation, montantTotal, typePaiement);

                    saveFactureToFile(newFacture);
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erreur lors de la création de la facture : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    System.out.println(ex);
                }
            }
        });

        dialog.add(submitButton); // Ajouter le bouton au dialog

        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setSize(450, 400); // Ajuster la taille pour s'assurer que tout est visible
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    }

    private void saveFactureToFile(Facture facture) throws IOException {
        try (FileWriter fw = new FileWriter("myFileBase/factures.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(facture.getIdsituationFinanciere() + "|" +
                    facture.getIdConsultation() + "|" +
                    facture.getDateFacturation() + "|" +
                    facture.getIdFacture() + "|" +
                    facture.getTypePaiement().toString() + "|" +
                    facture.getMontantTotal() + "|" +
                    facture.getMontantPaye());
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FacturesView());
//    }
}