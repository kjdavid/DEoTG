package model;

import java.util.Random;

public class Game {

    private int numOfAgents;
    private int size;
    private GameGraph graph;
    private Connectivity connectivity;
    private int l;

    private Sync sync;

    public Game(Connectivity connectivity, Sync sync, int size, int l) {
        this.sync = sync;
        this.connectivity = connectivity;
        if(connectivity == Connectivity.ONE_INTERVAL_CONNECTIVITY){
            if(sync==Sync.FULL_SYNC){
                this.numOfAgents = 2*l;
            }else{
                this.numOfAgents = 2*l+1;
            }
        }else{
            if(sync==Sync.FULL_SYNC){
                this.numOfAgents = 2*l;
            }else{
                this.numOfAgents = 2*l+1;
            }
        }
        this.size = size;
        this.l = l;
    }

    public GameGraph getGraph() {
        return graph;
    }

    public Sync getSync() {
        return sync;
    }
    public void initGame() {

        graph = new GameGraph(size, connectivity, l);

        Random rand = new Random(0);

        for (int i = 0; i < numOfAgents; i++) {
            int indexOfVertex = rand.nextInt(size);
            int numOfV = graph.vertices.size();
            graph.vertices.get(indexOfVertex).getAgents().add(
                    connectivity == Connectivity.ONE_INTERVAL_CONNECTIVITY ?
                            new Agent(i, graph.vertices.get(indexOfVertex), (long) Math.pow((numOfV - 1), numOfV) * (long) Math.pow(numOfV, 2 * l + 1) * (long) Math.pow(numOfV - 1, 2 * l + 1)) :
                            new Agent(i, graph.vertices.get(indexOfVertex))
            );
        }
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
}
