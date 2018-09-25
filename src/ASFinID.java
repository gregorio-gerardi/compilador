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
            if (al.estaEnTabla(id)) {
                //esta en tabla devuelvo referencia
                al.setEntrada(al.getEntrada(id));
            } else {
                // no esta en tabla, agrega a TS y tambien setea entrada en getToken para darle al parser la referencia
                EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(id, "Identificador");
                al.agregarATablaSimbolos(id, elementoTS);
                al.setEntrada(elementoTS);
            }
        }
    }
}
