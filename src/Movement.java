import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Movement {

    final static int SCREEN_WIDTH = 1800;
    final static int SCREEN_HEIGHT = 800;

    final static int DIAMETER = 25;
    final int delta = 1;
    final int timing = 5;

    JFrame frame;
    DrawPanel panel;

    ArrayList<Laser> lasers;
    ArrayList<Laser> removalList;

    int playerX;
    int playerY;

    public Movement() {
        lasers = new ArrayList<Laser>();
        removalList = new ArrayList<Laser>();
        playerX = playerY = 0;

        frame = new JFrame();
        panel = new DrawPanel();

        panel.setBackground(new Color(13, 13, 13));

        KeyListen k = new KeyListen();
        frame.addKeyListener(k);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.pack();

        new Thread(() -> {
            for (;;) {
                try {
                    Thread.sleep(timing);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k.tick();
                synchronized (lasers) {
                    if (removalList.size() > 0) {
                        for (Laser laser : removalList) {
                            System.out.println("removing laser: " + laser);
                        }
                    }
                    lasers.removeAll(removalList);
                    for (Laser laser : lasers) {
                        if (laser.getX() > SCREEN_WIDTH + Laser.WIDTH) {
                            removalList.add(laser);
                        }
                        laser.tick();
                    }
                }
                panel.repaint();
            }
        }).start();
    }

    class KeyListen implements KeyListener {

        boolean upPressed;
        boolean downPressed;
        boolean leftPressed;
        boolean rightPressed;

        boolean wPressed;
        boolean aPressed;
        boolean sPressed;
        boolean dPressed;

        public KeyListen() {
            upPressed = downPressed = leftPressed = rightPressed = false;
            wPressed = aPressed = sPressed = dPressed = false;
        }

        public void tick() {
            if (upPressed || wPressed) {
                playerY -= delta;
            }
            if (downPressed || sPressed) {
                playerY += delta;
            }
            if (leftPressed || aPressed) {
                playerX -= delta;
            }
            if (rightPressed || dPressed) {
                playerX += delta;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                synchronized (lasers) {
                    lasers.add(new Laser(playerX, playerY));
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = false;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            keyPressed(e);
        }

    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.fillOval(playerX, playerY, DIAMETER, DIAMETER);
            g.setColor(Color.GREEN);
            synchronized (lasers) {
                for (Laser laser : lasers) {
                    g.fillRect(laser.getX(), laser.getY(), Laser.WIDTH, Laser.HEIGHT);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Movement();
        });
    }
}
