public class ASFinSimboloIncPos implements AccionSemantica{

    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setBuffer(al.getBuffer()+ al.getChar());
        al.incPosition();
        al.setTokenActual(al.getIDforPR(al.getBuffer()));
        al.addListaDeTokens(String.format("SimboloSimple %2$s (linea %1$d",al.getLinea(),al.getBuffer()));
    }
}
