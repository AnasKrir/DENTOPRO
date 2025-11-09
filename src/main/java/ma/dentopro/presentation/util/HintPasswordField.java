package ma.dentopro.presentation.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintPasswordField extends JPasswordField {
    private final String hint;
    private boolean showingHint;

    public HintPasswordField(final String hint) {
        this.hint = hint;
        this.showingHint = true;
        this.setEchoChar((char) 0); // Desactiver le masquage du texte pour afficher le hint
        this.setText(hint);
        this.setForeground(Color.GRAY);

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingHint) {
                    setText("");
                    setForeground(Color.BLACK);
                    setEchoChar('•'); // Activer le masquage
                    showingHint = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setForeground(Color.GRAY);
                    setEchoChar((char) 0); // Desactiver le masquage
                    setText(hint);
                    showingHint = true;
                }
            }
        });
    }

    @Override
    public char[] getPassword() {
        return showingHint ? new char[0] : super.getPassword();
    }
}
