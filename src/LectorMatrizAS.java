import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorMatrizAS {
    public static int fila = 15;
    public static int columna = 16;
    private String sourceCode = new String();
    private static AccionSemantica[][] mAS = new AccionSemantica[columna][fila];

    public LectorMatrizAS(String path) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        BufferedReader inputReader = new BufferedReader(new FileReader(path));
        int read;
        while ((read = inputReader.read()) != -1) {
            sourceCode += (char) read;
        }
        sourceCode=sourceCode.replace("\r","\t");
        sourceCode=sourceCode.replace("\n","");
        String[] separados = sourceCode.split("\t");
        int cont = 0;
        String aux = new String();
        for (int i = 0; i < mAS[0].length; i++) {
            for (int j = 0; j < mAS.length; j++) {
                if (!separados[cont].equals("\n") && !separados[cont].equals("\r")) {
                    aux = String.valueOf(separados[cont]);
                    Class temporal = Class.forName("AS"+aux);
                    AccionSemantica asTemp = (AccionSemantica) temporal.newInstance();
                    mAS[j][i] = asTemp;
                }
                cont++;
            }
        }
    }

    public static AccionSemantica[][] getMatriz() {
        return mAS;
    }
/*    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        LectorMatrizAS lectorAS = new LectorMatrizAS("MAS.txt");
        int fila = 15;
        int columna = 16;
        AccionSemantica[][] mAS = new AccionSemantica[columna][fila];
        mAS = lectorAS.getMatriz();
        for (AccionSemantica[] as:mAS
                ) {
            for (AccionSemantica as2:as
                    ) {
                as2.ejecutar(new AnalisadorLexicoTest());
            }
        }
    }*/
}

