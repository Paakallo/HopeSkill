import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;


/**
 * Creates and manages the game window for "HopeSkill".
 * <p>
 * The {@code GameWindow} class sets up a window with the specified dimensions,
 * title, and game canvas. It ensures consistent sizing and disables resizing
 * for a stable game environment.
 * </p>
 *
 * <p>
 * Key responsibilities:
 * </p>
 * <ul>
 *     <li>Configures the game window's size, title, and layout.</li>
 *     <li>Adds the main game canvas to the window.</li>
 *     <li>Ensures proper window behavior, such as closing and centering on the screen.</li>
 * </ul>
 *
 */
public class GameWindow{
    private JFrame frame;
    private Dimension size;

    
    public GameWindow(int width, int height, String title, Main game){
        size = new Dimension(width, height);
        frame = new JFrame(title);

        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        
        frame.setVisible(true);
    }

}
