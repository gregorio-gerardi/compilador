import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private String sourceCode="";
    private int position = 1;
    public static int actualLine = 1;
    BufferedReader inputReader;

    public int getActualLine() {
        return actualLine;
    }

    public boolean isNotFinal() {
        return position < sourceCode.length();
    }

    public boolean isFinal() {
        return position == sourceCode.length();
    }

    public Reader(String path) throws IOException {
        BufferedReader inputReader = new BufferedReader(new FileReader(path));
        int read ;
        while ((read=inputReader.read())!=-1){
            sourceCode+=(char)read;}
    }

    public int getCaracter() {
        return sourceCode.charAt(position);
    }
    public void incLinea(){
        actualLine++;
    }

    public void incPosition() {
        position++;
    }
    public void decPosition() {
        position--;
    }
}
