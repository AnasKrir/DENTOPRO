package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.*;
import ma.dentopro.model.Consultation;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultationsView {

    private JFrame mainFrame;
    private JPanel sideNavBarPanel;
    private JTable consultationsTable;
    private JPanel topBarPanel;
    AuthService serviceauthentif;
    private JTextField searchField;
    private JButton searchButton;
    DossierMedicaleDao dossierMedicaleDao = new DossierMedicaleDao();

    public ConsultationsView(Long idDossier) {
        mainFrame = new JFrame("DENTOPRO");
        mainFrame.setSize(1225, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        createTopBar();
        mainFrame.add(topBarPanel, BorderLayout.NORTH);
        createSideNavBar();
        mainFrame.add(sideNavBarPanel, BorderLayout.WEST);

        JPanel consultationsPanel = new JPanel(new BorderLayout());
        consultationsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainFrame.add(consultationsPanel, BorderLayout.CENTER);

        ArrayList<Consultation> patientConsultations = fetchConsultationsFordossier(idDossier);
        consultationsTable = new JTable(consultationTableModel(patientConsultations));
        consultationsTable.setRowHeight(30);

        JTableHeader header = consultationsTable.getTableHeader();
        header.setBackground(new Color(34, 187, 209));
        header.setForeground(Color.WHITE);

        consultationsTable.setDefaultRenderer(Object.class, customTableCellRenderer());
        consultationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(consultationsTable);
        consultationsPanel.add(scrollPane, BorderLayout.CENTER);

        addSearchBar(consultationsPanel, consultationsTable);
        styleTableHeaders(consultationsTable);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void addSearchBar(JPanel consultationsPanel, JTable consultationsTable) {
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchField = new JTextField(30);
        searchBarPanel.add(searchField);
        searchButton = new JButton("SearchByID");
        searchBarPanel.add(searchButton);
        consultationsPanel.add(searchBarPanel, BorderLayout.NORTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                filterTableByConsultationId(consultationsTable, searchTerm);
            }
        });
    }

    private void filterTableByConsultationId(JTable table, String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        if (searchTerm.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            // Filtrer par la première colonne (ID de la consultation)
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm, 0));
        }
    }

    private static DefaultTableCellRenderer customTableCellRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) {
                    c.setBackground(new Color(66, 107, 120));
                } else {
                    c.setBackground(new Color(66, 107, 120));
                }
                c.setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                return c;
            }
        };
    }

    private void styleTableHeaders(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(34, 187, 209));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private DefaultTableModel consultationTableModel(ArrayList<Consultation> consultations) {
        String[] columns = {"Consultation ID", "Date", "Type"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Consultation consultation : consultations) {
            Object[] rowData = {consultation.getIdConsultation(), consultation.getDateConsultation(), consultation.getTypeConsultation()};
            model.addRow(rowData);
        }

        return model;
    }

    private ArrayList<Consultation> fetchConsultationsFordossier(Long id) {
        ConsultationDao consultationDao = new ConsultationDao();
        List<Consultation> allConsultations = consultationDao.findAll();
        ArrayList<Consultation> consultationsForPatient = new ArrayList<>();

        for (Consultation consultation : allConsultations) {
            if (consultation.getIddossierMedicale().equals(id)) {
                consultationsForPatient.add(consultation);
            }
        }
        return consultationsForPatient;
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
        logoLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

        JLabel textLabel = new JLabel("DENTOPRO");
        textLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        textLabel.setForeground(new Color(34, 187, 209));

        topBarPanel.add(logoLabel);
        topBarPanel.add(textLabel);

        topBarPanel.add(Box.createHorizontalStrut(800));

        ImageIcon iconWithText = new ImageIcon("src/main/resources/lightTheme/icons/buttons/Dentiste.png");
        Image iconedntst = iconWithText.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon finaliconedntst = new ImageIcon(iconedntst);
        JLabel iconLabel = new JLabel(finaliconedntst);
        JLabel dentisteTextLabel = new JLabel("Dentiste");
        dentisteTextLabel.setFont(new Font("Georgia", Font.PLAIN, 16));

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
//        Long dossierId = 1L;
//        SwingUtilities.invokeLater(() -> {
//            ConsultationsView consultationsView = new ConsultationsView(dossierId);
//        });
//    }
}