public class ASFinID implements AccionSemantica {

    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        //evaluo si es mayor de lo permitido
        String id = al.getBuffer();
        if (id.length() > 25) {
            al.setTokenActual(-1);
        } else {
            //Si no esta en la tabla de simbolos lo agrego, sino devuelvo referencia
            al.setTokenActual(al.getIDforPR("ID"));
            EntradasTablaSimbolos referencia = null;
            if (al.estaEnTabla(id, referencia)) {
                //esta en tabla devuelvo referencia
                al.setEntrada(referencia);
            } else {
                // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
                EntradasTablaSimbolos elementoTS = new EntradasTablaSimbolos(id, "Identificador");
                al.agregarATablaSimbolos(id, elementoTS);
                al.setEntrada(elementoTS);
            }
        }
    }
}
