/Martín\eclipse-workspace\Compiler\src\compiler>yacc -J -v gramatica.y


/* practico 3

funcion move. como chequear que las variables perteneces o fueron declaradas dentro de la funcion?

arbol semantico: diseñar clases y  usarlas en cada regla

dividir <bloque> entre <bloque_if> y <bloque_else>

pensar switch como una cadena de if

agregar a tabla de simbolos a que se refiere un identificador (si es variable o una funcion)

agregar como sufijo a cada variable su ambito (name manling)

chequear todos los errores semanticos : funciones re declaradas, funciones llamadas sin ()

no tenemos conversiones, por lo tanto no se puede hacer una operacion entre operands de diferentes tipos (mas errores)

a las funciones, en la tabla de simbolos, al tipo pongo el tipo que retorna la funcion

*/

%{
package compiler;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import compiler.Token;
import java.util.ArrayList;
import compiler.Code;
import java.util.Vector;
%}


%token IF THEN ELSE ENDIF BEGIN END OUT SWITCH CASE FUNCTION MOVE RETURN UINT DOUBLE CADMULTI COMP_DIFERENTE COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_IGUAL COMP_MAYOR COMP_MENOR CTE ID
%left '+' '-'
%left '*' '/'
%nonassoc ELSE
%start programa

%%

programa : contenidoPrograma {addRule("Linea "+ code.getLine() + ": Programa leido correctamente."); System.out.println("Programa leido correctamente.");}
;

elementoPrograma: sentencia
                | funcion
;

contenidoPrograma : elementoPrograma
                  | contenidoPrograma elementoPrograma
;

tipo : UINT | DOUBLE
;

headerFUNCTION : tipo FUNCTION ID {
                                    st.get(((Token)$3.obj).getId()).setUse("funcion");
                                  }
              | tipo MOVE FUNCTION ID
              | tipo error ID {addSyntacticError("Linea "+ code.getLine() +": Se espera FUNCION o MOVE FUNCTION."); }
              | error FUNCTION ID {addSyntacticError("Linea "+ code.getLine() +": Tipo mal definido."); }
              | tipo FUNCTION error {addSyntacticError("Linea "+ code.getLine() +": Identificador de función mal definido."); }
              | tipo MOVE error ID {addSyntacticError("Linea "+ code.getLine() +": Se espera la palabra reservada FUNCTION luego de MOVE"); }
;

funcion : headerFUNCTION '{' conjuntoSentencias RETURN '(' expresion ')' '.' '}';
//no se permite anidamiento de funciones!

invokeFUNCTION: ID '(' ')'
              | ID '(' error {addSyntacticError("Linea "+ code.getLine() +": Falta paréntesis en la invocación a función.");  }
              | error '(' ')' {addSyntacticError("Linea "+ code.getLine() +": Identificador mal definido en la invocación a función."); }
;

declaracionVariables: listaVariables ':' tipo '.' {addRule("Linea "+ code.getLine() + ": sentencia Declarativa."); setDeclaration(((Token)$3.obj).getId());}
                    | listaVariables ':' error '.' {addSyntacticError("Linea "+ code.getLine() +": Tipo inválido."); listaVariables.clear();}
;

sentenciaDeclarativa: declaracionVariables
;

listaVariables: ID {addVariable( ((Token)$1.obj).getId() );}
              | listaVariables ',' ID {addVariable(((Token)$3.obj).getId());}
;

sentenciaEjecutable : sentenciaIF
                    | sentenciaSalida
                    | sentenciaSWITCH
                    | asignacion '.' { addRule("Linea "+ code.getLine() + ": Asignación.");}
;

sentencia : sentenciaDeclarativa
          | sentenciaEjecutable
;

conjuntoSentencias: sentencia
                  | conjuntoSentencias sentencia
;

conjuntoSentenciaEjecutables: sentenciaEjecutable
                            | conjuntoSentenciaEjecutables sentenciaEjecutable
                            | error {addSyntacticError("Linea "+ code.getLine() +": Conjunto de sentencias ejecutables mal definida.");}
;


bloqueSentencia : BEGIN conjuntoSentenciaEjecutables END '.'
                | error conjuntoSentenciaEjecutables END '.' {addSyntacticError("Linea "+ code.getLine() +": Falta BEGIN.");}
                | BEGIN conjuntoSentenciaEjecutables error '.' {addSyntacticError("Linea "+ code.getLine() +": Falta END.");}
;

comparador: COMP_IGUAL
          | COMP_MAYOR
          | COMP_MENOR
          | COMP_MAYOR_IGUAL
          | COMP_MENOR_IGUAL
          | COMP_DIFERENTE
;

//SENTENCIA IF

condicion : expresion comparador expresion
          | expresion comparador error { addSyntacticError("Linea "+ code.getLine() +": Se espera una expresion luego del operador de comparacion."); }
          | expresion error expresion { addSyntacticError("Linea "+ code.getLine() +": Se espera un comparador."); }
          | error comparador expresion { addSyntacticError("Linea "+ code.getLine() +": Se espera una expresion antes del operador de comparacion."); }
;

sentenciaIF : IF '(' condicion ')' THEN bloqueSentencia ELSE bloqueSentencia ENDIF {addRule("Linea "+ code.getLine() + ": sentencia IF con rama ELSE.");}
            | IF '(' condicion ')' THEN bloqueSentencia ENDIF {addRule("Linea "+ code.getLine() + ": sentencia IF.");}
            | IF '(' condicion ')' error bloqueSentencia ENDIF { addSyntacticError("Linea "+ code.getLine() +": Falta THEN."); }
            | IF '(' condicion ')' THEN bloqueSentencia error { addSyntacticError("Linea "+ code.getLine() +": falta ENDIF"); }
;

//SENTENCIA CASE

lineaCASE : CASE cte ':' bloqueSentencia '.'
          | error cte ':' bloqueSentencia '.' {addSyntacticError("Linea "+ code.getLine() +": Se espera palabra reservada CASE."); }
          | CASE error ':' bloqueSentencia '.' {addSyntacticError("Linea "+ code.getLine() +": Se espera una constante luego de CASE."); }
          | CASE cte error bloqueSentencia '.' {addSyntacticError("Linea "+ code.getLine() +": Se espera ':' luego de la constante."); }
;

bloqueCASE: lineaCASE
          | bloqueCASE lineaCASE
;

sentenciaSWITCH : SWITCH '(' ID ')' '{' bloqueCASE '}' '.' {addRule("Linea "+ code.getLine() + ": sentencia SWITCH CASE");}
                | SWITCH error ID ')' '{' bloqueCASE '}' '.' {addSyntacticError("Linea "+ code.getLine() +": Falta paréntesis inicial del identificador."); }
                | SWITCH '(' ID error '{' bloqueCASE '}' '.' {addSyntacticError("Linea "+ code.getLine() +": Falta paréntesis final del identificador.");}
                | SWITCH '(' ID ')' error bloqueCASE '}' '.' {addSyntacticError("Linea "+ code.getLine() +": Falta llave inicial del bloqueCASE."); }
                | SWITCH '(' ID ')' '{' bloqueCASE error '.' {addSyntacticError("Linea "+ code.getLine() +": Falta llave inicial del bloqueCASE."); }
                | SWITCH '(' error ')' '{' bloqueCASE '}' '.' {addSyntacticError("Linea "+ code.getLine() +": Se espera un identificador entre paréntesis."); }
                | SWITCH '(' error ')' '{' error '}' '.'{addSyntacticError("Linea "+ code.getLine() +": Bloque case mal definido."); }
                | error '(' error ')' '{' bloqueCASE '}' '.' {addSyntacticError("Linea "+ code.getLine() +": Se espera palabra reservada SWITCH."); }
;
// SENTENCIA SALIDA

sentenciaSalida : OUT '(' CADMULTI ')' '.' {addRule("Linea "+ code.getLine() + ": sentencia OUT");}
;

// EXPRESION

factor : ID { if (!isDeclared(((Token)$1.obj).getId())) addSyntacticError("Linea "+ code.getLine() + ": Identificador '" + ((Token)$1.obj).getId() + "' no declarado.");}
      | cte
      | invokeFUNCTION
;

cte : CTE
    | '-' CTE {st.redefineRecord(((Token)$2.obj).getId());}
;

termino : termino '*' factor
        | termino '/' factor
        | factor
;

expresion : expresion '+' termino
          | expresion '-' termino
          | termino
;

//ASIGNACION
asignacion: ID '=' expresion { if (!isDeclared(((Token)$1.obj).getId())) addSyntacticError("Linea "+ code.getLine() + ": Identificador '" + ((Token)$1.obj).getId() + "' no declarado.");}
          | ID error expresion { addSyntacticError("Linea "+ code.getLine() +": Se espera '=' en la asignacion."); }
          | ID '=' error { addSyntacticError("Linea "+ code.getLine() +": Expresion invalida en asignación."); }
          |error '=' expresion { addSyntacticError("Linea "+ code.getLine() +": Se espera un identificador válido al comienzo de la asignación."); }
          // | ID ASIGN expresion error {addRule("Linea "+ code.getLine() + ": ");}
;
//tenemos que hacer asignaciones si no tenemos LET??
%%

private LexicAnalyzer lexic;
private Code code;
private ArrayList<String> lexicErrors;
private ArrayList<String> rules;
private ArrayList<String> tokens;
private ArrayList<String> syntacticErrors;
private ArrayList<String> semanticErrors;
private SymbolTable st = new SymbolTable();
private ArrayList<String> listaVariables;

public Parser(String source){
  System.out.println(source);
	code = new Code(source);
	lexic = new LexicAnalyzer(code);
	rules = new ArrayList <String>();
	tokens = lexic.getTokens();
  lexicErrors = lexic.getErrors();
  syntacticErrors = new ArrayList <String>();
	semanticErrors = new ArrayList <String>();
  listaVariables = new ArrayList <String>();
}

public int yylex(){
	Token token = lexic.getToken();
  if (token!=null){
        yylval = new ParserVal(token);
		switch (token.getUse()) {
      case "<" : {return COMP_MENOR;}
      case ">" : {return COMP_MAYOR;}
			case "<=" : {return COMP_MENOR_IGUAL;}
			case ">=" : {return COMP_MAYOR_IGUAL;}
			case "<>" : {return COMP_DIFERENTE;}
      case "==" : {return COMP_IGUAL;}
      case "=" : {return '=';}
      case "." : {return '.';}
      case ":" : {return ':';}
			case "+" : {return '+';}
			case "-" : {return '-';}
      case "*" : {return '*';}
      case "/" : {return '/';}
			case "(" : {return '(';}
			case ")" : {return ')';}
			case "," : {return ',';}
			case "{" : {return '{';}
			case "}" : {return '}';}
			case "if":{return IF;}
      case "then":{return THEN;}
      case "begin":{return BEGIN;}
      case "end":{return END;}
      case "out":{return OUT;}
      case "switch":{return SWITCH;}
      case "case":{return CASE;}
      case "move":{return MOVE;}
			case "endif":{return ENDIF;}
			case "else":{return ELSE;}
			case "uinteger":{return UINT;}
      case "double" :{return DOUBLE;}
			case "cte" : {return CTE;}
			case "id": {return ID;}
			case "cadMulti":{return CADMULTI;}
			case "function":{return FUNCTION;}
			case "return":{return RETURN;}
    	}
    }
	return 0;
}

public boolean isDeclared(String lexema){
		return st.hasType(lexema);
}

public void setDeclaration(String type){
  for (String id : listaVariables){
      st.setType(id,type);
  }
  listaVariables.clear();
}

public void addVariable(String var){
  listaVariables.add(var);
}

public void yyerror(String error){
}

public int yyparser(){
	return yyparse();
}

private void addLexicError(String error) {
	lexicErrors.add(error);
}

private ArrayList<String> getLexicErrors() {
	return lexicErrors;
}


private void addSyntacticError(String error){
  //System.out.println("##########" + error);
		syntacticErrors.add(error);
}

public ArrayList<String> getSyntacticErrors(){
        return syntacticErrors;
}

private void addSemanticError(String error){
  //System.out.println("##########" + error);
		semanticErrors.add(error);
}

public ArrayList<String> getSemanticErrors(){
        return semanticErrors;
}

private void addRule(String rule) {
  //System.out.println("##########" + rule);
	rules.add(rule);
}

public ArrayList<String> getRules(){
	return rules;
}

public ArrayList<String> getTokens(){
  return tokens;
}


public SymbolTable getST(){
  return st;
}

public LexicAnalyzer getLexicAnalyzer(){
  return lexic;
}

public void showMessages(String name){
		try
		{
			File file = new File(name+"_mensajes.txt");
			Files.deleteIfExists(file.toPath());
			PrintWriter pw = new PrintWriter(file);
			pw.println("REGLAS:");
			pw.println("------------------------------");
			pw.println("");
			for(String e:rules)
			{
				pw.println(e);
			}
			pw.println("");
			pw.println("ERRORES DEL ANALIZADOR LEXICO:");
			pw.println("------------------------------");
			pw.println("");
			if (lexicErrors.isEmpty())
				pw.println("No se encontraron errores lexicos.");
			else
				for(String e:lexicErrors)
				{
					pw.println(e);
				}
			pw.println("");
			pw.println("ERRORES SINTACTICOS:");
			pw.println("--------------------");
			pw.println("");
			if (syntacticErrors.isEmpty())
				pw.println("No se detectaron errores sintacticos.");
			else
				for(String e: syntacticErrors){
				pw.println(e);
				}
			pw.println("");
			pw.close();
		}catch(IOException ex){ex.printStackTrace();}
	}
