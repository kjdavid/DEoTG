package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Vertex implements Cloneable {
    private int id;
    private HashSet<Agent> agents;
    private LinkedList<Pair<Edge,Agent>> edges; //Technically this is the whiteboard.
    private int pointerV;
    public Vertex(int id, HashSet<Agent> agents) {
        this.id = id;
        this.agents = agents;
        this.edges = new LinkedList<>();
        this.pointerV=0;
    }

    public int getPointerV() {
        return pointerV;
    }

    public void setPointerV(int pointerV) {
        this.pointerV = pointerV;
    }

    public int getId() {
        return id;
    }

    public HashSet<Agent> getAgents() {
        return agents;
    }

    public LinkedList<Pair<Edge, Agent>> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        this.edges.add(new Pair<>(edge, null));
    }

    public void wakeUpAgents(){//This only should call in semi sync case
        for(Pair<Edge, Agent> p :edges){
            if(p.getKey().isActive() && p.getValue()!=null && p.getValue().isSleeping()){
                p.getValue().setSleeping(false);
            }
        }
    }

    public void sendAgents(int t){
        for(Pair<Edge, Agent> p :edges){
            if(p.getKey().isActive() && p.getValue()!=null){
                Agent a = p.getValue();
                if(p.getKey().v1.equals(this)){
                    p.getKey().v2.agents.add(a);
                    a.move(t,p.getKey().v2);
                }else{
                    p.getKey().v1.agents.add(a);
                    a.move(t,p.getKey().v1);
                }
                p.setValue(null);
                agents.remove(a);
            }
        }
    }

    public LinkedList<Vertex> getAdjacentVertices(){
        LinkedList<Vertex> vertices= new LinkedList<>();
        for(Pair<Edge,Agent> p : edges ){
            if(p.getKey().isActive()){
                vertices.add(p.getKey().v1.equals(this) ? p.getKey().v2 : p.getKey().v1);
            }
        }
        return vertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
