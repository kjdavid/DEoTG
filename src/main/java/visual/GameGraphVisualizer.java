package visual;

import model.Edge;
import model.GameGraph;
import model.Vertex;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameGraphVisualizer extends JPanel {

    public List<VisGameVertex> visVertices;
    public List<VisGameEdge> visEdges;
    public View view;
    public boolean moveView;
    public Point moveLastPos;
    public final int width = 1600;
    public final int height = 720;

    public GameGraphVisualizer() {

        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));

        visVertices = new LinkedList<>();
        visEdges = new LinkedList<>();

        view = new View(-width / 2, -height / 2);
        this.addMouseListener(new ViewMouseListener(this));
        this.addMouseWheelListener(new ViewMouseListener(this));
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        }, 0, 1000 / 60);
    }

    public void setGraph(GameGraph graph) {

        visVertices.clear();
        visEdges.clear();

        Map<Vertex, VisGameVertex> vTemp = new HashMap<>();

        List<Vertex> vertices = graph.vertices;//.stream().sorted(Comparator.comparingInt(v -> -v.edges.size())).collect(Collectors.toList());

        double angStep = (Math.PI * 2) / vertices.size();
        double radius = 30 * vertices.size();
        double radStep = 0;

        int i = 0;
        for (Vertex v : vertices) {
            VisGameVertex gv = new VisGameVertex((int) Math.round((radius + radStep * v.getEdges().size()) * Math.cos(angStep * i)), (int) Math.round((radius + radStep * v.getEdges().size()) * Math.sin(angStep * i)), v);
            visVertices.add(gv);
            vTemp.put(v, gv);
            i++;
        }

        for (Edge e : graph.edges) {
            visEdges.add(new VisGameEdge(vTemp.get(e.v1), vTemp.get(e.v2), e));
        }

        this.repaint();
    }

    public void tick() {
        if (moveView) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            view.x -= p.x - moveLastPos.x;
            view.y -= p.y - moveLastPos.y;
            moveLastPos = p;
            this.repaint();
        }
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (VisGameEdge e : visEdges) {
            if (!e.edge.isActive()) {
                e.paint(g, view);
            }
        }

        for (VisGameEdge e : visEdges) {
            if (e.edge.isActive()) {
                e.paint(g, view);
            }
        }

        for (VisGameVertex v : visVertices) {
            v.paint(g, view);
        }
    }
}

class ViewMouseListener implements MouseListener, MouseWheelListener {

    GameGraphVisualizer parent;

    public ViewMouseListener(GameGraphVisualizer parent) {
        this.parent = parent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            parent.moveLastPos = MouseInfo.getPointerInfo().getLocation();
            parent.moveView = true;
        }
        ;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON2) {
            parent.moveView = false;
        }
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {


        parent.view.zoom = Math.max(0.01f, parent.view.zoom + e.getWheelRotation() / -50.0f);
        parent.repaint();
    }
}