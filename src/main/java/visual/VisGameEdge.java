package visual;

import model.Edge;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class VisGameEdge {
    public VisGameVertex start, end;
    public Edge edge;

    public VisGameEdge(VisGameVertex start, VisGameVertex end, Edge edge) {
        this.start = start;
        this.end = end;
        this.edge = edge;
    }

    public void paint(Graphics g1, View view) {

        Point start = view.transform(this.start.x, this.start.y);
        Point end = view.transform(this.end.x, this.end.y);

        Graphics2D g = (Graphics2D) g1;
        g.setStroke(new BasicStroke(4.0f * view.zoom));
        if(edge.isActive()){
            g.setColor(new Color(200, 200, 200));
        }else if( !edge.isTrans()) {
            g.setColor(new Color(250, 50, 50));
        }else{
            g.setColor(new Color(50, 250, 50));
        }

        g.drawLine(start.x, start.y, end.x, end.y);

    }
}
