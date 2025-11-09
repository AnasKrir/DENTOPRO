package ma.dentopro.presentation.vue;

import ma.dentopro.dao.api.specificAPI.repository.fileBase_Impl.UtilisateurDao;
import ma.dentopro.exceptions.AuthenticationException;
import ma.dentopro.model.Utilisateur;
import ma.dentopro.presentation.util.HintPasswordField;
import ma.dentopro.presentation.util.HintTextField;
import ma.dentopro.presentation.vue.palette.imagesTools.ImageUtils;
import ma.dentopro.service.api.specificAPI.UtilisateurService;
import ma.dentopro.model.enums.Role;
import ma.dentopro.service.api.specificAPI.service_Impl.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private UtilisateurService utilisateurService;
    private JPanel pnl_main, pnl_buttons, pnl_banner, pnl_form, pnl_formFields, pnl_linkButton, pnl_formIcons, pnl_passwordField;
    private Color color_bg, color_btnLogin, color_btnCancel, color_textFields, color_link;
    private Font font_buttons, font_textFields, font_textFieldsHint, font_labels;
    private JButton btn_login, btn_cancel;
    private ImageIcon img_banner, icon_user, icon_password;
    private JLabel lbl_banner, lbl_user, lbl_password, lbl_newAccountIcon, lbl_eyeIcon;
    private JTextField txt_login;
    private AuthService authService;
    private JPasswordField txt_password;
    private boolean isPasswordVisible = false;

    public LoginView(AuthService authService) {
        this.authService = authService;
        initPannels();
        setTitle("Dentopro - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 600));
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
        pnl_buttons.add(btn_login);
        pnl_buttons.add(btn_cancel);

        pnl_banner = new JPanel();
        pnl_banner.setPreferredSize(new Dimension(485, 307));
        pnl_banner.setBackground(color_bg);
        pnl_banner.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
        pnl_banner.setLayout(new GridLayout(1, 1));
        pnl_banner.add(lbl_banner);

        pnl_formFields = new JPanel();
        pnl_formFields.setLayout(new GridLayout(2, 1, 0, 20));
        pnl_formFields.setBorder(new EmptyBorder(10, 10, 10, 30));
        pnl_formFields.setBackground(color_bg);
        pnl_formFields.add(txt_login);
        pnl_formFields.add(txt_password);

        pnl_passwordField = new JPanel();
        pnl_passwordField.setLayout(new GridLayout(1, 1, 0, 0));
        pnl_passwordField.setBorder(new EmptyBorder(80, 10, 0, 10));
        pnl_passwordField.setBackground(color_bg);
        pnl_passwordField.add(lbl_eyeIcon);

        pnl_linkButton = new JPanel();
        pnl_linkButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnl_linkButton.setBorder(new EmptyBorder(1, 1, 1, 8));
        pnl_linkButton.setBackground(color_bg);
        pnl_linkButton.add(lbl_newAccountIcon);

        pnl_formIcons = new JPanel();
        pnl_formIcons.setBorder(new EmptyBorder(0, 20, 0, 10));
        pnl_formIcons.setLayout(new GridLayout(2, 1, 0, 20));
        pnl_formIcons.setBackground(color_bg);
        pnl_formIcons.add(lbl_user);
        pnl_formIcons.add(lbl_password);

        pnl_form = new JPanel();
        pnl_form.setPreferredSize(new Dimension(485, 108));
        pnl_form.setBackground(color_bg);
        pnl_form.setBorder(new EmptyBorder(0, 3, 3, 3));
        pnl_form.setLayout(new BorderLayout());
        pnl_form.add(pnl_formIcons, BorderLayout.WEST);
        pnl_form.add(pnl_formFields, BorderLayout.CENTER);
        pnl_form.add(pnl_linkButton, BorderLayout.SOUTH);
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
        color_btnLogin = new Color(49, 83, 168);
        color_btnCancel = new Color(234, 41, 90);
        color_textFields = new Color(25, 9, 236);
        color_link = new Color(74, 114, 227);
    }

    private void initFonts() {
        font_buttons = new Font("Georgia", Font.BOLD, 16);
        font_textFields = new Font("Georgia", Font.BOLD, 16);
        font_textFieldsHint = new Font("Georgia", Font.ITALIC, 14);
        font_labels = new Font("Georgia", Font.BOLD, 14);
    }

    private void initButtons() {
        btn_login = new JButton("Login");
        btn_login.setFont(font_buttons);
        btn_login.setForeground(color_btnLogin);
        btn_login.setBackground(Color.white);
        btn_login.setPreferredSize(new Dimension(120, 46));
        btn_login.setHorizontalAlignment(SwingConstants.CENTER);
        btn_login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_login.setToolTipText("Se Connecter");
        btn_login.setFocusable(false);
        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txt_login.getText();
                String password = new String(txt_password.getPassword());

                try {
                    if (authService.login(username, password)) {
                        Utilisateur loggedInUser = authService.getLoggedInUser();

                        if (loggedInUser.getRoles() == Role.Administateur) {
                            dispose();
                            new AccueilDentisteView();
                        } else {
                            dispose();
                            new AccueilSecretaireView();
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginView.this, "Invalid username or password!");
                    }
                } catch (AuthenticationException ex) {
                    JOptionPane.showMessageDialog(LoginView.this, "Authentication error: " + ex.getMessage());
                }
            }
        });

        btn_cancel = new JButton("Cancel");
        btn_cancel.setFont(font_buttons);
        btn_cancel.setForeground(color_btnCancel);
        btn_cancel.setBackground(Color.white);
        btn_cancel.setPreferredSize(new Dimension(120, 46));
        btn_cancel.setHorizontalAlignment(SwingConstants.CENTER);
        btn_cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_cancel.setToolTipText("Fermer la fenêtre");
        btn_cancel.setFocusable(false);
        btn_cancel.addActionListener(e -> dispose());
    }

    private void initLabels() {
        lbl_banner = new JLabel(ImageUtils.resizeImageIcon(
                new ImageIcon("src/main/resources/darkTheme/images/img_banner.png"), 480, 300
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

        lbl_newAccountIcon = new JLabel(new ImageIcon("src/main/resources/darkTheme/icons/buttons/add_user.png"));
        lbl_newAccountIcon.setText("Nouveau Compte");
        lbl_newAccountIcon.setHorizontalTextPosition(JLabel.RIGHT);
        lbl_newAccountIcon.setForeground(color_link);

        lbl_newAccountIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RegisterView(authService);
                dispose();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lbl_newAccountIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lbl_newAccountIcon.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lbl_newAccountIcon.setForeground(color_link);
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txt_password.setEchoChar('\u2022');
            lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeClosed.png"), 20, 20));
            lbl_eyeIcon.setToolTipText("Afficher le mot de passe");
        } else {
            txt_password.setEchoChar((char) 0);
            lbl_eyeIcon.setIcon(ImageUtils.resizeImageIcon(new ImageIcon("src/main/resources/darkTheme/icons/buttons/EyeOpened.png"), 20, 20));
            lbl_eyeIcon.setToolTipText("Cacher le mot de passe");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    private void initTextFields() {
        txt_login = new HintTextField("Username");
        txt_login.setFont(font_textFields);

        txt_password = new HintPasswordField("Password");
        txt_password.setFont(font_textFields);
    }

//    public static void main(String[] args) {
//        UtilisateurDao utilisateurDao = new UtilisateurDao();
//        AuthService authService = new AuthService(utilisateurDao);
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new LoginView(authService);
//            }
//        });
//    }
}