package visual;

import model.Agent;
import model.Vertex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class VisGameVertex {

    public int x, y;
    public int size;
    public Vertex vertex;

    public VisGameVertex(int x, int y, Vertex vertex) {
        this.x = x;
        this.y = y;
        this.vertex = vertex;
        this.size = 120;
    }

    public void paint(Graphics g1, View view) {

        Graphics2D g = (Graphics2D) g1;

        Point p = view.transform(x, y);
        int size = view.transform(this.size);

        g.setColor(new Color(80, 80, 80));
        g.fillOval(p.x - size / 2, p.y - size / 2, size, size);
        int c = (Main.turn - vertex.getLastTurnVisited()) > 200 ? 200 : Main.turn - vertex.getLastTurnVisited();
        g.setColor(new Color(200, 200, 200 - c));
        g.setStroke(new BasicStroke(2.0f));
        g.drawOval(p.x - size / 2, p.y - size / 2, size, size);

        int i = 0;
        int start = (int) (size / 4 * vertex.getAgents().size() / 2.0f);

        for (Agent a : vertex.getAgents()) {
            if (!a.isSleeping()) {
                g.setColor(new Color(80, 80, 200));
                g.fillOval(p.x - start + i * size / 4, p.y, size / 4, size / 4);
                g.setColor(new Color(20, 20, 100));
                g.setStroke(new BasicStroke(2.0f));
                g.drawOval(p.x - start + i * size / 4, p.y, size / 4, size / 4);
            } else {
                g.setColor(new Color(200, 0, 0));
                g.fillOval(p.x - start + i * size / 4, p.y, size / 4, size / 4);
                g.setColor(new Color(100, 0, 0));
                g.setStroke(new BasicStroke(2.0f));
                g.drawOval(p.x - start + i * size / 4, p.y, size / 4, size / 4);

            }
            Font font = new Font("Serif", Font.BOLD, view.transform(25));
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics();
            int tx = p.x - start + (i) * size / 4 + fontMetrics.stringWidth(a.getId() + "") / (a.getId() + "").length() - size * ((a.getId() + "").length() - 1) / 16;
            int ty = p.y + size / 8;
            g.setColor(new Color(255, 255, 255));
            g.drawString(a.getId() + "", tx, ty + 5);
            i++;
        }

        Font font = new Font("Serif", Font.BOLD, view.transform(20));
        g.setFont(font);
        g.setColor(new Color(200, 200, 200));
        FontMetrics fontMetrics = g.getFontMetrics();
        int tx = p.x - fontMetrics.stringWidth("V" + vertex.getId()) / 2;
        int ty = p.y - size / 8;
        g.drawString("V" + vertex.getId(), tx, ty);

    }
}
