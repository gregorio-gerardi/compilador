import java.util.ArrayList;
import java.util.HashMap;

public class AnalizadorLexico {

    public static int ERROR = 256;
    public static int FINAL = 100;
    public static int FINALARCHIVO = 1000;
    public static double MAX_LONG = 2147483648.; //contemplando el maximo valor negativo posible
    public static double MAX_FLOAT = 340282347000000000000000000000000000000.;
    public static double MIN_FLOAT = 0.0000000000000000000000000000000000000117549435;
    public HashMap<String, EntradaTablaSimbolos> tablaDeSimbolos = new HashMap<>();
    //--------//
    private DefaultCharacterAnalyser analizadorDeChar = new DefaultCharacterAnalyser();
    private Reader reader;
    private String buffer = "";
    private int tokenActual = -1;
    private char c;
    private int estadoActual = 0;
    private int[][] mTE = LectorMatrizTE.getMatriz();
    private AccionSemantica[][] mAS = LectorMatrizAS.getMatriz();
    private EntradaTablaSimbolos entrada;
    private ArrayList<String> listaPalabrasReservadas = new ArrayList<>();
    private ArrayList<String> listaDeErroresLexicos = new ArrayList<>();
    private ArrayList<String> listaDeTokens = new ArrayList<>();

    public AnalizadorLexico(Reader reader) {
        this.reader = reader;
        cargarListaPR();
    }

    public boolean esPalabraReservada(String pr) {
        return listaPalabrasReservadas.contains(pr);
    }

    public ArrayList<String> getListaDeErroresLexicos() {
        return listaDeErroresLexicos;
    }

    public ArrayList<String> getListaDeTokens() {
        return listaDeTokens;
    }

    public void addListaDeErroresLexicos(String error) {
        this.listaDeErroresLexicos.add(error);
    }

    public void addListaDeTokens(String token) {
        this.listaDeTokens.add(token);
    }

    public void agregarATablaSimbolos(EntradaTablaSimbolos entrada) {
        tablaDeSimbolos.put(entrada.getLexema(), entrada);
    }

    public boolean estaEnTabla(String lexema) {
        return !(tablaDeSimbolos.get(lexema) == null);
    }

    private void cargarListaPR() {
        listaPalabrasReservadas.add("if");
        listaPalabrasReservadas.add("else");
        listaPalabrasReservadas.add("end_if");
        listaPalabrasReservadas.add("print");
        listaPalabrasReservadas.add("while");
        listaPalabrasReservadas.add("linteger");
        listaPalabrasReservadas.add("single");
        listaPalabrasReservadas.add("let");
        listaPalabrasReservadas.add("mut");
    }

    public int getLinea() {
        return reader.getActualLine();
    }

    public Reader getReader() {
        return reader;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public void setTokenActual(int tokenActual) {
        this.tokenActual = tokenActual;
    }

    public char getChar() {
        return c;
    }

    //GET TOKEN DEVUELVE -1 EN CASO DE UN TOKEN ERRONEO
    public int getToken() {
        entrada = null;
        tokenActual = ERROR;
        buffer = "";
        estadoActual = 0; //Estado inicial.
        while ((estadoActual != ERROR) && (estadoActual != FINAL) && (estadoActual != FINALARCHIVO)) {
            c = (char) reader.getCaracter();
            AccionSemantica aS = mAS[analizadorDeChar.getColumnaSimbolo(c)][estadoActual]; //Accion semantica a realizar [Estado][Simbolo]
            aS.ejecutar(this);// ejecuta la accion. incrementa o no la posicion y carga el buffer, o resetea todo por error, desde metodos del analizador pasado como this;
            estadoActual = mTE[analizadorDeChar.getColumnaSimbolo(c)][estadoActual];
        }
        return estadoActual==FINALARCHIVO ? -1 : tokenActual;
    }


    public void incPosition() {
        this.getReader().incPosition();
    }

    public int getIDforPR(String Token) {
        //vinculado a las variables estaticas publicas de YACC
        switch (Token) {
            case "YYERRCODE":
                return Parser.YYERRCODE;
            case "ID":
                return Parser.ID;
            case "ASIGNACION":
                return Parser.ASIGNACION;
            case "COMP_MAYOR_IGUAL":
                return Parser.COMP_MAYOR_IGUAL;
            case "COMP_MENOR_IGUAL":
                return Parser.COMP_MENOR_IGUAL;
            case "COMP_DIFERENTE":
                return Parser.COMP_DIFERENTE;
            case "IF":
                return Parser.IF;
            case "ELSE":
                return Parser.ELSE;
            case "END_IF":
                return Parser.END_IF;
            case "PRINT":
                return Parser.PRINT;
            case "LINTEGER":
                return Parser.LINTEGER;
            case "SINGLE":
                return Parser.SINGLE;
            case "WHILE":
                return Parser.WHILE;
            case "LET":
                return Parser.LET;
            case "MUT":
                return Parser.MUT;
            case "CADENA":
                return Parser.CADENA;
            case "CTE":
                return Parser.CTE;
            //para simbolos simples devuelvo el ascii ; , ( ) { } [ ] * / &
            case ";":
                return (int) ';';
            case "+":
                return (int) '+';
            case "-":
                return (int) '-';
            case "<":
                return (int) '<';
            case ">":
                return (int) '>';
            case ",":
                return (int) ',';
            case "(":
                return (int) '(';
            case ")":
                return (int) ')';
            case "{":
                return (int) '{';
            case "}":
                return (int) '}';
            case "[":
                return (int) '[';
            case "]":
                return (int) ']';
            case "*":
                return (int) '*';
            case "/":
                return (int) '/';
            case "&":
                return (int) '&';
            case "=":
                return (int) '=';
        }
        return -1;//palabra u operador reservado no valido
    }

    public void incLinea() {
        this.reader.incLinea();
    }

    public void setEntrada(EntradaTablaSimbolos elementoTS) {
        entrada = elementoTS;
    }

    public EntradaTablaSimbolos getEntrada(String id) {
        return tablaDeSimbolos.get(id);
    }

    public EntradaTablaSimbolos getEntradaTablaSimbolo() {
        return entrada;
    }

    public HashMap<String, EntradaTablaSimbolos> getTablaDeSimbolos() {
        return tablaDeSimbolos;
    }
}
