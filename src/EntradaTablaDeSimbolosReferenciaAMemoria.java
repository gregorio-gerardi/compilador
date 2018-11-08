public class EntradaTablaDeSimbolosReferenciaAMemoria extends EntradaTablaSimbolos implements ReferenciaAMemoria{

    public EntradaTablaDeSimbolosReferenciaAMemoria(String lexema, String tipo, EntradaTablaSimbolos apuntado) {
        super(lexema, tipo);
        this.referenciado=referenciado;
    }

    private EntradaTablaSimbolos referenciado;

    public void setReferenciado(EntradaTablaSimbolos referenciado) {
        this.referenciado = referenciado;
    }

    public EntradaTablaSimbolos getReferenciado() {
        return referenciado;
    }

    @Override
    public String getReferenciadoTipo() {
        return getReferenciado().getTipo();
    }
}
