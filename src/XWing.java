import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;

public class XWing {
    final static int SCREEN_WIDTH = 1600;
    final static int SCREEN_HEIGHT = 800;

    final static int DIAMETER = 25;
    // final static int DELTA = 2; // change in pixels per tick (speed)
    final static int TIMING = 10; // delay between repaints in ms
    final static int BORDER = 40; // space for info display

    JFrame frame;
    GamePanel panel;
    KeyListen k;

    Player player;
    ArrayList<Laser> removalList;
    ArrayList<EnemyShip> enemyShips; // stores an arraylist of enemy ships

    public XWing() {
        player = new Player();
        removalList = new ArrayList<Laser>();
        enemyShips = new ArrayList<EnemyShip>();

        frame = new JFrame();
        panel = new GamePanel(this);

        // panel.setBackground(new Color(13, 13, 13));

        k = new KeyListen(this);
        frame.addKeyListener(k);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.pack();

        new Thread(() -> {
            for (;;) {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k.tick();
                synchronized (player.getLasers()) {
                    if (removalList.size() > 0) {
                        // System.out.println("rem");
                        // for (Laser laser : removalList) {
                        // System.out.println("removing laser: " + laser);
                        // }
                    }
                    player.getLasers().removeAll(removalList);
                    removalList.removeAll(removalList);
                    for (Laser laser : player.getLasers()) {
                        if (laser.getX() > SCREEN_WIDTH + Laser.WIDTH || laser.getX() < 0 - Laser.WIDTH) {
                            removalList.add(laser);
                        }
                        laser.tick();
                    }
                }
                synchronized (enemyShips) {
                    for (EnemyShip ship : enemyShips) {
                        ship.step();
                    }
                }
                panel.repaint();
            }
        }).start();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<EnemyShip> getEnemyShips() {
        return enemyShips;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new XWing();
        });
    }
}