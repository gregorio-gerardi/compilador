import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Vector;

public class GeneradorAssembler {
    private static Vector<String> code = new Vector<>();
    private static Integer desdeLinea = 0;

    public GeneradorAssembler() {
    }

    public static void generarAssembler(ArrayList<Terceto> listaTercetos) throws IOException {
        addData();
        addCode(listaTercetos);
        addFinalCode();
        addTablaDeSimbolos(desdeLinea);
        codeToTxt();
    }

    private static void addTablaDeSimbolos(int desde) {
        int indexV = desde;
        int indexS = desde;
        for (EntradaTablaSimbolos e : AnalizadorLexico.tablaDeSimbolos.values()) {
            if (e.getLexema().substring(0, 1).equals("_") || e.getLexema().substring(0, 1).equals("@")) {  //Variables
                if (e.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add(indexV, e.getLexema().replace("-", "neg") + " REAL8 ?");
                    indexS++;
                } else if (e.getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add(indexV, e.getLexema().replace("-", "neg") + " DD ?");
                    indexS++;
                }
            } else if (e.getTipo().equals("String")) {                                              //Strings
                code.add(indexS, e.getLexema().replace(" ", "") + " db \"" + e.getLexema() + "\", 0");
            } else {
                if (e.tipo.equals(EntradaTablaSimbolos.LONG)) {
                    code.add(desde, "mem@cte" + e.getLexema().replace(".", "").replace("-", "neg") + " DD " + e.getLexema());                 //Constantes
                    indexV++;
                    indexS++;
                } else if
                (e.tipo.equals(EntradaTablaSimbolos.SINGLE)) {
                    code.add(desde, "mem@cte" + e.getLexema().replace(".", "@") + " REAL8 " + e.getLexema());                 //Constantes
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
        code.add("FNINIT");
        code.add("invoke ExitProcess, 0");
        code.add("end start");
    }

    private static void addData() {
/*      code.add(".386");
        code.add(".MODEL flat, stdcall");
        code.add("option casemap :none");
        code.add(".STACK 200h");*/
        code.add("include\\masm32\\include\\masm32rt.inc");
        code.add("includelib \\masm32\\lib\\kernel32.lib");
        code.add("includelib \\masm32\\lib\\user32.lib");
        code.add("includelib \\masm32\\lib\\masm32.lib");
        code.add("dll_dllcrt0 PROTO C");
        code.add("printf PROTO C :VARARG");
        code.add(".DATA");
        desdeLinea = code.size();
        code.add("@aux_mem DW ?");
        code.add("max_double REAL8 " + "340282347000000000000000000000000000000.");
        code.add("min_double REAL8 " + "0.0000000000000000000000000000000000000117549435");
        code.add("mensaje_error db \"Error en tiempo de ejecucion!\",0");
        code.add("mensaje_fin db \"Fin de la ejecucion!\",0");
        code.add("mensaje_overflow_producto db \"OVERFLOW DETECTADO EN PRODUCTO\", 0");
        code.add("mensaje_overflow_suma db \"OVERFLOW DETECTADO EN SUMA\", 0");
        code.add("mensaje_division_cero db \"DIVISION POR CERO DETECTADA\", 0");
    }

    private static String getResult(Terceto t) {
        t.setAuxResultado("@aux" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice(t));
        return ("@aux" + ListaTercetos.getInstanceOfListaDeTercetos().getIndice(t));
    }

    private static void addCode(ArrayList<Terceto> listaTercetos) {
        code.add(String.valueOf('\n'));
        code.add(".code");
        code.add("start:");
        code.add("FNINIT");
        Terceto t;
        ArrayList<Integer> labelsCondicional = new ArrayList<>();
        ArrayList<Integer> labelsIncondicional = new ArrayList<>();

        for (int i = 0; i < listaTercetos.size(); i++) {
            t = listaTercetos.get(i);
            //--ARITMETICOS--
            //suma
            if (t.getOperador().equals("+")) {
                if (t.getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    //TODO QUEDA CON JA Y JB NO ANDA LOS JUMP SIGNADOS
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler());
                    code.add("FADD");
                    code.add("FSTP " + getResult(t));
                    code.add("FLD max_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW @aux_mem");
                    code.add("MOV AX, @aux_mem");
                    code.add("SAHF");
                    code.add("JA @LABEL_OVF_SUMA");
                    code.add("FLD min_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JB @LABEL_OVF_SUMA");
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
                    //TODO QUEDA CON JA Y JB NO ANDA LOS JUMP SIGNADOS
                    code.add("FLD " + t.getOperando1ForAssembler());
                    code.add("FLD " + t.getOperando2ForAssembler()); //tener en cuenta que hace ST(1) * ST
                    code.add("FMUL");
                    code.add("FSTP " + getResult(t));
                    code.add("FLD max_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JA @LABEL_OVF_PRODUCTO");
                    code.add("FLD min_double");
                    code.add("FLD " + getResult(t));
                    code.add("FCOM");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JB @LABEL_OVF_PRODUCTO");
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
                    code.add("FTST");
                    code.add("FSTSW AX");
                    code.add("SAHF");
                    code.add("JE @LABEL_DIV_CERO");
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
                    code.add("MOV EDX,0");
                    code.add("MOV EAX, " + t.getOperando1ForAssembler());
                    code.add("MOV EBX, " + t.getOperando2ForAssembler());
                    code.add("IDIV EBX");
                    code.add("MOV " + getResult(t) + ", EAX");
                    AnalizadorLexico.agregarATablaSimbolos(new EntradaTablaSimbolos(t.getAuxResultado(), t.getTipo()));
                }

            }

            //asignacion
            if (t.getOperador().equals("ASIGNACION")) {
                if (t.getOperando1().getTipo().equals(EntradaTablaSimbolos.LONG)) {
                    code.add("MOV EAX ," + t.getOperando2ForAssembler());
                    code.add("MOV " + t.getOperando1ForAssembler() + ", EAX");
                } else if (t.getOperando1().getTipo().equals(EntradaTablaSimbolos.SINGLE)) {
                    if (t.getOperando2() instanceof EntradaTablaSimbolos && !(((EntradaTablaSimbolos) t.getOperando2()).getLexema().startsWith("_", 0))) {
                        code.add("FLD mem@cte" + t.getOperando2ForAssembler().replace(".", "@"));
                        code.add("FSTP " + t.getOperando1ForAssembler());
                    } else {
                        code.add("FLD " + t.getOperando2ForAssembler());
                        code.add("FSTP " + t.getOperando1ForAssembler());
                    }
                }
            }

            //print
            if (t.getOperador().equals("PRINT")) {
                code.add("invoke MessageBox, NULL, addr " + t.getOperando1ForAssembler().replace(" ", "") + ", addr " + t.getOperando1ForAssembler().replace(" ", "") + ", MB_OK");
            }

            //-----------------------------------------Labels Jumps y Comparaciones-----------------------------------//
            //salto incondicional hacia atras
            if (t.getOperador().equals("BI") && Integer.valueOf(t.getOperando2ForAssembler()) < i) {
                code.add("JMP @labelSaltoIncondicional" + t.getOperando2ForAssembler());
            }

            //salto inconcicional hacia adelante
            if (t.getOperador().equals("BI") && Integer.valueOf(t.getOperando2ForAssembler()) > i) {
                code.add("JMP @labelSaltoIncondicional" + t.getOperando2ForAssembler());
                labelsIncondicional.add(Integer.valueOf(t.getOperando2ForAssembler()) - 1);//agrego el anterior al que va a saltar
                //para completar el codigo al finalizar la pasada del terceto por el traductor a assembler, porque sino puede que agregue
                //un numero mas de la cantidad de tercetos que tenemos y nunca rellene el label, esto haria que tenga que chequear que las
                //listas que use no esten vacias por si querdo alguno colgado
            }

            //comparaciones
            if ((t.getOperador().equals("<")) || (t.getOperador().equals(">")) || (t.getOperador().equals("COMP_MENOR_IGUAL")) || (t.getOperador().equals("COMP_MAYOR_IGUAL")) || (t.getOperador().equals("="))) {
                Terceto tnext = listaTercetos.get(i + 1);
                Terceto tretroceso = listaTercetos.get(Integer.valueOf(tnext.getOperando2ForAssembler()) - 1);

                //caso salto incondicional hacia atras (evaluando a futuro)
                if (tretroceso.getOperador().equals("BI") && Integer.valueOf(tretroceso.getOperando2ForAssembler()) == i) {
                    code.add("@labelSaltoIncondicional" + i + ":");
                }

                code.add("MOV EAX, " + t.getOperando1ForAssembler());
                code.add("CMP EAX, " + t.getOperando2ForAssembler());

                //---------------me cada caso de comparador para poner el jump correspondiente-----------------------//
                //------------saltos condicionales-------------//
                labelsCondicional.add(Integer.valueOf(tnext.getOperando2ForAssembler()) - 1);
                //comparacion menor
                if (t.getOperador().equals("<")) {
                    if (tnext.getOperador().equals("BF")) {
                        code.add("JGE @labelSaltoCondicional" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                    }
                }
                //comparacion mayor
                if (t.getOperador().equals(">")) {
                    if (tnext.getOperador().equals("BF")) {
                        code.add("JLE @labelSaltoCondicional" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                    }
                }
                //comparacion menor igual
                if (t.getOperador().equals("COMP_MENOR_IGUAL")) {
                    if (tnext.getOperador().equals("BF")) {
                        code.add("JG @labelSaltoCondicional" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                    }
                }
                //comparacion mayor igual
                if (t.getOperador().equals("COMP_MAYOR_IGUAL")) {
                    if (tnext.getOperador().equals("BF")) {
                        code.add("JL @labelSaltoCondicional" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                    }
                }
                //comparacion igual
                if (t.getOperador().equals("=")) {
                    if (tnext.getOperador().equals("BF")) {
                        code.add("JNE @labelSaltoCondicional" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                    }
                }
                //comparacion distinto
                if (t.getOperador().equals("!=")) {
                    if (tnext.getOperador().equals("BF")) {
                        code.add("JE @labelSaltoCondicional" + (((TercetoDestino) tnext.getOperando2()).destino.toString()));
                    }
                }
            }


            for (int j = labelsIncondicional.size()-1; j >= 0; j--) {
                if (labelsIncondicional.get(j)==i){
                    code.add("@labelSaltoIncondicional" + (i + 1) + ":");
                }
            }

            for (int j = labelsCondicional.size()-1; j >= 0; j--) {
                if (labelsCondicional.get(j)==i){
                    code.add("@labelSaltoCondicional" + (i + 1) + ":");
                }
            }
/*            if (labelsIncondicional.contains(i)) {
                code.add("@labelSaltoIncondicional" + (i + 1) + ":");
            }
            if (labelsCondicional.contains(i)) {
                code.add("@labelSaltoCondicional" + (i + 1) + ":");
            }*/
        }
        code.add("JMP @LABEL_END");
    }
}

