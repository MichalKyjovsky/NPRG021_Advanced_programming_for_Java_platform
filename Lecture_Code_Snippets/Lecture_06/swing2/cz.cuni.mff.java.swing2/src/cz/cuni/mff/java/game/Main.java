package cz.cuni.mff.java.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Main {
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new GamePanel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    public static class GamePanel extends JPanel implements Runnable {
        private static final int PWIDTH = 500;
        private static final int PHEIGHT = 400;
        private Thread animator;
        private volatile boolean running = false;
        private volatile boolean gameOver = false;
        private final Random random = new Random();

        private Point point = new Point(0, 0);
        private long shot;
        private long miss;
        private long ticks;

        public GamePanel() {
            setBackground(Color.white);
            setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
            setFocusable(true);
            requestFocus();
            readyForTermination();
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    testPress(e.getX(), e.getY());
                }
            });
        }

        public void addNotify() {
            super.addNotify();
            startGame();
        }

        private void startGame() {
            if (animator == null || !running) {
                animator = new Thread(this);
                animator.start();
            }
        }

        public void stopGame() {
            running = false;
        }

        public void run() {
            running = true;
            while (running) {
                gameUpdate();
                gameRender();
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            }
            System.exit(0);
        }

        private void gameUpdate() {
            if (!gameOver) {
                if (miss >= 10) {
                    gameOver = true;
                } else {
                    if (ticks % 100 == 0) {
                        point.setLocation(random.nextInt(PWIDTH), random.nextInt(PHEIGHT));
                    }
                    ticks++;
                }
            }
        }

        private Graphics dbg;
        private Image dbImage = null;

        private void gameRender() {
            if (dbImage == null) {
                dbImage = createImage(PWIDTH, PHEIGHT);
                if (dbImage == null) {
                    System.out.println("dbImage is null");
                    return;
                } else
                    dbg = dbImage.getGraphics();
            }
            dbg.setColor(Color.white);
            dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
            dbg.setColor(Color.black);
            dbg.drawString("Shots: " + shot + " Misses: " + miss, 0, 10);
            dbg.setColor(Color.red);
            dbg.fillRect(point.x, point.y, 10, 10);
            dbg.setColor(Color.black);
            dbg.drawRect(point.x, point.y, 10, 10);
            if (gameOver)
                gameOverMessage(dbg);
        }

        private void gameOverMessage(Graphics g) {
            g.drawString("GAME OVER", 100, 100);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (dbImage != null) {
                g.drawImage(dbImage, 0, 0, null);
            }
        }

        private void readyForTermination() {
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if ((keyCode == KeyEvent.VK_ESCAPE) ||
                            (keyCode == KeyEvent.VK_Q) ||
                            (keyCode == KeyEvent.VK_END) ||
                            ((keyCode == KeyEvent.VK_C) && e.isControlDown())) {
                        stopGame();
                    }
                }
            });
        }

        private void testPress(int x, int y) {
            if (!gameOver) {
                if ((x >= point.x && x <= point.x + 10) && (y >=point.y && y <= point.y+10)) {
                    shot++;
                } else {
                    miss++;
                }
            }
        }
    }
}
