import java.io.IOException;
import java.util.ArrayList;

public class MainPrueba {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Reader lectorFuente = new ReaderTest();
        Parser parser=new Parser(lectorFuente);
        parser.run();
        ArrayList<String> reglas=parser.getListaDeReglas();
        for(String s: reglas){
            System.out.println(s);
        }
    }
}
