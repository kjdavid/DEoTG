package visual;

import java.awt.Point;

public class View {
    public int x, y;
    public float zoom;

    public View(int x, int y) {
        this.x = x;
        this.y = y;
        this.zoom = 0.5f;
    }

    public Point transform(int x, int y) {
        return new Point((int) (x * zoom) - this.x, (int) (y * zoom) - this.y);
    }

    public int transform(int v) {
        return (int) (v * zoom);
    }
//public float zoom;
}
