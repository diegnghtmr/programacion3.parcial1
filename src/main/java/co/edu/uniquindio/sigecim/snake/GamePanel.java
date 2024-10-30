package co.edu.uniquindio.sigecim.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel {
    List<Vivora> vivoras;
    CopyOnWriteArrayList<Food> foods = new CopyOnWriteArrayList<>();
    protected static final int MARGIN_LEFT = 20;
    protected static final int MARGIN_RIGHT = 780;
    protected static final int MARGIN_TOP = 20;
    protected static final int MARGIN_BOTTOM = 580;

    public GamePanel(List<Vivora> vivoras) {
        this.vivoras = vivoras;
        setLayout(null); // Use absolute positioning for the game area
        setBackground(Color.WHITE);
        setFocusable(true);
        setPreferredSize(new Dimension(MARGIN_RIGHT - MARGIN_LEFT, MARGIN_BOTTOM - MARGIN_TOP));

        // Add key listener for controlling the active snake
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                for (Vivora vivora : vivoras) {
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
            }
        });

        // Generate initial food
        generarComida();
    }

    public void generarComida() {
        foods.add(new Food());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw margins
        g.setColor(Color.BLACK);
        g.drawRect(MARGIN_LEFT, MARGIN_TOP, MARGIN_RIGHT - MARGIN_LEFT, MARGIN_BOTTOM - MARGIN_TOP);

        for (Vivora vivora : vivoras) {
            if (vivora.viva) {
                vivora.draw(g);
            }
        }
        for (Food food : foods) {
            food.draw(g);
        }
    }

    // Method to update the game state
    public void actualizarJuego() {
        for (Vivora vivora : vivoras) {
            if (!vivora.viva) {
                continue;
            }

            synchronized (vivora) {
                // Obtener la cabeza de manera segura
                Nodo cabeza = vivora.getCabeza();
                Nodo current = cabeza.siguienteNodo;
                while (current != null) {
                    if (cabeza.x == current.x && cabeza.y == current.y) {
                        vivora.viva = false;
                        break;
                    }
                    current = current.siguienteNodo;
                }

                // Comprobación de colisión con comida
                for (Food food : foods) {
                    if (cabeza.x == food.x && cabeza.y == food.y) {
                        vivora.agregarNodo();
                        foods.remove(food);
                        generarComida();
                        break;
                    }
                }

                // Comprobación de colisiones con otras víboras
                for (Vivora otherVivora : vivoras) {
                    if (otherVivora != vivora && otherVivora.viva) {
                        Nodo otherCurrent = otherVivora.getCabeza();
                        synchronized (otherVivora) {
                            while (otherCurrent != null) {
                                if (cabeza.x == otherCurrent.x && cabeza.y == otherCurrent.y) {
                                    vivora.viva = false;
                                    break;
                                }
                                otherCurrent = otherCurrent.siguienteNodo;
                            }
                        }
                    }
                }
            }
        }
        repaint();
    }
}