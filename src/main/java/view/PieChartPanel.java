package view;

import javax.swing.*;

import java.awt.*;
import java.util.Map;

public class PieChartPanel extends JPanel {

    private final Map<String, Float> data;
    private final float total;

    private static final Color[] PRESET_COLORS = {
        new Color(220, 53, 69),
        new Color(0, 123, 255),
        new Color(40, 167, 69),
        new Color(111, 66, 193),
        new Color(255, 193, 7)
    };

    public PieChartPanel(Map<String, Float> data, float total) {
        this.data = data;
        this.total = total;
        setPreferredSize(new Dimension(350, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diameter = Math.min(getWidth(), getHeight() - 200);
        int x = (getWidth() - diameter) / 2;
        int y = 40;

        float startAngle = 0;
        int index = 0;

        for (String category : data.keySet()) {
            float amount = data.get(category);
            float angle = (amount / total) * 360f;

            if (index < PRESET_COLORS.length) {
                g2.setColor(PRESET_COLORS[index]);
            } else {
                g2.setColor(randomColor(category.hashCode()));
            }

            g2.fillArc(x, y, diameter, diameter,
                Math.round(startAngle),
                Math.round(angle));

            startAngle += angle;
            index++;
        }

        int holeSize = (int) (diameter * 0.60);
        int holeX = (getWidth() - holeSize) / 2;
        int holeY = y + (diameter - holeSize) / 2;

        g2.setColor(getBackground());  // Use the panel background
        g2.fillOval(holeX, holeY, holeSize, holeSize);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));

        String text = "$" + String.format("%.2f", total);
        FontMetrics fm = g2.getFontMetrics();
        int textX = getWidth() / 2 - fm.stringWidth(text) / 2;
        int textY = holeY + holeSize / 2 + fm.getAscent() / 2;

        g2.drawString(text, textX, textY);
    }

    private Color randomColor(int seed) {
        return new Color((seed >> 16) & 0xFF, (seed >> 8) & 0xFF, seed & 0xFF).brighter();
    }
}