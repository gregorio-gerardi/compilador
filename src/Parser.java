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
    0,    1,    1,    2,    2,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    5,    5,    6,    6,    6,    8,
    8,    7,    7,    7,    7,    4,    4,    4,    4,   10,
   14,   14,   14,   14,   14,   15,   15,   15,   15,   15,
   15,   15,   15,   11,   11,   11,   11,   11,   12,   12,
   12,   12,   17,   17,   17,   17,   17,   19,   19,   16,
   16,   16,   16,   18,   18,   18,   18,   18,   18,   18,
   21,   21,   21,   22,   22,    9,    9,    9,    9,    9,
   20,   20,   20,   20,   20,   20,   13,   13,   13,   13,
   13,
};
final static short yylen[] = {                            2,
    1,    2,    3,    1,    1,    4,    4,    4,    4,    4,
    3,    3,    3,    3,    1,    1,    1,    3,    3,    2,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    2,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    4,
    2,    2,    2,    5,    5,    5,    5,    5,    3,    3,
    3,    3,    2,    3,    3,    3,    3,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    2,    2,    2,    2,
    1,    1,    1,    1,    1,    1,    4,    4,    4,    4,
    4,
};
final static short yydefred[] = {                         0,
    0,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    5,    0,   26,   27,   28,   29,    0,    0,   15,
   16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,    0,    2,    0,    0,    0,    0,
    0,   30,    0,    0,   74,   76,    0,    0,   75,    0,
    0,   73,    0,    0,    0,    0,    0,    0,   12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   13,    0,    0,    0,   11,    3,    0,    0,    0,    0,
    0,   43,    0,    0,    0,    0,   53,    0,    0,   41,
   80,    0,    0,   77,   79,   78,    0,    0,    0,    0,
    0,   17,   84,   85,   86,   81,   82,   83,    0,   88,
   32,    0,    0,    0,    0,    0,   33,   34,   35,   31,
   89,   90,   91,   87,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,   58,   56,    0,   55,    0,   54,
    0,    0,    0,   67,   68,    0,    0,    0,    0,    0,
   71,   72,    0,    0,    0,    0,    0,    0,   25,   23,
    0,   22,   45,   46,   47,    0,   44,   37,   59,   38,
   39,   40,   36,   19,   18,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   41,   24,  101,   59,   13,   49,   14,
   15,   16,   17,   18,   42,   56,   43,   57,   84,  109,
   51,   52,
};
final static short yysindex[] = {                        47,
    2,    0,  -39,  -24,   -3, -176, -254,    0,   47,  -10,
    0,    0, -212,    0,    0,    0,    0,   57,  -36,    0,
    0, -156,  -38,    5,  -34,  -32, -257, -211,  -34,  -30,
   40, -170,   27,    0,   -1,    0,  -36,  -28,   17,   99,
   11,    0, -190,   36,    0,    0, -205, -142,    0,   50,
   45,    0,  -22,  366,   63,   66,  376, -187,    0, -208,
   78,  346,  -19,   83,   94,   -2,   97,  356,    8,  -22,
    0,  -22,   33, -187,    0,    0,   50,   36,   50,    1,
   61,    0,   44,   71,  -13,   75,    0,   61,   85,    0,
    0,  -36,  -36,    0,    0,    0,  -26,   -9,   -7,   -7,
  -54,    0,    0,    0,    0,    0,    0,    0,  -36,    0,
    0,  -36,   -5,  -17,  -17,  -15,    0,    0,    0,    0,
    0,    0,    0,    0,   61,   61,   61,   89,  -54,  -54,
    0,  -54,   43, -180,    0,    0,   72,    0,    1,    0,
 -138,   30, -232,    0,    0, -127,   45, -127,   45, -127,
    0,    0,  -22,  -22,   50,   50,   36,   50,    0,    0,
 -127,    0,    0,    0,    0,   43,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  139,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   96,
  109,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   98,    0,    0,  100,  101,  102,    0,
    0,    0,    0,    0,    0,    0,    0,  103,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  104,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  105,  107,
    0,  111,    0,    0,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    0,    0,  115,  121,  144,  154,    0,
    0,    0,    0,    0,   10,   15,   20,   35,    0,    0,
  113,    0,    0,    0,    0,  117,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  134,    0,  383,   46,   48,   67,  400,   18,    0,
    0,    0,    0,    0,    0,   76,  -63,   16,  123,  110,
   25,   37,
};
final static int YYTABLESIZE=637;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
   26,   48,   34,   48,  154,   48,   47,   48,   47,   48,
   47,   48,   47,   64,   47,   28,   47,  134,   47,    7,
   48,  120,   48,  172,  141,  143,   23,   47,   48,   47,
   48,  173,   48,   36,   50,   47,   30,   47,  124,   47,
   23,   23,   76,   37,   65,   38,    7,  115,  128,  116,
   61,   33,   77,   79,   87,   62,   23,   57,    7,   66,
   63,  163,  164,  165,  167,   88,   94,   53,    7,   23,
  114,    7,   89,   90,    7,   60,   70,   73,   92,   31,
   93,    7,   23,  168,    7,   72,   99,  135,    7,   20,
   21,  100,   97,   32,   98,   20,   21,   71,    7,   75,
   61,   63,    7,  110,   67,   69,  111,  144,  145,   20,
   21,  138,    7,   95,   96,  169,    7,  129,  117,  130,
  132,  147,  149,  121,  155,  170,    7,  156,  158,   91,
    7,  159,  160,  162,  122,  151,  152,  125,    1,   50,
    7,   14,   35,   51,   52,   49,   42,    8,    7,   70,
    9,   70,   70,   70,    6,   65,   24,   65,   65,   65,
   48,   64,   86,   64,   64,   64,  113,    0,   70,   70,
   70,    0,    0,    0,   65,   65,   65,    0,    0,   40,
   64,   64,   64,   40,   66,    0,   66,   66,   66,    0,
    0,    0,    0,    0,   69,  136,   69,   69,   69,  140,
    0,  153,    0,   66,   66,   66,    0,   40,    0,    0,
    0,   40,    0,   69,   69,   69,   25,   54,   45,   44,
   45,   54,   45,   62,   45,   68,   45,   78,   45,  146,
   45,   27,   55,   46,    2,   46,  119,   46,  150,   46,
  161,   46,    0,   46,   19,   46,  148,   45,  150,   45,
  157,   45,   29,  123,   46,    0,   46,    0,   19,   19,
   58,    2,   46,  127,   46,   61,   46,   20,   21,   57,
   62,   22,   80,    2,   19,   63,   57,   57,    3,   81,
   82,    4,   74,    2,    5,   80,    2,   19,  131,    2,
   60,    3,   91,  171,    4,   58,    2,    5,   80,    2,
   19,    0,    1,    2,    3,   20,   21,    4,    3,    0,
    5,    4,   39,    2,    5,    6,  133,    2,    3,    0,
    0,    4,    3,    0,    5,    4,   80,    2,    5,    0,
  139,    2,    3,    0,    0,    4,    3,    0,    5,    4,
  142,    2,    5,    0,  166,    2,    3,    0,    0,    4,
    3,    0,    5,    4,   85,    2,    5,    0,    0,    0,
    3,    0,    0,    4,   70,    0,    5,   70,   70,   70,
   65,    0,    0,   65,   65,   65,   64,    0,    0,   64,
   64,   64,   12,    0,    0,    0,  118,    0,   92,    0,
   93,   12,    0,    0,    0,    0,  126,    0,   92,   66,
   93,    0,   66,   66,   66,  108,  106,  107,   92,   69,
   93,    0,   69,   69,   69,  108,  106,  107,   97,    0,
   98,   83,   83,   60,    0,  108,  106,  107,    0,    0,
   60,    0,   60,    0,    0,  108,  106,  107,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  102,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  137,    0,  137,  102,
    0,  102,  102,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   83,    0,    0,    0,    0,
    0,    0,    0,    0,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   83,    0,
    0,    0,  174,  175,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   91,    0,  103,  104,  105,    0,    0,    0,
    0,    0,   91,    0,  103,  104,  105,    0,    0,    0,
    0,    0,   91,    0,  103,  104,  105,    0,    0,    0,
    0,  112,    0,    0,  103,  104,  105,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         38,
   40,   38,  257,   38,   59,   38,   45,   38,   45,   38,
   45,   38,   45,  271,   45,   40,   45,   81,   45,   42,
   38,   41,   38,  256,   88,   89,   40,   45,   38,   45,
   38,  264,   38,   44,   19,   45,   40,   45,   41,   45,
   40,   40,   44,  256,  256,  258,   42,  256,   41,  258,
   41,    6,   37,   38,   44,   41,   40,   44,   42,  271,
   41,  125,  126,  127,  128,  256,  272,   22,   42,   40,
  258,   42,  263,  264,   42,   41,   31,   32,   43,  256,
   45,   42,   40,  264,   42,  256,   42,   44,   42,  266,
  267,   47,   43,  270,   45,  266,  267,   31,   42,   33,
   25,   26,   42,   41,   29,   30,   41,   92,   93,  266,
  267,  125,   42,  256,  257,   44,   42,   70,   41,   72,
   73,   97,   98,   41,  109,  264,   42,  112,  113,  257,
   42,  114,  115,  116,   41,   99,  100,   41,    0,   44,
   42,   44,    9,   44,   44,   44,   44,   44,   44,   41,
   44,   43,   44,   45,   44,   41,   44,   43,   44,   45,
   44,   41,   40,   43,   44,   45,   57,   -1,   60,   61,
   62,   -1,   -1,   -1,   60,   61,   62,   -1,   -1,  123,
   60,   61,   62,  123,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,   41,  125,   43,   44,   45,  125,
   -1,  256,   -1,   60,   61,   62,   -1,  123,   -1,   -1,
   -1,  123,   -1,   60,   61,   62,  256,  256,  257,  256,
  257,  256,  257,  256,  257,  256,  257,  256,  257,  256,
  257,  256,  271,  272,  257,  272,  256,  272,  256,  272,
  256,  272,   -1,  272,  258,  272,  256,  257,  256,  257,
  256,  257,  256,  256,  272,   -1,  272,   -1,  258,  258,
  256,  257,  272,  256,  272,  256,  272,  266,  267,  256,
  256,  270,  256,  257,  258,  256,  263,  264,  262,  263,
  264,  265,  256,  257,  268,  256,  257,  258,  256,  257,
  256,  262,  257,  264,  265,  256,  257,  268,  256,  257,
  258,   -1,  256,  257,  262,  266,  267,  265,  262,   -1,
  268,  265,  256,  257,  268,  269,  256,  257,  262,   -1,
   -1,  265,  262,   -1,  268,  265,  256,  257,  268,   -1,
  256,  257,  262,   -1,   -1,  265,  262,   -1,  268,  265,
  256,  257,  268,   -1,  256,  257,  262,   -1,   -1,  265,
  262,   -1,  268,  265,  256,  257,  268,   -1,   -1,   -1,
  262,   -1,   -1,  265,  256,   -1,  268,  259,  260,  261,
  256,   -1,   -1,  259,  260,  261,  256,   -1,   -1,  259,
  260,  261,    0,   -1,   -1,   -1,   41,   -1,   43,   -1,
   45,    9,   -1,   -1,   -1,   -1,   41,   -1,   43,  256,
   45,   -1,  259,  260,  261,   60,   61,   62,   43,  256,
   45,   -1,  259,  260,  261,   60,   61,   62,   43,   -1,
   45,   39,   40,   24,   -1,   60,   61,   62,   -1,   -1,
   31,   -1,   33,   -1,   -1,   60,   61,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   53,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   84,   -1,   86,   70,
   -1,   72,   73,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  133,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  142,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  166,   -1,
   -1,   -1,  153,  154,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  259,  260,  261,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  259,  260,  261,   -1,   -1,   -1,
   -1,   -1,  257,   -1,  259,  260,  261,   -1,   -1,   -1,
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
"listaVariables : listaVariables error referencia",
"referencia : '*' ID",
"referencia : ID",
"asignacionCte : referencia ASIGNACION cte",
"asignacionCte : referencia error cte",
"asignacionCte : referencia ASIGNACION error",
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
"asignacion : referencia ASIGNACION expresion",
"asignacion : error ASIGNACION expresion",
"asignacion : referencia error expresion",
"asignacion : referencia ASIGNACION error",
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

//#line 217 "gramatica.y"
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

      public HashMap<String, EntradaTablaSimbolos> getTablaSimbolos() {
          return al.getTablaDeSimbolos();
      }
//#line 511 "Parser.java"
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
//#line 18 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 3:
//#line 19 "gramatica.y"
{addReglaSintacticaReconocida(String.format("Contenido de programa reconocido en linea %1$d",al.getLinea()));}
break;
case 4:
//#line 22 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 5:
//#line 23 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 6:
//#line 26 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));}
break;
case 7:
//#line 27 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 8:
//#line 28 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 9:
//#line 29 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 10:
//#line 30 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 11:
//#line 31 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));}
break;
case 12:
//#line 32 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 13:
//#line 33 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 14:
//#line 34 "gramatica.y"
{addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
break;
case 15:
//#line 37 "gramatica.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
break;
case 16:
//#line 38 "gramatica.y"
{addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));}
break;
case 17:
//#line 41 "gramatica.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
break;
case 18:
//#line 42 "gramatica.y"
{addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));}
break;
case 19:
//#line 43 "gramatica.y"
{addErrorSintactico(String.format(" declaracion de lista de variables esperaba un ; entre variables en linea %1$d",al.getLinea()));}
break;
case 20:
//#line 47 "gramatica.y"
{addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}
break;
case 21:
//#line 49 "gramatica.y"
{addReglaSintacticaReconocida(String.format("referencia reconocida en linea %1$d",al.getLinea()));}
break;
case 22:
//#line 53 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));}
break;
case 23:
//#line 54 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 24:
//#line 55 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 25:
//#line 56 "gramatica.y"
{addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
break;
case 26:
//#line 60 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 27:
//#line 61 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 28:
//#line 62 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 29:
//#line 63 "gramatica.y"
{addReglaSintacticaReconocida(String.format("sentencia ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 30:
//#line 67 "gramatica.y"
{addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));}
break;
case 32:
//#line 71 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 33:
//#line 72 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 34:
//#line 73 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 35:
//#line 74 "gramatica.y"
{addErrorSintactico(String.format("encabezado del if mal definido en linea %1$d",al.getLinea()));}
break;
case 37:
//#line 79 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 38:
//#line 80 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 39:
//#line 81 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 40:
//#line 82 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 42:
//#line 84 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 43:
//#line 85 "gramatica.y"
{addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
break;
case 44:
//#line 90 "gramatica.y"
{addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));}
break;
case 45:
//#line 91 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 46:
//#line 92 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 47:
//#line 93 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 48:
//#line 94 "gramatica.y"
{addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
break;
case 49:
//#line 98 "gramatica.y"
{addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));}
break;
case 50:
//#line 99 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 51:
//#line 100 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 52:
//#line 101 "gramatica.y"
{addErrorSintactico(String.format("asignacion mal definida en linea %1$d",al.getLinea()));}
break;
case 53:
//#line 105 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 54:
//#line 106 "gramatica.y"
{addReglaSintacticaReconocida(String.format("bloque sentencia reconocida en linea %1$d",al.getLinea()));}
break;
case 55:
//#line 107 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 56:
//#line 108 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 57:
//#line 109 "gramatica.y"
{addErrorSintactico(String.format("bloque de sentencias mal definid0 en linea %1$d",al.getLinea()));}
break;
case 58:
//#line 114 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 59:
//#line 115 "gramatica.y"
{addReglaSintacticaReconocida(String.format("conj sent ejecutable reconocida en linea %1$d",al.getLinea()));}
break;
case 60:
//#line 118 "gramatica.y"
{addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));}
break;
case 61:
//#line 119 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 62:
//#line 120 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 63:
//#line 121 "gramatica.y"
{addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
break;
case 64:
//#line 125 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 65:
//#line 126 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 66:
//#line 127 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 67:
//#line 128 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 68:
//#line 129 "gramatica.y"
{addErrorSintactico(String.format("expresion mal definido en linea %1$d",al.getLinea()));}
break;
case 69:
//#line 130 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 70:
//#line 131 "gramatica.y"
{addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));}
break;
case 71:
//#line 134 "gramatica.y"
{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
break;
case 72:
//#line 135 "gramatica.y"
{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
break;
case 73:
//#line 136 "gramatica.y"
{addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));}
break;
case 74:
//#line 139 "gramatica.y"
{addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));}
break;
case 75:
//#line 140 "gramatica.y"
{addReglaSintacticaReconocida(String.format("factor reconocida en linea %1$d",al.getLinea()));}
break;
case 76:
//#line 145 "gramatica.y"
{
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
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
case 77:
//#line 160 "gramatica.y"
{
        EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) (val_peek(0).obj);
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
case 78:
//#line 177 "gramatica.y"
{
        addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea()));
    }
break;
case 79:
//#line 182 "gramatica.y"
{
        addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea()));
    }
break;
case 80:
//#line 188 "gramatica.y"
{
        addErrorSintactico(String.format("valor cte mal definido en linea %1$d", al.getLinea()));
    }
break;
case 81:
//#line 198 "gramatica.y"
{addReglaSintacticaReconocida(String.format("comp = reconocida en linea %1$d",al.getLinea()));}
break;
case 82:
//#line 199 "gramatica.y"
{addReglaSintacticaReconocida(String.format("comp > reconocida en linea %1$d",al.getLinea()));}
break;
case 83:
//#line 200 "gramatica.y"
{addReglaSintacticaReconocida(String.format("comp < reconocida en linea %1$d",al.getLinea()));}
break;
case 84:
//#line 201 "gramatica.y"
{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
break;
case 85:
//#line 202 "gramatica.y"
{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
break;
case 86:
//#line 203 "gramatica.y"
{addReglaSintacticaReconocida(String.format("comp reconocida en linea %1$d",al.getLinea()));}
break;
case 87:
//#line 207 "gramatica.y"
{addReglaSintacticaReconocida(String.format("print reconocida en linea %1$d",al.getLinea()));}
break;
case 88:
//#line 208 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 89:
//#line 209 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 90:
//#line 210 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
case 91:
//#line 211 "gramatica.y"
{addErrorSintactico(String.format("print mal definido en linea %1$d",al.getLinea()));}
break;
//#line 1044 "Parser.java"
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
