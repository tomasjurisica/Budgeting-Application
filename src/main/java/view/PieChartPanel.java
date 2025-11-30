//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import javax.swing.JPanel;

public class PieChartPanel extends JPanel {
    private Map<String, Float> data;
    private float total;
    private static final Color[] PRESET_COLORS = new Color[]{new Color(220, 53, 69), new Color(0, 123, 255), new Color(40, 167, 69), new Color(111, 66, 193), new Color(255, 193, 7)};

    public PieChartPanel(Map<String, Float> data, float total) {
        this.data = data;
        this.total = total;
        this.setPreferredSize(new Dimension(350, 600));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int diameter = Math.min(this.getWidth(), this.getHeight() - 40);
        int x = (this.getWidth() - diameter) / 2;
        int y = 40;
        float startAngle = 0.0F;
        int index = 0;

        for(String category : this.data.keySet()) {
            float amount = (Float)this.data.get(category);
            float angle = amount / this.total * 360.0F;
            if (index < PRESET_COLORS.length) {
                g2.setColor(PRESET_COLORS[index]);
            } else {
                g2.setColor(this.randomColor(category.hashCode()));
            }

            g2.fillArc(x, y, diameter, diameter, Math.round(startAngle), Math.round(angle));
            startAngle += angle;
            ++index;
        }

        int holeSize = (int)((double)diameter * 0.6);
        int holeX = (this.getWidth() - holeSize) / 2;
        int holeY = y + (diameter - holeSize) / 2;
        g2.setColor(this.getBackground());
        g2.fillOval(holeX, holeY, holeSize, holeSize);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", 1, 22));
        Object[] var10001 = new Object[]{this.total};
        String text = "$" + String.format("%.2f", var10001);
        FontMetrics fm = g2.getFontMetrics();
        int textX = this.getWidth() / 2 - fm.stringWidth(text) / 2;
        int textY = holeY + holeSize / 2 + fm.getAscent() / 2;
        g2.drawString(text, textX, textY);
    }

    private Color randomColor(int seed) {
        return (new Color(seed >> 16 & 255, seed >> 8 & 255, seed & 255)).brighter();
    }
}
