%token ID ASIGNACION COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_MAYOR COMP_MENOR COMP_IGUAL COMP_DIFERENTE IF ELSE END_IF PRINT LINTEGER SINGLE WHILE LET MUT CADENA CTE
%left '+' '-'
%left '*' '/'
%start programa

%%

programa : contenidoPrograma
;

contenidoPrograma : sentencia ','
                  | contenidoPrograma sentencia ','
;

sentencia : sentenciaDeclarativa
            | sentenciaEjecutable
;

sentenciaDeclarativa : LET MUT tipo listaVariables
                     | LET tipo asignacionCte
;

tipo : LINTEGER
        | SINGLE
;

listaVariables : referencia
              | listaVariables ';' referencia
;

referencia : '*' ID
            | ID
;

asignacionCte : referencia ASIGNACION cte
;

sentenciaEjecutable : sentenciaIf
                    | sentenciaWhile
                    | asignacion
                    | sentenciaPrint
;

sentenciaIf : IF '(' condicion ')' bloqueSentencias ELSE bloqueSentencias END_IF
            | IF '(' condicion ')' bloqueSentencias ELSE END_IF
;

sentenciaWhile : WHILE '(' condicion ')' bloqueSentencias
;

asignacion :    referencia ASIGNACION expresion
;

bloqueSentencias :  sentenciaEjecutable
                    | '{' conjuntoSentenciasEjecutables '}'
;

conjuntoSentenciasEjecutables : sentenciaEjecutable ','
                                | conjuntoSentenciasEjecutables sentenciaEjecutable ','
;

condicion : expresion comparador expresion
;

expresion : expresion '+' termino
          | expresion '-' termino
          | termino
;

termino : termino '*' factor
        | termino '/' factor
        | factor
;

factor : ID
      | cte
;

cte : CTE
    | '-' CTE%prec '*'
    | '&' ID
;

comparador: COMP_IGUAL
          | COMP_MAYOR
          | COMP_MENOR
          | COMP_MAYOR_IGUAL
          | COMP_MENOR_IGUAL
          | COMP_DIFERENTE
;


sentenciaSalida : PRINT '(' CADENA ')'
;

%%