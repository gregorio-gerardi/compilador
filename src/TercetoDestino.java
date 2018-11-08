public class TercetoDestino implements Operando {
    public Integer destino;
    public TercetoDestino(int i) {
        destino=i;
    }

    @Override
    public String getTipo() {
        return "Terceto Destino";
    }
}
