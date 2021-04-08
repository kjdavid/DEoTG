package model;

public class Edge implements Cloneable {
    private boolean active;
    public Vertex v1;
    public Vertex v2;

    public Edge(Vertex v1, Vertex v2) {
        this.active = true;
        this.v1 = v1;
        this.v2 = v2;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (!v1.equals(edge.v1)) return false;
        return v2.equals(edge.v2);
    }

    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        return result;
    }
}
