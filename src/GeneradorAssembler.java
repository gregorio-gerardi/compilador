import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class GeneradorAssembler {
    private static Vector<String> code;
    private static String result;
    private static int id;

    public GeneradorAssembler() {
        id = 0;
    }

    public static void generarAssembler(ArrayList<Terceto> listaTercetos){
        addData();
        addCode(listaTercetos);
    }

    private static void addData(){
        code.add(".MODEL small");
        code.add(".STACK 200h");
        code.add(".DATA");
    }

    private static String getResult(Terceto t){
        t.setAuxResultado("@aux"+id);
        return ("@aux" + id++);
    }

    private static void addCode(ArrayList<Terceto> listaTercetos){
        for (int i = 0; i < listaTercetos.size(); i++) {
            Terceto t = listaTercetos.get(i);
            //--ARITMETICOS--
            //suma
            if (t.getOperador().equals('+')){
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)){

                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)){
                    code.add("MOV EAX," + t.getOperando1());
                    code.add("ADD EAX," + t.getOperando2());
                    code.add("MOV "+ getResult(t) + ",EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(),t.getTipo()));
                }

                //todo chequeo de overflow
            }
            //resta
            if (t.getOperador().equals('-')){
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)){

                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)){
                    code.add("MOV EAX," + t.getOperando1());
                    code.add("SUB EAX," + t.getOperando2());
                    code.add("MOV "+ getResult(t) + ",EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(),t.getTipo()));
                }
            }
            //multiplicacion
            if (t.getOperador().equals('-')){
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)){

                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)){

                }
            }
            //todo chequeo overflow
        }
    }
}

/*
linteger
simple
referenciaAMemoria
desconocido --> asignacion, sentencia control, print
*/

