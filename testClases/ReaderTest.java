import java.io.IOException;

public class ReaderTest extends Reader {
    public static final String NADA="Nothing";
    @Override
    public int getActualLine() {
        return 0;
    }

    @Override
    public boolean isNotFinal() {
        return true;
    }

    @Override
    public boolean isFinal() {
        return true;
    }

    public ReaderTest() throws IOException {
    super(NADA);
    }

    @Override
    public int getCaracter() {
        return 'A';
    }

    @Override
    public void incLinea() {
    }

    @Override
    public void incPosition() {
    }

    @Override
    public void decPosition() {
    }
}
