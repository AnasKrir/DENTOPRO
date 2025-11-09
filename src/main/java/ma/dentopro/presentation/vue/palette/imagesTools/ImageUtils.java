package ma.dentopro.presentation.vue.palette.imagesTools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



public class ImageUtils {

    public static Color getDominantColor(ImageIcon icon) {
        // Convertir l'ImageIcon en BufferedImage
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        // Analyser les pixels pour determiner la couleur dominante
        int red = 0, green = 0, blue = 0, pixelCount = 0;

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                Color color = new Color(rgb);

                // Ignorer les pixels tres sombres ou tres clairs (optionnel)
                if (isSignificantColor(color)) {
                    red += color.getRed();
                    green += color.getGreen();
                    blue += color.getBlue();
                    pixelCount++;
                }
            }
        }

        // eviter la division par zero
        if (pixelCount == 0) return Color.GRAY;

        // Calculer la couleur moyenne
        red /= pixelCount;
        green /= pixelCount;
        blue /= pixelCount;

        // Augmenter la luminosite de la couleur (ajuster selon vos besoins)
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        hsb[2] = Math.min(1.0f, hsb[2] + 0.2f); // Augmenter la luminosite (Value)
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    private static boolean isSignificantColor(Color color) {
        // Ignorer les couleurs trop sombres ou trop claires
        int brightness = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        return brightness > 30 && brightness < 130;
    }


    public static ImageIcon applyColorOverlay(ImageIcon icon, Color tintColor, float alpha) {
        if (icon == null) return null;

// Convertir l'image en BufferedImage

        Image img = icon.getImage();
        BufferedImage buffered = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffered.createGraphics();
        g2.drawImage(img, 0, 0, null);

// Appliquer une couche de couleur semi-transparente

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g2.setColor(tintColor);
        g2.fillRect(0, 0, buffered.getWidth(), buffered.getHeight());
        g2.dispose();

        return new ImageIcon(buffered);
    }

    public static ImageIcon resizeImageIcon(ImageIcon originalIcon, int newWidth, int newHeight){

        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);

    }
}

