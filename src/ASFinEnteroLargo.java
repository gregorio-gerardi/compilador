public class ASFinEnteroLargo implements AccionSemantica {

    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("CTE"));
        al.addListaDeTokens(String.format("CTE linteger (linea %1$d",al.getLinea()));
        //evaluo si es mayor de lo permitido
        Long valor = Long.valueOf(al.getBuffer());
        if (valor > Long.MAX_VALUE){
            al.addListaDeErroresLexicos(String.format("Warning cte entera fuera de rango at linea: %1$d",al.getLinea()));
            valor = Long.MAX_VALUE;
        }

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        EntradaTablaSimbolos referencia = null;
        if (al.estaEnTabla(String.valueOf(valor),referencia)){
            //esta en tabla devuelvo referencia
            al.setEntrada(referencia);
        }else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(String.valueOf(valor), "Linteger");
            al.agregarATablaSimbolos(String.valueOf(valor), elementoTS);
            al.setEntrada(elementoTS);
        }
    }
}