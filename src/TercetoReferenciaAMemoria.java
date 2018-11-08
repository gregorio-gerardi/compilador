public class TercetoReferenciaAMemoria extends Terceto implements ReferenciaAMemoria{
    String tipoResultanteApuntado;

    public TercetoReferenciaAMemoria(String s, Operando operando, Operando operando1) {
        super(s,operando,operando);
    }
    public void setTipoResultanteApuntado(String s){
        tipoResultanteApuntado=s;
    }

    @Override
    public String getReferenciadoTipo() {
        return tipoResultanteApuntado;
    }
}
