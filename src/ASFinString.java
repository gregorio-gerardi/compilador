/*
public class ASFinString implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("CADENA"));

        //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
        EntradaTablaSimbolos referencia = null;
        if (al.estaEnTabla(al.getBuffer(),referencia)){
            //esta en tabla devuelvo referencia
            al.setEntrada(referencia);
        }else {
            // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(al.getBuffer(), "String");
            al.agregarATablaSimbolos(al.getBuffer(), elementoTS);
            al.setEntrada(elementoTS);
        }
    }
}
*/
