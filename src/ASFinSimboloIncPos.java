public class ASFinSimboloIncPos implements AccionSemantica {

    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setBuffer(al.getBuffer() + al.getChar());
        al.addListaDeTokens(String.format("SimboloSimple %2$s (linea %1$d)", al.getLinea(), al.getBuffer()));
        String buffer = al.getBuffer();
        switch (buffer) {
            case ":=":
                al.setTokenActual(al.getIDforPR("ASIGNACION"));
                return;
            case ">=":
                al.setTokenActual(al.getIDforPR("COMP_MAYOR_IGUAL"));
                return;
            case "<=":
                al.setTokenActual(al.getIDforPR("COMP_MENOR_IGUAL"));
                return;
            case "!=":
                al.setTokenActual(al.getIDforPR("COMP_DIFERENTE"));
                return;
        }
        al.setTokenActual(al.getIDforPR(buffer));
    }
}
