package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import interface_adapter.detailed_spending.DetailedSpendingController;


public class PieChartPanel extends JPanel {

    private Map<String, Float> data;
    private float total;

    private final DetailedSpendingController detailedSpendingController;
    private final String username;
    private final int month;
    private final int year;

    private static final Color[] PRESET_COLORS = {
            new Color(220, 53, 69),
            new Color(0, 123, 255),
            new Color(40, 167, 69),
            new Color(111, 66, 193),
            new Color(255, 193, 7)
    };

    public PieChartPanel(Map<String, Float> data,
                         float total,
                         DetailedSpendingController detailedSpendingController,
                         String username,
                         int month,
                         int year) {
        this.data = data;
        this.total = total;
        this.detailedSpendingController = detailedSpendingController;
        this.username = username;
        this.month = month;
        this.year = year;
        setPreferredSize(new Dimension(350, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e){
                String category = handleClick(e.getX(), e.getY());
                if (!category.isEmpty() && !category.isEmpty()) {
                    detailedSpendingController.execute(username, category, month, year);
                }
            }
        });
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
            float angle = (amount/total) * 360f;

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

        int holeSize = (int)(diameter * 0.60);
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
        return new Color((seed >> 16)  & 0xFF, (seed >> 8)  & 0xFF, seed & 0xFF).brighter();
    }

String handleClick(int mouseX, int mouseY) {
    int diameter = Math.min(getWidth(), getHeight() - 200);
    int drawX = (getWidth() - diameter) / 2;
    int drawY = 40;

    double centerX = drawX + diameter / 2.0;
    double centerY = drawY + diameter / 2.0;
    double radius = (double) diameter / 2;

    double dx = mouseX - centerX;
    double dy = mouseY - centerY;

    double distance = Math.sqrt(dx * dx + dy * dy);
    //Case 1: Outside bounds
    if (distance > radius){
        return "";
    }
    // Case 2: Inside the donut
    else if (distance<radius*0.60){
        return "";
    }
    // Case 3: In the category section
    else {
        // Do you round the angle?
        double angle = Math.toDegrees(Math.atan2(-dy, dx));
        if (angle < 0) {
            angle += 360;
        }
        // Map<String, AngleRange> CategorytoAngleRange = new HashMap<>();
        int currentStart = 0;
        for (String category: data.keySet()){

            float amount = data.get(category);
            float sliceAngle = (amount/total) * 360f;
            int start = currentStart; // Get the start of the angle
            int end = start + (int) sliceAngle; // Get the end of the angle

            // CategorytoAngleRange.put(category, new AngleRange(currentStart, end));

            // See if the angle falls in the range
            if (angle >= start && angle < end) {
                System.out.println("User clicked on:"+ category);
                return category;
            }
            currentStart = end;
        }


    }

    return "";}
}
