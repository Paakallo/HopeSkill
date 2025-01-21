import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Flag extends GameObject {

    //textures
    private BufferedImage flag;


    public Flag(float x, float y, int scale) {
        super(x, y, ObjectId.Flag, 32, 32, 1);


        try {
            flag = ImageIO.read(new File("resources/obsidian.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        setX(getX() + getVel_x());
        setY(getY() + getVel_y());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(flag, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), 32, 32); // Adjust to match the flag's dimensions
    }

}
