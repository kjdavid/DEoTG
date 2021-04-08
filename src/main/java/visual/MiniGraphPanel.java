package visual;

import model.Edge;
import model.Game;
import model.State;
import model.Vertex;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MiniGraphPanel extends JPanel {
    public List<State> nextStates = new LinkedList<>();
    public int startIndex;
    public static final int stateCount = 10;

    public Game game;
    public GameGraphVisualizer visual;

    public MiniGraphPanel(Game game, GameGraphVisualizer visual) {

        this.game = game;
        this.visual = visual;

        this.addMouseWheelListener(e -> {
            startIndex = Math.max(0, Math.min(startIndex + e.getWheelRotation(), nextStates.size() - stateCount));
            MiniGraphPanel.this.repaint();
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { //TODO: make states to turns
//                int index = e.getX() / (getWidth()/stateCount);
//                game.setState(nextStates.get(Math.min(startIndex + index, nextStates.size()-1)));
//                visual.setGraph(game.state.graph);
//                refreshNextStates(game.state.getNextStates(game.maxTurn));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void refreshNextStates(List<State> nextStates) {
        this.nextStates = nextStates;

        this.startIndex = 0;

        this.repaint();
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        g.setColor(new Color(20, 20, 20));
        g.fillRect(0, 0, getWidth(), getHeight());

        int boxWidth = getWidth() / stateCount;
        int boxHeight = getHeight();
        int boxRadius = Math.min(boxWidth, boxHeight) / 2;

        g.setColor(new Color(200, 200, 200));

        State t;
        for (int i = startIndex; i < Math.min(startIndex + stateCount, nextStates.size()); i++) {
            t = nextStates.get(i);

            int origoX = (i - startIndex) * boxWidth + (boxWidth - boxRadius) / 2 + boxRadius / 2;
            int origoY = boxHeight / 2;

            double angStep = (Math.PI * 2) / t.graph.vertices.size();
            double radius = boxRadius * 0.75;
            double radStep = 0;

            Map<Vertex, Integer> vTemp = new HashMap<>();

            int j = 0;
            for (Vertex v : t.graph.vertices) {

                vTemp.put(v, j);
                j++;
            }

            j = 0;

            for (Edge e : t.graph.edges) {
                if (!e.isActive()) {
                    j = vTemp.get(e.v1);
                    int sx = (int) Math.round((radius + radStep) * Math.cos(angStep * j)) + origoX;
                    int sy = (int) Math.round((radius + radStep) * Math.sin(angStep * j)) + origoY;

                    j = vTemp.get(e.v2);
                    int ex = (int) Math.round((radius + radStep) * Math.cos(angStep * j)) + origoX;
                    int ey = (int) Math.round((radius + radStep) * Math.sin(angStep * j)) + origoY;


                    g.setColor(new Color(80, 40, 40));
                    g.drawLine(sx, sy, ex, ey);
                }
            }

            for (Edge e : t.graph.edges) {
                if (e.isActive()) {
                    j = vTemp.get(e.v1);
                    int sx = (int) Math.round((radius + radStep) * Math.cos(angStep * j)) + origoX;
                    int sy = (int) Math.round((radius + radStep) * Math.sin(angStep * j)) + origoY;

                    j = vTemp.get(e.v2);
                    int ex = (int) Math.round((radius + radStep) * Math.cos(angStep * j)) + origoX;
                    int ey = (int) Math.round((radius + radStep) * Math.sin(angStep * j)) + origoY;

                    g.setColor(new Color(200, 200, 200));
                    g.drawLine(sx, sy, ex, ey);
                }
            }
            j = 0;
            for (Vertex v : t.graph.vertices) {
                int vx = (int) Math.round((radius + radStep) * Math.cos(angStep * j)) + origoX;
                int vy = (int) Math.round((radius + radStep) * Math.sin(angStep * j)) + origoY;

                j++;

                if (v.getAgents().size() != 0) {
                    g.setColor(new Color(100, 100, 250));
                } else {
                    g.setColor(new Color(200, 200, 200));
                }

//                g.setColor(new Color(200,200,200));
                g.fillOval(vx - 4, vy - 4, 8, 8);
            }

        }
    }
}
