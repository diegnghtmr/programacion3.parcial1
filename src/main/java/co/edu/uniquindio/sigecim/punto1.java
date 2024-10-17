package co.edu.uniquindio.sigecim;

public class punto1 {

    String[][] laberinto =
            {
                    {"E", "", "T", "P", "", "", "", "T"},
                    {"T", "", "", "", "", "P", "", "T"},
                    {"", "", "T", "P", "", "", "", "T"},
                    {"", "", "", "P", "", "P", "", ""},
                    {"", "T", "", "", "", "T", "T", ""},
                    {"", "", "P", "T", "", "", "", ""},
                    {"T", "", "P", "", "", "P", "T", ""},
                    {"", "", "", "P", "", "", "", ""},
                    {"", "T", "", "", "", "P", "", "S"}
            };

    int salidasEncontradas = 0;
    int peligrosEvitados = 0;

    public static void main(String[] args) {
        punto1 m = new punto1();
        m.moverRobot(0, 0);
        System.out.println("Salidas encontradas: " + m.salidasEncontradas);
        System.out.println("Peligros evitados: " + m.peligrosEvitados);
    }

    public boolean moverRobot(int i, int j) {
        if (i < 0 || j < 0 || i >= laberinto.length || j >= laberinto[0].length) {
            return false;
        }

        if (laberinto[i][j].equals("S")) {
            salidasEncontradas++;
            System.out.println("Solución");
            imprimirLaberinto(0, 0);
            System.out.println(); // Para que se vea mejor
            return false; // Lo que me dijo en asesoria, creo
        }

        if (laberinto[i][j].equals("T")) {
            peligrosEvitados++;
        }

        if (!laberinto[i][j].equals("") && !laberinto[i][j].equals("E") && !laberinto[i][j].equals("F")) {
            return false;
        }

        laberinto[i][j] = "+";

        if (moverRobot(i, j + 1)) { // Derecha
            return true;
        }
        if (moverRobot(i + 1, j)) { // Abajo
            return true;
        }
        if (moverRobot(i, j - 1)) { // Izquierda
            return true;
        }
        if (moverRobot(i - 1, j)) { // Arriba
            return true;
        }

        laberinto[i][j] = "F";
        return false;
    }

    public void imprimirLaberinto(int i, int j) {
        if (i >= laberinto.length) {
            return;
        }
        if (j >= laberinto[0].length) {
            System.out.println();
            imprimirLaberinto(i + 1, 0);
            return;
        }
        System.out.print(laberinto[i][j] + " ");
        imprimirLaberinto(i, j + 1);
    }

}