package visual;

import model.Connectivity;
import model.GameGraph;
import model.Game;
import model.Sync;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame {

    private static boolean found;
    public Game game;
    public GameGraphVisualizer canvas;

    public Main(String[] args) throws Exception {
        if (args.length == 0) {
            game = new Game(Connectivity.TEMPORAL_CONNECTIVITY, Sync.FULL_SYNC, 10, 3);
        } else {
            game = new Game(Connectivity.values()[Integer.parseInt(args[0])],Sync.values()[Integer.parseInt(args[1])],Integer.parseInt(args[2]),Integer.parseInt(args[3]));
        }
        game.initGame();

        GameGraph graph = game.getGraph();
        canvas = new GameGraphVisualizer();

        canvas.setGraph(graph);

        this.add(canvas);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(new KeyListener() {
            int t = 0;

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    game.getGraph().setEdgesToTurnT(++t);
                    if (game.getSync() == Sync.FULL_SYNC) {
                        game.getGraph().vertices.forEach((v) -> {
                            v.getAgents().forEach((a) -> a.lookAndCompute(t));
                        });
                    } else {
                        game.sleepAgents(t);
                        game.getGraph().vertices.forEach((v) -> {
                            v.getAgents().forEach((a) -> a.lookAndCompute(t));
                        });
                        game.getGraph().vertices.forEach((v) -> {
                            v.wakeUpAgents();
                        });

                    }

                    System.out.println("##### " + t + ". moment #####");
                    game.getGraph().vertices.forEach(vertex -> {
                        vertex.getAgents().forEach(agent -> {
                            Main.found = false;
                            vertex.getEdges().forEach(pair -> {
                                if(agent.equals(pair.getValue())){
                                    System.out.println("Agent-"+agent.getId()+" wanted to go from Vertex-"+ vertex.getId() +" to Vertex-"+ (pair.getKey().v1.equals(vertex)?pair.getKey().v2.getId()+".":pair.getKey().v1.getId()+"."));
                                    found=true;
                                }
                            });
                            if (!found){
                                System.out.println("Agent-"+agent.getId()+" stayed in Vertex-"+ vertex.getId());
                            }
                        });
                    });
                    game.getGraph().vertices.forEach((v) -> v.sendAgents(t));
                    canvas.setGraph(game.getGraph());
                    System.out.println();

                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
    }

    public static void main(String[] args) throws Exception {
        Main frame = new Main(args);
    }
}
