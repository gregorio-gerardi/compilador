public class AnalizadorLexico {
    private Reader fuente;
    private String buffer;
    private int[][] mTE = {//Matriz de transici�n de estados.
            //todo valores random

            //0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29
            { 1, 2, 3, 4, 5, 2, 1, 1,10, 9, 9, 9, 9, 9, 9, 9, 9,15,11,14,13,13,16, 0, 0, 0, 0, 0,23, 0},//
            { 0, 1, 1,-1,-1, 1, 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 1, 1,-1,-1,-1},//
            { 100, 0, 0, 0, 0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            { -1, 0, 8, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            { 4, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            { -1, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            {-1,-1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            { 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            {-1,-1,	8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            { 0, 0,	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            { 0, 0,	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,-1,-1,-1,-1,-1,-1},//
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,20,18,18,18,18,18},//
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,19,18,18,18,18,18,18},//
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18, 0,18,18,18,18,18,18,18},//
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,21,18,18,18,18},//
            {18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,22,18,18,18},//
            {22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,-1,22,22},//
            {24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24, 0, 0,24},//
            {24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24, 0,-1,24}};//

    private AccionSemantica[][] mAS = {//matriz de acciones semanticas
            //todo valores random

            {as1,as1,eci,asc,asc,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,eci,eci,eci,eci,asc,asc,eci},
            {as2,as2,as2,as3,as3,as2,as2,as2,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as2,as2,as3,as3,as3},
            {eeui,eeui,eeui,eeui,eeui,eeui,as2,as2,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui},
            {eend,eend,as2,eend,eend,eend,eend,eend,as2,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend},
            {eei,eei,eei,eei,eei,eei,as2,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei},
            {eed,eed,as2,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed},
            {as5,as5,as2,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5},
            {eed,eed,as2,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed},
            {as4,as4,as2,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4},
            {as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
            {as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as2,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
            {eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,as2,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig},
            {as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
            {as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as2,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
            {eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,as2,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig},
            {as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
            {as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as2,as6,as6,as6,as6,as6,as6},
            {asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,asc,asc,asc,asc,asc},
            {asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc},
            {asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc},
            {asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,asc,asc,asc,asc},
            {asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,asc,asc,asc},
            {asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,as7,asc,asc},
            {as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,ecl,ecl,as1},
            {as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,ecl,as8,as2}};

    public Token getToken(){
        //come gato
        buffer="";
        int estado = 0; //Estado inicial.
        while ((estado != -1) && (fuente.isNotFinal())){
            char c = (char)fuente.getCaracter();
            //System.out.println("El caracter que vino es " + c);
            AccionSemantica aS = mAS[estado][DefaultCharacterAnalyser.getColumnaSimbolo(c)]; //Acci�n semantica a realizar [Estado][Simbolo]
            if (aS.ejecutar(c,this) == 0){ //0 consume.
                fuente.incrPosicion();
            }
            if (!resetear){
                estado = mTE [estado][fuente.getNroSimbolo(c)]; //Dame el nuevo estado
                //System.out.println("El nuevo Estado es " + estado);
                if (estado == -1){
                    lTokens.add(String.format("%-20s%s",tokenActual.getId(),tokenActual.getLexema()));
                    return tokenActual;
                }
            }
            else { //resetea buffer despues de haber reconocido un error
                estado = 0;
                resetear = false;
            }
        }
        return null;
    }

    public AnalizadorLexico(Reader fuente) {
        this.fuente = fuente;
    }
}
