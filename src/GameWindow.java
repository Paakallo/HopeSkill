import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

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
