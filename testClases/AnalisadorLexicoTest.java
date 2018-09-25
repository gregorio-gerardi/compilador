import java.io.IOException;
import java.util.ArrayList;

public class AnalisadorLexicoTest extends AnalizadorLexico {
    public AnalisadorLexicoTest() throws IOException {
        super(new ReaderTest());
        tokensDePrueba=new ArrayList<Integer>();
        etsPrueba=new ArrayList<>();
        tokensDePrueba.add(getIDforPR("ID"));
        etsPrueba.add(new EntradaTablaSimbolos("idDePrueba",null));
    }

    private ArrayList<Integer> tokensDePrueba;
    private ArrayList<EntradaTablaSimbolos> etsPrueba;

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
    public EntradaTablaSimbolos getEntrada(String id) {
        return etsPrueba.remove(0);
    }

    public int getToken(ArrayList<String> listaDeTokens, ArrayList<String> listaDeErroresLexicos) {
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
