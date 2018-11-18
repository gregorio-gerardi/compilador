public class ASFinEnteroLargo implements AccionSemantica {

    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("CTE"));
        al.addListaDeTokens(String.format("CTE linteger (linea %1$d)", al.getLinea()));
        //evaluo si es mayor de lo permitido
        Long valor = Long.valueOf(al.getBuffer().substring(0, al.getBuffer().length() - 1));
        if (valor > AnalizadorLexico.MAX_LONG) {
            al.addListaDeErroresLexicos(String.format("Warning cte entera fuera de rango at linea: %1$d", al.getLinea()));
            valor = AnalizadorLexico.MAX_LONG;
        }

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        long val = Double.valueOf(valor).longValue();
        if (al.estaEnTabla(String.valueOf(val))) {
            //esta en tabla devuelvo referencia
            al.setEntrada(al.getEntrada(String.valueOf(val)));
        } else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(String.valueOf(val), "Linteger");
            al.agregarATablaSimbolos(elementoTS);
            al.setEntrada(elementoTS);
        }
    }
}
