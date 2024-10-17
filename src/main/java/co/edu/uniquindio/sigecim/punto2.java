package co.edu.uniquindio.sigecim;

public class punto2 {
    static String[][] matriz =
            {
                    {"vacio", "carro", "baul", "perro", "vacio"},
                    {"colombia", "casa", "moto", "caza", "colombia"},
                    {"llanta", "reir", "archivo", "silla", "llanta"},
                    {"cocina", "ola", "ave", "freir", "cocina"}
            };

    static String[][] matrizInversa =
            {
                    {"", "", "", "", ""},
                    {"", "", "", "", ""},
                    {"", "", "", "", ""},
                    {"", "", "", "", ""}
            };

    public static void main(String[] args) {
        recorrerMatriz(matriz, matriz.length - 1, matriz[0].length - 1, matrizInversa);
        imprimirMatrizInversa(0, 0);
    }

    private static void recorrerMatriz(String[][] matriz, int i, int j, String[][] matrizInversa) {
        if (i >= 0) {
            if (j >= 0) {
                matrizInversa[i][j] = invertirPalabra(matriz[i][j], matriz[i][j].length() - 1);
                recorrerMatriz(matriz, i, j - 1, matrizInversa);
            } else {
                recorrerMatriz(matriz, i - 1, matriz[0].length - 1, matrizInversa);
            }
        }
    }

    public static String invertirPalabra(String palabra, int longitud){
        if(longitud==0){
            return palabra.charAt(longitud)+"";
        }else{
            return palabra.charAt(longitud) + (invertirPalabra(palabra, longitud-1));
        }

    }

    public static void imprimirMatrizInversa(int i, int j) {
        if (i >= matrizInversa.length) {
            return;
        }
        if (j >= matrizInversa[0].length) {
            System.out.println();
            imprimirMatrizInversa(i + 1, 0);
            return;
        }
        System.out.print(matrizInversa[i][j] + " ");
        imprimirMatrizInversa(i, j + 1);
    }
}