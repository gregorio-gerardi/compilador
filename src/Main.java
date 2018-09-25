
import java.io.IOException;
import java.util.ArrayList;

/*si se usa windows para hacer el texto, un salto de linea es /n/r por el carriage return. No sé si en linux es igual, o solo /n.
* tab -> \t -> 9
* space -> ' ' -> 32
* new line -> \n -> 10
* carriage return -> \r -> 13*/

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Reader lectorFuente = new Reader(args[0]);
        LectorMatrizTE lectorME = new LectorMatrizTE(args[1]);
        LectorMatrizAS lectorAS = new LectorMatrizAS(args[2]);
        Parser parser=new Parser(lectorFuente);
        parser.run();
        ArrayList<String> erroresLexicos=parser.getListaDeErroresLexicos();
        ArrayList<String> erroresSintacticos=parser.getListaDeErroresSintacticos();
        ArrayList<String> reglas=parser.getListaDeReglas();
        ArrayList<String> tokens=parser.getListaDeTokens();
    }
}


