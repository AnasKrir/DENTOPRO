package ma.dentopro.presentation.vue.palette.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TablesUtils {
    public static void alignDataCells(JTable table, int alignement){

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignement);
        table.setDefaultRenderer(Object.class, renderer );

    }


    public static void customizeCellRendererWithBorders(JTable table, int alignment, Color evenColor, Color oddColor, Color borderColor, Color SelectionColor) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Appliquer l'alignement
                setHorizontalAlignment(alignment);

                // Appliquer les couleurs alternees
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? evenColor : oddColor);
                    c.setForeground(Color.BLACK);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Texte normal
                } else {
                    c.setBackground(SelectionColor); // Couleur pour la selection
                    c.setForeground(Color.BLACK);
                    c.setFont(c.getFont().deriveFont(Font.BOLD)); // Texte en gras
                }

                // Ajouter une bordure pour simuler des traits
                // setBorder(BorderFactory.createLineBorder(borderColor));
                setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor)); // Bordures de 1 pixel sur chaque côte


                return c;
            }
        };

        // Appliquer le renderer à la table
        table.setDefaultRenderer(Object.class, renderer);
    }


    public static void customizeCellRenderer(JTable table, int alignment, Color evenColor, Color oddColor, Color SelectionColor) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Appliquer l'alignement
                setHorizontalAlignment(alignment);

                // Appliquer les couleurs alternees
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? evenColor : oddColor);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(SelectionColor); // Couleur pour la selection
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        // Appliquer le renderer à la table
        table.setDefaultRenderer(Object.class, renderer);
    }


    /**
     * Aligner le contenu de toutes les cellules dans un JTable.
     */
    public static void alignementCellData(JTable table, int alignement) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(alignement);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    /**
     * Centre le contenu de toutes les cellules dans un JTable.
     */
    public static void centerCellData(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    /**
     * Applique une alternance de couleurs de fond aux lignes du JTable.
     */
    public static void alternateRowColors(JTable table, Color evenColor, Color oddColor) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? evenColor : oddColor);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.YELLOW); // Couleur pour la selection
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    /**
     * Definit la largeur fixe des colonnes d'un JTable.
     */
    public static void setColumnWidths(JTable table, int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
            column.setMinWidth(widths[i]);
            column.setMaxWidth(widths[i]);
        }
    }

    /**
     * Definit la police de toutes les cellules du JTable.
     */
    public static void setTableFont(JTable table, Font font) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setFont(font);
        table.setDefaultRenderer(Object.class, renderer);
    }

    /**
     * Ajoute une bordure personnalisee aux cellules.
     */
    public static void setCellBorders(JTable table, Color borderColor) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (hasFocus) {
                    setBorder(BorderFactory.createLineBorder(borderColor));
                } else {
                    setBorder(null);
                }
                return c;
            }
        });
    }

    /**
     * Met en forme les cellules en fonction de leur valeur (par exemple, valeurs negatives en rouge).
     */
    public static void applyConditionalFormatting(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Exemple : mettre en rouge les valeurs negatives
                if (value instanceof Number && ((Number) value).doubleValue() < 0) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(isSelected ? Color.BLACK : Color.BLUE);
                }
                return c;
            }
        });
    }


}

