import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Vector;

public class GeneradorAssembler {
    private static Vector<String> code = new Vector<>();
    private static String result;

    public GeneradorAssembler() {
    }

    public static void generarAssembler(ArrayList<Terceto> listaTercetos) throws IOException {
        addData();
        addCode(listaTercetos);
        addFinalCode();
        codeToTxt();
    }

    private static void codeToTxt() throws IOException {
        File file = new File("assembler.txt");
        Files.deleteIfExists(file.toPath());
        PrintWriter pw = new PrintWriter(file);
        for (int i = 0; i <code.size() ; i++) {
            pw.println(code.get(i));
        }
        pw.close();
    }

    private static void addFinalCode() {
        code.add("@LABEL_OVF_PRODUCTO:");
        code.add("invoke MessageBox, NULL, addr mensaje_overflow_producto, addr mensaje_overflow_producto, MB_OK");
        code.add("JMP @LABEL_END");
        code.add("@LABEL_OVF_SUMA:");
        code.add("invoke MessageBox, NULL, addr mensaje_overflow_suma, addr mensaje_overflow_suma, MB_OK");
        code.add("JMP @LABEL_END");
        code.add("@LABEL_DIV_CERO:");
        code.add("invoke MessageBox, NULL, addr mensaje_division_cero, addr mensaje_division_cero, MB_OK");
        code.add("@LABEL_END:");
        code.add("invoke ExitProcess, 0");
        code.add("end start");
    }

    private static void addData() {
        code.add(".MODEL small");
        code.add(".STACK 200h");
        code.add(".DATA");
        code.add("mensaje_overflow_producto db \"ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN PRODUCTO\", 0");
        code.add("mensaje_overflow_suma db \"ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN SUMA\", 0");
    }

    private static String getResult(Terceto t) {
        t.setAuxResultado("@aux" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice(t));
        return ("@aux" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice(t));
    }

    private static void addCode(ArrayList<Terceto> listaTercetos) {
        code.add(".code");
        code.add("start:");
        for (int i = 0; i < listaTercetos.size(); i++) {
            Terceto t = listaTercetos.get(i);
            //--ARITMETICOS--
            //suma
            if (t.getOperador().equals("+")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler());
                    code.add("FADD");
                    code.add("FISTP " + getResult(t));
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX, " + t.getOperando1ForAssembler());
                    code.add("ADD EAX, " + t.getOperando2ForAssembler());
                    code.add("JO @LABEL_OVF_SUMA");
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                //chequeo overflow
            }
            //resta
            if (t.getOperador().equals("-")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler()); //tener en cuenta que hace ST(1) - ST
                    code.add("FSUB");
                    code.add("FSTP " + getResult(t));
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX, " + t.getOperando1ForAssembler());
                    code.add("SUB EAX, " + t.getOperando2ForAssembler());
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
            }
            //multiplicacion
            if (t.getOperador().equals("*")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler()); //tener en cuenta que hace ST(1) * ST
                    code.add("FMUL");
                    code.add("FSTP " + getResult(t));
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX ," + t.getOperando1ForAssembler());
                    code.add("IMUL EAX ," + t.getOperando2ForAssembler());
                    code.add("JO @LABEL_OVF_PRODUCTO");
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
            }
            //division
            if (t.getOperador().equals("/")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler()); //tener en cuenta que hace ST(1) / ST
                    code.add("FDIV");
                    code.add("FSTP " + getResult(t));
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX ," + t.getOperando1ForAssembler());
                    code.add("IDIV " + t.getOperando2ForAssembler());
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
            }
            //asignacion
            if (t.getOperador().equals("ASIGNACION")) {
                if (t.getOperando1().getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV ," + t.getOperando1() + "," + t.getOperando2ForAssembler());
                }
                else {
                    code.add("FLD "+ t.getOperando2ForAssembler());
                    code.add("FSTP " + t.getOperando1());
                }
            }

        }
    }
}

/*
linteger
simple
referenciaAMemoria
desconocido --> asignacion, sentencia control, print
*/

