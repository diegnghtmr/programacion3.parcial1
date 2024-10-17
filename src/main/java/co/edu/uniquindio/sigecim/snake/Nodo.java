package co.edu.uniquindio.sigecim.snake;

public class Nodo {
    int x, y;
    Nodo siguienteNodo = null;

    public Nodo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Method to move the node recursively
    public void mover(int newX, int newY) {
        int tempX = this.x;
        int tempY = this.y;

        this.x = newX;
        this.y = newY;

        if (siguienteNodo != null) {
            siguienteNodo.mover(tempX, tempY);
        }
    }

    // Method to get the tail node recursively
    public Nodo getCola() {
        if (siguienteNodo == null) {
            return this;
        } else {
            return siguienteNodo.getCola();
        }
    }
}

