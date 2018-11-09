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
import java.util.HashSet;

//#line 22 "Parser.java"




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
    9,    9,    8,    8,    7,    7,    7,    7,    4,    4,
    4,    4,   11,   15,   15,   15,   15,   15,   16,   16,
   16,   16,   16,   16,   16,   16,   18,   19,   12,   12,
   12,   12,   12,   21,   13,   13,   13,   13,   20,   20,
   20,   20,   20,   23,   23,   17,   17,   17,   17,   22,
   22,   22,   22,   22,   22,   22,   25,   25,   25,   26,
   26,   10,   10,   10,   10,   10,   24,   24,   24,   24,
   24,   24,   14,   14,   14,   14,   14,
};
final static short yylen[] = {                            2,
    1,    2,    2,    3,    1,    1,    4,    4,    4,    4,
    4,    3,    3,    3,    3,    1,    1,    1,    3,    3,
    2,    1,    2,    1,    3,    3,    3,    3,    1,    1,
    1,    1,    2,    4,    4,    4,    4,    4,    4,    4,
    4,    4,    4,    2,    2,    2,    1,    1,    5,    5,
    5,    5,    5,    1,    3,    3,    3,    3,    2,    3,
    3,    3,    3,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    2,    2,    2,    2,    1,    1,    1,    1,
    1,    1,    4,    4,    4,    4,    4,
};
final static short yydefred[] = {                         0,
    0,   22,    0,    0,   54,    0,    0,    0,    0,    0,
    5,    6,    0,   29,   30,   31,   32,    0,    0,    0,
   16,   17,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   21,    0,    0,    2,    0,    0,    0,
    0,    0,   33,    0,   47,    0,    0,    0,   82,    0,
    0,   80,   81,    0,    0,   79,    0,    0,    0,    0,
    0,    0,   24,    0,   13,    0,    0,    0,    0,    0,
    0,    0,    0,   14,    0,    0,    0,   12,    4,    0,
    0,    0,    0,    0,   46,    0,    0,    0,    0,   59,
    0,    0,   44,    0,    0,    0,   86,    0,    0,   83,
   85,   84,    0,    0,    0,    0,    0,   18,   90,   91,
   92,   87,   88,   89,    0,   94,   35,    0,    0,    0,
   23,    0,    0,   36,   37,   38,   34,   95,   96,   97,
   93,    0,    0,   11,    0,    0,    0,   48,   64,   62,
    0,   61,    0,   60,    0,    0,    0,    0,    0,    0,
    0,   73,   74,    0,    0,    0,    0,    0,   77,   78,
    0,    0,    0,    0,    0,    0,   28,   26,    0,   25,
   40,   65,   41,   42,   43,   39,   50,   51,   52,    0,
   49,   20,   19,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   42,   26,  107,   65,  108,   13,   53,
   14,   15,   16,   17,   18,   43,   60,   44,  137,  138,
   19,   61,   87,  115,   55,   56,
};
final static short yysindex[] = {                        58,
   13,    0,   -5,   18,    0, -193, -235,    0,   68,  -14,
    0,    0, -238,    0,    0,    0,    0,   78,   26,  -36,
    0,    0, -131,    0,  -38,  -41,  -30,  -28, -229, -216,
   10, -155,   51,    0,  -35,   16,    0,  -36,  -19,   28,
  116,   23,    0, -135,    0,  -30,  -17,  -32,    0, -200,
 -114,    0,    0,   73,   14,    0,    7,  378,   34,   74,
  389, -179,    0, -165,    0, -172,   84,  227,   -4,   89,
   97,   39,    7,    0,    7,   61, -179,    0,    0,   73,
  -32,   73,   -2,   82,    0,   55,   92,   -8,   99,    0,
   82,  118,    0,  105,  369,   44,    0,  -36,  -36,    0,
    0,    0,  -11,   -9,    1,    1,  -56,    0,    0,    0,
    0,    0,    0,    0,  -36,    0,    0,  -36,    3,    6,
    0,    6,    9,    0,    0,    0,    0,    0,    0,    0,
    0,  -56,  -56,    0,  -56,   54, -169,    0,    0,    0,
   75,    0,   -2,    0, -132,   41, -240,   82,   82,   82,
  120,    0,    0, -148,   14, -148,   14, -148,    0,    0,
    7,    7,   73,   73,  -32,   73,    0,    0, -148,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  140,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  107,  109,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  113,    0,    0,  115,
  117,  119,    0,    0,    0,    0,    0,    0,    0,    0,
  129,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  133,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  135,  139,    0,  141,    0,    0,    0,    0,    0,
    0,    0,   38,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  131,  137,  358,  364,    0,    0,    0,
    0,    0,   48,   57,   65,   72,    0,    0,  143,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  144,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  175,    0,   50,   91,   29,  100,   45,  408,  -58,
    0,    0,    0,    0,    0,    0,   80,    0,   53,  311,
    0,   49,  108,  128,   52,   60,
};
final static int YYTABLESIZE=650;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   64,   51,  162,    7,   25,    7,   50,   51,   50,   51,
   98,    7,   99,    7,   50,  175,   50,   38,   51,   39,
   51,   34,    7,  176,    7,   50,   51,   50,   51,   37,
    7,   25,    7,   50,   28,   50,  127,   25,   51,   71,
   51,   70,    7,   51,    7,   50,   51,   50,   64,   12,
   50,   64,   25,   50,   72,  105,   24,   30,   12,   79,
  106,  167,   31,  168,  170,   47,   90,   25,   54,    7,
   66,  100,   21,   22,  116,   66,   32,   66,  120,  131,
   25,   63,    7,  122,  151,  123,   80,   82,   67,   86,
   86,  121,   64,   25,  171,    7,   33,   68,  139,    7,
   75,  132,   64,  133,  135,   69,   67,   69,   97,    7,
   21,   22,   66,   57,  117,  103,  142,  104,  172,    7,
   91,   73,   76,    7,  124,   94,   96,   92,   93,  128,
   74,  173,   78,    7,   21,   22,  141,  129,  141,    1,
    7,  101,  102,  145,  147,  148,  152,  153,   89,   76,
   56,   76,   76,   76,  155,  157,   15,    7,   57,    7,
   58,    7,   55,  163,  159,  160,  164,  166,   76,   76,
   76,   71,   45,   71,   71,   71,    9,   70,    8,   70,
   70,   70,   10,   36,    7,   86,   27,   53,  119,    0,
   71,   71,   71,    0,    0,   86,   70,   70,   70,  161,
   41,    0,    0,    0,   41,  182,  183,    0,    0,    0,
    0,    0,    0,    0,   62,   63,  140,   58,    2,   48,
    2,    0,   20,  144,   97,   58,    2,   68,    2,   86,
   21,   22,   59,   49,   23,   49,   81,    2,   95,    2,
   41,   49,   41,   49,  154,    2,  156,    2,    0,   20,
   27,  126,   49,    0,   49,   20,  158,    2,  165,    2,
   49,  158,   49,   63,  169,   62,   63,  125,    0,   98,
   20,   99,   49,   29,   49,   21,   22,   49,   21,   22,
   49,   46,   23,   83,    2,   20,  114,  112,  113,    3,
   84,   85,    4,   63,  130,    5,   83,    2,   20,  150,
   63,   63,    3,   67,  174,    4,   77,   63,    5,   83,
    2,   20,   68,    1,    2,    3,  134,   63,    4,    3,
   69,    5,    4,   35,    2,    5,    6,   66,   45,    3,
    0,    0,    4,   40,    2,    5,    6,  136,    2,    3,
    0,    0,    4,    3,    0,    5,    4,   83,    2,    5,
    0,    0,    0,    3,  143,    2,    4,    0,    0,    5,
    3,    0,    0,    4,   76,    0,    5,   76,   76,   76,
    0,   88,    2,  146,    2,  180,    2,    3,    0,    3,
    4,    3,    4,    5,    4,    5,   71,    5,    0,   71,
   71,   71,   70,    0,    0,   70,   70,   70,   72,    0,
   72,   72,   72,    0,   75,    0,   75,   75,   75,  149,
    0,   98,    0,   99,    0,    0,    0,   72,   72,   72,
   98,    0,   99,   75,   75,   75,    0,   52,  114,  112,
  113,  103,   52,  104,   52,   52,    0,  114,  112,  113,
    0,    0,    0,    0,    0,   52,   52,    0,  114,  112,
  113,    0,    0,   52,   52,    0,    0,    0,  177,  178,
  179,  181,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   97,    0,  109,  110,  111,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   52,   52,    0,    0,    0,
   52,   52,   52,   52,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,    0,   52,   52,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   72,    0,    0,   72,   72,   72,   75,
    0,    0,   75,   75,   75,   97,    0,  109,  110,  111,
    0,    0,    0,    0,   97,    0,  109,  110,  111,    0,
    0,    0,    0,    0,  118,    0,    0,  109,  110,  111,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   42,   38,   59,   42,   40,   42,   45,   38,   45,   38,
   43,   42,   45,   42,   45,  256,   45,  256,   38,  258,
   38,  257,   42,  264,   42,   45,   38,   45,   38,   44,
   42,   40,   42,   45,   40,   45,   41,   40,   38,  256,
   38,  271,   42,   38,   42,   45,   38,   45,   42,    0,
   45,   42,   40,   45,  271,   42,   44,   40,    9,   44,
   47,  120,  256,  122,  123,   40,   44,   40,   20,   42,
   26,  272,  266,  267,   41,   31,  270,   33,  258,   41,
   40,   44,   42,  256,   41,  258,   38,   39,   41,   40,
   41,  257,   42,   40,  264,   42,    6,   41,   44,   42,
  256,   73,   42,   75,   76,   41,   27,   28,  257,   42,
  266,  267,   41,   23,   41,   43,  125,   45,   44,   42,
  256,   31,   32,   42,   41,   46,   47,  263,  264,   41,
   31,  264,   33,   42,  266,  267,   87,   41,   89,    0,
   42,  256,  257,   91,   92,   41,   98,   99,   41,   41,
   44,   43,   44,   45,  103,  104,   44,   42,   44,   42,
   44,   42,   44,  115,  105,  106,  118,  119,   60,   61,
   62,   41,   44,   43,   44,   45,   44,   41,   44,   43,
   44,   45,   44,    9,   44,  136,   44,   44,   61,   -1,
   60,   61,   62,   -1,   -1,  146,   60,   61,   62,  256,
  123,   -1,   -1,   -1,  123,  161,  162,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,  125,  256,  257,  256,
  257,   -1,  258,  125,  257,  256,  257,  256,  257,  180,
  266,  267,  271,  272,  270,  272,  256,  257,  256,  257,
  123,  272,  123,  272,  256,  257,  256,  257,   -1,  258,
  256,  256,  272,   -1,  272,  258,  256,  257,  256,  257,
  272,  256,  272,  257,  256,  256,  257,   41,   -1,   43,
  258,   45,  272,  256,  272,  266,  267,  272,  266,  267,
  272,  256,  270,  256,  257,  258,   60,   61,   62,  262,
  263,  264,  265,  256,  256,  268,  256,  257,  258,  256,
  263,  264,  262,  256,  264,  265,  256,  257,  268,  256,
  257,  258,  256,  256,  257,  262,  256,  257,  265,  262,
  256,  268,  265,  256,  257,  268,  269,  256,   18,  262,
   -1,   -1,  265,  256,  257,  268,  269,  256,  257,  262,
   -1,   -1,  265,  262,   -1,  268,  265,  256,  257,  268,
   -1,   -1,   -1,  262,  256,  257,  265,   -1,   -1,  268,
  262,   -1,   -1,  265,  256,   -1,  268,  259,  260,  261,
   -1,  256,  257,  256,  257,  256,  257,  262,   -1,  262,
  265,  262,  265,  268,  265,  268,  256,  268,   -1,  259,
  260,  261,  256,   -1,   -1,  259,  260,  261,   41,   -1,
   43,   44,   45,   -1,   41,   -1,   43,   44,   45,   41,
   -1,   43,   -1,   45,   -1,   -1,   -1,   60,   61,   62,
   43,   -1,   45,   60,   61,   62,   -1,   20,   60,   61,
   62,   43,   25,   45,   27,   28,   -1,   60,   61,   62,
   -1,   -1,   -1,   -1,   -1,   38,   39,   -1,   60,   61,
   62,   -1,   -1,   46,   47,   -1,   -1,   -1,  148,  149,
  150,  151,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   -1,  259,  260,  261,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   98,   99,   -1,   -1,   -1,
  103,  104,  105,  106,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  115,   -1,   -1,  118,  119,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
"listaVariables : identificadorDec",
"listaVariables : listaVariables ';' identificadorDec",
"listaVariables : listaVariables error identificadorDec",
"identificador : '*' ID",
"identificador : ID",
"identificadorDec : '*' ID",
"identificadorDec : ID",
"asignacionCte : identificadorDec ASIGNACION cte",
"asignacionCte : identificadorDec error cte",
"asignacionCte : identificadorDec ASIGNACION error",
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
"cuerpoIf : cuerpoThen ELSE cuerpoElse END_IF",
"cuerpoIf : error ELSE cuerpoElse END_IF",
"cuerpoIf : cuerpoThen error cuerpoElse END_IF",
"cuerpoIf : cuerpoThen ELSE error END_IF",
"cuerpoIf : cuerpoThen ELSE cuerpoElse error",
"cuerpoIf : cuerpoThen END_IF",
"cuerpoIf : cuerpoThen error",
"cuerpoIf : error END_IF",
"cuerpoThen : bloqueSentencias",
"cuerpoElse : bloqueSentencias",
"sentenciaWhile : inicioWhile '(' condicion ')' bloqueSentencias",
"sentenciaWhile : inicioWhile error condicion ')' bloqueSentencias",
"sentenciaWhile : inicioWhile '(' error ')' bloqueSentencias",
"sentenciaWhile : inicioWhile '(' condicion error bloqueSentencias",
"sentenciaWhile : inicioWhile '(' condicion ')' error",
"inicioWhile : WHILE",
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

//#line 614 "gramatica.y"
    private AnalizadorLexico al;
    private ArrayList<String> listaDeTokens;
    private ArrayList<String> listaDeReglas;
    private ArrayList<String> listaDeErroresLexicos;
    private ArrayList<String> listaDeErroresSintacticos;
    private ArrayList<String> listaDeErroresSemanticos;
    private HashSet<String> listaDeLexemasDeclarados;

    public Parser(Reader fuente) {
        al = new AnalizadorLexico(fuente);
        listaDeReglas = new ArrayList<>();
        listaDeTokens = new ArrayList<>();
        listaDeErroresLexicos = new ArrayList<>();
        listaDeErroresSintacticos = new ArrayList<>();
        listaDeErroresSemanticos = new ArrayList<>();
                listaDeLexemasDeclarados = new HashSet<>();
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

    public ArrayList<String> getListaDeErroresSemanticos() {
        return listaDeErroresSemanticos;
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


public HashSet<String> getListaDeLexemasDeclarados() {
        return listaDeLexemasDeclarados;
        }

    public HashMap<String,EntradaTablaSimbolos> getTablaDeSimbolos() {
        return al.getTablaDeSimbolos();
    }

//#line 541 "Parser.java"
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
//#line 15 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Programa reconocido en linea %1$d",al.getLinea()));}
break;
case 2:
//#line 19 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 3:
//#line 21 "gramatica.y"
{addErrorSintactico(String.format("sentencia mal declarada recuperando en ',' siguiente en linea %1$d",al.getLinea()));}
break;
case 4:
//#line 23 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 5:
//#line 27 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 6:
//#line 29 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 7:
//#line 33 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));
    for(EntradaTablaSimbolos e: ((ArrayList<EntradaTablaSimbolos>)val_peek(0).obj)){
        /*redeclaracion de variables ya revisado en regla del identificadorDec*/
        /*seteo el tipo de la variable, o de lo apuntado en caso de punteros.*/
        e.setTipo((String) val_peek(1).sval);
        /*las marco como mutables*/
        e.setMutable(true);
        }
    }
break;
case 8:
//#line 44 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 9:
//#line 46 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 10:
//#line 48 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 11:
//#line 50 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 12:
//#line 52 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));
    /*redeclaracion de variables ya revisado en regla del identificadorDec*/
    EntradaTablaSimbolos ets = ((ParEntradas)val_peek(0).obj).e1;
    /*seteo el tipo del identificador o de la variable apuntada en caso de un puntero*/
    ets.setTipo((String) val_peek(1).sval);
    /*lo marco como inmutable*/
    ets.setMutable(false);
    /*chequeo compatibilidad de tipos en la asignacion, o de punteros*/
    /*en caso de asignar a un puntero una referencia a la direccion de memoria de otra variable*/
    EntradaTablaSimbolos etsCte = ((ParEntradas)val_peek(0).obj).e2;
    if (ets instanceof EntradaTablaDeSimbolosPuntero) {
        /*corroboro se intente asignar una direccion de memoria*/
        if (!(etsCte instanceof EntradaTablaDeSimbolosReferenciaAMemoria)) {
            addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
        } else {
            /*debo corroborar que el tipo del puntero y el de la variable referenciada sean el mismo*/
            if (ets.getTipo() == etsCte.getTipo()) {
                ((EntradaTablaDeSimbolosPuntero) ets).setApuntado(((EntradaTablaDeSimbolosReferenciaAMemoria) etsCte).getReferenciado());
                /*creo un terceto para la asignacion y lo agrego al listado*/
                Terceto terceto = new Terceto("ASIGNACION", ets, etsCte);
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
            } else
                addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
        }
    }
    else {
        /*en caso de no tratarse de un puntero debo checkear que el r-value sea una variable (no referencia a memoria) y del mismo tipo que el l-value*/
        if ((etsCte instanceof EntradaTablaDeSimbolosReferenciaAMemoria)) {
            addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
        }
        else {
            /*chequeo compatibilidad de tipos*/
            if (ets.getTipo() != etsCte.getTipo()) {
                addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
            }
            else{
                /*creo un terceto para la asignacion y lo agrego al listado*/
                Terceto terceto = new Terceto("ASIGNACION", ets, etsCte);
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
            }
        }
    }
    }
break;
case 13:
//#line 96 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 14:
//#line 98 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 15:
//#line 100 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 16:
//#line 104 "gramatica.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));
    yyval=new ParserVal("Linteger");
}
break;
case 17:
//#line 108 "gramatica.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));
    yyval=new ParserVal("Single");
}
break;
case 18:
//#line 114 "gramatica.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));
    ArrayList<EntradaTablaSimbolos> listaIds = new ArrayList<>();
    listaIds.add(((EntradaTablaSimbolos)val_peek(0).obj));
    yyval=new ParserVal(listaIds);
}
break;
case 19:
//#line 120 "gramatica.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));
    ((ArrayList<EntradaTablaSimbolos>)val_peek(2).obj).add((EntradaTablaSimbolos) val_peek(0).obj);
    yyval= val_peek(2);
}
break;
case 20:
//#line 125 "gramatica.y"
{addErrorSintactico(String.format(" declaracion de lista de variables esperaba un ; entre variables en linea %1$d",al.getLinea()));}
break;
case 21:
//#line 129 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
            EntradaTablaSimbolos ets=(EntradaTablaSimbolos) val_peek(0).obj;
    String lexema = ets.getLexema();
    /*chequeo que la variable ya halla sido declarada*/
    if (!listaDeLexemasDeclarados.contains(lexema))
        addErrorSemantico(String.format("variable redeclarada, en linea %1$d", al.getLinea()));
    else {
        /*chequeo que si estoy tratando de desreferenciar a un puntero la variable sea efectivamente de tipo puntero*/
        if (!(ets.getTipo() == "ReferenciaAMemoria")) {
            addErrorSemantico(String.format("variable desreferenciada no es de tipo puntero, en linea %1$d", al.getLinea()));
        } else {
            /*chequeo que halla sido inicializado el puntero*/
            if ((((EntradaTablaDeSimbolosPuntero) ets).getApuntado()) == null) {
                addErrorSemantico(String.format("variable desreferenciada no apunta a nada, en linea %1$d", al.getLinea()));
            } else {
                /*retorno la variable apuntada por el puntero*/
                yyval = new ParserVal(((EntradaTablaDeSimbolosPuntero) ets).getApuntado());
            }
        }
    }
}
break;
case 22:
//#line 153 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
        String lexema = ((EntradaTablaSimbolos) val_peek(0).obj).getLexema();
        /*chequeo que la variable ya halla sido declarada*/
        if (!listaDeLexemasDeclarados.contains(lexema))
            addErrorSemantico(String.format("variable no declarada, en linea %1$d", al.getLinea()));
        else {
            /*si fue declarada solo retorno su entrada en la tabla de simbolos*/
            yyval = val_peek(0);
        }
    }
break;
case 23:
//#line 165 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
    /*si ya fue declarada la variable o un puntero con el mismo nombre*/
    String lexema = ((EntradaTablaSimbolos) val_peek(0).obj).getLexema();
    if (listaDeLexemasDeclarados.contains(lexema))
        addErrorSemantico(String.format("variable redeclarada, en linea %1$d", al.getLinea()));
    else {
        /*creo una entrada para el puntero, apuntando a nada por el momento -null-*/
        EntradaTablaSimbolos ets = new EntradaTablaDeSimbolosPuntero(lexema, "ReferenciaAMemoria",null);
        /*la marco como ya declarada*/
        listaDeLexemasDeclarados.add(lexema);
        /*elimino la entrada de la tabla de simbolos del identificador que no era puntero y lo reemplazo por el puntero*/
        al.getTablaDeSimbolos().remove(((EntradaTablaSimbolos) val_peek(0).obj).getLexema());
        al.getTablaDeSimbolos().put(ets.getLexema(),ets);
        /*retorno la nueva entrada*/
        yyval=new ParserVal(ets);
    }
        }
break;
case 24:
//#line 183 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
    String lexema = ((EntradaTablaSimbolos) val_peek(0).obj).getLexema();
    /*si ya fue declarada la variable o un puntero con el mismo nombre*/
    if (listaDeLexemasDeclarados.contains(lexema))
        addErrorSemantico(String.format("variable redeclarada, en linea %1$d", al.getLinea()));
    else{
        /*la marco como ya declarada*/
        listaDeLexemasDeclarados.add(lexema);
        yyval=val_peek(0);
    }
}
break;
case 25:
//#line 197 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));
        yyval=new ParserVal(new ParEntradas((EntradaTablaSimbolos)val_peek(2).obj,(EntradaTablaSimbolos)val_peek(0).obj));
        }
break;
case 26:
//#line 201 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 27:
//#line 203 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 28:
//#line 205 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 29:
//#line 209 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 30:
//#line 211 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 31:
//#line 213 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 32:
//#line 215 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 33:
//#line 219 "gramatica.y"
{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));
    ListaTercetos lt = ListaTercetos.getInstanceOfListaDeTercetos();
    Terceto acompletar = lt.getTerceto(lt.getPilaTercetos().pop());
    acompletar.setOperando1(new TercetoDestino(lt.getTercetos().size()));
}
break;
case 35:
//#line 229 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 36:
//#line 231 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 37:
//#line 233 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 38:
//#line 235 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 40:
//#line 241 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 41:
//#line 243 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 42:
//#line 245 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 43:
//#line 247 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 45:
//#line 251 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 46:
//#line 253 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 47:
//#line 256 "gramatica.y"
{ListaTercetos lt = ListaTercetos.getInstanceOfListaDeTercetos();
    Terceto tercetoACompletar = lt.getTerceto(lt.getPilaTercetos().pop());
    tercetoACompletar.setOperando2(new TercetoDestino(lt.getTercetos().size()+1));
    Terceto incondicionalAcompletar = new Terceto("BI");
    lt.addTerceto(incondicionalAcompletar);
    lt.getPilaTercetos().push(lt.getTercetos().size()-1);
}
break;
case 49:
//#line 269 "gramatica.y"
{addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));
    ListaTercetos lt=ListaTercetos.getInstanceOfListaDeTercetos();
    Terceto tercetoIncompleto = lt.getTerceto(lt.getPilaTercetos().pop());
    tercetoIncompleto.setOperando2(new TercetoDestino(lt.getTercetos().size()+1));
    Terceto saltoAlInicio= new Terceto("BI", new TercetoDestino(lt.getPilaTercetos().pop()));
    lt.addTerceto(saltoAlInicio);
}
break;
case 50:
//#line 277 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 51:
//#line 279 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 52:
//#line 281 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 53:
//#line 283 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 54:
//#line 286 "gramatica.y"
{ ListaTercetos.getInstanceOfListaDeTercetos().getPilaTercetos().push(ListaTercetos.getInstanceOfListaDeTercetos().getTercetos().size());}
break;
case 55:
//#line 290 "gramatica.y"
{
        addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));
        /*chequeo compatibilidad de tipos*/
    if (((Operando)val_peek(2).obj).getTipo()!=((Operando)val_peek(0).obj).getTipo()){
        addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
    }
    else{
        /*chequeo el el l-value sea mutable*/
    if (!((EntradaTablaSimbolos)val_peek(2).obj).isMutable()){
        addErrorSemantico(String.format("asignacion a variable inmutable en linea %1$d",al.getLinea()));
    }
    else{
        /*chequeo el caso de una asignacion entre punteros, para que ademas de ser ambos de tipo puntero ambos apunten a elementos del mismo tipo*/
        /*el r-value puede no ser un puntero, sino una suma de direcciones de memoria usadas como variables de tipo puntero o &variable (no tipo puntero)*/
        if (((Operando) val_peek(2).obj).getTipo() == "ReferenciaAMemoria") {
            if (((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo() != (((ReferenciaAMemoria) val_peek(0).obj).getReferenciadoTipo())) {
                addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
            } else {
                Terceto terceto = new Terceto("ASIGNACION", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                yyval = new ParserVal(terceto);
            }
        }
        else {
            /*creo un nuevo terceto para la asignacion y lo retorno*/
            Terceto terceto = new Terceto("ASIGNACION", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
            ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
            yyval = new ParserVal(terceto);
        }
    }
    }
    }
break;
case 56:
//#line 323 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 57:
//#line 325 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 58:
//#line 327 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 59:
//#line 331 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 60:
//#line 333 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 61:
//#line 335 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 62:
//#line 337 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 63:
//#line 339 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 64:
//#line 343 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 65:
//#line 345 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 66:
//#line 349 "gramatica.y"
{
        addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));
        /*chequeo compatibilidad de tipos*/
    if (((Operando)val_peek(2).obj).getTipo()!=((Operando)val_peek(0).obj).getTipo()){
        addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
    }
    else {
        /*chequeo que el tipo sea entero largo, es el unico permitido para condiciones*/
        if (((Operando) val_peek(2).obj).getTipo() != ("Linteger")) {
            addErrorSemantico(String.format("tipo en condicion debe ser entero. linea %1$d", al.getLinea()));
        }
        else{
            /*creo un terceto para la comparacion, lo añao a la lista*/
            Terceto terceto = new Terceto(val_peek(1).sval, (Operando)val_peek(2).obj, (Operando)val_peek(0).obj);
            ListaTercetos lt= ListaTercetos.getInstanceOfListaDeTercetos();
            lt.addTerceto(terceto);
            /*añado un terceto para indicar el branch por falso y apilo el terceto recien creado incompleto para completar luego*/
            lt.addTerceto(new Terceto("BF",lt.getTerceto(lt.getTercetos().size()-1)));
            lt.getPilaTercetos().push(lt.getTercetos().size());
            /*retorno el terceto de la comparacion creo que es innecesario lo comento*/
            /*$$=new ParserVal(terceto);*/
            }
        }
    }
break;
case 67:
//#line 374 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 68:
//#line 376 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 69:
//#line 378 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 70:
//#line 382 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
        /*Chequeo compatibilidad de tipos para operar*/
        if (((Operando)val_peek(2).obj).getTipo()!=((Operando)val_peek(0).obj).getTipo()){
            addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
        }
        else {
            /*chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo*/
            if (((Operando) val_peek(2).obj).getTipo() == "ReferenciaAMemoria") {
                if (((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo() != (((ReferenciaAMemoria) val_peek(0).obj).getReferenciadoTipo())) {
                    addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
                } else {
                    TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("+", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                    terceto.setTipoResultante("ReferenciaAMemoria");
                    terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo());
                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                    yyval = new ParserVal(terceto);
                }
            } else {
                /*en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno*/
                Terceto terceto = new Terceto("+", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                terceto.setTipoResultante(((Operando) val_peek(2).obj).getTipo());
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                yyval = new ParserVal(terceto);
            }
        }
    }
break;
case 71:
//#line 409 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 72:
//#line 411 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 73:
//#line 413 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 74:
//#line 415 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 75:
//#line 417 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
        /*Chequeo compatibilidad de tipos para operar*/
        if (((Operando)val_peek(2).obj).getTipo()!=((Operando)val_peek(0).obj).getTipo()){
            addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
        }
        else {
            /*chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo*/
            if (((Operando) val_peek(2).obj).getTipo() == "ReferenciaAMemoria") {
                if (((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo() != (((ReferenciaAMemoria) val_peek(0).obj).getReferenciadoTipo())) {
                    addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
                } else {
                    TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("-", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                    terceto.setTipoResultante("ReferenciaAMemoria");
                    terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo());
                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                    yyval = new ParserVal(terceto);
                }
            } else {
                /*en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno*/
                Terceto terceto = new Terceto("-", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                terceto.setTipoResultante(((Operando) val_peek(2).obj).getTipo());
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                yyval = new ParserVal(terceto);
            }
        }
    }
break;
case 76:
//#line 444 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
    yyval=val_peek(0);}
break;
case 77:
//#line 449 "gramatica.y"
{
        /*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
        /*Chequeo compatibilidad de tipos para operar*/
        if (((Operando)val_peek(2).obj).getTipo()!=((Operando)val_peek(0).obj).getTipo()){
            addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
        }
        else {
            /*chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo*/
            if (((Operando) val_peek(2).obj).getTipo() == "ReferenciaAMemoria") {
                if (((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo() != (((ReferenciaAMemoria) val_peek(0).obj).getReferenciadoTipo())) {
                    addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
                } else {
                    TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("*", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                    terceto.setTipoResultante("ReferenciaAMemoria");
                    terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo());
                    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                    yyval = new ParserVal(terceto);
                }
            } else {
                /*en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno*/
                Terceto terceto = new Terceto("*", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                terceto.setTipoResultante(((Operando) val_peek(2).obj).getTipo());
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                yyval = new ParserVal(terceto);
            }
        }
    }
break;
case 78:
//#line 478 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
        /*Chequeo compatibilidad de tipos para operar*/
    if (((Operando)val_peek(2).obj).getTipo()!=((Operando)val_peek(0).obj).getTipo()){
        addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
    }
    else {
        /*chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo*/
        if (((Operando) val_peek(2).obj).getTipo() == "ReferenciaAMemoria") {
            if (((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo() != (((ReferenciaAMemoria) val_peek(0).obj).getReferenciadoTipo())) {
                addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
            } else {
                TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("/", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
                terceto.setTipoResultante("ReferenciaAMemoria");
                terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) val_peek(2).obj).getReferenciadoTipo());
                ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
                yyval = new ParserVal(terceto);
            }
        } else {
            /*en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno*/
            Terceto terceto = new Terceto("/", (Operando) val_peek(2).obj, (Operando) val_peek(0).obj);
            terceto.setTipoResultante(((Operando) val_peek(2).obj).getTipo());
            ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
            yyval = new ParserVal(terceto);
        }
    }
    }
break;
case 79:
//#line 506 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
    yyval=val_peek(0);}
break;
case 80:
//#line 511 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/
    yyval=val_peek(0);}
break;
case 81:
//#line 514 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/
    yyval=val_peek(0);}
break;
case 82:
//#line 519 "gramatica.y"
{
    EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
    if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
        /*chequeo si la cte positiva es mayor al maximo permitido-un valor por encima por si era negativa-*/
        if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
            /*si lo es, lo informo y utilizo reemplazo para bajarla al maximo permitido*/
            addErrorSintactico(String.format("warning linteger cte positiva mayor al maximo permitido en linea %1$d", al.getLinea()));
            String nuevoLexema = String.valueOf(AnalizadorLexico.MAX_LONG - 1);
            al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
            entradaTablaSimbolos.setLexema(nuevoLexema);
            al.getTablaDeSimbolos().put(entradaTablaSimbolos.getLexema(), entradaTablaSimbolos);
        }
    }
    /*addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));*/
    entradaTablaSimbolos.incUsos();
    yyval=val_peek(0);
}
break;
case 83:
//#line 537 "gramatica.y"
{
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
        String lexema = "-" + (entradaTablaSimbolos.getLexema());
    /* no esta en tabla, agrega a TS*/
    if (!al.estaEnTabla(lexema)) {
            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
            al.agregarATablaSimbolos(elementoTS);
        }
        /*addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));*/
    /*si el tipo es long debo chequear que su contraparte positivo que queda en la tabla de simbolos no sea mayor al maximo,*/
    /*todo si implementamos un contador de usos no deberia ser necesario, se podria utilizar que si el contador llega a 0  se elimine la positiva*/
        if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
            if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
                al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
            }
        }
        /*al.getTablaDeSimbolos().get(lexema).incUsos();*/
        /*retorno la entrada de la nueva cte negativa de la tabla de simbolos*/
        yyval=new ParserVal(al.getTablaDeSimbolos().get(lexema));
}
break;
case 84:
//#line 558 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea())); */
        EntradaTablaSimbolos ets = (EntradaTablaSimbolos)val_peek(0).obj;
        /*chequeo que ID sea una variable ya declarada*/
        if (!listaDeLexemasDeclarados.contains(ets.getLexema())){
            addErrorSemantico(String.format("variable nunca declarada en linea %1$d", al.getLinea()));
        }
        else {
            HashMap<String, EntradaTablaSimbolos> ts = al.getTablaDeSimbolos();
            /* String lexemaRefMemoria = "&" + ets.getLexema();*/
            /* si es la primera vez que referencio a ID como direccion de memoria lo doy de alta como cte en la tabla de simbolos*/
            /* creo que no es necesario darlo de alta, solo elevar una entrada como ref a memoria*/
            /* if (!ts.containsKey(lexemaRefMemoria))ts.put(lexemaRefMemoria, new EntradaTablaDeSimbolosReferenciaAMemoria(lexemaRefMemoria, ets.getTipo(),ets));*/
            yyval=new ParserVal(new EntradaTablaDeSimbolosReferenciaAMemoria(ets.getLexema(),"ReferenciaAMemoria",ets));
        }

}
break;
case 85:
//#line 575 "gramatica.y"
{addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
break;
case 86:
//#line 577 "gramatica.y"
{addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
break;
case 87:
//#line 581 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));*/
    yyval=new ParserVal("=");}
break;
case 88:
//#line 584 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));*/
    yyval=new ParserVal(">");}
break;
case 89:
//#line 587 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()))*/;
    yyval=new ParserVal("<");}
break;
case 90:
//#line 590 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
    yyval=new ParserVal("COMP_MAYOR_IGUAL");}
break;
case 91:
//#line 593 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
    yyval=new ParserVal("COMP_MENOR_IGUAL");}
break;
case 92:
//#line 596 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/
    yyval=new ParserVal("COMP_DIFERENTE");}
break;
case 93:
//#line 601 "gramatica.y"
{addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));
    ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(new Terceto("PRINT",(EntradaTablaSimbolos)val_peek(1).obj));}
break;
case 94:
//#line 604 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 95:
//#line 606 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 96:
//#line 608 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 97:
//#line 610 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
//#line 1412 "Parser.java"
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
