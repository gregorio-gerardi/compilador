public class ASFinFloat implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("SINGLE"));
<<<<<<< HEAD

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

        //TODO liquidar referencia tabla simbolos

=======
        //evaluo si es mayor de lo permitido
        //todo ver que pasa si es menor
        Float valor = Float.valueOf(al.getBuffer());
        if (valor > Float.MAX_VALUE){
            valor = Float.MAX_VALUE;
        }
        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        EntradaTablaSimbolos referencia = null;
        if (al.estaEnTabla(String.valueOf(valor),referencia)){
            //esta en tabla devuelvo referencia
            al.setEntrada(referencia);
        }else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(String.valueOf(valor), "Flotante");
            al.agregarATablaSimbolos(String.valueOf(valor), elementoTS);
            al.setEntrada(elementoTS);
        }*/
>>>>>>> gregorio
    }
}
