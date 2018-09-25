public class ASFinPalabra implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR(al.getBuffer()));
    }
}
