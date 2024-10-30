package co.edu.uniquindio.sigecim.snake;

public class Nodo {
    int x, y;
    int prevX, prevY;
    Nodo siguienteNodo = null;

    public Nodo(int x, int y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
    }

    // Método para mover el nodo recursivamente
    public void mover(int newX, int newY) {
        prevX = x;
        prevY = y;

        x = newX;
        y = newY;

        if (siguienteNodo != null) {
            siguienteNodo.mover(prevX, prevY);
        }
    }

    // Método para obtener la cola
    public Nodo getCola() {
        if (siguienteNodo == null) {
            return this;
        } else {
            return siguienteNodo.getCola();
        }
    }
}


