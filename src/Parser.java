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
    0,    0,    0,   31,    0,    0,   75,   77,    0,    0,
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
final static short yysindex[] = {                        49,
    2,    0,  -39,  -13,   -3, -199, -241,    0,   59,   12,
    0,    0, -208,    0,    0,    0,    0,   69,  -36,    0,
    0, -178,    0,  -38,   23,  -34,  -32, -236, -253,  -34,
  -30,   42, -242,   35,    0,    4,   20,    0,  -36,  -28,
   19,  102,   25,    0, -209,   47,    0,    0, -227, -139,
    0,   67,   34,    0,  -22,  386,   52,   65,  406, -185,
    0, -142,   72,  377,  -19,   82,   90,   -2,   92,  399,
    8,  -22,    0,  -22,   63, -185,    0,    0,   67,   47,
   67,    3,   73,    0,   56,   83,    1,   87,    0,   73,
  109,    0,    0,  -36,  -36,    0,    0,    0,  -26,   -9,
   -7,   -7,  -54,    0,    0,    0,    0,    0,    0,    0,
  -36,    0,    0,  -36,   -5,  -17,  -17,  -15,    0,    0,
    0,    0,    0,    0,    0,    0,   73,   73,   73,  122,
  -54,  -54,    0,  -54,   45, -155,    0,    0,   93,    0,
    3,    0, -130,   32, -230,    0,    0, -119,   34, -119,
   34, -119,    0,    0,  -22,  -22,   67,   67,   47,   67,
    0,    0, -119,    0,    0,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   97,  133,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   99,    0,    0,  101,  103,
  104,    0,    0,    0,    0,    0,    0,    0,    0,  105,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  106,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  108,  110,    0,  111,    0,    0,    0,    0,    0,    0,
   22,    0,    0,    0,    0,    0,    0,  139,  145,  366,
  372,    0,    0,    0,    0,    0,   10,   17,   37,   39,
    0,    0,  112,    0,    0,    0,    0,  113,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  137,    0,  412,   64,  -12,   98,  418,  -14,    0,
    0,    0,    0,    0,    0,   68,   -8,   13,  100,   94,
   36,   38,
};
final static int YYTABLESIZE=667;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   27,   50,   67,   50,  156,   50,   49,   50,   49,   50,
   49,   50,   49,   74,   49,   35,   49,   68,   49,    7,
   50,  122,   50,   20,   21,  174,   29,   49,   50,   49,
   50,   52,   50,  175,   66,   49,   31,   49,  126,   49,
   24,   24,   24,   24,   96,   23,   90,   39,  130,   40,
   62,   79,   81,   91,   92,   38,   32,   63,   24,  131,
    7,  132,  134,   78,    7,   58,   20,   21,   89,   34,
   33,   24,  116,    7,  136,  101,    7,   64,    1,   61,
  102,  143,  145,    7,   24,   55,    7,   20,   21,   94,
    7,   95,  112,   63,   65,   72,   75,   69,   71,  137,
    7,  161,  162,  164,    7,  113,  146,  147,  170,   99,
    7,  100,  119,  117,    7,  118,   97,   98,  165,  166,
  167,  169,  123,  157,    7,  140,  158,  160,    7,   73,
  124,   77,  127,  172,  149,  151,  171,   93,  153,  154,
   51,   88,   15,    7,   52,   37,   53,   50,   43,    9,
    7,    8,  115,   10,    7,   25,   49,    0,    0,    0,
    0,    0,    0,    7,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   71,    0,   71,   71,   71,    0,   66,
    0,   66,   66,   66,    0,   65,    0,   65,   65,   65,
    0,   42,   71,   71,   71,   42,    0,    0,   66,   66,
   66,  155,    0,    0,   65,   65,   65,  138,    0,    0,
    0,  142,    0,    0,    0,    0,   26,   56,   47,   46,
   47,   56,   47,   64,   47,   70,   47,   80,   47,  148,
   47,   42,   57,   48,    2,   48,  121,   48,  152,   48,
  163,   48,   28,   48,   42,   48,  150,   47,  152,   47,
  159,   47,   30,  125,   48,    0,   48,    0,   19,   19,
   19,   19,   48,  129,   48,   62,   48,   20,   21,   20,
   21,   22,   63,   22,   82,    2,   19,   58,   60,    2,
    3,   83,   84,    4,   58,   58,    5,   82,    2,   19,
   76,    2,   64,    3,   61,  173,    4,   60,    2,    5,
   82,    2,   19,   93,    1,    2,    3,   20,   21,    4,
    3,    0,    5,    4,   36,    2,    5,    6,  133,    2,
    3,    0,    0,    4,   41,    2,    5,    6,  135,    2,
    3,    0,    0,    4,    3,    0,    5,    4,   82,    2,
    5,    0,  141,    2,    3,    0,    0,    4,    3,    0,
    5,    4,    0,    0,    5,    0,    0,   87,    2,    0,
    0,    0,    0,    3,  144,    2,    4,    0,    0,    5,
    3,    0,    0,    4,    0,    0,    5,  168,    2,    0,
    0,    0,    0,    3,    0,    0,    4,    0,   71,    5,
    0,   71,   71,   71,   66,    0,    0,   66,   66,   66,
   65,    0,    0,   65,   65,   65,   67,    0,   67,   67,
   67,   12,   70,    0,   70,   70,   70,  120,    0,   94,
   12,   95,    0,    0,    0,   67,   67,   67,   94,    0,
   95,   70,   70,   70,    0,    0,  110,  108,  109,  128,
    0,   94,   62,   95,    0,  110,  108,  109,   99,   62,
  100,   62,   85,   85,    0,    0,    0,    0,  110,  108,
  109,    0,    0,    0,    0,  110,  108,  109,    0,    0,
    0,    0,  104,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  104,
    0,  104,  104,    0,    0,    0,    0,  139,    0,  139,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   85,    0,    0,    0,
    0,    0,    0,    0,    0,   85,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  176,  177,    0,    0,    0,    0,    0,   85,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   67,    0,    0,   67,   67,   67,   70,    0,    0,
   70,   70,   70,   93,    0,  105,  106,  107,    0,    0,
    0,    0,   93,    0,  105,  106,  107,    0,    0,    0,
    0,    0,    0,    0,    0,   93,    0,  105,  106,  107,
    0,  114,    0,    0,  105,  106,  107,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   40,   38,  256,   38,   59,   38,   45,   38,   45,   38,
   45,   38,   45,  256,   45,  257,   45,  271,   45,   42,
   38,   41,   38,  266,  267,  256,   40,   45,   38,   45,
   38,   19,   38,  264,  271,   45,   40,   45,   41,   45,
   40,   40,   40,   40,  272,   44,  256,  256,   41,  258,
   41,   39,   40,  263,  264,   44,  256,   41,   40,   72,
   42,   74,   75,   44,   42,   44,  266,  267,   44,    6,
  270,   40,  258,   42,   83,   42,   42,   41,    0,   41,
   47,   90,   91,   42,   40,   22,   42,  266,  267,   43,
   42,   45,   41,   26,   27,   32,   33,   30,   31,   44,
   42,  116,  117,  118,   42,   41,   94,   95,  264,   43,
   42,   45,   41,  256,   42,  258,  256,  257,  127,  128,
  129,  130,   41,  111,   42,  125,  114,  115,   42,   32,
   41,   34,   41,  264,   99,  100,   44,  257,  101,  102,
   44,   42,   44,   42,   44,    9,   44,   44,   44,   44,
   42,   44,   59,   44,   44,   44,   44,   -1,   -1,   -1,
   -1,   -1,   -1,   42,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   43,   44,   45,   -1,   41,
   -1,   43,   44,   45,   -1,   41,   -1,   43,   44,   45,
   -1,  123,   60,   61,   62,  123,   -1,   -1,   60,   61,
   62,  256,   -1,   -1,   60,   61,   62,  125,   -1,   -1,
   -1,  125,   -1,   -1,   -1,   -1,  256,  256,  257,  256,
  257,  256,  257,  256,  257,  256,  257,  256,  257,  256,
  257,  123,  271,  272,  257,  272,  256,  272,  256,  272,
  256,  272,  256,  272,  123,  272,  256,  257,  256,  257,
  256,  257,  256,  256,  272,   -1,  272,   -1,  258,  258,
  258,  258,  272,  256,  272,  256,  272,  266,  267,  266,
  267,  270,  256,  270,  256,  257,  258,  256,  256,  257,
  262,  263,  264,  265,  263,  264,  268,  256,  257,  258,
  256,  257,  256,  262,  256,  264,  265,  256,  257,  268,
  256,  257,  258,  257,  256,  257,  262,  266,  267,  265,
  262,   -1,  268,  265,  256,  257,  268,  269,  256,  257,
  262,   -1,   -1,  265,  256,  257,  268,  269,  256,  257,
  262,   -1,   -1,  265,  262,   -1,  268,  265,  256,  257,
  268,   -1,  256,  257,  262,   -1,   -1,  265,  262,   -1,
  268,  265,   -1,   -1,  268,   -1,   -1,  256,  257,   -1,
   -1,   -1,   -1,  262,  256,  257,  265,   -1,   -1,  268,
  262,   -1,   -1,  265,   -1,   -1,  268,  256,  257,   -1,
   -1,   -1,   -1,  262,   -1,   -1,  265,   -1,  256,  268,
   -1,  259,  260,  261,  256,   -1,   -1,  259,  260,  261,
  256,   -1,   -1,  259,  260,  261,   41,   -1,   43,   44,
   45,    0,   41,   -1,   43,   44,   45,   41,   -1,   43,
    9,   45,   -1,   -1,   -1,   60,   61,   62,   43,   -1,
   45,   60,   61,   62,   -1,   -1,   60,   61,   62,   41,
   -1,   43,   25,   45,   -1,   60,   61,   62,   43,   32,
   45,   34,   41,   42,   -1,   -1,   -1,   -1,   60,   61,
   62,   -1,   -1,   -1,   -1,   60,   61,   62,   -1,   -1,
   -1,   -1,   55,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   72,
   -1,   74,   75,   -1,   -1,   -1,   -1,   86,   -1,   88,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  135,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  144,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  155,  156,   -1,   -1,   -1,   -1,   -1,  168,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  256,   -1,   -1,  259,  260,  261,  256,   -1,   -1,
  259,  260,  261,  257,   -1,  259,  260,  261,   -1,   -1,
   -1,   -1,  257,   -1,  259,  260,  261,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,  259,  260,  261,
   -1,  256,   -1,   -1,  259,  260,  261,
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
"factor : ID",
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

//#line 268 "gramatica.y"
    private AnalizadorLexico al;
    private ArrayList<String> listaDeTokens;
    private ArrayList<String> listaDeReglas;
    private ArrayList<String> listaDeErroresLexicos;
    private ArrayList<String> listaDeErroresSintacticos;

    public Parser(Reader fuente) {
        al = new AnalizadorLexico(fuente);
        listaDeReglas = new ArrayList<>();
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

    public HashMap<String, EntradaTablaSimbolos> getTablaSimbolos() {
        return al.getTablaDeSimbolos();
    }
//#line 518 "Parser.java"
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
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/}
break;
case 22:
//#line 68 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/}
break;
case 23:
//#line 72 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
break;
case 24:
//#line 74 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 25:
//#line 76 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 26:
//#line 78 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 27:
//#line 82 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 28:
//#line 84 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 29:
//#line 86 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 30:
//#line 88 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 31:
//#line 92 "gramatica.y"
{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
break;
case 33:
//#line 98 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 34:
//#line 100 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 35:
//#line 102 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 36:
//#line 104 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 38:
//#line 110 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 39:
//#line 112 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 40:
//#line 114 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 41:
//#line 116 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 43:
//#line 120 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 44:
//#line 122 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 45:
//#line 126 "gramatica.y"
{addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
break;
case 46:
//#line 128 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 47:
//#line 130 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 48:
//#line 132 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 49:
//#line 134 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 50:
//#line 138 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));}
break;
case 51:
//#line 140 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 52:
//#line 142 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 53:
//#line 144 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 54:
//#line 148 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 55:
//#line 150 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 56:
//#line 152 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 57:
//#line 154 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 58:
//#line 156 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 59:
//#line 160 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 60:
//#line 162 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 61:
//#line 166 "gramatica.y"
{addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));}
break;
case 62:
//#line 168 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 63:
//#line 170 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 64:
//#line 172 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 65:
//#line 176 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 66:
//#line 178 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 67:
//#line 180 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 68:
//#line 182 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 69:
//#line 184 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 70:
//#line 186 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 71:
//#line 188 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 72:
//#line 192 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/}
break;
case 73:
//#line 194 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/}
break;
case 74:
//#line 196 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/}
break;
case 75:
//#line 200 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/}
break;
case 76:
//#line 202 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));*/}
break;
case 77:
//#line 206 "gramatica.y"
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
                                                        addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));
                                                    }
break;
case 78:
//#line 219 "gramatica.y"
{   EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
                                                        String lexema = "-" + (entradaTablaSimbolos.getLexema());
                                                        if (!al.estaEnTabla(lexema)) {
                                                            /* no esta en tabla, agrega a TS*/
                                                            EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
                                                            al.agregarATablaSimbolos(elementoTS);
                                                        }
                                                        addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));
                                                        if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
                                                            if ((Double.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
                                                                al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
                                                            }
                                                        }
                                                    }
break;
case 79:
//#line 234 "gramatica.y"
{addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea())); }
break;
case 80:
//#line 236 "gramatica.y"
{addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
break;
case 81:
//#line 238 "gramatica.y"
{addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea())); }
break;
case 82:
//#line 242 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));*/}
break;
case 83:
//#line 244 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));*/}
break;
case 84:
//#line 246 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()))*/;}
break;
case 85:
//#line 248 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/}
break;
case 86:
//#line 250 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/}
break;
case 87:
//#line 252 "gramatica.y"
{/*addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));*/}
break;
case 88:
//#line 256 "gramatica.y"
{addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));}
break;
case 89:
//#line 258 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 90:
//#line 260 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 91:
//#line 262 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 92:
//#line 264 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
//#line 1047 "Parser.java"
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
