import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Flag extends GameObject {

    
    public Flag(float x, float y, int scale) {
        super(x, y, ObjectId.Flag, 32, 32, 1);
    }

    @Override
    public void tick() {
        setX(getX() + getVel_x());
        setY(getY() + getVel_y());
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW); // Use a distinct color for the flag
        g.fillRect((int) getX(), (int) getY(), 32, 32); // Adjust size as needed
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), 32, 32); // Adjust to match the flag's dimensions
    }

}
