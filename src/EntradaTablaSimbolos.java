public class EntradaTablaSimbolos implements Operando {
    public static final String TIPO_DESCONOCIDO = "Desconocido";
    public static final String STRING = "String";
    public static final String SINGLE = "Single";
    public static final String LONG = "Linteger";
    String lexema;
    String tipo;
    int cantUsos;
    String uso;
    Boolean mutable;
    private boolean declarada;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public EntradaTablaSimbolos(String lexema, String tipo) {
        this.lexema = lexema;
        this.tipo = tipo;
        uso = null;
        mutable = null;
        cantUsos=0;
        declarada=false;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public boolean isMutable() {
        return mutable;
    }

    public void setMutable(boolean mutable) {
        this.mutable = mutable;
    }

    public void incUsos() {
        cantUsos++;
    }

    public void decUsos() {
        cantUsos--;
    }

    public boolean isDeclarada() {
        return declarada;
    }

    public void setDeclarada(boolean b) {
        declarada=b;
    }
}
