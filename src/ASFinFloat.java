public class ASFinFloat implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        //al.incPosition();

        //convierto a compatible con java
        String[] separados = new String[2];
        String buffer = al.getBuffer();
        double single = 0;
        if (String.valueOf(buffer).contains("F")) {
            separados = String.valueOf(buffer).split("(?<=F)");
            single = Double.valueOf(separados[0]);
            if (separados[1] != null) {
                single = single * Math.pow(10, Double.valueOf(separados[1]));
            }
        } else {
            buffer = String.valueOf(buffer) + "f";
            single = Double.valueOf(buffer);
        }
        //me fijo el rango
        if (single > AnalizadorLexico.MAX_FLOAT) {
            al.addListaDeErroresLexicos(String.format("Warning cte float mayor al rango at linea: %1$d", al.getLinea()));
            single = AnalizadorLexico.MAX_FLOAT;
        } else if (single < AnalizadorLexico.MIN_FLOAT) {
            al.addListaDeErroresLexicos(String.format("Warning cte float menor al rango at linea: %1$d", al.getLinea()));
            single = AnalizadorLexico.MIN_FLOAT;
        }

        al.addListaDeTokens(String.format("CTE Float %2$s (linea %1$d)", al.getLinea(), String.valueOf(single)));

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        al.setTokenActual(al.getIDforPR("CTE"));
        if (al.estaEnTabla(String.valueOf(single))) {
            //esta en tabla devuelvo referencia
            al.setEntrada(al.getEntrada(String.valueOf(single)));
        } else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(String.valueOf(single), EntradaTablaSimbolos.SINGLE);
            al.agregarATablaSimbolos(String.valueOf(single), elementoTS);
            al.setEntrada(elementoTS);
        }
    }
}
