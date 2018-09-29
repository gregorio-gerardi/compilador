public class EntradaTablaSimbolos {
    public static final String TIPO_DESCONOCIDO = "Desconocido";
    public static final String STRING = "String";
    public static final String SINGLE = "Single";
    public static final String LONG = "Linteger";
    String lexema;
    String tipo;

    public EntradaTablaSimbolos(String lexema, String tipo) {
        this.lexema = lexema;
        this.tipo = tipo;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
