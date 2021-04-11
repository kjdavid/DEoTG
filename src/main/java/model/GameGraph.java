package model;

import java.util.*;

public class GameGraph implements Cloneable {
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public Connectivity connectivity;

    public GameGraph(int n, Connectivity connectivity) {
        this.connectivity = connectivity;
        vertices = new ArrayList<>(n);
        edges = new ArrayList<>();
        ArrayList<Vertex> verticesWithoutMaxDegree = new ArrayList<>();

        Random rand = new Random(10);
        Random random = new Random(0);
        Edge edge;
        Vertex vertex1, vertex2;
        int v1, v2, bonusEdgesCount;

        vertices.add(new Vertex(0, new HashSet<>()));
        for (int i = 1; i < n; i++) {
            vertices.add(new Vertex(i, new HashSet<>()));
            v1 = rand.nextInt(i);
            edge = addEdge(vertices.get(v1), vertices.get(i));
            edge.setActive(random.nextInt() % 2 == 0);
            verticesWithoutMaxDegree.add(vertices.get(i));
        }
        if (((n * (n - 1) / 2) - (n - 1)) > 0) {
            bonusEdgesCount = rand.nextInt((n * (n - 1) / 2) - (n - 1));
        } else {
            bonusEdgesCount = 0;
        }
        for (int i = 0; i < bonusEdgesCount; i++) {
            v1 = rand.nextInt(verticesWithoutMaxDegree.size());
            v2 = rand.nextInt(verticesWithoutMaxDegree.size());
            while (v1 == v2 || edges.contains(new Edge(verticesWithoutMaxDegree.get(Math.min(v1, v2)), verticesWithoutMaxDegree.get(Math.max(v2, v1))))) {
                v1 = rand.nextInt(verticesWithoutMaxDegree.size());
                v2 = rand.nextInt(verticesWithoutMaxDegree.size());
            }
            vertex1 = verticesWithoutMaxDegree.get(v1);
            vertex2 = verticesWithoutMaxDegree.get(v2);
            edge = addEdge(vertex1, vertex2);
            edge.setActive(random.nextInt() % 2 == 0);
            if (verticesWithoutMaxDegree.get(v1).getEdges().size() == n - 1) {
                verticesWithoutMaxDegree.remove(vertex1);
                verticesWithoutMaxDegree.remove(vertex2);
            }
        }
    }

    public Edge addEdge(Vertex v1, Vertex v2) {
        Edge edge;
        if (v1.getId() < v2.getId()) {
            edge = new Edge(v1, v2);
        } else {
            edge = new Edge(v2, v1);
        }
        v1.addEdge(edge);
        v2.addEdge(edge);
        edges.add(edge);
        return edge;
    }

    public void setEdgesToTurnT(int t) {
        Random random = new Random(t);
        if (connectivity == Connectivity.ONE_INTERVAL_CONNECTIVITY) {

            do {
                for (int i = 0; i < edges.size(); i++) {
                    edges.get(i).setActive(random.nextInt() % 2 == 0);
                }
            } while (!isConnected(vertices.get(0)));
        } else {
            for (int i = 0; i < edges.size(); i++) {
                edges.get(i).setActive(random.nextInt() % 2 == 0);
            }
        }
    }


    public boolean isConnected(Vertex start) {
        Stack<Vertex> stack = new Stack<>();
        boolean[] isVisited = new boolean[vertices.size()];
        stack.push(start);
        int i = 0;
        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            isVisited[current.getId()] = true;

            for (Vertex dest : current.getAdjacentVertices()) {
                if (!isVisited[dest.getId()]) {
                    stack.push(dest);
                }
            }
        }
        for (boolean b : isVisited) {
            if (!b) {
                return false;
            }
        }
        System.out.println("graph is connected");
        return true;
    }

    @Override
    public Object clone() {
        GameGraph graph = new GameGraph(vertices.size(), connectivity);
        ArrayList<Vertex> vs = new ArrayList<>();
        for (Vertex v : vertices) {
            //vs.add((Vertex) v.clone()); //FIXME in Vertex class!
        }
        graph.vertices = vs;
        graph.edges = new ArrayList<>();
        HashMap<Vertex, Vertex> bijection = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            bijection.put(vertices.get(i), graph.vertices.get(i));
        }
        for (Edge edge : edges) {
            Edge newEdge = new Edge(bijection.get(edge.v1), bijection.get(edge.v2));
            newEdge.setActive(edge.isActive());
            graph.edges.add(newEdge);
            bijection.get(edge.v1).addEdge(newEdge);
            bijection.get(edge.v2).addEdge(newEdge);
        }
        return graph;
    }
}
