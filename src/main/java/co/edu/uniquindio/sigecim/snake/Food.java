package co.edu.uniquindio.sigecim.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food {
    int x, y;
    Color color;
    static final int SIZE = 20; // Tamaño de la comida

    public Food() {
        Random rand = new Random();
        this.x = rand.nextInt(40) * SIZE; // Ajustar para que coincida con el tamaño de la serpiente
        this.y = rand.nextInt(30) * SIZE; // Ajustar para que coincida con el tamaño de la serpiente
        this.color = Color.RED;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, SIZE, SIZE);
    }
}