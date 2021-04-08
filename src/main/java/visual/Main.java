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

    public Game game;
    public GameGraphVisualizer canvas;
    public MiniGraphPanel mgp;

    public Main(String[] args) throws Exception {
//        if (args.length == 0) {
        game = new Game(Connectivity.ONE_INTERVAL_CONNECTIVITY, Sync.FULL_SYNC, 5, 10);
        game.initGame();
//        } else if(args.length == 1) {
//            game = new Game(10, 1, Integer.parseInt(args[0]));
//            game.initGame();
//        } else if(args.length == 2) {
//            game = new Game(Integer.parseInt(args[1]),1,Integer.parseInt(args[0]));
//            game.initGame();
//        } else if(args.length == 3) {
//            game = new Game(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[0]));
//            game.initGame();
//        } else {
//            int cops[] = new int[Integer.parseInt(args[2])];
//            for (int i = 0;i < cops.length;i++) {
//                cops[i] = Integer.parseInt(args[i+3]);
//            }
//            int robber = Integer.parseInt(args[args.length-1]);
//
//            game = new Game(Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[0]));
//            game.initGame(cops, robber);

//        }
        GameGraph graph = game.getGraph();


        for (int k = 1; k < game.getSize(); k++) {//TODO make it compatible with our magnificent paper
//            List<State> stateList = game.initRoundZero(graph, k);
            long start = System.currentTimeMillis();

            System.out.println("Testing with " + k + " cop");
            boolean kCopWin = true;
            if (kCopWin) {
                break;
            } else {
                System.out.println("\nRobber win time: " + (System.currentTimeMillis() - start) + "ms");
            }
        }
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
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {//TODO: make states to turns
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    game.getGraph().setEdgesToTurnT(++t);
                    if (game.getSync() == Sync.FULL_SYNC) {
                        game.getGraph().vertices.forEach((v) -> {
                            v.getAgents().forEach((a) -> a.lookAndCompute(t));
                        });
                        game.getGraph().vertices.forEach((v) -> v.sendAgents(t));

                    } else {
                        game.sleepAgents(t);
                        game.getGraph().vertices.forEach((v) -> {
                            v.getAgents().forEach((a) -> a.lookAndCompute(t));
                        });
                        game.getGraph().vertices.forEach((v) -> {
                            v.wakeUpAgents();
                        });
                        game.getGraph().vertices.forEach((v) -> v.sendAgents(t));
                    }
                    canvas.setGraph(game.getGraph());
                }
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
