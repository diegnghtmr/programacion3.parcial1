package co.edu.uniquindio.sigecim.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import static co.edu.uniquindio.sigecim.snake.GamePanel.*;

public class Vivora extends Thread {
    Nodo cabeza;
    String direccion;
    boolean controladaPorUsuario;
    Color color;
    boolean viva = true; // Indicates if the snake is alive
    int velocidad = 500; // Initial speed in milliseconds
    String nombre;
    static final int SIZE = 100; // Tamaño de la serpiente

    public Vivora(boolean controladaPorUsuario, int startX, int startY, String nombre) {
        this.controladaPorUsuario = controladaPorUsuario;
        this.cabeza = new Nodo(startX, startY);
        this.direccion = "derecha"; // Initial direction
        this.color = generarColorAleatorio();
        this.nombre = nombre;
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
                newY -= SIZE;
                break;
            case "abajo":
                newY += SIZE;
                break;
            case "izquierda":
                newX -= SIZE;
                break;
            case "derecha":
                newX += SIZE;
                break;
        }

        // Check for boundaries
        if (newX < MARGIN_LEFT || newY < MARGIN_TOP || newX >= MARGIN_RIGHT || newY >= MARGIN_BOTTOM) {
            viva = false;
            return;
        }

        cabeza.mover(newX, newY);
    }

    // Method to change direction automatically
    private void cambiarDireccionAutomatica() {
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
            g.fillOval(current.x, current.y, SIZE, SIZE); // Adjusted size
            g.setColor(Color.BLACK);
            g.drawOval(current.x, current.y, SIZE, SIZE); // Outline for better visibility
            current = current.siguienteNodo;
            g.setColor(color);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}