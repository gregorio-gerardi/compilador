public class ASFinSimbolo implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.addListaDeTokens(String.format("SimboloSimple %2$s (linea %1$d)", al.getLinea(), al.getBuffer()));
        String buffer = al.getBuffer();
        al.setTokenActual(al.getIDforPR(buffer));
    }
}
