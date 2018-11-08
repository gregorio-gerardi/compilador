import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Reader lectorFuente = new Reader(args[0]);
        LectorMatrizTE lectorME = new LectorMatrizTE("MTE.txt");
        LectorMatrizAS lectorAS = new LectorMatrizAS("MAS.txt");
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
            pw.println("");
            if (p.getListaDeErroresLexicos().isEmpty())
                pw.println("No se encontraron errores lexicos.");
            else
                for (String e : p.getListaDeErroresLexicos()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");
            pw.println("TOKENS DEL ANALIZADOR LEXICO:");
            System.out.println("TOKENS DEL ANALIZADOR LEXICO:");
            pw.println("");
            if (p.getListaDeTokens().isEmpty())
                pw.println("No se encontraron tokens lexicos.");
            else
                for (String e : p.getListaDeTokens()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");
            pw.println("ERRORES SINTACTICOS:");
            System.out.println("ERRORES SINTACTICOS:");
            pw.println("");
            if (p.getListaDeErroresSintacticos().isEmpty())
                pw.println("No se detectaron errores sintacticos.");
            else
                for (String e : p.getListaDeErroresSintacticos()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");
            pw.println("REGLAS SINTACTICAS:");
            System.out.println("REGLAS SINTACTICAS:");
            pw.println("");
            if (p.getListaDeReglas().isEmpty())
                pw.println("No se detectaron reglas sintacticas.");
            else
                for (String e : p.getListaDeReglas()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");
            pw.println("CONTENIDO DE LA TABLA DE SIMBOLOS:");
            System.out.println("CONTENIDO DE LA TABLA DE SIMBOLOS:");
            pw.println("");
            if (p.getTablaDeSimbolos().isEmpty())
                pw.println("Tabla de simbolos vacia");
            else
                for (Map.Entry<String, EntradaTablaSimbolos> e : p.getTablaDeSimbolos().entrySet()) {
                    System.out.println(e.getKey() + " --> " + e.getValue().getTipo());
                    pw.println(e.getKey() + " --> " + e.getValue().getTipo());
                }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("ERRORES SEMANTICOS:");
            System.out.println("ERRORES SEMANTICOS:");
            pw.println("");
            if (p.getListaDeErroresSemanticos().isEmpty())
                pw.println("No se detectaron errores sintacticos.");
            else
                for (String e : p.getListaDeErroresSemanticos()) {
                    System.out.println(e);
                    pw.println(e);
                }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.println("");

            pw.println("LISTA DE TERCETOS GENERADOS:");
            System.out.println("LISTA DE TERCETOS GENERADOS:");
            pw.println("");
            ArrayList<Terceto> tercetos = ListaTercetos.getInstanceOfListaDeTercetos().getTercetos();
            if (tercetos.isEmpty())
                pw.println("Lista de tercetos vacia");
            else {
                int i = 0;
                for (Terceto t : tercetos) {
                    System.out.println(i + " - ( " + t.getOperador() + " , " + printOperador(t.getOperando1()) + " , " + printOperador(t.getOperando2()) + " ) ");
                    pw.println(i + " - ( " + t.getOperador() + " , " + printOperador(t.getOperando1()) + " , " + printOperador(t.getOperando2()) + " ) ");
                    i++;
                }
            }
            pw.println("--------------------");
            System.out.println("--------------------");
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String printOperador(Operando operando) {
        if (operando == null) return "-";
        if (operando instanceof Terceto)
            return ("[" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice((Terceto) operando) + "]");
        if (operando instanceof TercetoDestino) return (((TercetoDestino)operando).destino.toString());
        return ((EntradaTablaSimbolos) operando).getLexema();
    }
}


