
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Runnable {

    private int time;
    private long time2;
    private final Grid grid;
    private List<Block> shape;
    private List<Block> nshape;
    private List<Block> solid;
    private int sizex;
    private int sizey;
    private long nt;
    private int score;
    private int gameState;
    private int difficulty;
    private int odifficulty;
    int[][][] shapes = {{{3, 1}, {4, 1}, {5, 1}, {4, 0}}, {{4, 0}, {4, 1}, {5, 0}, {5, 1}}, {{3, 0}, {4, 0}, {4, 1}, {5, 1}},
    {{4, 0}, {5, 0}, {3, 1}, {4, 1}}, {{3, 0}, {3, 1}, {4, 1}, {5, 1}}, {{3, 1}, {4, 1}, {5, 0}, {5, 1}}, {{4, 0}, {4, 1}, {4, 2}, {4, 3}}};

    public Game(Grid grid, int sizex, int sizey, int time, long time2) {
        this.shape = new ArrayList<>();
        this.nshape = new ArrayList<>();
        this.solid = new ArrayList<>();
        this.time = time;
        this.time2 = time2;
        this.grid = grid;
        this.nt = System.currentTimeMillis();
        this.score = 0;
        this.sizex = sizex;
        this.sizey = sizey;
        this.gameState = 1;
        this.nshape = randShape();
        this.difficulty = 1;
        this.odifficulty = this.difficulty*20;
        newShape();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.grid.clearCells();
                for (Block p : this.shape) {
                    this.grid.addCell(p.x, p.y, p.color);
                }

                for (Block p : this.nshape) {
                    this.grid.addCell(p.x + 8, p.y + 2, p.color);
                }

                for (Block p : this.solid) {
                    this.grid.addCell(p.x, p.y, p.color);
                }
                this.grid.repaint();
                if (this.gameState == 1) {
                    if (System.currentTimeMillis() - nt >= this.time2 / this.difficulty) {
                        moveShape(0, 1);
                        nt = System.currentTimeMillis();
                    }
                    this.grid.setScore(this.score);
                }
                Thread.sleep(this.time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int moveShape(int mx, int my) {
        int out = 0;
        if (this.gameState == 1) {
            List<Block> ns = new ArrayList<>();
            for (Block s : this.shape) {
                ns.add(new Block(s.x + mx, s.y + my, s.color));
            }
            if (checkCollide(ns) == 1) {
                this.shape.clear();
                for (Block s : ns) {
                    this.shape.add(s);
                }
            } else {
                if (mx == 0 && my == 1) {
                    for (Block s : this.shape) {
                        this.solid.add(s);
                    }
                    out = 1;
                    newShape();
                    remLines();
                }
            }
        }
        return out;
    }

    private int checkCollide(List<Block> sh) {
        int out = 1;
        for (Block s : sh) {
            if (s.x < 0 || s.x >= sizex || s.y >= sizey) {
                out = 0;
            }
            for (Block b : this.solid) {
                if (s.equal(b)) {
                    out = 0;
                }
            }
        }
        return out;
    }

    private List<Block> randShape() {
        List<Block> newShape = new ArrayList<>();
        int r = (int) (Math.random() * this.shapes.length);
        BufferedImage c = grid.nextColor();
        for (int i = 0; i < this.shapes[r].length; i++) {
            newShape.add(new Block(this.shapes[r][i][0], this.shapes[r][i][1], c));
        }
        return newShape;

    }

    private void newShape() {
        if (this.gameState == 1) {
            this.shape = this.nshape;

            if (checkCollide(this.shape) == 0) {
                this.gameState = 0;
                this.grid.setGameState(0);
            }
            this.nshape = randShape();
        }

    }

    private void remLines() {
        int[] a;
        a = new int[this.sizey];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        for (Block b : this.solid) {
            a[b.y] += 1;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= this.sizex) {
                remLine(i);
                collide(i);
                this.score += this.sizex;
            }
        }
    }

    private void collide(int y) {
        for (Block s : this.solid) {
            if (s.y < y) {
                s.y++;
            }
        }
    }

    private void remLine(int y) {
        List<Block> toRem = new ArrayList<>();
        for (Block b : this.solid) {
            if (b.y == y) {
                toRem.add(b);
            }
        }

        for (Block b : toRem) {
            this.solid.remove(b);
        }
    }

    public void rotateShape() {
        if (this.gameState == 1) {
            List<Block> ns = new ArrayList<>();
            int minx = 100;
            int miny = 100;
            int macx = -1;
            int macy = -1;
            int mx, my;

            for (Block s : this.shape) {
                if (s.x < minx) {
                    minx = s.x;
                }
                if (s.x > macx) {
                    macx = s.x;
                }
                if (s.y < miny) {
                    miny = s.y;
                }
                if (s.y > macy) {
                    macy = s.y;
                }
            }

            mx = (macx - minx) / 2 + 1;
            my = (macy - miny) / 2 + 1;

            int ox;
            int oy;

            for (Block s : this.shape) {
                ox = mx - s.y + miny + minx;
                oy = s.x - minx + miny;
                ns.add(new Block(ox, oy, s.color));
            }
            if (checkCollide(ns) == 1) {
                this.shape.clear();
                for (Block s : ns) {
                    this.shape.add(s);
                }
            }

        }
    }

    public void toggleDrop() {
        int var=this.difficulty;
        this.difficulty = this.odifficulty;
        this.odifficulty = var;
    }

    public void clearGrid() {
        this.solid.clear();
        newShape();
    }

    public void togglePause() {
        if (this.gameState == 1) {
            this.gameState = 2;
        } else if (this.gameState == 2) {
            this.gameState = 1;
        }
        grid.setGameState(this.gameState);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        if (difficulty > 0 && difficulty < 10) {
            this.difficulty = difficulty;
            this.grid.setDifficulty(difficulty);
        }
    }

}
