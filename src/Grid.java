
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Grid extends JPanel {

    private int block;
    private List<Block> fillCells;
    private int score;
    private int gameState;
    private List<BufferedImage> colors;
    private int ci;
    public ImageLoader images;
    private int shadowOffset;
    private int sizex;
    private int sizey;
    private int difficulty;

    public Grid(int block, int sizex, int sizey) {
        this.block = block;
        fillCells = new ArrayList<>();
        this.score=0;
        this.gameState=1;
        this.images = new ImageLoader();
        colors = new ArrayList<>();
        this.colors.add(images.red);
        this.colors.add(images.green);
        this.colors.add(images.blue);
        this.colors.add(images.yellow);
        this.ci = (int) (Math.random() * colors.size());
        this.shadowOffset = 4;
        this.sizex = sizex;
        this.sizey = sizey;
        this.difficulty=1;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.images.background, 0, 0, this);
        //g.drawImage(this.images.transperanttest, 0, 0, this);
        for (Block fillCell : fillCells) { // draw shadows
            int x = fillCell.x * this.block;
            int y = fillCell.y * this.block;
            g.drawImage(this.images.shadow, x + this.shadowOffset, y + this.shadowOffset, this);
        }
        g.drawImage(images.panel, this.sizex * this.block, 0, this);
        g.setColor(Color.BLACK);
        g.setFont(new Font("LC", Font.PLAIN, 16));
        g.drawString("Next shape:", this.sizex * this.block + 8, 26);
        g.drawString("Score: " + this.score, this.sizex * this.block + 8, 180);
        g.drawString("Diccifulty: "+this.difficulty, this.sizex * this.block + 8, 200);
        String state = "";
        if (this.gameState == 0) {
            state = "Game Over";
        } else if (this.gameState == 2) {
            state = "Paused";
        }
        g.drawString(state, this.sizex * this.block + 8, 220);

        for (Block fillCell : fillCells) { // draw blocks
            int x = fillCell.x * this.block;
            int y = fillCell.y * this.block;
            g.drawImage(fillCell.color, x, y, this);
        }
    }

    public void addCell(int x, int y, BufferedImage c) {
        fillCells.add(new Block(x, y, c));
    }

    public void clearCells() {
        fillCells = new ArrayList<>();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGameState(int gs) {
        this.gameState = gs;
    }

    public BufferedImage nextColor() {
        incCi();
        return this.colors.get(this.ci);
    }

    public void incCi() {
        this.ci++;
        if (this.ci >= this.colors.size()) {
            this.ci = 0;
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
