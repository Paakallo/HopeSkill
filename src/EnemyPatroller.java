import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyPatroller extends GameObject {
    private float minX, maxX; // Patrol bounds

    public EnemyPatroller(float x, float y, int width, int height, float minX, float maxX, int scale) {
        super(x, y, ObjectId.Enemy, width, height, scale);
        this.minX = minX * scale;
        this.maxX = maxX * scale;
        setVel_X(2); // Initial speed
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    @Override
    public void tick() {
        setX(getX() + getVel_x());

        // Reverse direction when reaching bounds
        if (getX() <= minX || getX() >= maxX) {
            setVel_X(-getVel_x());
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
