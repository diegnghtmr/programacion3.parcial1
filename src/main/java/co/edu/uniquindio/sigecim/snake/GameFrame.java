package co.edu.uniquindio.sigecim.snake;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    GamePanel gamePanel;

    public GameFrame() {
        setTitle("Juego de Vívora");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // Add button to add new snake (to be implemented)
        // Add list of snakes (to be implemented)

        // Start game loop
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.actualizarJuego();
            }
        });
        timer.start();

        // Add the first snake
        gamePanel.agregarVivora();
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.setVisible(true);
    }
}

