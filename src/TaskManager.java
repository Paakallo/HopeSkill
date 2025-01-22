import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Manages tasks and interactions triggered by reflection points in the game "HopeSkill".
 * <p>
 * The {@code TaskManager} handles the execution of reflection tasks presented to the player.
 * It provides methods for displaying a graphical user interface (GUI) to interact with
 * reflection points and updates the player's state based on their choices.
 * </p>
 *
 * <p>
 * Key responsibilities:
 * </p>
 * <ul>
 *     <li>Displays a GUI for reflection tasks, including questions and answer options.</li>
 *     <li>Handles player interactions with the reflection task, updating game state accordingly.</li>
 * </ul>
 *
 */
public class TaskManager {
    public static void startReflectionTask(ReflectionPoint reflection, Player player) {
        JFrame frame = new JFrame("Reflection Point");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        JLabel questionLabel = new JLabel("<html><b>Question:</b> " + reflection.getQuestion() + "</html>");
        questionLabel.setBounds(10, 20, 380, 50);
        panel.add(questionLabel);

        JButton answer1 = new JButton(reflection.getAnsw1());
        answer1.setBounds(10, 80, 360, 25);
        panel.add(answer1);

        JButton answer2 = new JButton(reflection.getAnsw2());
        answer2.setBounds(10, 120, 360, 25);
        panel.add(answer2);

        answer1.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "You selected: " + reflection.getAnsw1());
            player.reflections += 1;
            reflection.completeTask(player);
            frame.dispose();
        });

        answer2.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "You selected: " + reflection.getAnsw2());
            player.reflections -= 1;
            reflection.completeTask(player);
            frame.dispose();
        });

        frame.setVisible(true);
    }
}
