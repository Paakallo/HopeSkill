import java.awt.*;
import java.util.List;
//TODO: TOP COLLISION!!!
public class Player extends GameObject {
    private ObjectHandler handler;
    private boolean jump = false;

    public Player(float x, float y, int scale, ObjectHandler handler) {
        super(x, y, ObjectId.Player, 16, 32, scale); // Player dimensions (width, height)
        this.handler = handler;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());

        // Debug: Draw collision boundaries
        showBounds(g);
    }

    @Override
    public void tick() {
        // Update position
        setX(getX() + getVel_x());
        setY(getY() + getVel_y());

        // Apply gravity
        if (!isGrounded()) {
            gravity();
        }

        // Handle collision
        handleCollisions();
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
        Rectangle playerBounds = getBounds();
        Rectangle objBounds = obj.getBounds();

        if (playerBounds.intersects(objBounds)) {
            Rectangle intersection = playerBounds.intersection(objBounds);

            if (intersection.getWidth() > intersection.getHeight()) {
                // Vertical collision
                if (getY() < obj.getY()) {
                    // Colliding from the top
                    setY(obj.getY() - getHeight());
                    setVel_y(0);
                    jump = false;
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
        List<GameObject> objects = handler.getObjects();

        for (GameObject obj : objects) {
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

    // Add directional bounds if needed (e.g., for fine-tuning collisions)
    public Rectangle getBoundsTop() {
        return new Rectangle((int) getX() + (int) getWidth() / 4, (int) getY(), (int) getWidth() / 2, (int) getHeight() / 4);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) getX() + (int) getWidth() - 5, (int) getY() + 5, 5, (int) getHeight() - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) getX(), (int) getY() + 5, 5, (int) getHeight() - 10);
    }

    private void showBounds(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        g2d.draw(getBounds());
    }

    public boolean getJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
