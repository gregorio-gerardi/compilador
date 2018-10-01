import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private static int actualLine = 1;
    private String sourceCode = "";
    private int position = 1;
    private static final char FIN = (char) 06;

    public Reader(String path) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader(path));
        int read;
        while ((read = inputReader.read()) != -1) {
            sourceCode += (char) read;
        }
        sourceCode += FIN;
    }

    public int getActualLine() {
        return actualLine;
    }

    public int getCaracter() {
        return sourceCode.charAt(position - 1);
    }

    public void incLinea() {
        actualLine++;
    }

    public void incPosition() {
        position++;
    }
}
