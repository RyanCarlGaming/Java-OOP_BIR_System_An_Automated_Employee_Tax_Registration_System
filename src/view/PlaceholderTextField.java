package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {

    private String placeholder;

    public PlaceholderTextField() {
        this(null);
    }

    public PlaceholderTextField(final String pText) {
        super();
        this.placeholder = pText;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(final String s) {
        this.placeholder = s;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(150, 150, 150));
        g.setFont(getFont().deriveFont(Font.ITALIC));

        int padding = (getHeight() - getFont().getSize()) / 2;
        int x = getInsets().left;
        int y = getHeight() - padding - 1;

        g.drawString(placeholder, x, y);
        g.dispose();
    }
}
