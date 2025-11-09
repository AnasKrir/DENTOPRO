package ma.dentopro.presentation.vue.palette.panels;
import ma.dentopro.model.Caisse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CirclePanel extends JPanel{

        private Color color;
        private String text;
        private Caisse caisse;

        public CirclePanel(Color color, String text) {
            this.color = color;
            this.text = text;
            setOpaque(false);
            JLabel label = new JLabel(text);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Arial", Font.ITALIC, 14));
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            GradientPaint gradientPaint = new GradientPaint(
                    0, 0, new Color(57, 230, 162),
                    getWidth(), getHeight(), new Color(144, 168, 151)
            );

            Graphics2D g2d = (Graphics2D) g;

            g2d.setPaint(gradientPaint);

            g2d.fill(new Ellipse2D.Double(0, 0, getWidth() - 1, getHeight() - 1));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }
    }



