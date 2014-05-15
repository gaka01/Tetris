
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Tetris {

    public static void main(String[] a) {
        final int sizex = 10;
        final int sizey = 18;
        final int block = 20;

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                Grid grid = new Grid(block, sizex, sizey);
                final Game g1 = new Game(grid, sizex, sizey, 20, 750);
                Thread t1 = new Thread(g1);
                //Thread t2 = new Thread(new moveDown(g1,699));
                t1.start();
                //t2.start();

                JFrame window = new JFrame("Tetris");
                window.addKeyListener(new KeyListener() {
                    List currentlyPressed = new ArrayList<>();

                    @Override
                    public void keyPressed(KeyEvent e) {
                        int n = e.getKeyCode();
                        if (!currentlyPressed.contains(n)) {
                            currentlyPressed.add(n);
                            if (n == 37) {
                                g1.moveShape(-1, 0);
                            } else if (n == 39) {
                                g1.moveShape(1, 0);
                            } else if (n == 38) {
                                g1.rotateShape();
                            } else if (n == 40) {
                                g1.toggleDrop();
                            } else if (n == 32) {
                                g1.togglePause();
                            } //else if (n == 66) {
                               // g1.moveShape(0, 1);
                             else if (n == 91) {
                                g1.setDifficulty(g1.getDifficulty()-1);
                            } else if (n == 93) {
                                g1.setDifficulty(g1.getDifficulty()+1);
                            }
                        }
                    }

                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        int n = e.getKeyCode();
                        if (currentlyPressed.contains(n)) {
                            int i = 0;
                            while (i < currentlyPressed.size()) {
                                currentlyPressed.get(i);
                                if ((int) currentlyPressed.get(i) == n) {
                                    currentlyPressed.remove(i);
                                    if (n == 40) {
                                        g1.toggleDrop();
                                    }
                                }
                                i++;
                            }
                        }
                    }
                }
                );

                window.setSize(sizex * block + grid.images.panel.getWidth() + 6, sizey * block + 28);
                window.setResizable(false);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.add(grid);
                window.setVisible(true);
            }
        });
    }
}
