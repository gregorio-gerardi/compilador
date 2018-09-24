import java.io.IOException;

public class AnalisadorLexicoTest extends AnalizadorLexico {
    public AnalisadorLexicoTest() throws IOException {
        super(new ReaderTest());
    }


    public Reader getFuente() {
        return super.getFuente();
    }


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

    public int getToken() {
        return 1;
    }

    public void incPosition() {
    }

    public int getIDforPR() {
    return 1;
    }

    public void incLinea() {
    }
}
