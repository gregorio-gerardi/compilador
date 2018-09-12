
import java.io.IOException;

/*si se usa windows para hacer el texto, un salto de linea es /n/r por el carriage return. No sé si en linux es igual, o solo /n.
* tab -> \t -> 9
* space -> ' ' -> 32
* new line -> \n -> 10
* carriage return -> \r -> 13*/

public class Main {
    public static void main(String[] args) throws IOException {
        Reader lectorFuente = new Reader(args[0]);
        LectorMatrizTE lectorME = new LectorMatrizTE(args[1]);
        LectorMatrizAS lectorAS = new LectorMatrizAS(args[2]);
        AnalizadorLexico analizadorLexico=new AnalizadorLexico(lector);
    }
}


