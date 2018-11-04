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
import java.util.ArrayList;
import java.util.HashMap;

//#line 21 "Parser.java"




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
    0,    1,    1,    1,    2,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    5,    5,    6,    6,    6,
    8,    8,    7,    7,    7,    7,    4,    4,    4,    4,
   10,   14,   14,   14,   14,   14,   15,   15,   15,   15,
   15,   15,   15,   15,   11,   11,   11,   11,   11,   12,
   12,   12,   12,   17,   17,   17,   17,   17,   19,   19,
   16,   16,   16,   16,   18,   18,   18,   18,   18,   18,
   18,   21,   21,   21,   22,   22,    9,    9,    9,    9,
    9,   20,   20,   20,   20,   20,   20,   13,   13,   13,
   13,   13,
};
final static short yylen[] = {                            2,
    1,    2,    2,    3,    1,    1,    4,    4,    4,    4,
    4,    3,    3,    3,    3,    1,    1,    1,    3,    3,
    2,    1,    3,    3,    3,    3,    1,    1,    1,    1,
    2,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    4,    2,    2,    2,    5,    5,    5,    5,    5,    3,
    3,    3,    3,    2,    3,    3,    3,    3,    2,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    2,    2,    2,
    2,    1,    1,    1,    1,    1,    1,    4,    4,    4,
    4,    4,
};
final static short yydefred[] = {                         0,
    0,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    5,    6,    0,   27,   28,   29,   30,    0,    0,   16,
   17,    0,    3,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   21,    0,    0,    2,    0,    0,
    0,    0,    0,   31,    0,    0,   77,    0,    0,   75,
   76,    0,    0,   74,    0,    0,    0,    0,    0,    0,
   13,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   14,    0,    0,    0,   12,    4,    0,    0,
    0,    0,    0,   44,    0,    0,    0,    0,   54,    0,
    0,   42,   81,    0,    0,   78,   80,   79,    0,    0,
    0,    0,    0,   18,   85,   86,   87,   82,   83,   84,
    0,   89,   33,    0,    0,    0,    0,    0,   34,   35,
   36,   32,   90,   91,   92,   88,    0,    0,    0,    0,
    0,    0,   11,    0,    0,    0,   59,   57,    0,   56,
    0,   55,    0,    0,    0,   68,   69,    0,    0,    0,
    0,    0,   72,   73,    0,    0,    0,    0,    0,    0,
   26,   24,    0,   23,   46,   47,   48,    0,   45,   38,
   60,   39,   40,   41,   37,   20,   19,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   43,   25,  103,   61,   13,   51,   14,
   15,   16,   17,   18,   44,   58,   45,   59,   86,  111,
   53,   54,
};
final static short yysindex[] = {                        58,
   13,    0,   -5,   18,   26, -194, -235,    0,   68,  -14,
    0,    0, -238,    0,    0,    0,    0,   78,  -36,    0,
    0, -162,    0,  -38,  -41,  -30,  -28, -229, -206,  -30,
  -19,   10, -189,   51,    0,  -35,   11,    0,  -36,  -17,
   28,  116,   15,    0, -200,  -32,    0, -201, -119,    0,
    0,   45,   27,    0,    7,  378,   73,   77,  389, -179,
    0, -161,   84,  227,   -4,   88,   90,   39,  101,  369,
   44,    7,    0,    7,   61, -179,    0,    0,   45,  -32,
   45,   -2,   82,    0,   55,   92,   -8,   99,    0,   82,
  118,    0,    0,  -36,  -36,    0,    0,    0,  -11,   -9,
    1,    1,  -56,    0,    0,    0,    0,    0,    0,    0,
  -36,    0,    0,  -36,    3,    6,    6,    9,    0,    0,
    0,    0,    0,    0,    0,    0,   82,   82,   82,  120,
  -56,  -56,    0,  -56,   54, -172,    0,    0,   89,    0,
   -2,    0, -155,   41, -240,    0,    0, -112,   27, -112,
   27, -112,    0,    0,    7,    7,   45,   45,  -32,   45,
    0,    0, -112,    0,    0,    0,    0,   54,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  146,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  103,  109,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  104,    0,    0,  105,  107,
  111,    0,    0,    0,    0,    0,    0,    0,    0,  112,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  113,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  115,  117,    0,  123,    0,    0,    0,    0,    0,    0,
   38,    0,    0,    0,    0,    0,    0,  131,  137,  358,
  364,    0,    0,    0,    0,    0,   48,   57,   65,   72,
    0,    0,  124,    0,    0,    0,    0,  129,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  168,    0,  395,   69,   12,   96,  434,    5,    0,
    0,    0,    0,    0,    0,   81,   36,   21,  141,  125,
   40,   42,
};
final static int YYTABLESIZE=650;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         49,
    7,   49,  156,    7,   24,    7,   48,   49,   48,   49,
   94,    7,   95,    7,   48,  174,   48,   39,   49,   40,
   49,   35,    7,  175,    7,   48,   49,   48,   49,   38,
    7,   24,    7,   48,   27,   48,  122,   24,   49,   52,
   49,   66,    7,   49,    7,   48,   49,   48,    7,   67,
   48,    7,   24,   48,   78,   90,   23,   29,   89,   79,
   81,   32,   91,   92,   68,   31,   74,   24,  101,    7,
   96,   20,   21,  102,   34,   33,   20,   21,  116,  126,
   24,   58,    7,  131,  130,  132,  134,   99,   62,  100,
   55,  170,    7,   24,  117,    7,  118,   63,  137,    7,
   72,   75,    7,   20,   21,   64,   63,   65,  172,    7,
   69,   71,   61,  112,  146,  147,  140,  113,  136,    7,
  161,  162,  164,    7,  119,  143,  145,   73,  123,   77,
  124,  157,  171,    7,  158,  160,   97,   98,  149,  151,
    7,  127,  153,  154,   93,    1,   51,   15,   52,   71,
   53,   71,   71,   71,   50,   43,    9,    7,    8,    7,
   10,    7,  165,  166,  167,  169,    7,   25,   71,   71,
   71,   66,   49,   66,   66,   66,   37,   65,    0,   65,
   65,   65,   88,  115,    0,    0,    0,    0,    0,    0,
   66,   66,   66,    0,    0,    0,   65,   65,   65,  155,
   42,    0,    0,    0,   42,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,    2,  138,   56,    2,   46,
    2,    0,   19,  142,   93,   56,    2,   64,    2,    0,
   20,   21,   57,   47,   22,   47,   70,    2,   80,    2,
   42,   47,   42,   47,  148,    2,  150,    2,    0,   19,
   26,  121,   47,    0,   47,   19,  152,    2,  159,    2,
   47,  152,   47,    2,  163,   60,    2,  120,    0,   94,
   19,   95,   47,   28,   47,   20,   21,   47,   20,   21,
   47,   30,   22,   82,    2,   19,  110,  108,  109,    3,
   83,   84,    4,   58,  125,    5,   82,    2,   19,  129,
   58,   58,    3,   62,  173,    4,   76,    2,    5,   82,
    2,   19,   63,    1,    2,    3,  133,    2,    4,    3,
   64,    5,    4,   36,    2,    5,    6,   61,    0,    3,
    0,    0,    4,   41,    2,    5,    6,  135,    2,    3,
    0,    0,    4,    3,    0,    5,    4,   82,    2,    5,
    0,    0,    0,    3,  141,    2,    4,    0,    0,    5,
    3,    0,    0,    4,   71,    0,    5,   71,   71,   71,
    0,   87,    2,  144,    2,  168,    2,    3,    0,    3,
    4,    3,    4,    5,    4,    5,   66,    5,    0,   66,
   66,   66,   65,    0,   12,   65,   65,   65,   67,    0,
   67,   67,   67,   12,   70,    0,   70,   70,   70,  128,
    0,   94,    0,   95,    0,    0,    0,   67,   67,   67,
   94,    0,   95,   70,   70,   70,    0,    0,  110,  108,
  109,   99,    0,  100,    0,   85,   85,  110,  108,  109,
    0,    0,    0,    0,    0,    0,    0,    0,  110,  108,
  109,    0,   50,    0,    0,    0,    0,   50,   62,   50,
   50,    0,    0,   50,   50,   62,    0,   62,    0,    0,
    0,    0,   50,   50,    0,    0,    0,    0,    0,    0,
  139,    0,  139,   93,    0,  105,  106,  107,  104,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  104,    0,  104,  104,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   50,   50,   85,
    0,    0,   50,   50,   50,   50,    0,    0,   85,    0,
    0,    0,    0,    0,   50,    0,    0,   50,   50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   85,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  176,  177,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,    0,    0,   67,   67,   67,   70,
    0,    0,   70,   70,   70,   93,    0,  105,  106,  107,
    0,    0,    0,    0,   93,    0,  105,  106,  107,    0,
    0,    0,    0,    0,  114,    0,    0,  105,  106,  107,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   42,   38,   59,   42,   40,   42,   45,   38,   45,   38,
   43,   42,   45,   42,   45,  256,   45,  256,   38,  258,
   38,  257,   42,  264,   42,   45,   38,   45,   38,   44,
   42,   40,   42,   45,   40,   45,   41,   40,   38,   19,
   38,  271,   42,   38,   42,   45,   38,   45,   42,  256,
   45,   42,   40,   45,   44,  256,   44,   40,   44,   39,
   40,  256,  263,  264,  271,   40,  256,   40,   42,   42,
  272,  266,  267,   47,    6,  270,  266,  267,  258,   41,
   40,   44,   42,   72,   41,   74,   75,   43,   41,   45,
   22,  264,   42,   40,  256,   42,  258,   41,   44,   42,
   32,   33,   42,  266,  267,   41,   26,   27,  264,   42,
   30,   31,   41,   41,   94,   95,  125,   41,   83,   42,
  116,  117,  118,   42,   41,   90,   91,   32,   41,   34,
   41,  111,   44,   42,  114,  115,  256,  257,   99,  100,
   42,   41,  101,  102,  257,    0,   44,   44,   44,   41,
   44,   43,   44,   45,   44,   44,   44,   42,   44,   42,
   44,   42,  127,  128,  129,  130,   44,   44,   60,   61,
   62,   41,   44,   43,   44,   45,    9,   41,   -1,   43,
   44,   45,   42,   59,   -1,   -1,   -1,   -1,   -1,   -1,
   60,   61,   62,   -1,   -1,   -1,   60,   61,   62,  256,
  123,   -1,   -1,   -1,  123,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,  125,  256,  257,  256,
  257,   -1,  258,  125,  257,  256,  257,  256,  257,   -1,
  266,  267,  271,  272,  270,  272,  256,  257,  256,  257,
  123,  272,  123,  272,  256,  257,  256,  257,   -1,  258,
  256,  256,  272,   -1,  272,  258,  256,  257,  256,  257,
  272,  256,  272,  257,  256,  256,  257,   41,   -1,   43,
  258,   45,  272,  256,  272,  266,  267,  272,  266,  267,
  272,  256,  270,  256,  257,  258,   60,   61,   62,  262,
  263,  264,  265,  256,  256,  268,  256,  257,  258,  256,
  263,  264,  262,  256,  264,  265,  256,  257,  268,  256,
  257,  258,  256,  256,  257,  262,  256,  257,  265,  262,
  256,  268,  265,  256,  257,  268,  269,  256,   -1,  262,
   -1,   -1,  265,  256,  257,  268,  269,  256,  257,  262,
   -1,   -1,  265,  262,   -1,  268,  265,  256,  257,  268,
   -1,   -1,   -1,  262,  256,  257,  265,   -1,   -1,  268,
  262,   -1,   -1,  265,  256,   -1,  268,  259,  260,  261,
   -1,  256,  257,  256,  257,  256,  257,  262,   -1,  262,
  265,  262,  265,  268,  265,  268,  256,  268,   -1,  259,
  260,  261,  256,   -1,    0,  259,  260,  261,   41,   -1,
   43,   44,   45,    9,   41,   -1,   43,   44,   45,   41,
   -1,   43,   -1,   45,   -1,   -1,   -1,   60,   61,   62,
   43,   -1,   45,   60,   61,   62,   -1,   -1,   60,   61,
   62,   43,   -1,   45,   -1,   41,   42,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,   61,
   62,   -1,   19,   -1,   -1,   -1,   -1,   24,   25,   26,
   27,   -1,   -1,   30,   31,   32,   -1,   34,   -1,   -1,
   -1,   -1,   39,   40,   -1,   -1,   -1,   -1,   -1,   -1,
   86,   -1,   88,  257,   -1,  259,  260,  261,   55,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   72,   -1,   74,   75,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   94,   95,  135,
   -1,   -1,   99,  100,  101,  102,   -1,   -1,  144,   -1,
   -1,   -1,   -1,   -1,  111,   -1,   -1,  114,  115,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  168,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  155,  156,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,   -1,   -1,  259,  260,  261,  256,
   -1,   -1,  259,  260,  261,  257,   -1,  259,  260,  261,
   -1,   -1,   -1,   -1,  257,   -1,  259,  260,  261,   -1,
   -1,   -1,   -1,   -1,  256,   -1,   -1,  259,  260,  261,
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
"contenidoPrograma : error ','",
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
"listaVariables : identificador",
"listaVariables : listaVariables ';' identificador",
"listaVariables : listaVariables error identificador",
"identificador : '*' ID",
"identificador : ID",
"asignacionCte : identificador ASIGNACION cte",
"asignacionCte : identificador error cte",
"asignacionCte : identificador ASIGNACION error",
"asignacionCte : error ASIGNACION cte",
"sentenciaEjecutable : sentenciaIf",
"sentenciaEjecutable : sentenciaWhile",
"sentenciaEjecutable : asignacion",
"sentenciaEjecutable : sentenciaPrint",
"sentenciaIf : encabezadoIf cuerpoIf",
"encabezadoIf : IF '(' condicion ')'",
"encabezadoIf : error '(' condicion ')'",
"encabezadoIf : IF error condicion ')'",
"encabezadoIf : IF '(' error ')'",
"encabezadoIf : IF '(' condicion error",
"cuerpoIf : bloqueSentencias ELSE bloqueSentencias END_IF",
"cuerpoIf : error ELSE bloqueSentencias END_IF",
"cuerpoIf : bloqueSentencias error bloqueSentencias END_IF",
"cuerpoIf : bloqueSentencias ELSE error END_IF",
"cuerpoIf : bloqueSentencias ELSE bloqueSentencias error",
"cuerpoIf : bloqueSentencias END_IF",
"cuerpoIf : bloqueSentencias error",
"cuerpoIf : error END_IF",
"sentenciaWhile : WHILE '(' condicion ')' bloqueSentencias",
"sentenciaWhile : WHILE error condicion ')' bloqueSentencias",
"sentenciaWhile : WHILE '(' error ')' bloqueSentencias",
"sentenciaWhile : WHILE '(' condicion error bloqueSentencias",
"sentenciaWhile : WHILE '(' condicion ')' error",
"asignacion : identificador ASIGNACION expresion",
"asignacion : error ASIGNACION expresion",
"asignacion : identificador error expresion",
"asignacion : identificador ASIGNACION error",
"bloqueSentencias : sentenciaEjecutable ','",
"bloqueSentencias : '{' conjuntoSentenciasEjecutables '}'",
"bloqueSentencias : '{' error '}'",
"bloqueSentencias : error conjuntoSentenciasEjecutables '}'",
"bloqueSentencias : '{' conjuntoSentenciasEjecutables error",
"conjuntoSentenciasEjecutables : sentenciaEjecutable ','",
"conjuntoSentenciasEjecutables : conjuntoSentenciasEjecutables sentenciaEjecutable ','",
"condicion : expresion comparador expresion",
"condicion : error comparador expresion",
"condicion : expresion error expresion",
"condicion : expresion comparador error",
"expresion : expresion '+' termino",
"expresion : expresion '+' error",
"expresion : expresion '-' error",
"expresion : error '+' expresion",
"expresion : error '-' expresion",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : identificador",
"factor : cte",
"cte : CTE",
"cte : '-' CTE",
"cte : '&' ID",
"cte : '&' error",
"cte : error ID",
"comparador : '='",
"comparador : '>'",
"comparador : '<'",
"comparador : COMP_MAYOR_IGUAL",
"comparador : COMP_MENOR_IGUAL",
"comparador : COMP_DIFERENTE",
"sentenciaPrint : PRINT '(' CADENA ')'",
"sentenciaPrint : error '(' CADENA ')'",
"sentenciaPrint : PRINT error CADENA ')'",
"sentenciaPrint : PRINT '(' error ')'",
"sentenciaPrint : PRINT '(' CADENA error",
};

//#line 301 "gramatica.y"
    private AnalizadorLexico al;
    private ArrayList<String> listaDeTokens;
    private ArrayList<String> listaDeReglas;
    private ArrayList<String> listaDeErroresLexicos;
    private ArrayList<String> listaDeErroresSintacticos;
    private ArrayList<String> listaDeErroresSemanticos;

    public Parser(Reader fuente) {
        al = new AnalizadorLexico(fuente);
        listaDeReglas = new ArrayList<>();
        listaDeTokens = new ArrayList<>();
        listaDeErroresLexicos = new ArrayList<>();
        listaDeErroresSintacticos = new ArrayList<>();
        listaDeErroresSemanticos = new ArrayList<>();
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

    public HashMap<String, EntradaTablaSimbolos> getTablaSimbolos() {
        return al.getTablaDeSimbolos();
    }
//#line 514 "Parser.java"
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
//#line 14 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Programa reconocido en linea %1$d",al.getLinea()));}
break;
case 2:
//#line 18 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 3:
//#line 20 "gramatica.y"
{addErrorSintactico(String.format("sentencia mal declarada recuperando en ',' siguiente en linea %1$d",al.getLinea()));}
break;
case 4:
//#line 22 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 5:
//#line 26 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 6:
//#line 28 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 7:
//#line 32 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));}
break;
case 8:
//#line 34 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 9:
//#line 36 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 10:
//#line 38 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 11:
//#line 40 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 12:
//#line 42 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));}
break;
case 13:
//#line 44 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 14:
//#line 46 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 15:
//#line 48 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 16:
//#line 52 "gramatica.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
break;
case 17:
//#line 54 "gramatica.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
break;
case 18:
//#line 58 "gramatica.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
break;
case 19:
//#line 60 "gramatica.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
break;
case 20:
//#line 62 "gramatica.y"
{addErrorSintactico(String.format(" declaracion de lista de variables esperaba un ; entre variables en linea %1$d",al.getLinea()));}
break;
case 21:
//#line 66 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
                                                            yyval=val_peek(0);}
break;
case 22:
//#line 69 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
                                                            yyval=val_peek(0);}
break;
case 23:
//#line 74 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
break;
case 24:
//#line 76 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 25:
//#line 78 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 26:
//#line 80 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 27:
//#line 84 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 28:
//#line 86 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 29:
//#line 88 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 30:
//#line 90 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 31:
//#line 94 "gramatica.y"
{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
break;
case 33:
//#line 100 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 34:
//#line 102 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 35:
//#line 104 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 36:
//#line 106 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 38:
//#line 112 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 39:
//#line 114 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 40:
//#line 116 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 41:
//#line 118 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 43:
//#line 122 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 44:
//#line 124 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 45:
//#line 128 "gramatica.y"
{addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
break;
case 46:
//#line 130 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 47:
//#line 132 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 48:
//#line 134 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 49:
//#line 136 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 50:
//#line 140 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));
                                                                    Terceto terceto = new Terceto("ASIGNACION", (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
                                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                                                                    yyval=new ParserVal(terceto); }
break;
case 51:
//#line 145 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 52:
//#line 147 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 53:
//#line 149 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 54:
//#line 153 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 55:
//#line 155 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 56:
//#line 157 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 57:
//#line 159 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 58:
//#line 161 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 59:
//#line 165 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 60:
//#line 167 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 61:
//#line 171 "gramatica.y"
{addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));
                                                    Terceto terceto = new Terceto(val_peek(1).sval, (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                                                    yyval=new ParserVal(terceto); }
break;
case 62:
//#line 176 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 63:
//#line 178 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 64:
//#line 180 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 65:
//#line 184 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
                                                    Terceto terceto = new Terceto("+", (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                                                    yyval=new ParserVal(terceto); }
break;
case 66:
//#line 189 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 67:
//#line 191 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 68:
//#line 193 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 69:
//#line 195 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 70:
//#line 197 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
                                                    Terceto terceto = new Terceto("-", (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                                                    yyval=new ParserVal(terceto); }
break;
case 71:
//#line 202 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
                                                    yyval=val_peek(0);}
break;
case 72:
//#line 207 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
                                                    Terceto terceto = new Terceto("*", (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                                                    yyval=new ParserVal(terceto); }
break;
case 73:
//#line 212 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
                                                    Terceto terceto = new Terceto("/", (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                                                    yyval=new ParserVal(terceto); }
break;
case 74:
//#line 217 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=val_peek(0);}
break;
case 75:
//#line 222 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=val_peek(0);}
break;
case 76:
//#line 225 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=val_peek(0);}
break;
case 77:
//#line 230 "gramatica.y"
{    EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
    if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
        if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
            addErrorSintactico(String.format("warning linteger cte positiva mayor al maximo permitido en linea %1$d", al.getLinea()));
            String nuevoLexema = String.valueOf(AnalizadorLexico.MAX_LONG - 1);
            al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
            entradaTablaSimbolos.setLexema(nuevoLexema);
            al.getTablaDeSimbolos().put(entradaTablaSimbolos.getLexema(), entradaTablaSimbolos);
        }
    }
    /*addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));*/
    yyval=val_peek(0);
}
break;
case 78:
//#line 244 "gramatica.y"
{   EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
    String lexema = "-" + (entradaTablaSimbolos.getLexema());
    if (!al.estaEnTabla(lexema)) {
        /* no esta en tabla, agrega a TS*/
        EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
        al.agregarATablaSimbolos(elementoTS);
    }
    /*addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));*/
    if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
        if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
            al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
        }
    }
    yyval=new ParserVal(al.getTablaDeSimbolos().get(lexema));
}
break;
case 79:
//#line 260 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea())); */}
break;
case 80:
//#line 262 "gramatica.y"
{addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
break;
case 81:
//#line 264 "gramatica.y"
{addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
break;
case 82:
//#line 268 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=new ParserVal("=");}
break;
case 83:
//#line 271 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=new ParserVal(">");}
break;
case 84:
//#line 274 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()))*/;
                                                    yyval=new ParserVal("<");}
break;
case 85:
//#line 277 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=new ParserVal("COMP_MAYOR_IGUAL");}
break;
case 86:
//#line 280 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
                                                     yyval=new ParserVal("COMP_MENOR_IGUAL");}
break;
case 87:
//#line 283 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
                                                    yyval=new ParserVal("COMP_DIFERENTE");}
break;
case 88:
//#line 288 "gramatica.y"
{addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));
                                                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(new Terceto("PRINT",(EntradaTablaSimbolos)val_peek(1).obj));}
break;
case 89:
//#line 291 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 90:
//#line 293 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 91:
//#line 295 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 92:
//#line 297 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
//#line 1076 "Parser.java"
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

%%
    programa:
    contenidoPrograma                                   {addReglaSintacticaReconocida(String.format("Programa reconocido en linea %1$d",al.getLinea()));}
    ;

    contenidoPrograma:
    sentencia ','                                       {addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
        |
    error ','                                           {addErrorSintactico(String.format("sentencia mal declarada recuperando en ',' siguiente en linea %1$d",al.getLinea()));}
        |
    contenidoPrograma sentencia ','                     {addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
    ;

    sentencia:
    sentenciaDeclarativa                                {addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
        |
    sentenciaEjecutable                                 {addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
    ;

    sentenciaDeclarativa:
    LET MUT tipo listaVariables                         {addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));}
        |
    LET error tipo listaVariables                       {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
    error MUT tipo listaVariables                       {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
    LET MUT error listaVariables                        {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
    LET MUT tipo error                                  {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
    LET tipo asignacionCte                              {addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));}
        |
    error tipo asignacionCte                            {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
    LET error asignacionCte                             {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
    LET tipo error                                      {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
    ;

    tipo:
    LINTEGER                                            {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
        |
    SINGLE                                              {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
    ;

    listaVariables:
    identificador                                          {addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
        |
    listaVariables ';' identificador                       {addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
        |
    listaVariables error identificador                     {addErrorSintactico(String.format(" declaracion de lista de variables esperaba un ; entre variables en linea %1$d",al.getLinea()));}
    ;

    identificador:
        '*' ID                                              {/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
    $$=$2;}
        |
    ID                                                  {/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
    $$=$1;}
    ;

    asignacionCte:
    identificador ASIGNACION cte                           {addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
        |
    identificador error cte                                {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
        |
    identificador ASIGNACION error                         {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
        |
    error ASIGNACION cte                                {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
    ;

    sentenciaEjecutable:
    sentenciaIf                                         {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
        |
    sentenciaWhile                                      {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
        |
    asignacion                                          {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
        |
    sentenciaPrint                                      {addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
    ;

    sentenciaIf:
    encabezadoIf cuerpoIf                               {addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
    ;

    encabezadoIf:
    IF '(' condicion ')'
        |
    error '(' condicion ')'                             {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
        |
    IF error condicion ')'                              {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
        |
    IF '(' error ')'                                    {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
        |
    IF '(' condicion error                              {addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
    ;

    cuerpoIf:
    bloqueSentencias ELSE bloqueSentencias END_IF
        |
    error ELSE bloqueSentencias END_IF                          {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
    bloqueSentencias error bloqueSentencias END_IF              {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
    bloqueSentencias ELSE error END_IF                          {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
    bloqueSentencias ELSE bloqueSentencias error                {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
    bloqueSentencias END_IF
        |
    bloqueSentencias error                                      {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
    error END_IF                                                {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
    ;

    sentenciaWhile:
    WHILE '(' condicion ')' bloqueSentencias                    {addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
        |
    WHILE error condicion ')' bloqueSentencias                  {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
        |
    WHILE '(' error ')' bloqueSentencias                        {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
        |
    WHILE '(' condicion error bloqueSentencias                  {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
        |
    WHILE '(' condicion ')' error                               {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
    ;

    asignacion:
    identificador ASIGNACION expresion                             {addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));
    Terceto terceto = new Terceto("ASIGNACION", (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
    $$=new ParserVal(terceto); }
        |
    error ASIGNACION expresion                                  {addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
        |
    identificador error expresion                                  {addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
        |
    identificador ASIGNACION error                                 {addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
    ;

    bloqueSentencias:
    sentenciaEjecutable ','                                     {addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
        |
                '{' conjuntoSentenciasEjecutables '}'                      {addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
        |
                '{' error '}'                                              {addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
        |
    error conjuntoSentenciasEjecutables '}'                    {addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
        |
                '{' conjuntoSentenciasEjecutables error                    {addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
    ;

    conjuntoSentenciasEjecutables:
    sentenciaEjecutable ','                                     {addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
        |
    conjuntoSentenciasEjecutables sentenciaEjecutable ','       {addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
    ;

    condicion:
    expresion comparador expresion              {addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));
    Terceto terceto = new Terceto($2.sval, (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
    $$=new ParserVal(terceto); }
        |
    error comparador expresion                  {addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
        |
    expresion error expresion                   {addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
        |
    expresion comparador error                  {addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
    ;

    expresion:
    expresion '+' termino                       {addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
    Terceto terceto = new Terceto("+", (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
    $$=new ParserVal(terceto); }
        |
    expresion '+' error                         {addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
        |
    expresion '-' error                         {addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
        |
    error '+' expresion                         {addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
        |
    error '-' expresion                         {addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
        |
    expresion '-' termino                       {addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
    Terceto terceto = new Terceto("-", (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
    $$=new ParserVal(terceto); }
        |
    termino                                     {addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
    $$=$1;}
    ;

    termino:
    termino '*' factor                          {/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
    if ()
    Terceto terceto = new Terceto("*", (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
    $$=new ParserVal(terceto); }
        |
    termino '/' factor                          {/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
    Terceto terceto = new Terceto("/", (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
    $$=new ParserVal(terceto); }
        |
    factor                                      {/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
    $$=$1;}
    ;

    factor:
    identificador                               {/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/
    $$=$1;}
        |
    cte                                         {/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/
    $$=$1;}
    ;

    cte:
    CTE                                         {    EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) ($1.obj);
    if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
        if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
            addErrorSintactico(String.format("warning linteger cte positiva mayor al maximo permitido en linea %1$d", al.getLinea()));
            String nuevoLexema = String.valueOf(AnalizadorLexico.MAX_LONG - 1);
            al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
            entradaTablaSimbolos.setLexema(nuevoLexema);
            al.getTablaDeSimbolos().put(entradaTablaSimbolos.getLexema(), entradaTablaSimbolos);
        }
    }
    /*addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));*/
    $$=$1;
}
        |
                '-'CTE %prec '*'                            {   EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) ($2.obj);
    String lexema = "-" + (entradaTablaSimbolos.getLexema());
    if (!al.estaEnTabla(lexema)) {
        // no esta en tabla, agrega a TS
        EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
        al.agregarATablaSimbolos(elementoTS);
    }
    /*addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));*/
    if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
        if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
            al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
        }
    }
    $$=new ParserVal(al.getTablaDeSimbolos().get(lexema));
}
        |
                '&'ID                                       {/*addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea())); */}
        |
                '&'error                                    {addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
        |
    error ID                                    {addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
    ;

    comparador:
        '='                                         {/*addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));*/
    $$=new ParserVal("=");}
        |
                '>'                                         {/*addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));*/
    $$=new ParserVal(">");}
        |
                '<'                                         {/*addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()))*/;
    $$=new ParserVal("<");}
        |
    COMP_MAYOR_IGUAL                            {/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
    $$=new ParserVal("COMP_MAYOR_IGUAL");}
        |
    COMP_MENOR_IGUAL                            {/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
    $$=new ParserVal("COMP_MENOR_IGUAL");}
        |
    COMP_DIFERENTE                              {/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
    $$=new ParserVal("COMP_DIFERENTE");}
    ;

    sentenciaPrint:
    PRINT '(' CADENA ')'                        {addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(new Terceto("PRINT",(EntradaTablaSimbolos)$3.obj));}
        |
    error '(' CADENA ')'                        {addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
        |
    PRINT error CADENA ')'                      {addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
        |
    PRINT '(' error ')'                         {addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
        |
    PRINT '(' CADENA error                      {addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
    ;

%%
    private AnalizadorLexico al;
    private ArrayList<String> listaDeTokens;
    private ArrayList<String> listaDeReglas;
    private ArrayList<String> listaDeErroresLexicos;
    private ArrayList<String> listaDeErroresSintacticos;
    private ArrayList<String> getListaDeErroresSemanticos;

    public Parser(Reader fuente) {
        al = new AnalizadorLexico(fuente);
        listaDeReglas = new ArrayList<>();
        listaDeTokens = new ArrayList<>();
        listaDeErroresLexicos = new ArrayList<>();
        listaDeErroresSintacticos = new ArrayList<>();
        listaDeErroresSemanticos = new ArrayList<>();
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

    private void addErrorSemantico(String error) {
        listaDeErroresSemanticos.add(error);
    }

    private void addReglaSintacticaReconocida(String regla) {
        listaDeReglas.add(regla);
    }

    public HashMap<String, EntradaTablaSimbolos> getTablaSimbolos() {
        return al.getTablaDeSimbolos();
    }

}
//################### END OF CLASS ##############################
