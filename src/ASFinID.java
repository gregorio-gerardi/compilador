public class ASFinID implements AccionSemantica {

    @Override
    public void ejecutar(AnalizadorLexico al) {
        //al.incPosition();
        //evaluo si es mayor de lo permitido
        String lexema = al.getBuffer();
        if (lexema.length() > 25) {
            al.setTokenActual(al.ERROR);
            al.addListaDeErroresLexicos(String.format("Error ID demasiado largo en linea: %1$d", al.getLinea()));
        } else {
            //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
            al.setTokenActual(al.getIDforPR("ID"));
            al.addListaDeTokens(String.format("ID (linea %1$d)", al.getLinea()));
            if (al.estaEnTabla(lexema)) {
                //esta en tabla devuelvo referencia
                al.setEntrada(al.getEntrada(lexema));
            } else {
                // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
                EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, EntradaTablaSimbolos.TIPO_DESCONOCIDO);
                al.agregarATablaSimbolos(lexema, elementoTS);
                al.setEntrada(elementoTS);
            }
        }
    }
}
