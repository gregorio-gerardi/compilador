public class ASFinString implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("CADENA"));

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        if (al.estaEnTabla(al.getBuffer())){
            //esta en tabla devuelvo referencia
            al.setEntrada(al.getEntrada(al.getBuffer()));
        }else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(al.getBuffer(), EntradaTablaSimbolos.STRING);
            al.agregarATablaSimbolos(al.getBuffer(), elementoTS);
            al.setEntrada(elementoTS);
        }
    }
}
