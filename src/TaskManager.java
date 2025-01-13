import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManager {
    public static void startReflectionTask(ReflectionPoint reflection, Player player) {
        JFrame frame = new JFrame("Refleksja");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        setupTaskPanel(panel, reflection, player, frame);

        frame.setVisible(true);
    }

    private static void setupTaskPanel(JPanel panel, ReflectionPoint reflection, Player player, JFrame frame) {
        panel.setLayout(null);

        JLabel questionLabel = new JLabel("<html>Wybierz pozytywną myśl, która Cię wzmacnia:<br>1. Mam wsparcie bliskich.<br>2. Uczę się na błędach.</html>");
        questionLabel.setBounds(10, 20, 380, 50);
        panel.add(questionLabel);

        JButton answer1 = new JButton("Mam wsparcie bliskich");
        answer1.setBounds(10, 80, 360, 25);
        panel.add(answer1);

        JButton answer2 = new JButton("Uczę się na błędach");
        answer2.setBounds(10, 120, 360, 25);
        panel.add(answer2);

        ActionListener correctAnswerListener = e -> {
            JOptionPane.showMessageDialog(panel, "Dobra odpowiedź! Otrzymujesz dodatkowe zdrowie.");
            reflection.completeTask(player);
            frame.dispose();
        };

        answer1.addActionListener(correctAnswerListener);
        answer2.addActionListener(correctAnswerListener);
    }
}
