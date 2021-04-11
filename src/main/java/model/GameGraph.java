package model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class GameGraph{
    public ArrayList<Vertex> vertices;
    public ArrayList<Edge> edges;
    public Connectivity connectivity;
    public int l;

    public GameGraph(int n, Connectivity connectivity, int l) {
        this.connectivity = connectivity;
        vertices = new ArrayList<>(n);
        edges = new ArrayList<>();
        ArrayList<Vertex> verticesWithoutMaxDegree = new ArrayList<>();

        this.l = l;

        Random rand = new Random(10);
        Random random = new Random(0);
        Edge edge;
        Vertex vertex1, vertex2;
        int v1, v2, bonusEdgesCount;

        vertices.add(new Vertex(0, new HashSet<>()));
        for (int i = 1; i < n; i++) {
            vertices.add(new Vertex(i, new HashSet<>()));
            v1 = rand.nextInt(i);
            edge = addEdge(vertices.get(v1), vertices.get(i),false);
            edge.setActive(true);
            verticesWithoutMaxDegree.add(vertices.get(i));
        }
        if (((n * (n - 1) / 3) - (n - 1)) > l) {
            bonusEdgesCount = rand.nextInt((n * (n - 1) / 3) - (n - 1) - l ) + l;
        } else {
            System.out.println("The given l is too small for the graph");
            System.exit(1);
            bonusEdgesCount = 0;
        }
        for (int i = 0; i < bonusEdgesCount; i++) {
            v1 = rand.nextInt(verticesWithoutMaxDegree.size());
            v2 = rand.nextInt(verticesWithoutMaxDegree.size());
            while (v1 == v2 || edges.contains(new Edge(verticesWithoutMaxDegree.get(Math.min(v1, v2)), verticesWithoutMaxDegree.get(Math.max(v2, v1)), i<l))) {
                v1 = rand.nextInt(verticesWithoutMaxDegree.size());
                v2 = rand.nextInt(verticesWithoutMaxDegree.size());
            }
            vertex1 = verticesWithoutMaxDegree.get(v1);
            vertex2 = verticesWithoutMaxDegree.get(v2);
            edge = addEdge(vertex1, vertex2,i<l && connectivity==Connectivity.TEMPORAL_CONNECTIVITY);
            edge.setActive(true);
            if (verticesWithoutMaxDegree.get(v1).getEdges().size() == n - 1) {
                verticesWithoutMaxDegree.remove(vertex1);
                verticesWithoutMaxDegree.remove(vertex2);
            }
        }
    }

    public Edge addEdge(Vertex v1, Vertex v2, boolean trans) {
        Edge edge;
        if (v1.getId() < v2.getId()) {
            edge = new Edge(v1, v2, trans);
        } else {
            edge = new Edge(v2, v1, trans);
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
                for(int i = 0; i <edges.size(); i++){
                    edges.get(i).setActive(true);
                }
                for (int i = 0; i < l; i++) {
                    edges.get(random.nextInt(edges.size()-1)).setActive(false);
                }
            } while (!isConnected(vertices.get(0)));
        } else {
            for (int i = 0; i < edges.size(); i++) {
                if(!edges.get(i).isActive() && edges.get(i).isTrans()){
                    edges.get(i).setActive(false);
                }else{
                    edges.get(i).setActive(random.nextInt() % 2 == 0);
                }
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

}
