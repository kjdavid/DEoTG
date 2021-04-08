package model;


public class State {//TODO: check if it's needed at all. Probably not. //TODO2: Norbi will handle this
    public int turn;
//    public Token player;
    public GameGraph graph;

    public State(GameGraph graph, int turn) {

        this.graph = graph;
//        this.player = player;
        this.turn = turn;
    }

//    private void rekurzio(ArrayList<State> states, GameGraph nextStateGraph, int mindegy){
//        if(nextStateGraph.getCops().size()<=mindegy){
//            State state = new State(nextStateGraph,Token.ROBBER,turn);
//            states.add(state);
//            return;
//        }
//        Vertex origin = nextStateGraph.getCops().get(mindegy);
//        for(Edge edge : origin.edges){
//            if(edge.active){
//                GameGraph nextStateGraphClone = (GameGraph) nextStateGraph.clone();
//                Edge move = nextStateGraphClone.edges.get(nextStateGraphClone.edges.indexOf(edge));
//                if(origin.equals(move.v1)){
//                    move.v2.tokens.add(Token.COP);
//                    move.v1.tokens.remove(Token.COP);
//                }else{
//                    move.v1.tokens.add(Token.COP);
//                    move.v2.tokens.remove(Token.COP);
//                }
//                rekurzio(states, nextStateGraphClone,mindegy+1);
//            }
//        }
//        nextStateGraph = (GameGraph) nextStateGraph.clone();
//        rekurzio(states, nextStateGraph,mindegy+1);
//
//    }

//    public ArrayList<State> getNextStates(int maxTurn){
//        ArrayList<State> states = new ArrayList<>();
//
//        if (graph.copOnRobber()) {
//            return states;
//        }
//
//        if(player==Token.COP){
//            GameGraph nextStateGraph = (GameGraph) graph.clone();
//            nextStateGraph.setEdgesToTurnT(turn);
//            rekurzio(states, nextStateGraph, 0);
//        }else{
//            if (turn == maxTurn) {
//                return states;
//            }
//            Vertex robber = graph.getRobber();
//            for(Edge edge : robber.edges){
//                if(edge.active){
//                    GameGraph nextStateGraph = (GameGraph) graph.clone();
//                    Edge move = nextStateGraph.edges.get(nextStateGraph.edges.indexOf(edge));
//                    if(robber.equals(move.v1)){
//                        move.v2.tokens.add(Token.ROBBER);
//                        move.v1.tokens.remove(Token.ROBBER);
//                    }else{
//                        move.v1.tokens.add(Token.ROBBER);
//                        move.v2.tokens.remove(Token.ROBBER);
//                    }
//                    State state = new State(nextStateGraph,Token.COP,turn+1);
//                    states.add(state);
//                }
//            }
//            GameGraph nextStateGraph = (GameGraph) graph.clone();
//            State state = new State(nextStateGraph,Token.COP,turn+1);
//            states.add(state);
//        }
//        return states;
//    }
}
