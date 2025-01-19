import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class EnemyJumper extends GameObject {
    private boolean isJumping = false;
    private ObjectHandler handler;

    public EnemyJumper(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectId.Enemy, 16, 32, scale);
        this.handler = handler;
    }

    @Override
    public void render(Graphics g) {
        if (isAlive){
            g.setColor(Color.BLUE);
            g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        }
    }

    @Override
    public void tick() {
        if (isAlive){
            setX(getX() + getVel_x());
            setY(getY() + getVel_y());

            // Apply gravity
            if (!isGrounded()) {
                gravity();
            }

            // Handle collisions
            handleCollisions();


            if (isJumping) {
                setVel_y(-80); // Jump up
                isJumping = false;
            }
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
        Rectangle jumperBounds = getBounds();
        Rectangle objBounds = obj.getBounds();

        if (jumperBounds.intersects(objBounds)) {
            Rectangle intersection = jumperBounds.intersection(objBounds);

            if (intersection.getWidth() > intersection.getHeight()) {
                // Vertical collision
                if (getY() < obj.getY()) {
                    // Colliding from the top
                    setY(obj.getY() - getHeight());
                    setVel_y(0);
                    isJumping = true; // Reset jump state, by default jumper is jumping
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
                } else {
                    // Colliding from the right
                    setX(obj.getX() + obj.getWidth());
                }
            }
        }
    }

    private boolean isGrounded() {
        

        for (GameObject obj : handler.getObjects()) {
            if (obj == this) continue;

            if ((obj.getId() == ObjectId.Block || obj.getId() == ObjectId.Platform) && getBounds().intersects(obj.getBounds())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
