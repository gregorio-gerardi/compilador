%{
import java.io.IOException;
import java.util.ArrayList;
%}

%token ID ASIGNACION COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_DIFERENTE IF ELSE END_IF PRINT LINTEGER SINGLE WHILE LET MUT CADENA CTE
%left '+' '-'
%left '*' '/'
%start programa

%%

        programa : contenidoPrograma {addReglaSintacticaReconocida(String.format("Programa reconocido en linea %1$d",al.getLinea()));}
        ;

        contenidoPrograma : sentencia ','{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
                | contenidoPrograma sentencia ',' {addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
        ;

        sentencia : sentenciaDeclarativa {addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
                | sentenciaEjecutable{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
        ;

    sentenciaDeclarativa : LET MUT tipo listaVariables{addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));}
                    | LET error tipo listaVariables{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
                    | error MUT tipo listaVariables{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
                    | LET MUT error listaVariables{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
                    | LET MUT tipo error{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
                    | LET tipo asignacionCte{addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));}
                    | error tipo asignacionCte{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
                    | LET error asignacionCte{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
                    | LET tipo error{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
    ;

        tipo : LINTEGER {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
                | SINGLE {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
        ;

        listaVariables : referencia {addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
                | listaVariables ';' referencia{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
                        | listaVariables error referencia{addErrorSintactico(String.format(" declaracion de lista de variables esperaba un ; entre variables en linea %1$d",al.getLinea()));}

        ;

        referencia : '*' ID {addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}

                | ID {addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}

        ;

        asignacionCte : referencia ASIGNACION cte {addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
        | referencia error cte {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
                        | referencia ASIGNACION error {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
                        | error ASIGNACION cte {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}


        ;

        sentenciaEjecutable : sentenciaIf {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
                | sentenciaWhile {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
                | asignacion {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
                | sentenciaPrint {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}

        ;

         sentenciaIf : encabezadoIf cuerpoIf {addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
          ;

          encabezadoIf : IF '(' condicion ')'
                      | error '(' condicion ')' {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
                      | IF error condicion ')' {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
                      | IF '(' error ')' {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
                      | IF '(' condicion error {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}

          ;

          cuerpoIf : bloqueSentencias ELSE bloqueSentencias END_IF
              | error ELSE bloqueSentencias END_IF {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
              | bloqueSentencias error bloqueSentencias END_IF {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
              | bloqueSentencias ELSE error END_IF {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
              | bloqueSentencias ELSE bloqueSentencias error {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}


              |bloqueSentencias END_IF
              |bloqueSentencias error {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
              |error END_IF {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}

          ;

    sentenciaWhile : WHILE '(' condicion ')' bloqueSentencias {addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
           |WHILE error condicion ')' bloqueSentencias {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
           |WHILE '(' error ')' bloqueSentencias {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
           |WHILE '(' condicion error bloqueSentencias {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
           |WHILE '(' condicion ')' error {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}

    ;
        asignacion :    referencia ASIGNACION expresion {addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));}
        ;

        bloqueSentencias :  sentenciaEjecutable ','{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
                | '{' conjuntoSentenciasEjecutables '}'{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
        ;

        conjuntoSentenciasEjecutables : sentenciaEjecutable ',' {addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
                | conjuntoSentenciasEjecutables sentenciaEjecutable ','{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
        ;

        condicion : expresion comparador expresion {addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));}
        ;

        expresion : expresion '+' termino{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
                | expresion '-' termino{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
                | termino{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
        ;

        termino : termino '*' factor{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
                | termino '/' factor{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
                | factor{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
        ;

        factor : ID{addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));}
                | cte{addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));}
        ;
    cte :CTE

    {
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) ($1.obj);
        if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
            if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
                addErrorSintactico(String.format("warning linteger cte positiva mayor al maximo permitido en linea %1$d", al.getLinea()));
                String nuevoLexema = String.valueOf(AnalizadorLexico.MAX_LONG - 1);
                entradaTablaSimbolos.setLexema(nuevoLexema);
            }
        }
        addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));
    }
                |'-'CTE %prec '*'

    {
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) ($1.obj);
        String lexema = "-" + (entradaTablaSimbolos.getLexema());
        if (!al.estaEnTabla(lexema)) {
            // no esta en tabla, agrega a TS
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
            al.agregarATablaSimbolos(lexema, elementoTS);
        }
        addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));
    }
                |'&'ID

    {
        addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea()));
    }

    ;



        comparador: '='{addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));}
                | '>'{addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));}
                | '<'{addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()));}
                | COMP_MAYOR_IGUAL{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
                | COMP_MENOR_IGUAL{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
                | COMP_DIFERENTE{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
        ;


        sentenciaPrint : PRINT '(' CADENA ')'{addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));}

        ;

%%
  private AnalizadorLexico al;
      private ArrayList<String> listaDeTokens;
      private ArrayList<String> listaDeReglas;
      private ArrayList<String> listaDeErroresLexicos;
      private ArrayList<String> listaDeErroresSintacticos;

      public Parser(Reader fuente) {
          al = new AnalizadorLexico(fuente);
          listaDeReglas=new ArrayList<>();
          listaDeTokens = new ArrayList<>();
          listaDeErroresLexicos = new ArrayList<>();
          listaDeErroresSintacticos = new ArrayList<>();
      }

      private void yyerror(String syntax_error) {
      }

      private int yylex() {
          int token = al.getToken();
          yylval = new ParserVal(al.getEntradaTablaSimbolo());
          return token;
      }

      public ArrayList<String> getListaDeTokens() {
          return al.getListaDeTokens();
      }

      public ArrayList<String> getListaDeReglas() {
          return listaDeReglas;
      }

      public ArrayList<String> getListaDeErroresLexicos() {
          return al.getListaDeErroresLexicos();
      }

      public ArrayList<String> getListaDeErroresSintacticos() {
          return listaDeErroresSintacticos;
      }

      private void addErrorSintactico(String error) {
          listaDeErroresSintacticos.add(error);
      }

      private void addReglaSintacticaReconocida(String regla) {
          listaDeReglas.add(regla);
      }
