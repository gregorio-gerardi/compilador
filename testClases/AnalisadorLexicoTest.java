import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnalisadorLexicoTest extends AnalizadorLexico {
    public AnalisadorLexicoTest() throws IOException {
        super(new ReaderTest());
        tokensDePrueba=new ArrayList<Integer>();
    }

    private ArrayList<Integer> tokensDePrueba;

    public String getBuffer() {
        return "";
    }

    public void setBuffer(String buffer) {
    }

    public int getTokenActual() {
    return 1;
    }

    public void setTokenActual(int tokenActual) {
    }

    public char getC() {
        return 'A';
    }

    public void setC(char c) {
    }


    public int getEstadoActual() {
    return 1;
    }


    public void setEstadoActual(int estadoActual) {
    }

    @Override
    public int getToken(EntradaTablaSimbolos entradaTablaSimbolos) {
        return tokensDePrueba.remove(0);
    }

    public void incPosition() {
    }

    public int getIDforPR() {
    return 1;
    }

    public void incLinea() {
    }
}
