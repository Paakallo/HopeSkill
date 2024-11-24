import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.BoxLayout;


public class Main {
    static boolean isRunning = true;
    public static void main(String[] args) {
        
        if (isRunning){
            
        }
    }
}


//TODO
class GameMenu extends JFrame{
    public GameMenu(){
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.RED);
        setFocusable(true);
    }

    void mainPage(){
        // Create a JPanel with a BoxLayout for vertical positioning
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        JButton start_g = new JButton("Start Game");
        JButton setting_g = new JButton("Settings");
        JButton exit_g = new JButton("Exit");

        start_g.setAlignmentX(Component.CENTER_ALIGNMENT);
        setting_g.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit_g.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons.add(start_g);buttons.add(setting_g);buttons.add(exit_g);

        this.add(buttons,BorderLayout.CENTER);
    }
}
