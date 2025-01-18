import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;

public class ReflectionPoint extends GameObject {
    private boolean isActive = true;
    private boolean taskCompleted = false;

    public ReflectionPoint(float x, float y, int scale) {
        super(x, y, ObjectId.Reflection, 32, 32, scale);
    }

    @Override
    public void render(Graphics g) {
        if (isActive) {
            g.setColor(Color.CYAN);
            g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        } else if (taskCompleted) {
            g.setColor(Color.GREEN);
            g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        }
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
        player.health += 2; // Nagroda za ukończenie refleksji
        player.reflections += 1;
    }
}
