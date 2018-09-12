public class DefaultCharacterAnalyser {
   public boolean esMayus(char c){
        if (c >= 65 && c <= 90){
            return true;
        }
        return false;
    }
    public boolean esMinus(char c){
        if (c >= 97 && c <= 122){
            return true;
        }
        return false;
    }

    public boolean esDigito(char c){
        return (c >= 48  && c <= 57);
    }

    public boolean isEOL(char c){
        return (c=='\n' || c=='\r');
    }

    public int getColumnaSimbolo(char c) {
       //todo valores random

        if (c == 'i')
            return 6;
        if (c == 'u')
            return 7;
        if (c == 'N')
            return 25;
        if (c == 'C')
            return 26;
        if (esMinus(c))
            return 0;
        if (esMayus(c))
            return 1;
        if (esDigito(c))
            return 2;

        switch (c) {
            case '@' : {return 24;}
            case '_' : {return 5;}
            case '+' : {return 9;}
            case '-' : {return 8;}
            case '*' : {return 10;}
            case '/' : {return 22;}
            case '#' : {return 23;}
            case '=' : {return 17;}
            case '<' : {return 20;}
            case '>' : {return 21;}
            case '(' : {return 11;}
            case ')' : {return 12;}
            case ',' : {return 15;}
            case ';' : {return 16;}
            case ':' : {return 18;}
            case '!' : {return 19;}
            case '\'' : {return 28;}
            case ' ' : {return 4;}
            case '\t' : {return 3;}
            case '\n' : {return 27;}
            case '{' : {return 13;}
            case '}' : {return 14;}
        }
        return 29; //Otros
    }

}
