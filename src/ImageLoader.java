
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageLoader {

    public BufferedImage background;
    public BufferedImage red;
    public BufferedImage green;
    public BufferedImage blue;
    public BufferedImage yellow;
    public BufferedImage transperanttest;
    public BufferedImage shadow;
    public BufferedImage panel;

    public ImageLoader() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            this.background = ImageIO.read(getClass().getClassLoader().getResource("background.png"));
            this.red = ImageIO.read(getClass().getClassLoader().getResource("red.png"));
            this.green = ImageIO.read(getClass().getClassLoader().getResource("green.png"));
            this.blue = ImageIO.read(getClass().getClassLoader().getResource("blue.png"));
            this.yellow = ImageIO.read(getClass().getClassLoader().getResource("yellow.png"));
            this.shadow = ImageIO.read(getClass().getClassLoader().getResource("shadow.png"));
            this.panel = ImageIO.read(getClass().getClassLoader().getResource("panel.png"));
            //this.transperanttest= ImageIO.read(new File("images/transperant.png"));

        } catch (IOException ex) {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
