package co.edu.uniquindio.sigecim.snake;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame {
    Map<Vivora, GamePanel> panelesVivoras;
    JPanel mainPanel;
    CardLayout cardLayout;
    DefaultListModel<Vivora> listModel;

    public GameFrame() {
        setTitle("Juego de Vívora");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelesVivoras = new HashMap<>();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

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
                    cardLayout.show(mainPanel, selectedVivora.toString());
                }
            }
        });
        JScrollPane snakeListScrollPane = new JScrollPane(snakeList);
        sidePanel.add(snakeListScrollPane, BorderLayout.CENTER);

        // Add the side panel to the main panel
        add(mainPanel, BorderLayout.CENTER);
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
        GamePanel primerPanel = new GamePanel(primeraVivora);
        panelesVivoras.put(primeraVivora, primerPanel);
        mainPanel.add(primerPanel, nombre);
        listModel.addElement(primeraVivora);
        cardLayout.show(mainPanel, nombre);
        primeraVivora.start();
    }

    public void agregarVivora() {
        String nombre = "Vívora " + (panelesVivoras.size() + 1);
        Vivora nuevaVivora = new Vivora(false, 200, 200, nombre);
        GamePanel nuevoPanel = new GamePanel(nuevaVivora);
        panelesVivoras.put(nuevaVivora, nuevoPanel);
        mainPanel.add(nuevoPanel, nombre);
        listModel.addElement(nuevaVivora);
        cardLayout.show(mainPanel, nombre);
        nuevaVivora.start();
    }

    public void actualizarJuego() {
        for (GamePanel panel : panelesVivoras.values()) {
            panel.actualizarJuego();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}