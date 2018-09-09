import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorMatriz {
    public static int fila = 15;
    public static int columna = 16;
    private String sourceCode;
    private int[][] mTE = new int[columna][fila];

    public LectorMatriz(String path) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader(path));
        int read;
        while ((read = inputReader.read()) != -1) {
            sourceCode += read;
        }
        String[] separados = sourceCode.split("\t");
        int cont = 0;
        for (int i = 0; i < mTE.length; i++) {
            for (int j = 0; j < mTE[0].length; j++) {
                if (!separados[cont].equals("\n") && !separados[cont].equals("\r")) {
                    mTE[i][j] = Integer.valueOf(separados[cont]);
                }
                cont++;
            }
        }
    }

    public int[][] getMatriz() {
        return mTE;
    }
}
