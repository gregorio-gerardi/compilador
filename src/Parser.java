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






//#line 2 "gramatica.y"

//#line 20 "Parser.java"


import java.io.IOException;
import java.util.ArrayList;

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
public final static short YYERRCODE=256;
public final static short ID=257;
public final static short ASIGNACION=258;
public final static short COMP_MAYOR_IGUAL=259;
public final static short COMP_MENOR_IGUAL=260;
public final static short COMP_MAYOR=261;
public final static short COMP_MENOR=262;
public final static short COMP_IGUAL=263;
public final static short COMP_DIFERENTE=264;
public final static short IF=265;
public final static short ELSE=266;
public final static short END_IF=267;
public final static short PRINT=268;
public final static short LINTEGER=269;
public final static short SINGLE=270;
public final static short WHILE=271;
public final static short LET=272;
public final static short MUT=273;
public final static short CADENA=274;
public final static short CTE=275;
<<<<<<< HEAD:src/Parser.java
public final static short YYERRCODE=256;
=======
>>>>>>> master:Parser.java
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    5,    5,    6,
    6,    8,    8,    7,    4,    4,    4,    4,   10,   10,
   11,   12,   15,   15,   17,   17,   14,   16,   16,   16,
   19,   19,   19,   20,   20,    9,    9,    9,   18,   18,
   18,   18,   18,   18,   13,
};
final static short yylen[] = {                            2,
    1,    2,    3,    1,    1,    4,    3,    1,    1,    1,
    3,    2,    1,    3,    1,    1,    1,    1,    8,    7,
    5,    3,    1,    3,    2,    3,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    2,    2,    1,    1,
    1,    1,    1,    1,    4,
};
final static short yydefred[] = {                         0,
   13,    0,    0,    0,    0,    0,    0,    0,    0,    4,
    5,    0,   15,   16,   17,   18,    0,    0,    0,    8,
    9,    0,    0,   12,    0,    2,    0,   34,   36,    0,
    0,   35,    0,    0,    0,   33,    0,    0,    0,    7,
    0,    3,    0,   37,   38,    0,   42,   43,   40,   41,
   39,   44,    0,    0,    0,    0,    0,   45,    0,    0,
   10,    0,    0,   23,    0,    0,    0,    0,   31,   32,
   21,    0,   14,    0,    0,    0,   11,   25,   24,    0,
   20,    0,   26,   19,
};
final static short yydgoto[] = {                          7,
    8,    9,   10,   64,   23,   60,   40,   12,   32,   13,
   14,   15,   16,   33,   65,   34,   75,   55,   35,   36,
};
final static short yysindex[] = {                       -40,
    0,  -16,  -10,   -8, -248, -223,    0,  -40,   -6,    0,
    0, -222,    0,    0,    0,    0,  -38, -237,  -38,    0,
    0, -258,  -39,    0,   -5,    0,  -38,    0,    0, -235,
 -216,    0,    1,   33,  -32,    0,    2,    3,  -39,    0,
 -213,    0,  -29,    0,    0,  -23,    0,    0,    0,    0,
    0,    0,  -38,  -38,  -38,  -38,  -38,    0,  -23,  -13,
    0,  -37,   -7,    0, -218,  -32,  -32,  -29,    0,    0,
    0,  -39,    0,    8,  -11,  -24,    0,    0,    0,   10,
    0, -212,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    6,    0,    0,    0,    0,    0,
    0,    0,  -31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
    0,    0,    0,    0,    0,   15,   24,   21,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   55,    0,    9,   42,    0,    0,  -19,    4,    0,
    0,    0,    0,   51,  -53,  -22,    0,    0,  -27,  -28,
};
final static int YYTABLESIZE=297;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         31,
   31,    6,    6,   41,   43,   71,   30,   30,   11,   56,
   20,   21,   22,   53,   57,   54,   11,    6,    6,   61,
   20,   21,   82,   17,   22,   66,   67,   69,   70,   18,
    6,   19,   68,   24,    6,   27,   37,   26,   42,   44,
   45,   46,   58,   59,   62,   72,   30,   76,   30,   30,
   30,   78,   77,   83,   84,   28,    1,   28,   28,   28,
    6,   27,   25,   39,   29,   73,   29,   29,   29,   38,
    0,   74,    0,    0,    0,   53,    0,   54,    0,    0,
    0,    0,    0,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   63,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   79,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    1,   28,    0,
    0,    0,    0,    0,    2,    0,    0,    3,    0,    0,
    4,    5,    1,    1,   22,   22,   29,   29,    0,    0,
    2,    2,   81,    3,    3,    1,    4,    4,    0,    1,
    0,    0,    0,    2,    0,    0,    3,    2,    0,    4,
    3,    0,    0,    4,   30,   30,   30,   30,   30,   30,
    0,   30,   30,   28,   28,   28,   28,   28,   28,    0,
   28,   28,   29,   29,   29,   29,   29,   29,    0,   29,
   29,   47,   48,   49,   50,   51,   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   38,   42,   42,   23,   27,   59,   45,   45,    0,   42,
  269,  270,   44,   43,   47,   45,    8,   42,   42,   39,
  269,  270,   76,   40,  273,   53,   54,   56,   57,   40,
   42,   40,   55,  257,   42,  258,  274,   44,   44,  275,
  257,   41,   41,   41,  258,   59,   41,  266,   43,   44,
   45,   44,   72,   44,  267,   41,    0,   43,   44,   45,
   44,   41,    8,   22,   41,   62,   43,   44,   45,   19,
   -1,   63,   -1,   -1,   -1,   43,   -1,   45,   -1,   -1,
   -1,   -1,   -1,   75,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,  123,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  257,  257,   -1,
   -1,   -1,   -1,   -1,  265,   -1,   -1,  268,   -1,   -1,
  271,  272,  257,  257,  266,  267,  275,  275,   -1,   -1,
  265,  265,  267,  268,  268,  257,  271,  271,   -1,  257,
   -1,   -1,   -1,  265,   -1,   -1,  268,  265,   -1,  271,
  268,   -1,   -1,  271,  259,  260,  261,  262,  263,  264,
   -1,  266,  267,  259,  260,  261,  262,  263,  264,   -1,
  266,  267,  259,  260,  261,  262,  263,  264,   -1,  266,
  267,  259,  260,  261,  262,  263,  264,
};
}
final static short YYFINAL=7;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'&'",null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'",null,null,null,null,null,null,null,null,null,null,null,null,null,null,
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
"COMP_MENOR_IGUAL","COMP_MAYOR","COMP_MENOR","COMP_IGUAL","COMP_DIFERENTE","IF",
"ELSE","END_IF","PRINT","LINTEGER","SINGLE","WHILE","LET","MUT","CADENA","CTE",
};
final static String yyrule[] = {
"$accept : programa",
"programa : contenidoPrograma",
"contenidoPrograma : sentencia ','",
"contenidoPrograma : contenidoPrograma sentencia ','",
"sentencia : sentenciaDeclarativa",
"sentencia : sentenciaEjecutable",
"sentenciaDeclarativa : LET MUT tipo listaVariables",
"sentenciaDeclarativa : LET tipo asignacionCte",
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
"sentenciaIf : IF '(' condicion ')' bloqueSentencias ELSE END_IF",
"sentenciaWhile : WHILE '(' condicion ')' bloqueSentencias",
"asignacion : referencia ASIGNACION expresion",
"bloqueSentencias : sentenciaEjecutable",
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
"comparador : COMP_IGUAL",
"comparador : COMP_MAYOR",
"comparador : COMP_MENOR",
"comparador : COMP_MAYOR_IGUAL",
"comparador : COMP_MENOR_IGUAL",
"comparador : COMP_DIFERENTE",
"sentenciaPrint : PRINT '(' CADENA ')'",
};

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


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################


  private AnalizadorLexico al;
  private ArrayList<String> listaDeTokens;
  private ArrayList<String> listaDeReglas;
  private ArrayList<String> listaDeErroresLexicos;
  private ArrayList<String> listaDeErroresSintacticos;

  //todo PASAR A GRAMATICA
  public Parser(String fuente)
  {
    try {
      al=new AnalizadorLexico(new Reader(fuente));
    } catch (IOException e) {
      e.printStackTrace();
    }
    listaDeTokens = new ArrayList<>();
    listaDeErroresLexicos = new ArrayList<>();
    listaDeErroresSintacticos = new ArrayList<>();
  }

  private void yyerror(String syntax_error) {
  }

  private int yylex() {
    EntradaTablaSimbolos entradaTablaSimbolos=null;
    //todo incorporar el recopilaor de tokens y errores al analisador lexico que lo cargue para luego imprimirlo aca
    int token = al.getToken(entradaTablaSimbolos);
    yylval=new ParserVal(entradaTablaSimbolos);
    return token;
  }
  private void addErrorSintactico(String error){
    listaDeErroresSintacticos.add(error);
  }

  private void addReglaSintacticaReconocida(String regla){
    listaDeReglas.add(regla);
  }
}
//################### END OF CLASS ##############################
