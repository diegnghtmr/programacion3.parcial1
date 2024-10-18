package co.edu.uniquindio.sigecim.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import static co.edu.uniquindio.sigecim.snake.GamePanel.*;

public class Food {
    int x, y;
    Color color;
    static final int SIZE = 20; // Tamaño de la comida

    public Food() {
        Random rand = new Random();
        this.x = MARGIN_LEFT + rand.nextInt((MARGIN_RIGHT - MARGIN_LEFT) / SIZE) * SIZE;
        this.y = MARGIN_TOP + rand.nextInt((MARGIN_BOTTOM - MARGIN_TOP) / SIZE) * SIZE;
        this.color = Color.RED;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, SIZE, SIZE);
    }
}