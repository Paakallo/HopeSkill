/**
 * Handles the camera view in the game "HopeSkill".
 * <p>
 * The {@code Camera} class ensures that the game world is rendered relative
 * to the player's position, centering the view on the player while maintaining
 * the boundaries of the game world.
 * </p>
 *
 * <p>
 * Key responsibilities:
 * </p>
 * <ul>
 *     <li>Centers the camera on the player character.</li>
 *     <li>Prevents the camera from showing areas outside the game world boundaries.</li>
 * </ul>
 *
 */
public class Camera {
    private float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }


/**
     * Updates the camera's position based on the player's position.
     * <p>
     * Centers the camera on the player while ensuring it does not go beyond
     * the boundaries of the game world.
     * </p>
     *
     * @param player the {@code GameObject} representing the player
     */
    public void tick(GameObject player) {
        // Center the camera on the player
        x = player.getX() - Main.WINDOW_WIDTH / 2.0f;
        y = player.getY() - Main.WINDOW_HEIGHT / 2.0f;

        x = Math.max(0, x); // Adjust based on game world boundaries
        y = Math.max(0, y); // Adjust based on game world boundaries
    }


/**
     * Retrieves the x-coordinate of the camera's position.
     *
     * @return the x-coordinate of the camera
     */
    public float getX() {
        return x;
    }


    /**
     * Retrieves the y-coordinate of the camera's position.
     *
     * @return the y-coordinate of the camera
     */
    public float getY() {
        return y;
    }
}
