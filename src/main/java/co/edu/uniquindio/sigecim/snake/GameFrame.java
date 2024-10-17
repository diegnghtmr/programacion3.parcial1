package co.edu.uniquindio.sigecim.snake;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    GamePanel gamePanel;

    public GameFrame() {
        setTitle("Juego de Vívora");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();

        // Set up the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // Create the side panel for controls and snake list
        JPanel sidePanel = new JPanel(new BorderLayout());

        // Add button to add new snake
        JButton agregarVivoraButton = new JButton("Agregar Vívora");
        agregarVivoraButton.addActionListener(e -> gamePanel.agregarVivora());
        sidePanel.add(agregarVivoraButton, BorderLayout.NORTH);

        // Add list of snakes
        JScrollPane snakeListScrollPane = new JScrollPane(gamePanel.snakeList);
        sidePanel.add(snakeListScrollPane, BorderLayout.CENTER);

        // Add the side panel to the main panel
        mainPanel.add(sidePanel, BorderLayout.EAST);

        // Add the main panel to the frame
        add(mainPanel);

        // Start game loop
        Timer timer = new Timer(50, e -> gamePanel.actualizarJuego());
        timer.start();

        // Add the first snake
        gamePanel.agregarVivora();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}

