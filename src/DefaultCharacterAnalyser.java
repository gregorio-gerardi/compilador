public class DefaultCharacterAnalyser {
    public boolean esMayus(char c) {
        return c >= 65 && c <= 90;
    }

    public boolean esMinus(char c) {
        return c >= 97 && c <= 122;
    }

    public boolean esLetra(char c) {
        return (esMayus(c) || esMinus(c));
    }


    public boolean esDigito(char c) {
        return (c >= 48 && c <= 57);
    }

    public boolean isEOL(char c) {
        return (c == '\n' || c == '\r');
    }

    public int getColumnaSimbolo(char c) {

        if (esDigito(c)) {
            return 5;
        }
        if (esLetra(c) && c != 'F' && c != 'l') {
            return 8;
        }

        switch (c) {
            case '#': {
                return 0;
            }
            case '<': {
                return 1;
            }
            case '>': {
                return 1;
            }
            case ':': {
                return 2;
            }
            case '!': {
                return 2;
            }
            case '\'': {
                return 3;
            }
            case '_': {
                return 4;
            }
            case '.': {
                return 6;
            }
            case '=': {
                return 7;
            }
            case 'F': {
                return 9;
            }
            case 'l': {
                return 10;
            }
            case '+': {
                return 11;
            }
            case '-': {
                return 11;
            }
            case ';': {
                return 12;
            }
            case ',': {
                return 12;
            }
            case '(': {
                return 12;
            }
            case ')': {
                return 12;
            }
            case '{': {
                return 12;
            }
            case '}': {
                return 12;
            }
            case '[': {
                return 12;
            }
            case ']': {
                return 12;
            }
            case '*': {
                return 12;
            }
            case '/': {
                return 12;
            }
            case '&': {
                return 12;
            }
            case ' ': {
                return 14;
            }
            case '\t': {
                return 14;
            }
            case '\r': {
                return 14;
            }
            case '\n': {
                return 15;
            }
            case (char)06: {
                return 16;
            }
        }
        return 13; //Otros
    }
}
