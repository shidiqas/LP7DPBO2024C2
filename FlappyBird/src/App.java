import javax.swing.*;
import java.awt.*; 

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Menampilkan GUI Form
            showMenu();
        });
    }

    public static void showMenu() {
        JFrame menuFrame = new JFrame("Menu");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setSize(360, 640);
        menuFrame.setLocationRelativeTo(null);

        // Panel dengan latar belakang gambar Flappy Bird
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridBagLayout());

        // Membuat tombol untuk membuka game
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            // Menutup form menu saat tombol ditekan
            menuFrame.dispose();
            // Membuka JFrame game FlappyBird
            showGame();
        });

        // Menempatkan tombol di tengah
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        panel.add(startButton, gbc);

        menuFrame.add(panel);
        menuFrame.setVisible(true);
    }

    public static void showGame() {
        JFrame gameFrame = new JFrame("Flappy Bird");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(360, 640);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);

        // Membuat objek JPanel FlappyBird
        FlappyBird flappyBird = new FlappyBird();
        gameFrame.add(flappyBird);

        // Memastikan panel mendapatkan fokus untuk menerima input
        flappyBird.requestFocus();

        gameFrame.setVisible(true);
    }
}
