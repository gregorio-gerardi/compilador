public class ASFinString implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("CADENA"));
        String cadena=al.getBuffer().substring(1,al.getBuffer().length());
        al.addListaDeTokens(String.format("Cadena Caracteres: \"%2$s\" (linea %1$d)", al.getLinea(), cadena));

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        if (al.estaEnTabla(cadena)) {
            //esta en tabla devuelvo referencia
            al.setEntrada(al.getEntrada(cadena));
        } else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(cadena, EntradaTablaSimbolos.STRING);
            al.agregarATablaSimbolos(cadena, elementoTS);
            al.setEntrada(elementoTS);
        }
    }
}
