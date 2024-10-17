package co.edu.uniquindio.sigecim.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food {
    int x, y;
    Color color;

    public Food() {
        Random rand = new Random();
        this.x = rand.nextInt(8) * 100;
        this.y = rand.nextInt(6) * 100;
        this.color = Color.RED;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 100, 100);
    }
}

