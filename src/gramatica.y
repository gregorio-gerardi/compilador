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
                     | LET tipo asignacionCte{addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));}
        ;

        tipo : LINTEGER {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
                | SINGLE {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
        ;

        listaVariables : referencia {addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
                | listaVariables ';' referencia{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
        ;

        referencia : '*' ID {addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}

                | ID {addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}

        ;

        asignacionCte : referencia ASIGNACION cte {addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
        ;

        sentenciaEjecutable : sentenciaIf {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
                | sentenciaWhile
                | asignacion
                | sentenciaPrint
        ;

        sentenciaIf : IF '(' condicion ')' bloqueSentencias ELSE bloqueSentencias END_IF{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
            | IF '(' condicion ')' bloqueSentencias ELSE END_IF {addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
        ;

        sentenciaWhile : WHILE '(' condicion ')' bloqueSentencias {addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
        ;

        asignacion :    referencia ASIGNACION expresion {addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));}
        ;

        bloqueSentencias :  sentenciaEjecutable {addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
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

        cte : CTE{addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d",al.getLinea()));}
                | '-' CTE%prec '*'{addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d",al.getLinea()));}
                | '&' ID{addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d",al.getLinea()));}
        ;

        comparador: '='{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
                | '>'{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
                | '<'{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
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
