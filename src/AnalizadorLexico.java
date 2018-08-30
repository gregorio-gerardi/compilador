public class AnalizadorLexico {
    private Token tokenActual;
    private fuente
    public Token obtenerToken(){
        Token tokenActual = null;
        int estado = 0; //Estado inicial.
        while ((estado != -1) && (f.noEsFinal())){
            char c = f.getCaracter();
            //System.out.println("El caracter que vino es " + c);
            AccionSemantica aS = mAS[estado][f.getNroSimbolo(c)]; //Acci�n semantica a realizar [Estado][Simbolo]
            if (aS.ejecutar(c, this) == 0){ //0 consume.
                f.incrPosicion();
            }
            if (!resetear){
                estado = mTE [estado][f.getNroSimbolo(c)]; //Dame el nuevo estado
                //System.out.println("El nuevo Estado es " + estado);
                if (estado == -1){
                    lTokens.add(String.format("%-20s%s",tokenActual.getId(),tokenActual.getLexema()));
                    return tokenActual;
                }
            }
            else { //resetea buffer despues de haber reconocido un error
                estado = 0;
                buffer = "";
                resetear = false;
            }
        }
        return null;
    }
''
}
