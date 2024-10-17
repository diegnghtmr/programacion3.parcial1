package co.edu.uniquindio.sigecim.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Vivora extends Thread {
    Nodo cabeza;
    String direccion;
    boolean controladaPorUsuario;
    Color color;
    boolean viva = true; // Indicates if the snake is alive
    int velocidad = 500; // Initial speed in milliseconds

    public Vivora(boolean controladaPorUsuario, int startX, int startY) {
        this.controladaPorUsuario = controladaPorUsuario;
        this.cabeza = new Nodo(startX, startY);
        this.direccion = "derecha"; // Initial direction
        this.color = generarColorAleatorio();
    }

    // Generates a random color for the snake
    private Color generarColorAleatorio() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public void run() {
        while (viva) {
            moverSnake();
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to move the snake
    public void moverSnake() {
        int newX = cabeza.x;
        int newY = cabeza.y;

        if (!controladaPorUsuario) {
            cambiarDireccionAutomatica();
        }

        switch (direccion) {
            case "arriba":
                newY -= 100;
                break;
            case "abajo":
                newY += 100;
                break;
            case "izquierda":
                newX -= 100;
                break;
            case "derecha":
                newX += 100;
                break;
        }

        // Check for boundaries
        if (newX < 0 || newY < 0 || newX >= 800 || newY >= 600) {
            viva = false;
            return;
        }

        // Check for collisions with other snakes (to be implemented)

        cabeza.mover(newX, newY);
    }


    // Method to change direction automatically
    private void cambiarDireccionAutomatica() {
        // Implement logic to change direction automatically without reversing
        String[] direcciones = {"arriba", "abajo", "izquierda", "derecha"};
        String direccionAnterior = this.direccion;
        Random rand = new Random();

        do {
            this.direccion = direcciones[rand.nextInt(4)];
        } while ((direccionAnterior.equals("arriba") && this.direccion.equals("abajo")) ||
                (direccionAnterior.equals("abajo") && this.direccion.equals("arriba")) ||
                (direccionAnterior.equals("izquierda") && this.direccion.equals("derecha")) ||
                (direccionAnterior.equals("derecha") && this.direccion.equals("izquierda")));
    }

    // Method to add a new node when the snake eats
    public void agregarNodo() {
        Nodo cola = cabeza.getCola();
        Nodo nuevoNodo = new Nodo(cola.x, cola.y);
        cola.siguienteNodo = nuevoNodo;
        // Increase speed slightly
        if (velocidad > 100) {
            velocidad -= 20;
        }
    }

    // Method to draw the snake
    public void draw(Graphics g) {
        Nodo current = cabeza;
        g.setColor(color);
        while (current != null) {
            g.fillOval(current.x, current.y, 100, 100);
            current = current.siguienteNodo;
        }
    }
}

