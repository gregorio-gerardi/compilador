public class ASFinPalabra implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        //al.incPosition();
        if (al.esPalabraReservada(al.getBuffer())) {
            al.setTokenActual(al.getIDforPR(al.getBuffer()));
            al.addListaDeTokens(String.format("Palabra Reservada %2$s (linea %1$d)",al.getLinea(),al.getBuffer()));
        } else {
            al.setTokenActual(AnalizadorLexico.ERROR);
            al.addListaDeErroresLexicos(String.format("Error lexico en linea: %1$d",al.getLinea()));
        }
    }
}
