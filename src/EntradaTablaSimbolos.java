public class EntradaTablaSimbolos implements Operando{
    public static final String TIPO_DESCONOCIDO = "Desconocido";
    public static final String STRING = "String";
    public static final String SINGLE = "Single";
    public static final String LONG = "Linteger";
    String lexema;
    String tipo;
    String uso;
    boolean mutable;
    boolean puntero;

    public EntradaTablaSimbolos(String lexema, String tipo) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.uso = null;
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

    public boolean isPuntero() {
        return puntero;
    }

    public void setPuntero(boolean puntero) {
        this.puntero = puntero;
    }

}
