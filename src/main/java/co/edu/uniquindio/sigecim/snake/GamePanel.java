package co.edu.uniquindio.sigecim.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    Vivora vivora;
    ArrayList<Food> foods = new ArrayList<>();

    public GamePanel(Vivora vivora) {
        this.vivora = vivora;
        setLayout(null); // Use absolute positioning for the game area
        setBackground(Color.WHITE);
        setFocusable(true);

        // Add key listener for controlling the active snake
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (vivora.controladaPorUsuario) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (!vivora.getDireccion().equals("abajo"))
                                vivora.setDireccion("arriba");
                            break;
                        case KeyEvent.VK_DOWN:
                            if (!vivora.getDireccion().equals("arriba"))
                                vivora.setDireccion("abajo");
                            break;
                        case KeyEvent.VK_LEFT:
                            if (!vivora.getDireccion().equals("derecha"))
                                vivora.setDireccion("izquierda");
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (!vivora.getDireccion().equals("izquierda"))
                                vivora.setDireccion("derecha");
                            break;
                    }
                }
            }
        });

        // Generate initial food
        generarComida();
    }

    public void generarComida() {
        if (foods.size() < 1) { // Limit the number of food items
            foods.add(new Food());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vivora.viva) {
            vivora.draw(g);
        }
        for (Food food : foods) {
            food.draw(g);
        }
    }

    // Method to update the game state
    public void actualizarJuego() {
        if (!vivora.viva) {
            return;
        }

        // Mover la víbora
        vivora.moverSnake();

        // Check for collisions
        Nodo cabeza = vivora.cabeza;
        // Check self-collision
        Nodo current = vivora.cabeza.siguienteNodo;
        while (current != null) {
            if (cabeza.x == current.x && cabeza.y == current.y) {
                vivora.viva = false;
                break;
            }
            current = current.siguienteNodo;
        }

        // Check for food collisions
        foods.removeIf(food -> {
            if (cabeza.x == food.x && cabeza.y == food.y) {
                vivora.agregarNodo(); // Add a new node to the snake
                generarComida();
                return true;
            }
            return false;
        });

        repaint();
    }
}