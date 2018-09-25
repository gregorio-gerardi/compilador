import java.util.ArrayList;
import java.util.HashMap;

public class AnalizadorLexico {

    public static int ERROR = -1;
    public static int FINAL = 100;
    //--------//
    private DefaultCharacterAnalyser analisadorDeChar = new DefaultCharacterAnalyser();
    private Reader reader;
    private String buffer = "";
    private int tokenActual = -1;
    private char c;
    private int estadoActual = 0;
    private int[][] mTE = {{0}};//
    private AccionSemantica[][] mAS = LectorMatrizAS.getMatriz();
    public HashMap<String, EntradaTablaSimbolos> tablaDeSimbolos = new HashMap<>();
    private EntradaTablaSimbolos entrada;

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

    private ArrayList<String> listaDeErroresLexicos;
    private ArrayList<String> listaDeTokens;


    public void agregarATablaSimbolos(String lexema, EntradaTablaSimbolos entrada) {
        tablaDeSimbolos.put(entrada.getLexema(), entrada);
    }

    public boolean estaEnTabla(String lexema) {
        //todo verificar que si no esta en hash devuelve null
        if (!(tablaDeSimbolos.get(lexema) == null)){
            return true;
        }
        return false;
    }

    public AnalizadorLexico(Reader reader) {
        this.reader = reader;
        cargarListaPR();
    }

    private void cargarListaPR() {

    }

    public int getLinea(){
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

    public int getTokenActual() {
        return tokenActual;
    }

    public void setTokenActual(int tokenActual) {
        this.tokenActual = tokenActual;
    }

    public char getChar() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(int estadoActual) {
        this.estadoActual = estadoActual;
    }

    //GET TOKEN DEVUELVE -1 EN CASO DE UN TOKEN ERRONEO
    public int getToken() {
        entrada = null;
        buffer = "";
        estadoActual = 0; //Estado inicial.
        while ((estadoActual != ERROR) && (estadoActual != FINAL) && (reader.isNotFinal())) {
            c = (char) reader.getCaracter();
            AccionSemantica aS = mAS[estadoActual][analisadorDeChar.getColumnaSimbolo(c)]; //Accion semantica a realizar [Estado][Simbolo]
            aS.ejecutar(this);// ejecuta la accion. incrementa o no la posicion y carga el buffer, o resetea todo por error, desde metodos del analizador pasado como this;
            estadoActual = mTE[estadoActual][analisadorDeChar.getColumnaSimbolo(c)];
        }
        return tokenActual;
    }


    public void incPosition() {
        this.getReader().incPosition();
    }

    public int getIDforPR(String Token) {
        if (Token.length() == 1) {
            return (int) Token.charAt(0);
        } else {
            //vinculado a las variables estaticas publicas de YACC
            switch (Token) {
                case "YYERRCODE":
                    return 256;
                case "ID":
                    return 257;
                case "ASIGNACION":
                    return 258;
                case "COMP_MAYOR_IGUAL":
                    return 259;
                case "COMP_MENOR_IGUAL":
                    return 260;
                case "COMP_MAYOR":
                    return 261;
                case "COMP_MENOR":
                    return 262;
                case "COMP_IGUAL":
                    return 263;
                case "COMP_DIFERENTE":
                    return 264;
                case "IF":
                    return 265;
                case "ELSE":
                    return 266;
                case "END_IF":
                    return 267;
                case "PRINT":
                    return 268;
                case "LINTEGER":
                    return 269;
                case "SINGLE":
                    return 270;
                case "WHILE":
                    return 271;
                case "LET":
                    return 272;
                case "MUT":
                    return 273;
                case "CADENA":
                    return 274;
                case "CTE":
                    return 275;
                //para simbolos simples devuelvo el ascii ; , ( ) { } [ ] * / &
                case ";":
                    return (int) ';';
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
            }
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
}
