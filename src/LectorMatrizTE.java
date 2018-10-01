import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorMatrizTE {
    public static int fila = 15;
    public static int columna = 17;
    private static int[][] mTE = new int[columna][fila];

    public LectorMatrizTE(String path) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader(path));
        int read;
        String sourceCode = new String();
        while ((read = inputReader.read()) != -1) {
            sourceCode += (char) read;
        }
        sourceCode = sourceCode.replace("\r", "\t");
        sourceCode = sourceCode.replace("\n", "");
        String[] separados = sourceCode.split("\t");
        int cont = 0;
        for (int i = 0; i < mTE[0].length; i++) {
            for (int j = 0; j < mTE.length; j++) {
                mTE[j][i] = Integer.valueOf(separados[cont]);
                cont++;
            }
        }
    }

    public static int[][] getMatriz() {
        return mTE;
    }
}
