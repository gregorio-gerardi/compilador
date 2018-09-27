import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

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
        Parser parser = new Parser(lectorFuente);
        parser.run();
        mostrarMensajes(parser, "salida");
    }

    private static void mostrarMensajes(Parser p, String nombre) {
        try {
            File file = new File(nombre + "_mensajes.txt");
            Files.deleteIfExists(file.toPath());
            PrintWriter pw = new PrintWriter(file);
            pw.println("ERRORES DEL ANALIZADOR LEXICO:");
            System.out.println("ERRORES DEL ANALIZADOR LEXICO:");
            pw.println("------------------------------");
            System.out.println("------------------------------");
            pw.println("");
            if (p.getListaDeErroresLexicos().isEmpty())
                pw.println("No se encontraron errores lexicos.");
            else
                for (String e : p.getListaDeErroresLexicos()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("");
            pw.println("TOKENS DEL ANALIZADOR LEXICO:");
            System.out.println("TOKENS DEL ANALIZADOR LEXICO:");
            pw.println("------------------------------");
            System.out.println("------------------------------");
            pw.println("");
            if (p.getListaDeTokens().isEmpty())
                pw.println("No se encontraron tokens lexicos.");
            else
                for (String e : p.getListaDeTokens()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("");
            pw.println("ERRORES SINTACTICOS:");
            System.out.println("ERRORES SINTACTICOS:");
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");
            if (p.getListaDeErroresSintacticos().isEmpty())
                pw.println("No se detectaron errores sintacticos.");
            else
                for (String e : p.getListaDeErroresSintacticos()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("");
            pw.println("REGLAS SINTACTICAS:");
            System.out.println("REGLAS SINTACTICAS:");
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");
            if (p.getListaDeReglas().isEmpty())
                pw.println("No se detectaron reglas sintacticas.");
            else
                for (String e : p.getListaDeReglas()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


