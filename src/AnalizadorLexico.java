public class AnalizadorLexico {
    public static int ERROR = -1;
    public static int FINAL = 100;
//--------//
    private DefaultCharacterAnalyser analisadorDeChar= new DefaultCharacterAnalyserTest();
    private Reader fuente;
    private String buffer;
    private char c;
    private int estadoActual=0;
    private int[][] mTE = {{0}};//
    private AccionSemantica[][] mAS = {{new ASTest()}};
    public AnalizadorLexico(Reader fuente) {
        this.fuente = fuente;
    }

    public Token getToken() {
        buffer = "";
        estadoActual = 0; //Estado inicial.
        while ((estadoActual != ERROR) && (estadoActual != FINAL) && (fuente.isNotFinal())) {
            c = (char) fuente.getCaracter();
            AccionSemantica aS = mAS[estadoActual][analisadorDeChar.getColumnaSimbolo(c)]; //Accion semantica a realizar [Estado][Simbolo]
            aS.ejecutar(this);// ejecuta la accion. incrementa o no la posicion y carga el buffer, o resetea todo por error, desde metodos del analizador pasado como this;
            estadoActual = mTE[estadoActual][analisadorDeChar.getColumnaSimbolo(c)];
        }
        if (estadoActual==ERROR){

        }

    }
}
