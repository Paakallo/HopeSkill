public class Camera {
    private float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject player) {
        // Center the camera on the player
        x = player.getX() - Main.WINDOW_WIDTH / 2.0f;
        y = player.getY() - Main.WINDOW_HEIGHT / 2.0f;

        // Optional: Clamp the camera position within the bounds of your game world
        x = Math.max(0, x); // Adjust based on game world boundaries
        y = Math.max(0, y); // Adjust based on game world boundaries
        // x = Math.max(0, Math.min(player.getX() - Main.WINDOW_WIDTH / 2.0f, gameWorldWidth - Main.WINDOW_WIDTH));
        // y = Math.max(0, Math.min(player.getY() - Main.WINDOW_HEIGHT / 2.0f, gameWorldHeight - Main.WINDOW_HEIGHT));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
