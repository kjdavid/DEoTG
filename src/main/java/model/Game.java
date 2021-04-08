package model;

import java.util.*;

public class Game {
    private int numOfAgents;
    private int size;
    private GameGraph graph;
    private Connectivity connectivity;

    private Sync sync;

    public Game(Connectivity connectivity, Sync sync, int numOfAgents, int size) {
        this.sync = sync;
        this.connectivity = connectivity;
        this.numOfAgents = numOfAgents;
        this.size = size;
    }

    public GameGraph getGraph() {
        return graph;
    }

    public Sync getSync() {
        return sync;
    }

    public void setSync(Sync sync) {
        this.sync = sync;
    }

    public void initGame() {

        graph = new GameGraph(size, connectivity);

        Random rand = new Random(0);

        for (int i = 0; i < numOfAgents; i++) {
            int indexOfVertex = rand.nextInt(size);
            int numOfV = graph.vertices.size();
            int l = graph.edges.size() - (numOfV - 1);
            graph.vertices.get(indexOfVertex).getAgents().add(
                    connectivity == Connectivity.ONE_INTERVAL_CONNECTIVITY ?
                            new Agent(i, graph.vertices.get(indexOfVertex), (long) Math.pow((numOfV - 1), numOfV) * (long) Math.pow(numOfV, 2 * l + 1) * (long) Math.pow(numOfV - 1, 2 * l + 1)) :
                            new Agent(i, graph.vertices.get(indexOfVertex))
            );
        }
        //state = new State(graph, Token.COP, turn); //TODO: Norbi will handle this

        //pastStates = new ArrayList<>();
        //pastStates.add(state);
//        stateIndex = 0;
//        stateSize = 0;
    }

    public int getSize() {
        return size;
    }

    public void sleepAgents(int t) {
        Random random = new Random(t);
        graph.vertices.forEach((v) -> {
            v.getAgents().forEach((a) -> {
                if (random.nextInt() % 2 == 0) {
                    a.setSleeping(true);
                }
            });
        });
    }

//    public void setState(State state) { //TODO: Norbi will handle this
//        if (stateIndex == pastStates.size() - 1) {
//            pastStates.add(state);
//            stateIndex++;
//            stateSize++;
//        } else {
//            stateIndex++;
//            pastStates.set(stateIndex,state);
//            stateSize = stateIndex;
//        }
//        this.state = state;
//    }

//    public void prevState() {
//        if (stateIndex > 0) {
//            stateIndex--;
//        }
//        state = pastStates.get(stateIndex);
//    }

//    public void nextState() {
//        if (stateIndex < stateSize) {
//            stateIndex++;
//        }
//        state = pastStates.get(stateIndex);
//    }
}
