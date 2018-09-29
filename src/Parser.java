//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramaticabu.y"
import java.io.IOException;
import java.util.ArrayList;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short ASIGNACION=258;
public final static short COMP_MAYOR_IGUAL=259;
public final static short COMP_MENOR_IGUAL=260;
public final static short COMP_DIFERENTE=261;
public final static short IF=262;
public final static short ELSE=263;
public final static short END_IF=264;
public final static short PRINT=265;
public final static short LINTEGER=266;
public final static short SINGLE=267;
public final static short WHILE=268;
public final static short LET=269;
public final static short MUT=270;
public final static short CADENA=271;
public final static short CTE=272;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    5,    5,    6,    6,    8,    8,
    7,    4,    4,    4,    4,   10,   10,   11,   12,   15,
   15,   17,   17,   14,   16,   16,   16,   19,   19,   19,
   20,   20,    9,    9,    9,   18,   18,   18,   18,   18,
   18,   13,
};
final static short yylen[] = {                            2,
    1,    2,    3,    1,    1,    4,    4,    4,    4,    4,
    3,    3,    3,    3,    1,    1,    1,    3,    2,    1,
    3,    1,    1,    1,    1,    8,    6,    5,    3,    2,
    3,    2,    3,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    2,    2,    1,    1,    1,    1,    1,
    1,    4,
};
final static short yydefred[] = {                         0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    5,    0,   22,   23,   24,   25,   15,   16,    0,
    0,    0,    0,    0,    0,    0,    0,   19,    0,    2,
    0,    0,   12,    0,   41,   43,    0,    0,   42,    0,
    0,    0,   40,    0,    0,    0,   13,    0,    0,   14,
   11,    3,    0,    0,   17,    0,   44,   45,    0,   49,
   50,   51,    0,    0,   46,   47,   48,    0,    0,    0,
   52,    0,    0,    0,   10,    0,    0,   21,    0,    0,
    0,    0,    0,    0,   38,   39,   28,   18,    0,    0,
   30,    0,   27,   32,   31,    0,    0,   33,   26,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   80,   21,   54,   33,   13,   39,   14,
   15,   16,   17,   40,   81,   41,   90,   68,   42,   43,
};
final static short yysindex[] = {                       -40,
 -180,    0,  -18,  -14,   -4, -191, -213,    0,  -40,    5,
    0,    0, -204,    0,    0,    0,    0,    0,    0, -252,
  -39,  -38, -215,  -38,  -12, -246,  -36,    0,   23,    0,
  -38,  -39,    0, -185,    0,    0, -195, -173,    0,   50,
   10,  -19,    0,   51,   52,  -39,    0,  -39,  -33,    0,
    0,    0,  -16,   36,    0,  -37,    0,    0,  -26,    0,
    0,    0,  -38,  -38,    0,    0,    0,  -38,  -38,  -38,
    0,  -26,   36,   36,    0,   36,  -39,    0,  -24,   54,
 -203,  -19,  -19,  -16,    0,    0,    0,    0,   59,  -25,
    0,  -26,    0,    0,    0,   60, -168,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  105,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -10,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   62,   63,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   64,   65,    0,   66,    0,    0,    0,    0,
    0,   -3,    2,   70,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  103,    0,    4,   -1,   20,   12,   53,   57,    0,
    0,    0,    0,   90,  -60,  -20,    0,    0,   18,   19,
};
final static int YYTABLESIZE=271;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         38,
   38,    7,    7,   12,   27,    7,   37,   37,    7,   48,
   53,   87,   12,   18,   19,    7,    7,    7,   32,   18,
   19,   22,   69,   46,   49,   23,   63,   70,   64,    7,
   37,   97,   37,   37,   37,   24,   47,   35,   51,   35,
   35,   35,   36,   28,   36,   36,   36,   84,   30,   37,
   37,   37,   63,   31,   64,   44,   35,   35,   35,   92,
   93,   36,   36,   36,   25,   73,   52,   74,   76,   67,
   65,   66,   56,   34,   18,   19,   57,   34,   26,   34,
   82,   83,   89,   58,   55,   18,   19,   85,   86,   20,
   59,   71,   72,   96,   77,   99,   79,   91,   55,   95,
   55,   55,   94,   98,    1,   29,    8,    7,    9,    6,
   34,   29,   78,   45,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   88,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    2,    2,   35,   50,
    2,    3,   75,    2,    4,    0,    0,    5,    6,    0,
    2,    2,    2,   36,   36,    3,    3,    3,    4,    4,
    4,    5,    5,    5,    2,    0,    0,    0,   37,   37,
   37,    0,    0,   18,   19,   35,   35,   35,    0,    0,
   36,   36,   36,    0,    0,    0,    0,    0,   60,   61,
   62,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   38,   42,   42,    0,    6,   42,   45,   45,   42,  256,
   31,   72,    9,  266,  267,   42,   42,   42,   20,  266,
  267,   40,   42,   25,   26,   40,   43,   47,   45,   42,
   41,   92,   43,   44,   45,   40,   25,   41,   27,   43,
   44,   45,   41,  257,   43,   44,   45,   68,   44,   60,
   61,   62,   43,  258,   45,  271,   60,   61,   62,  263,
  264,   60,   61,   62,  256,   46,   44,   48,   49,   60,
   61,   62,  258,   21,  266,  267,  272,   25,  270,   27,
   63,   64,   79,  257,   32,  266,  267,   69,   70,  270,
   41,   41,   41,   90,   59,  264,  123,   44,   46,  125,
   48,   49,   44,   44,    0,   44,   44,   44,   44,   44,
   41,    9,   56,   24,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   77,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,  257,  257,  256,
  257,  262,  256,  257,  265,   -1,   -1,  268,  269,   -1,
  257,  257,  257,  272,  272,  262,  262,  262,  265,  265,
  265,  268,  268,  268,  257,   -1,   -1,   -1,  259,  260,
  261,   -1,   -1,  266,  267,  259,  260,  261,   -1,   -1,
  259,  260,  261,   -1,   -1,   -1,   -1,   -1,  259,  260,
  261,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=272;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"ID","ASIGNACION","COMP_MAYOR_IGUAL",
"COMP_MENOR_IGUAL","COMP_DIFERENTE","IF","ELSE","END_IF","PRINT","LINTEGER",
"SINGLE","WHILE","LET","MUT","CADENA","CTE",
};
final static String yyrule[] = {
"$accept : programa",
"programa : contenidoPrograma",
"contenidoPrograma : sentencia ','",
"contenidoPrograma : contenidoPrograma sentencia ','",
"sentencia : sentenciaDeclarativa",
"sentencia : sentenciaEjecutable",
"sentenciaDeclarativa : LET MUT tipo listaVariables",
"sentenciaDeclarativa : LET error tipo listaVariables",
"sentenciaDeclarativa : error MUT tipo listaVariables",
"sentenciaDeclarativa : LET MUT error listaVariables",
"sentenciaDeclarativa : LET MUT tipo error",
"sentenciaDeclarativa : LET tipo asignacionCte",
"sentenciaDeclarativa : error tipo asignacionCte",
"sentenciaDeclarativa : LET error asignacionCte",
"sentenciaDeclarativa : LET tipo error",
"tipo : LINTEGER",
"tipo : SINGLE",
"listaVariables : referencia",
"listaVariables : listaVariables ';' referencia",
"referencia : '*' ID",
"referencia : ID",
"asignacionCte : referencia ASIGNACION cte",
"sentenciaEjecutable : sentenciaIf",
"sentenciaEjecutable : sentenciaWhile",
"sentenciaEjecutable : asignacion",
"sentenciaEjecutable : sentenciaPrint",
"sentenciaIf : IF '(' condicion ')' bloqueSentencias ELSE bloqueSentencias END_IF",
"sentenciaIf : IF '(' condicion ')' bloqueSentencias END_IF",
"sentenciaWhile : WHILE '(' condicion ')' bloqueSentencias",
"asignacion : referencia ASIGNACION expresion",
"bloqueSentencias : sentenciaEjecutable ','",
"bloqueSentencias : '{' conjuntoSentenciasEjecutables '}'",
"conjuntoSentenciasEjecutables : sentenciaEjecutable ','",
"conjuntoSentenciasEjecutables : conjuntoSentenciasEjecutables sentenciaEjecutable ','",
"condicion : expresion comparador expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : cte",
"cte : CTE",
"cte : '-' CTE",
"cte : '&' ID",
"comparador : '='",
"comparador : '>'",
"comparador : '<'",
"comparador : COMP_MAYOR_IGUAL",
"comparador : COMP_MENOR_IGUAL",
"comparador : COMP_DIFERENTE",
"sentenciaPrint : PRINT '(' CADENA ')'",
};

//#line 141 "gramaticabu.y"
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
//#line 361 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 13 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("Programa reconocido en linea %1$d",al.getLinea()));}
break;
case 2:
//#line 16 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 3:
//#line 17 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 4:
//#line 20 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 5:
//#line 21 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 6:
//#line 24 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));}
break;
case 7:
//#line 25 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 8:
//#line 26 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 9:
//#line 27 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 10:
//#line 28 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 11:
//#line 29 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));}
break;
case 12:
//#line 30 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 13:
//#line 31 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 14:
//#line 32 "gramaticabu.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 15:
//#line 35 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
break;
case 16:
//#line 36 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
break;
case 17:
//#line 39 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
break;
case 18:
//#line 40 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
break;
case 19:
//#line 43 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}
break;
case 20:
//#line 45 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}
break;
case 21:
//#line 49 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
break;
case 22:
//#line 52 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 26:
//#line 58 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
break;
case 27:
//#line 59 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
break;
case 28:
//#line 62 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
break;
case 29:
//#line 65 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));}
break;
case 30:
//#line 68 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 31:
//#line 69 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 32:
//#line 72 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 33:
//#line 73 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 34:
//#line 76 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));}
break;
case 35:
//#line 79 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 36:
//#line 80 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 37:
//#line 81 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 38:
//#line 84 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
break;
case 39:
//#line 85 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
break;
case 40:
//#line 86 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
break;
case 41:
//#line 89 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));}
break;
case 42:
//#line 90 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));}
break;
case 43:
//#line 94 "gramaticabu.y"
{
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
        if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
            if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
                addErrorSintactico(String.format("warning linteger cte positiva mayor al maximo permitido en linea %1$d", al.getLinea()));
                String nuevoLexema = String.valueOf(AnalizadorLexico.MAX_LONG - 1);
                entradaTablaSimbolos.setLexema(nuevoLexema);
            }
        }
        addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));
    }
break;
case 44:
//#line 107 "gramaticabu.y"
{
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(1).obj);
        String lexema = "-" + (entradaTablaSimbolos.getLexema());
        if (!al.estaEnTabla(lexema)) {
            /* no esta en tabla, agrega a TS*/
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
            al.agregarATablaSimbolos(lexema, elementoTS);
        }
        addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));
    }
break;
case 45:
//#line 119 "gramaticabu.y"
{
        addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea()));
    }
break;
case 46:
//#line 127 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));}
break;
case 47:
//#line 128 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));}
break;
case 48:
//#line 129 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()));}
break;
case 49:
//#line 130 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
break;
case 50:
//#line 131 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
break;
case 51:
//#line 132 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
break;
case 52:
//#line 136 "gramaticabu.y"
{addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));}
break;
//#line 727 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
