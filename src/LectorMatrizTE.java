import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorMatrizTE {
    public static int fila = 15;
    public static int columna = 16;
    private String sourceCode = new String();
    private static int[][] mTE = new int[columna][fila];

    public LectorMatrizTE(String path) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader(path));
        int read;
        while ((read = inputReader.read()) != -1) {
            sourceCode += (char) read;
        }
        sourceCode=sourceCode.replace("\r","\t");
        sourceCode=sourceCode.replace("\n","");
        String[] separados = sourceCode.split("\t");
        int cont = 0;
        for (int i = 0; i < mTE[0].length; i++) {
            for (int j = 0; j < mTE.length; j++) {
                if (!separados[cont].equals("\n") && !separados[cont].equals("\r")) {
                    mTE[j][i] = Integer.valueOf(separados[cont]);
                }
                cont++;
            }
        }
    }

    public static int[][] getMatriz() {
        return mTE;
    }
}

//unit test para clase con print
/*
public class MainPrueba {
    public static void main(String[] args) throws IOException {

        int[][] mTE = new int[16][15];
        System.out.println(mTE.length);
        System.out.println(mTE[0].length);
        LectorMatrizTE lector = new LectorMatrizTE(args[0]);
        mTE = lector.getMatriz();
        for (int i = 0; i < mTE[0].length; i++) {
            System.out.println();
            for (int j = 0; j < mTE.length; j++) {
                System.out.print(mTE[j][i] + "    ");
            }
        }
    }
}*/
