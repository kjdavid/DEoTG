package model;

import java.util.LinkedList;
import java.util.Random;

public class Agent {
    private int id;

    private long waiting;

    private long waited;

    private boolean atPort;

    private boolean sleeping;

    private LinkedList<Pair<Integer, Vertex>> notes;

    public Agent(int id, Vertex v) {
        this.id = id;
        notes = new LinkedList<>();
        notes.add(new Pair<>(0, v));
        atPort = false;
        waiting = -1;
        waited = 0;
    }

    public Agent(int id, Vertex v, long waiting) {
        this.id = id;
        notes = new LinkedList<>();
        notes.add(new Pair<>(0, v));
        atPort = false;
        this.waiting = waiting;
        waited = 0;
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }

    public void lookAndCompute(int t) {
        if (waiting != -1) {
            if (waited++ == waiting) {
                for (Pair<Edge, Agent> p : notes.getLast().getValue().getEdges()) {
                    if (p.getValue().equals(this)) {
                        p.setValue(null);
                    }
                }
            }
        }
        if (!atPort) {
            Vertex v = notes.getLast().getValue();
            int p = v.getPointerV();
            int i = 0;
            while (v.getEdges().size() > i && v.getEdges().get(p).getValue() != null) {
                p = (p + 1) % v.getEdges().size();
                i++;
            }


            if (new Random(t + id).nextInt() % 2 == 0) {
                if(i < v.getEdges().size()){
                    v.setPointerV((p + 1) % v.getEdges().size());
                    v.getEdges().get(p).setValue(this);
                    atPort = true;
                }
            }
        }
    }

    public void move(int t, Vertex v) {
        notes.add(new Pair<>(t, v));
        waited = 0;
        atPort = false;
        v.incCounter(t);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent agent = (Agent) o;

        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
