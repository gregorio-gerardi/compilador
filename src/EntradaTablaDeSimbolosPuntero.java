public class EntradaTablaDeSimbolosPuntero extends EntradaTablaSimbolos implements ReferenciaAMemoria{

    private String tipoApuntable = "Desconocido";

    public EntradaTablaDeSimbolosPuntero(String lexema, String tipo, EntradaTablaSimbolos apuntado) {
        super(lexema, tipo);
        this.apuntado=apuntado;
    }

    private EntradaTablaSimbolos apuntado;

    public void setApuntado(EntradaTablaSimbolos apuntado) {
        this.apuntado = apuntado;
    }

    public EntradaTablaSimbolos getApuntado() {
        return apuntado;
    }

    @Override
    public String getReferenciadoTipo() {
        return getApuntado()==null? tipoApuntable: getApuntado().getTipo();
    }

    public void setTipoApuntable(String s){
        tipoApuntable=s;
    }
}
