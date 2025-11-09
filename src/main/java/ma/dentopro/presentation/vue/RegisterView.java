package ma.dentopro.presentation.vue;

import ma.dentopro.exceptions.AuthenticationException;
import ma.dentopro.exceptions.UserRegistrationException;
import ma.dentopro.model.Utilisateur;
import ma.dentopro.model.enums.Role;
import ma.dentopro.presentation.util.HintPasswordField;
import ma.dentopro.presentation.util.HintTextField;
import ma.dentopro.presentation.vue.palette.imagesTools.ImageUtils;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterView extends JFrame {

    private AuthService authService; // Ajout de AuthService
    private JPanel pnl_main, pnl_buttons, pnl_banner, pnl_form, pnl_formFields, pnl_formIcons, pnl_passwordField;
    private Color color_bg, color_btnCreate, color_btnCancel, color_textFields;
    private Font font_buttons, font_textFields, font_textFieldsHint, font_labels;
    private JButton btn_create, btn_cancel;
    private JLabel lbl_banner, lbl_user, lbl_password, lbl_eyeIcon;
    private JTextField txt_username;
    private JPasswordField txt_password, txt_confirmPassword;
    private boolean isPasswordVisible = false;

    public RegisterView(AuthService authService) {
        this.authService = authService; // Initialisation de AuthService
        initPannels();
        setTitle("Dentopro - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 650));
        setLocationRelativeTo(null);
        setUndecorated(false);
        setResizable(false);
        setVisible(true);
    }

    private void initPannels() {
        initColors();
        initFonts();
        initButtons();
        initLabels();
        initTextFields();

        pnl_buttons = new JPanel();
        pnl_buttons.setPreferredSize(new Dimension(485, 69));
        pnl_buttons.setBackground(color_bg);
        pnl_buttons.setBorder(new EmptyBorder(0, 0, 2, 13));
        pnl_buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnl_buttons.add(btn_create);
        pnl_buttons.add(btn_cancel);

        pnl_banner = new JPanel();
        pnl_banner.setPreferredSize(new Dimension(485, 307));
        pnl_banner.setBackground(color_bg);
        pnl_banner.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
        pnl_banner.setLayout(new GridLayout(1, 1));
        pnl_banner.add(lbl_banner);

        pnl_formFields = new JPanel();
        pnl_formFields.setLayout(new GridLayout(3, 1, 0, 20));
        pnl_formFields.setBorder(new EmptyBorder(10, 10, 10, 30));
        pnl_formFields.setBackground(color_bg);
        pnl_formFields.add(txt_username);
        pnl_formFields.add(txt_password);
        pnl_formFields.add(txt_confirmPassword);

        pnl_passwordField = new JPanel();
        pnl_passwordField.setLayout(new GridLayout(1, 1, 0, 0));
        pnl_passwordField.setBorder(new EmptyBorder(80, 10, 0, 10));
        pnl_passwordField.setBackground(color_bg);
        pnl_passwordField.add(lbl_eyeIcon);

        pnl_formIcons = new JPanel();
        pnl_formIcons.setBorder(new EmptyBorder(0, 20, 0, 10));
        pnl_formIcons.setLayout(new GridLayout(3, 1, 0, 20));
        pnl_formIcons.setBackground(color_bg);
        pnl_formIcons.add(lbl_user);
        pnl_formIcons.add(lbl_password);
        pnl_formIcons.add(new JLabel()); // Placeholder pour l'alignement

        pnl_form = new JPanel();
        pnl_form.setPreferredSize(new Dimension(485, 108));
        pnl_form.setBackground(color_bg);
        pnl_form.setBorder(new EmptyBorder(0, 3, 3, 3));
        pnl_form.setLayout(new BorderLayout());
        pnl_form.add(pnl_formIcons, BorderLayout.WEST);
        pnl_form.add(pnl_formFields, BorderLayout.CENTER);
        pnl_form.add(pnl_passwordField, BorderLayout.EAST);

        pnl_main = new JPanel();
        pnl_main.setBackground(color_bg);
        pnl_main.setLayout(new BorderLayout());
        pnl_main.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnl_main.add(pnl_buttons, BorderLayout.SOUTH);
        pnl_main.add(pnl_banner, BorderLayout.NORTH);
        pnl_main.add(pnl_form, BorderLayout.CENTER);

        setContentPane(pnl_main);
    }

    private void initColors() {
        color_bg = new Color(244, 244, 244);
        color_btnCreate = new Color(49, 83, 168);
        color_btnCancel = new Color(234, 41, 90);
        color_textFields = new Color(25, 9, 236);
    }

    private void initFonts() {
        font_buttons = new Font("Georgia", Font.BOLD, 16);
        font_textFields = new Font("Georgia", Font.BOLD, 16);
        font_textFieldsHint = new Font("Georgia", Font.ITALIC, 14);
        font_labels = new Font("Georgia", Font.BOLD, 14);
    }

    private void initButtons() {
        btn_create = new JButton("Create");
        btn_create.setFont(font_buttons);
        btn_create.setForeground(color_btnCreate);
        btn_create.setBackground(Color.white);
        btn_create.setPreferredSize(new Dimension(120, 46));
        btn_create.setHorizontalAlignment(SwingConstants.CENTER);
        btn_create.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_create.setToolTipText("Cree un Compte");
        btn_create.setFocusable(false);
        btn_create.addActionListener(e -> {
            String username = txt_username.getText();
            String password = new String(txt_password.getPassword());
            String confirmPassword = new String(txt_confirmPassword.getPassword());

            // Validation des champs
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showError("Tous les champs doivent être remplis.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError("Les mots de passe ne correspondent pas.");
                return;
            }

            try {
                // Création d'un nouvel utilisateur
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setUsername(username);
                utilisateur.setPassword(password);

                // Création du compte via AuthService
                authService.Register(utilisateur); // Appel de la méthode Register avec un objet Utilisateur
                showSuccess("Compte créé avec succès !");
                dispose(); // Fermer la fenêtre d'inscription
                new LoginView(authService); // Ouvrir la fenêtre de connexion
            } catch (UserRegistrationException ex) { // Attraper UserRegistrationException au lieu de AuthenticationException
                showError("Erreur lors de la création du compte : " + ex.getMessage());
            }
        });

        btn_cancel = new JButton("Cancel");
        btn_cancel.setFont(font_buttons);
        btn_cancel.setForeground(color_btnCancel);
        btn_cancel.setBackground(Color.white);
        btn_cancel.setPreferredSize(new Dimension(120, 46));
        btn_cancel.setHorizontalAlignment(SwingConstants.CENTER);
        btn_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_cancel.setToolTipText("Retour à la fenêtre Login");
        btn_cancel.setFocusable(false);
        btn_cancel.addActionListener(e -> {
            dispose(); // Fermer la fenêtre d'inscription
            new LoginView(authService); // Ouvrir la fenêtre de connexion
        });
    }

    private void initLabels() {
        lbl_banner = new JLabel(ImageUtils.resizeImageIcon(
                new ImageIcon("src/main/resources/darkTheme/images/img_banner_register.png"), 480, 300
        ));
        lbl_banner.setBackground(Color.white);

        lbl_user = new JLabel(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/user.png"), 30, 30));
        lbl_password = new JLabel(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/password.png"), 30, 30));

        lbl_eyeIcon = new JLabel(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeClosed.png"), 20, 20));
        lbl_eyeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_eyeIcon.setToolTipText("Afficher le mot de passe");

        lbl_eyeIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                togglePasswordVisibility();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (isPasswordVisible) {
                    lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeOpened.png"), 20, 20));
                } else {
                    lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeClosed.png"), 20, 20));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (isPasswordVisible) {
                    lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeOpened.png"), 20, 20));
                } else {
                    lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeClosed.png"), 20, 20));
                }
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txt_password.setEchoChar('•');
            txt_confirmPassword.setEchoChar('•');
            lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeClosed.png"), 20, 20));
            lbl_eyeIcon.setToolTipText("Afficher le mot de passe");
        } else {
            txt_password.setEchoChar((char) 0);
            txt_confirmPassword.setEchoChar((char) 0);
            lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeOpened.png"), 20, 20));
            lbl_eyeIcon.setToolTipText("Cacher le mot de passe");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    private void initTextFields() {
        txt_username = new HintTextField("Username");
        txt_username.setFont(font_textFields);

        txt_password = new HintPasswordField("Password");
        txt_password.setFont(font_textFields);

        txt_confirmPassword = new HintPasswordField("Confirm Password");
        txt_confirmPassword.setFont(font_textFields);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}