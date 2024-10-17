package co.edu.uniquindio.sigecim.snake;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    ArrayList<Vivora> vivoras = new ArrayList<>();
    ArrayList<Food> foods = new ArrayList<>();
    Vivora vivoraActiva;

    JList<Vivora> snakeList; // List to display snakes
    DefaultListModel<Vivora> listModel; // Model for the JList

    public void generarComida() {
        foods.add(new Food());
    }

    public void setVivoraActiva(Vivora vivora) {
        if (vivoraActiva != null) {
            vivoraActiva.controladaPorUsuario = false;
        }
        vivoraActiva = vivora;
        vivoraActiva.controladaPorUsuario = true;
    }

    public GamePanel() {
        listModel = new DefaultListModel<>();
        snakeList = new JList<>(listModel);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (vivoraActiva != null && vivoraActiva.controladaPorUsuario) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (!vivoraActiva.getDireccion().equals("abajo"))
                                vivoraActiva.setDireccion("arriba");
                            break;
                        case KeyEvent.VK_DOWN:
                            if (!vivoraActiva.getDireccion().equals("arriba"))
                                vivoraActiva.setDireccion("abajo");
                            break;
                        case KeyEvent.VK_LEFT:
                            if (!vivoraActiva.getDireccion().equals("derecha"))
                                vivoraActiva.setDireccion("izquierda");
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (!vivoraActiva.getDireccion().equals("izquierda"))
                                vivoraActiva.setDireccion("derecha");
                            break;
                    }
                }
            }
        });
    }

    // Method to add a new snake
    public void agregarVivora() {
        Vivora nuevaVivora = new Vivora(true, 200, 200);
        vivoras.add(nuevaVivora);
        listModel.addElement(nuevaVivora); // Add to the list model

        if (vivoraActiva != null) {
            vivoraActiva.controladaPorUsuario = false;
        }
        vivoraActiva = nuevaVivora;
        vivoraActiva.controladaPorUsuario = true;

        nuevaVivora.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        // Remove dead snakes
        vivoras.removeIf(vivora -> !vivora.viva);

        // Check for collisions
        for (Vivora vivora : vivoras) {
            if (vivora.viva) {
                Nodo cabeza = vivora.cabeza;
                for (Vivora otraVivora : vivoras) {
                    Nodo current = otraVivora.cabeza;
                    while (current != null) {
                        if (current != cabeza && cabeza.x == current.x && cabeza.y == current.y) {
                            vivora.viva = false;
                            break;
                        }
                        current = current.siguienteNodo;
                    }
                    if (!vivora.viva) {
                        break;
                    }
                }
            }
        }

        // Check for food collisions
        for (Vivora vivora : vivoras) {
            if (vivora.viva) {
                Nodo cabeza = vivora.cabeza;
                foods.removeIf(food -> {
                    if (cabeza.x == food.x && cabeza.y == food.y) {
                        vivora.agregarNodo();
                        generarComida();
                        return true;
                    }
                    return false;
                });
            }
        }

        repaint();
    }

}
