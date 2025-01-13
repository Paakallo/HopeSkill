import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class EnemyPatroller extends GameObject {
    private float minX, maxX; // Patrol bounds
    private ObjectHandler handler;

    public EnemyPatroller(float x, float y, int width, int height, float minX, float maxX, int scale, ObjectHandler handler) {
        super(x, y, ObjectId.Enemy, width, height, scale);
        this.minX = minX * scale;
        this.maxX = maxX * scale;
        this.handler = handler;
        setVel_X(2); // Initial speed
    }

    @Override
    public void render(Graphics g) {
        if (isAlive){
        g.setColor(Color.RED);
        g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        }
    }

    @Override
    public void tick() {
        if (isAlive){
            setX(getX() + getVel_x());
            setY(getY() + getVel_y());

            // Apply gravity
            gravity();

            // Handle collisions
            handleCollisions();

            // Reverse direction when reaching patrol bounds
            if (getX() <= minX || getX() >= maxX) {
                setVel_X(-getVel_x());
            }
        } else{
            // avoid synchronization problems and don't delete enemies
            setX(0);
            setY(0);
        }
    }

    private void handleCollisions() {
        List<GameObject> objects = handler.getObjects();

        for (GameObject obj : objects) {
            if (obj == this) continue; // Skip self

            if (obj.getId() == ObjectId.Block || obj.getId() == ObjectId.Pipe || obj.getId() == ObjectId.Platform) {
                resolveCollision(obj);
            }
        }
    }


    private void resolveCollision(GameObject obj) {
        Rectangle enemyBounds = getBounds();
        Rectangle objBounds = obj.getBounds();

        if (enemyBounds.intersects(objBounds)) {
            Rectangle intersection = enemyBounds.intersection(objBounds);

            if (intersection.getWidth() > intersection.getHeight()) {
                // Vertical collision
                if (getY() < obj.getY()) {
                    // Colliding from the top
                    setY(obj.getY() - getHeight());
                    setVel_y(0);
                } else {
                    // Colliding from the bottom
                    setY(obj.getY() + obj.getHeight());
                    setVel_y(0);
                }
            } else {
                // Horizontal collision
                if (getX() < obj.getX()) {
                    // Colliding from the left
                    setX(obj.getX() - getWidth());
                    setVel_X(-getVel_x());
                } else {
                    // Colliding from the right
                    setX(obj.getX() + obj.getWidth());
                    setVel_X(-getVel_x());
                }
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
