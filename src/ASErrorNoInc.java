public class ASErrorNoInc implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setTokenActual(AnalizadorLexico.ERROR);
        al.addListaDeErroresLexicos(String.format("Error lexico en linea: %1$d",al.getLinea()));
    }
}
