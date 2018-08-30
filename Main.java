import java.io.FileReader;
import java.io.IOException;
/*si se usa windows para hacer el texto, un salto de linea es /n/r por el carriage return. No sé si en linux es igual, o solo /n.
* tab -> \t -> 9
* space -> ' ' -> 32
* new line -> \n -> 10
* carriage return -> \r -> 13
* */
public class Main {
    public static void main(String[] args) throws IOException {
        FileReader inputStream = null;
        try {
            inputStream = new FileReader(args[0]);
            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.print(c);
                System.out.print('-');
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}


