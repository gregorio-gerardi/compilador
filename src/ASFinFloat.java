public class ASFinFloat implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        //aumento contador posicion
        al.incPosition();

        //convierto a compatible con java
        String[] separados = new String[2];
        String buffer = al.getBuffer();
        double single=0;
        if (String.valueOf(buffer).contains("F")) {
            separados = String.valueOf(buffer).split("(?<=F)");
            single = Double.valueOf(separados[0]);
            if (separados[1] != null) {
                single = single * Math.pow(10, Double.valueOf(separados[1]));
            }
        }else {
            buffer = String.valueOf(buffer)+"f";
            single = Double.valueOf(buffer);
        }
        //me fijo el rango
        if (single>Float.MAX_VALUE) {
            single = Float.MAX_VALUE;
        } else if (single<Float.MIN_VALUE){
            single = Float.MIN_VALUE;
        }

        al.addListaDeTokens(String.format("CTE Float %2$s (linea %1$d)",al.getLinea(),String.valueOf(single)));

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        al.setTokenActual(al.getIDforPR("CTE"));
        al.addListaDeTokens(String.format("CTE (linea %1$d",al.getLinea()));
        if (al.estaEnTabla(String.valueOf(single))) {
            //esta en tabla devuelvo referencia
            al.setEntrada(al.getEntrada(String.valueOf(single)));
        } else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(String.valueOf(single), "Single");
            al.agregarATablaSimbolos(String.valueOf(single), elementoTS);
            al.setEntrada(elementoTS);
        }

    }
}
