import java.util.HashMap;

public class AnalizadorLexico {

    public static int ERROR = -1;
    public static int FINAL = 100;
//--------//
    private DefaultCharacterAnalyser analisadorDeChar= new DefaultCharacterAnalyserTest();
    private Reader fuente;
    private String buffer="";
    private int tokenActual=-1;
    private char c;
    private int estadoActual=0;
    private int[][] mTE = {{0}};//
    private AccionSemantica[][] mAS = {{new ASTest()}};
    private HashMap<String,Integer> listaPalabrasReservadas = new HashMap<>();
    public AnalizadorLexico(Reader fuente) {
        this.fuente = fuente;
        cargarListaPR();
    }

    private void cargarListaPR() {

    }

    public Reader getFuente(){
        return fuente;
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

    public char getC() {
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
        buffer = "";
        estadoActual = 0; //Estado inicial.
        while ((estadoActual != ERROR) && (estadoActual != FINAL) && (fuente.isNotFinal())) {
            c = (char) fuente.getCaracter();
            AccionSemantica aS = mAS[estadoActual][analisadorDeChar.getColumnaSimbolo(c)]; //Accion semantica a realizar [Estado][Simbolo]
            aS.ejecutar(this);// ejecuta la accion. incrementa o no la posicion y carga el buffer, o resetea todo por error, desde metodos del analizador pasado como this;
            estadoActual = mTE[estadoActual][analisadorDeChar.getColumnaSimbolo(c)];
        }
        return tokenActual;
    }

    public void incPosition() {
        this.getFuente().incPosition();
    }

    public int getIDforPR(String buffer) {
        if (buffer.length()==1){
            return (int)buffer.charAt(0);
        }
        else{
            //TODO vincular a las variables estaticas publicas de YACC
        }
        return -1;//palabra u operador reservado no valido
    }

    public void incLinea() {
        this.fuente.incLinea();
    }
}
