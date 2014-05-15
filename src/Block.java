
import java.awt.image.BufferedImage;

public class Block {

    public int x;
    public int y;
    public BufferedImage color;

    public Block(int x, int y, BufferedImage color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equal(Block b) {
        if (this.x == b.x && this.y == b.y) {
            return true;
        }
        return false;
    }

}
