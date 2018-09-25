public class ASErrorNoInc implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setTokenActual(-1);
        al.addListaDeErroresLexicos(String.format("Error lexico en linea: %1$d",al.getLinea()));
    }
}
