package co.edu.uniquindio.sigecim.snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    List<Vivora> vivoras;
    GamePanel gamePanel;
    DefaultListModel<Vivora> listModel;

    public GameFrame() {
        setTitle("Juego de Vívora");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vivoras = new ArrayList<>();
        gamePanel = new GamePanel(vivoras);

        // Create the side panel for controls and snake list
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        // Add button to add new snake
        JButton agregarVivoraButton = new JButton("Agregar Vívora");
        agregarVivoraButton.addActionListener(e -> agregarVivora());
        sidePanel.add(agregarVivoraButton, BorderLayout.NORTH);

        // Add list of snakes
        listModel = new DefaultListModel<>();
        JList<Vivora> snakeList = new JList<>(listModel);
        snakeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        snakeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Vivora selectedVivora = snakeList.getSelectedValue();
                if (selectedVivora != null) {
                    actualizarControl(selectedVivora);
                    gamePanel.requestFocusInWindow(); // Request focus for the game panel
                }
            }
        });
        JScrollPane snakeListScrollPane = new JScrollPane(snakeList);
        sidePanel.add(snakeListScrollPane, BorderLayout.CENTER);

        // Add the side panel to the main panel
        add(gamePanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        // Create the first snake controlled by the user
        agregarVivoraInicial();

        // Start game loop
        Timer timer = new Timer(50, e -> actualizarJuego());
        timer.start();
    }

    private void agregarVivoraInicial() {
        String nombre = "Vívora 1";
        Vivora primeraVivora = new Vivora(true, 200, 200, nombre);
        vivoras.add(primeraVivora);
        listModel.addElement(primeraVivora);
        actualizarControl(primeraVivora);
        primeraVivora.start();
    }

    private void agregarVivora() {
        String nombre = "Vívora " + (vivoras.size() + 1);
        Vivora nuevaVivora = new Vivora(true, 200, 200, nombre); // Set the new snake as controlled by the user
        vivoras.add(nuevaVivora);
        listModel.addElement(nuevaVivora);
        actualizarControl(nuevaVivora); // Update control state of all snakes
        nuevaVivora.start();

        // Select the new snake in the list to make it the active one
        int index = listModel.getSize() - 1;
        JList<Vivora> snakeList = (JList<Vivora>) ((JScrollPane) ((JPanel) getContentPane().getComponent(1)).getComponent(1)).getViewport().getView();
        snakeList.setSelectedIndex(index);
    }

    public void actualizarJuego() {
        gamePanel.actualizarJuego();
    }

    private void actualizarControl(Vivora selectedVivora) {
        for (Vivora vivora : vivoras) {
            vivora.setControladaPorUsuario(vivora == selectedVivora);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}