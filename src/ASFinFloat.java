public class ASFinFloat implements AccionSemantica{
//TODO HACER FLOAT NI LO ARRANQUE
    @Override
    public void ejecutar(AnalizadorLexico al) {
       /* al.incPosition();
        al.setTokenActual(al.getIDforPR("SINGLE"));
        //evaluo si es mayor de lo permitido
        //todo ver que pasa si es menor
        Float valor = Float.valueOf(al.getBuffer());
        if (valor > Float.MAX_VALUE){
            valor = Float.MAX_VALUE;
        }
        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        EntradasTablaSimbolos referencia = null;
        if (al.estaEnTabla(String.valueOf(valor),referencia)){
            //esta en tabla devuelvo referencia
            al.setEntrada(referencia);
        }else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradasTablaSimbolos elementoTS = new EntradasTablaSimbolos(String.valueOf(valor), "Flotante");
            al.agregarATablaSimbolos(String.valueOf(valor), elementoTS);
            al.setEntrada(elementoTS);
        }*/
    }
}
