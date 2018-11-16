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
        addTablaDeSimbolos(12);
        codeToTxt();
    }

    private static void addTablaDeSimbolos(int desde) {
        int indexV = desde;
        int indexS = desde;
        for (EntradaTablaSimbolos e : AnalizadorLexico.tablaDeSimbolos.values()) {
            if (e.getLexema().substring(0, 1).equals("_") || e.getLexema().substring(0, 1).equals("@")) {  //Variables
                code.add(indexV, e.getLexema() + " DD ?");
                indexS++;
            } else if (e.getTipo().equals("String")) {                                              //Strings
                code.add(indexS, e.getLexema().replace(" ","") + " db \"" + e.getLexema() + "\", 0");
            } else {
                if (e.tipo.equals(EntradaTablaSimbolos.LONG)) {
                    code.add(desde, "mem@cte" + e.getLexema().replace(".", "") + " DD " + e.getLexema());                 //Constantes
                    indexV++;
                    indexS++;
                }else if
                    (e.tipo.equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add(desde, "mem@cte" + e.getLexema().replace(".", "") + " DD " + e.getLexema());                 //Constantes
                    indexV++;
                    indexS++;
                }
            }
        }
    }

    private static void codeToTxt() throws IOException {
        File file = new File("assembler.asm");
        Files.deleteIfExists(file.toPath());
        PrintWriter pw = new PrintWriter(file);
        for (int i = 0; i < code.size(); i++) {
            if (!code.get(i).equals("\n"))
                pw.println(code.get(i));
            else
                pw.print(code.get(i));
        }
        pw.close();
    }

    private static void addFinalCode() {
        code.add(String.valueOf('\n'));
        code.add("@LABEL_OVF_PRODUCTO:");
        code.add("invoke MessageBox, NULL, addr mensaje_overflow_producto,addr mensaje_error, MB_OK");
        code.add("JMP @LABEL_END");
        code.add("@LABEL_OVF_SUMA:");
        code.add("invoke MessageBox, NULL, addr mensaje_overflow_suma, addr mensaje_error, MB_OK");
        code.add("JMP @LABEL_END");
        code.add("@LABEL_DIV_CERO:");
        code.add("invoke MessageBox, NULL, addr mensaje_division_cero, addr mensaje_error, MB_OK");
        code.add("@LABEL_END:");
        code.add("invoke MessageBox, NULL, addr mensaje_fin, addr mensaje_fin, MB_OK");
        code.add("invoke ExitProcess, 0");
        code.add("end start");
    }

    private static void addData() {
        code.add(".386");
        code.add(".MODEL flat, stdcall");
        code.add("option casemap :none");
        code.add(".STACK 200h");
        code.add("include \\masm32\\include\\windows.inc");
        code.add("include \\masm32\\include\\kernel32.inc");
        code.add("include \\masm32\\include\\user32.inc");
        code.add("include \\masm32\\include\\masm32.inc");
        code.add("includelib \\masm32\\lib\\kernel32.lib");
        code.add("includelib \\masm32\\lib\\user32.lib");
        code.add("includelib \\masm32\\lib\\masm32.lib");
        code.add(".DATA");
        code.add("max_double DD "+ AnalizadorLexico.MAX_FLOAT);
        code.add("min_double DD "+ AnalizadorLexico.MIN_FLOAT);
        code.add("mensaje_error db \"Error en tiempo de ejecucion!\",0");
        code.add("mensaje_fin db \"Fin de la ejecucion!\",0");
        code.add("mensaje_overflow_producto db \"ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN PRODUCTO\", 0");
        code.add("mensaje_overflow_suma db \"ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN SUMA\", 0");
        code.add("mensaje_division_cero db \"ERROR EN TIEMPO DE EJECUCION --> DIVISION POR CERO DETECTADA\", 0");
    }

    private static String getResult(Terceto t) {
        t.setAuxResultado("@aux" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice(t));
        return ("@aux" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice(t));
    }

    private static void addCode(ArrayList<Terceto> listaTercetos) {
        code.add(String.valueOf('\n'));
        code.add(".code");
        code.add("start:");
        Terceto t;
        ArrayList<Integer> labelsIf = new ArrayList<>();
        ArrayList<Integer> labelsWhile = new ArrayList<>();

        for (int i = 0; i < listaTercetos.size(); i++) {
            t = listaTercetos.get(i);

            //guardo labelTerceto en caso de que tenga vaya ser utilizado por una condicion
            for (int j = 0; j < labelsIf.size(); j++) {
                if (labelsIf.get(j) == i){
                    code.add("@labelTerceto" + i+":");
                    labelsIf.remove(j);
                }
            }

            //guardo labelsWhile para while
            for (int j = 0; j < labelsWhile.size(); j++) {
                if (labelsWhile.get(j) == i){
                    code.add("JMP @labelTercetoWhile" + t.getOperando2ForAssembler());
                    labelsWhile.remove(j);
                }
            }


            //--ARITMETICOS--
            //suma
            if (t.getOperador().equals("+")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    //todo CHEQUEAR ESTABA DISNTO EL FILMINA DE ANTO Y DE MARCELA, IMPROVISE
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler());
                    code.add("FADD");
                    code.add("FSTP " + getResult(t));
                    code.add("FLD max_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JO @LABEL_OVF_SUMA");
                    code.add("FLD min_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JO @LABEL_OVF_SUMA"); //todo consultar
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX, " + t.getOperando1ForAssembler());
                    code.add("ADD EAX, " + t.getOperando2ForAssembler());
                    code.add("JO @LABEL_OVF_SUMA");
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }

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
                    //TODO CHEQUEAR
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler()); //tener en cuenta que hace ST(1) * ST
                    code.add("FMUL");
                    code.add("FSTP " + getResult(t));
                    code.add("FLD max_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JO @LABEL_OVF_PRODUCTO");
                    code.add("FLD min_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JO @LABEL_OVF_PRODUCTO");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX," + t.getOperando1ForAssembler());
                    code.add("IMUL EAX," + t.getOperando2ForAssembler());
                    code.add("JO @LABEL_OVF_PRODUCTO");
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }

            }
            //division
            if (t.getOperador().equals("/")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    //chequeo division cero TODO VERIFICAR
                    code.add("FLD " + t.getOperando2ForAssembler());
                    code.add("FTST"); //COMPARO ST CON CERO
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JO @LABEL_DIC_CERO");
                    //
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler()); //tener en cuenta que hace ST(1) / ST
                    code.add("FDIV");
                    code.add("FSTP " + getResult(t));
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }
                if (t.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    //chequeo division cero
                    code.add("MOV EAX," + t.getOperando2ForAssembler());
                    code.add("SUB EAX, 0");
                    code.add("JZ @LABEL_DIV_CERO");
                    //
                    code.add("MOV EAX," + t.getOperando1ForAssembler());
                    code.add("IDIV " + t.getOperando2ForAssembler());
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }

            }
            //asignacion
            if (t.getOperador().equals("ASIGNACION")) {
                if (t.getOperando1().getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX ,"+t.getOperando2ForAssembler());
                    code.add("MOV " + t.getOperando1ForAssembler() + ", EAX");
                } else {
                    if (t.getOperando2() instanceof EntradaTablaSimbolos && !(((EntradaTablaSimbolos) t.getOperando2()).getLexema().startsWith("_", 0))) {
                        code.add("FLD mem@cte" + t.getOperando2ForAssembler().replace(".", ""));
                        code.add("FSTP " + t.getOperando1ForAssembler());
                    } else {
                        code.add("FLD " + t.getOperando2ForAssembler());
                        code.add("FSTP " + t.getOperando1ForAssembler());
                    }
                }
            }
            //print
            if (t.getOperador().equals("PRINT")) {
                code.add("invoke MessageBox, NULL, addr " + t.getOperando1ForAssembler().replace(" ","") + ", addr " + t.getOperando1ForAssembler().replace(" ","") + ", MB_OK");
            }

            //comparaciones
            if ((t.getOperador().equals("<")) || (t.getOperador().equals(">")) || (t.getOperador().equals("COMP_MENOR_IGUAL")) || (t.getOperador().equals("COMP_MAYOR_IGUAL")) || (t.getOperador().equals("="))) {
                //guardo labelsWhile para while
                Terceto tnext = listaTercetos.get(i + 1);
                Terceto retroceso = listaTercetos.get(Integer.valueOf(tnext.getOperando2ForAssembler())-1);
                if (retroceso.getOperador().equals("WHILE")) {
                    code.add("@labelTercetoWhile"+i+":");
                    labelsWhile.add((((TercetoDestino) tnext.getOperando2()).destino)-1);
                }
                //
                code.add("MOV EAX, " + t.getOperando1ForAssembler());
                code.add("CMP EAX, " + t.getOperando2ForAssembler());
            }

            //comparacion menor
            if (t.getOperador().equals("<")) {
                Terceto tnext = listaTercetos.get(i + 1);
                code.add("JGL @labelTerceto" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                labelsIf.add((((TercetoDestino) tnext.getOperando2()).destino));
            }
            //comparacion mayor
            if (t.getOperador().equals(">")) {
                Terceto tnext = listaTercetos.get(i + 1);
                code.add("JLE @labelTerceto" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                labelsIf.add((((TercetoDestino) tnext.getOperando2()).destino));
                i++;
            }
            //comparacion menor igual
            if (t.getOperador().equals("COMP_MENOR_IGUAL")) {
                Terceto tnext = listaTercetos.get(i + 1);
                code.add("JG @labelTerceto" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                labelsIf.add((((TercetoDestino) tnext.getOperando2()).destino));
                i++;
            }
            //comparacion mayor igual
            if (t.getOperador().equals("COMP_MAYOR_IGUAL")) {
                Terceto tnext = listaTercetos.get(i + 1);
                code.add("JL @labelTerceto" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                labelsIf.add((((TercetoDestino) tnext.getOperando2()).destino));
                i++;
            }
            //comparacion igual
            if (t.getOperador().equals("=")) {
                Terceto tnext = listaTercetos.get(i + 1);
                code.add("JNE @labelTerceto" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                labelsIf.add((((TercetoDestino) tnext.getOperando2()).destino));
                i++;
            }
            //comparacion distinto
            if (t.getOperador().equals("!=")) {
                Terceto tnext = listaTercetos.get(i + 1);
                code.add("JE @labelTerceto" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                labelsIf.add((((TercetoDestino) tnext.getOperando2()).destino));
                i++;
            }
        }
        code.add("JMP @LABEL_END");
    }
}

/*
linteger
simple
referenciaAMemoria
desconocido --> asignacion, sentencia control, print
*/

