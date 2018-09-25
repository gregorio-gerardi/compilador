import java.io.IOException;

public class AnalisadorLexicoTest extends AnalizadorLexico {
    public AnalisadorLexicoTest() throws IOException {
        super(new ReaderTest());
    }

    @Override
    public Reader getFuente() {
        return super.getFuente();
    }

    @Override
    public String getBuffer() {
        return "";
    }

    @Override
    public void setBuffer(String buffer) {
    }

    @Override
    public int getTokenActual() {
    return 1;
    }

    @Override
    public void setTokenActual(int tokenActual) {
    }

    @Override
    public char getC() {
        return 'A';
    }

    @Override
    public void setC(char c) {
    }

    @Override
    public int getEstadoActual() {
    return 1;
    }

    @Override
    public void setEstadoActual(int estadoActual) {
    }

    @Override
    public int getToken(EntradaTablaSimbolos entradaTablaSimbolos) {
        return 1;
    }

    @Override
    public void incPosition() {
    }

    @Override
    public int getIDforPR(String buffer) {
    return 1;
    }

    @Override
    public void incLinea() {
    }
}
