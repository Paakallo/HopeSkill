import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReflectionPoint extends GameObject {
    private boolean isActive = true;
    private boolean taskCompleted = false;

    private BufferedImage flower;

    String ques;
    String answ1;
    String answ2;

    public ReflectionPoint(float x, float y, String ques, String answ1, String answ2) {
        super(x, y, ObjectId.Reflection, 50, 50);
        this.ques = ques;
        this.answ1 = answ1;
        this.answ2 = answ2;

        try {
            flower = ImageIO.read(new File("resources/flower.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {  
        g.drawImage(flower, (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public void tick() {
        setX(getX() + getVel_x());
        setY(getY() + getVel_y());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    public boolean activate(Player player) {
        if (isActive) {
            isActive = false;
            return true; // Wywołaj zadanie refleksji
        }
        return false;
    }

    public void completeTask(Player player) {
        taskCompleted = true;
    }

    public String getQuestion() {
        return ques;
    }

    public String getAnsw1() {
        return answ1;
    }

    public String getAnsw2() {
        return answ2;
    }
}
