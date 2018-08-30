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






//#line 2 "Gramatica.y"

package compilador;

import java.util.ArrayList;

import tercetos.*;
import java.util.Vector;


//#line 29 "Parser.java"




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
public final static short IF=257;
public final static short ELSE=258;
public final static short ENDIF=259;
public final static short PRINT=260;
public final static short INTEGER=261;
public final static short TO=262;
public final static short ALLOW=263;
public final static short MENOSIGUAL=264;
public final static short UINTEGER=265;
public final static short FUNCTION=266;
public final static short RETURN=267;
public final static short FOR=268;
public final static short INTTOUINT=269;
public final static short CONSTANTE=270;
public final static short IDENTIFICADOR=271;
public final static short CADENA=272;
public final static short ANOTACION=273;
public final static short MAYIGUAL=274;
public final static short MENIGUAL=275;
public final static short DOSPUNTOSIGUAL=276;
public final static short DISTINTO=277;
public final static short EOF=0;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    2,    3,    0,    0,    0,    5,    0,    1,    1,    6,
    6,    6,    9,    9,    4,    4,    8,    8,    8,    8,
   16,   16,   15,   15,   14,   14,   14,   14,   14,   14,
   14,   26,   17,   17,   23,   24,   25,   30,   18,   34,
   29,   31,   22,   22,   22,   22,   22,   28,   28,   35,
   35,   35,   27,   27,   27,   27,   27,   32,   32,   32,
   32,   32,   32,   19,   19,   19,   19,   19,    7,    7,
    7,    7,    7,   33,   33,   33,   33,   33,   33,   33,
   33,   33,   33,   33,   33,   33,   36,   36,   36,   36,
   36,   36,   36,   37,   37,   37,   20,   20,   20,   20,
   20,   11,   11,   11,   10,   10,   12,   12,   38,   38,
   39,   39,   40,   40,   40,   40,   40,   40,   40,   13,
   13,   41,   41,   41,   41,   41,   41,   21,
};
final static short yylen[] = {                            2,
    0,    0,    7,    4,    5,    0,    5,    1,    2,    2,
    1,    1,    6,    5,    1,    2,    3,    3,    2,    2,
    1,    3,    1,    1,    1,    1,    2,    3,    2,    2,
    2,    0,    4,    2,    4,    2,    3,    0,    4,    0,
   10,    1,    3,    3,    3,    3,    3,    1,    3,    1,
    2,    1,    3,    3,    2,    2,    2,    1,    1,    1,
    1,    1,    1,    3,    3,    3,    2,    2,    4,    4,
    4,    4,    2,    3,    3,    1,    4,    3,    3,    3,
    3,    4,    4,    2,    4,    3,    3,    3,    1,    3,
    3,    3,    3,    1,    1,    1,    4,    4,    3,    2,
    1,    2,    4,    7,    3,    3,    1,    2,    1,    1,
    1,    2,    1,    1,    2,    3,    2,    2,    2,    6,
    5,    3,    3,    6,    2,    3,    4,    2,
};
final static short yydefred[] = {                         0,
    0,   23,    0,   24,    0,    0,    0,    0,    8,    0,
   11,   12,    0,    0,    0,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    9,   10,    0,    0,    0,
    0,    0,   19,    0,    0,    0,    0,    0,    0,  106,
    0,    0,    0,    0,    0,   38,    0,    0,   15,   25,
   26,    0,    0,    0,    0,    0,  102,    0,    0,  109,
  110,    0,  107,   18,  105,   17,   22,    0,   72,   71,
   70,   69,    0,    0,    0,    0,  100,    0,    0,    0,
    0,    0,   68,    0,    0,    0,  128,    4,   16,    0,
   29,   30,   31,    0,   48,   32,   34,    0,    0,    0,
    0,    0,  113,  114,    0,    0,    0,    0,    0,  111,
    0,  108,    5,    0,    7,    0,    0,   95,    0,   96,
    0,    0,   89,   62,   61,   63,   60,   58,   59,    0,
    0,    0,   99,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  125,    0,   28,    0,   50,
    0,    0,   36,    0,    0,  103,    0,   14,    0,  117,
  118,  119,    0,  112,    0,    0,    0,    0,    0,    0,
   84,    0,    0,    0,    0,    0,   35,   55,   56,   57,
    0,   98,   97,    0,    0,   42,   39,    0,  122,  126,
  123,   49,   51,   33,    0,   37,    0,    0,  116,    0,
   13,    3,    0,    0,    0,   91,   93,    0,   86,    0,
    0,    0,    0,    0,   90,   87,   92,   88,    0,    0,
    0,  127,    0,    0,    0,    0,   83,   82,   85,   77,
    0,    0,  104,  121,    0,    0,  124,  120,    0,   40,
    0,    0,    0,   41,
};
final static short yydgoto[] = {                          7,
    8,   23,   73,   48,   24,    9,   10,   11,   12,   13,
   29,   62,  102,   49,   14,   15,   50,   51,   52,   53,
  120,   55,   56,   96,   97,  152,  130,   98,  136,   79,
  187,  131,  121,  241,  151,  122,  123,   63,  109,  110,
   87,
};
final static short yysindex[] = {                        74,
  183,    0,  -81,    0, -174,    0,    0, -106,    0,   30,
    0,    0,   19,  -67,   88, -174,    0,  -89,  242, -119,
 -113, -115,  183,   68,  234,    0,    0,  -28,  -65,  126,
  -53,  143,    0,  -29,    4,  234, -174, -174,   39,    0,
  183,  234,   29,  224,  -33,    0,   77,   91,    0,    0,
    0,  240,  246,  252,  257,   46,    0, -147,  195,    0,
    0,  -10,    0,    0,    0,    0,    0,  122,    0,    0,
    0,    0,  175,  135,  -62,  -25,    0, -145,  269,  -62,
   21,  245,    0,  -62,  -41,  -62,    0,    0,    0,   48,
    0,    0,    0,  239,    0,    0,    0, -243,   56,  288,
  292,  209,    0,    0,  279,  283,  297,  299,  208,    0,
  195,    0,    0,  234,    0,  281,  -22,    0,  321,    0,
  167,   97,    0,    0,    0,    0,    0,    0,    0,  323,
  110,   80,    0,  -37,  106,   46,  167,  281,  167,  281,
  167,  167,  115,  348,  -20,    0,  167,    0,   29,    0,
  140,   46,    0,  340,  364,    0,  -62,    0,  134,    0,
    0,    0,  370,    0,  287,  145,  104,  104,   26,   26,
    0,    2,  164,  172,  241,  247,    0,    0,    0,    0,
  250,    0,    0,    3,  358,    0,    0,  -21,    0,    0,
    0,    0,    0,    0,  165,    0,  385,  322,    0,  -62,
    0,    0,  206,   97,   97,    0,    0,  489,    0,   12,
  206,   97,  206,   97,    0,    0,    0,    0,  281,  167,
  160,    0,  395,  396,  381,  342,    0,    0,    0,    0,
  -34,  404,    0,    0,  394,  -62,    0,    0,  136,    0,
 -232,  -36,  416,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  -39,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  399,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -73,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  346,    0,    0,    0,    0,    0,  411,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  414,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  113,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
  -27,    6,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  415,  -19,   44,  421,
  126,   67,    0,    0,    0,    0,   92,    0,  162,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  221,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   11,   33,    0,    0,    0,    0,    0,
   38,   45,   69,   75,    0,    0,    0,    0,  442,  444,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    7,    0,    0,  -13,    0,   23,    0,   53,  116,    0,
    0,    0,  382,  320,  422,  255,   37,   94,   62,  102,
  373,   43,    0,    0,  374,    0,    0,   34,    0,    0,
    0, -122,   82,    0,    0,   47,  353,  438,    0,  418,
    0,
};
final static int YYTABLESIZE=539;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        146,
   94,  209,   83,  183,   21,   76,   78,   18,   84,  181,
   79,  229,   57,   46,  153,  154,   25,  172,  223,   21,
  191,   47,   68,   43,   86,  128,  127,  129,   74,   41,
   26,   46,   81,   36,  128,  127,  129,   78,  242,   47,
   26,   94,   94,   94,   74,   94,   76,   94,   76,   20,
   76,   79,  230,   79,  173,   79,  174,   59,   28,   94,
   94,   94,   94,   26,   76,   76,   76,   76,   80,   79,
   79,   79,   79,   81,   75,   81,   83,   81,   78,  180,
   78,   60,   78,    6,   43,   74,    2,   74,   27,   74,
    4,   81,   81,   81,   81,  103,   78,   78,   78,   78,
  166,  108,   43,   74,   74,   74,   74,   44,  236,   80,
  133,   80,  111,   80,   60,   75,   85,   75,   99,   75,
  105,   84,  173,  100,  174,   44,  134,   80,   80,   80,
   80,   34,   45,   75,   75,   75,   75,   86,  175,  128,
  127,  129,   38,  176,   61,  103,   33,  103,   39,   16,
   45,  108,  104,  108,    2,   40,    3,  132,    4,    5,
  106,  137,  139,  141,   17,  142,   16,  147,   94,  186,
  105,    2,  105,    3,   20,    4,    5,   61,  173,    2,
  174,   17,   20,    4,   64,  195,   34,   20,   30,   20,
   42,   20,   20,  116,  240,    2,  185,   20,   31,    4,
    5,   66,  104,   17,  104,   17,  117,  118,  119,  173,
  106,  174,  106,  204,  205,   88,    1,   65,  182,  212,
  214,    1,   77,    1,  143,    1,    1,   81,  144,  145,
  116,    1,    2,  171,  222,  190,    4,   27,  198,  124,
  125,   67,  126,  117,  118,  119,  113,  169,  124,  125,
    2,  126,  170,  210,    4,    5,   94,  208,   80,  115,
   17,   76,  220,   76,  192,   37,   79,  228,   32,  202,
  117,  118,  119,   32,   94,   94,  138,   94,   82,   76,
   76,  226,   76,  243,   79,   79,   52,   79,   81,  117,
  118,  119,   75,   78,   71,  118,  119,  114,   90,    2,
   74,   43,   44,    4,   91,   45,   81,   81,  135,   81,
   92,   78,   78,   46,   78,   93,   47,  239,   74,   74,
  148,   74,  169,  167,   80,  168,  155,  170,  156,    1,
   75,  157,   80,  158,    2,  179,    3,  159,    4,    5,
   81,  160,   80,   80,    6,   80,   43,   44,   75,   75,
   45,   75,   82,  124,  125,  161,  126,  162,   46,  203,
   85,   47,  225,  177,  173,  178,  174,   89,   27,   27,
   27,   27,   27,  118,  119,   95,  184,   43,   44,   27,
   27,   45,  235,   27,  173,  188,  174,   89,  189,   46,
   43,   44,   47,   89,   45,   43,   44,   54,  196,   45,
   43,   44,   46,  197,   45,   47,  199,   46,   54,  200,
   47,  201,   46,  150,   54,   47,  221,   52,   52,  211,
   54,   52,   19,  154,   21,  224,   22,  213,   54,   52,
  231,  107,   52,  118,  119,  232,  233,   35,   16,  234,
   54,  118,  119,    2,  237,    3,   54,    4,    5,   58,
   43,   44,  238,   17,   45,   95,  244,   73,   69,   70,
   72,  101,   46,   43,   44,   47,   54,   45,    2,  101,
  193,   95,   67,   65,  163,   46,  115,  115,   47,   66,
  115,  107,   54,  107,   53,   89,   54,  115,  115,   43,
   44,  115,  165,   45,  149,   44,  215,   30,   45,  112,
  140,   46,  217,   37,   47,  219,   46,   31,   54,   47,
  118,  119,   17,  117,  118,  119,  118,  119,  117,  118,
  119,  206,  207,   54,   54,  194,  164,  216,  218,  227,
  169,  167,    0,  168,    0,  170,    0,    0,   54,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,    0,    0,   41,   44,    0,   40,    1,   45,  132,
    0,    0,   41,   41,  258,  259,  123,   40,   40,   59,
   41,   41,   36,  256,   61,   60,   61,   62,   42,   23,
    8,   59,    0,  123,   60,   61,   62,    0,  271,   59,
   18,   41,   42,   43,    0,   45,   41,   47,   43,  123,
   45,   41,   41,   43,   43,   45,   45,  123,   40,   59,
   60,   61,   62,   41,   59,   60,   61,   62,    0,   59,
   60,   61,   62,   41,    0,   43,    0,   45,   41,    0,
   43,   29,   45,  123,   41,   41,  261,   43,   59,   45,
  265,   59,   60,   61,   62,   59,   59,   60,   61,   62,
  114,   59,   59,   59,   60,   61,   62,   41,  231,   41,
  256,   43,  123,   45,   62,   41,   40,   43,  266,   45,
   59,   45,   43,  271,   45,   59,  272,   59,   60,   61,
   62,   44,   41,   59,   60,   61,   62,   61,   42,   60,
   61,   62,  262,   47,   29,  109,   59,  111,  262,  256,
   59,  109,   59,  111,  261,  271,  263,   76,  265,  266,
   59,   80,   81,   82,  271,   84,  256,   86,  123,  136,
  109,  261,  111,  263,  256,  265,  266,   62,   43,  261,
   45,  271,  256,  265,   59,  152,   44,  261,  256,  263,
  123,  265,  266,  256,   59,  261,  135,  271,  266,  265,
  266,   59,  109,  271,  111,  271,  269,  270,  271,   43,
  109,   45,  111,  167,  168,  125,  256,  271,  256,  173,
  174,  261,  256,  263,  266,  265,  266,  264,  270,  271,
  256,  271,  261,  256,  256,  256,  265,  125,  157,  274,
  275,  271,  277,  269,  270,  271,  125,   42,  274,  275,
  261,  277,   47,  172,  265,  266,  256,  256,  256,  125,
  271,  256,  181,   40,  125,  262,  256,  256,   14,  125,
  269,  270,  271,   19,  274,  275,  256,  277,  276,  274,
  275,  200,  277,  241,  274,  275,  125,  277,  256,  269,
  270,  271,  264,  256,  256,  270,  271,  123,   59,  261,
  256,  256,  257,  265,   59,  260,  274,  275,   40,  277,
   59,  274,  275,  268,  277,   59,  271,  236,  274,  275,
  273,  277,   42,   43,  256,   45,  271,   47,   41,  256,
  256,   40,  256,  125,  261,  256,  263,   59,  265,  266,
  264,   59,  274,  275,  271,  277,  256,  257,  274,  275,
  260,  277,  276,  274,  275,   59,  277,   59,  268,  256,
   40,  271,   41,   41,   43,  256,   45,   48,  256,  257,
  258,  259,  260,  270,  271,   56,  271,  256,  257,  267,
  268,  260,   41,  271,   43,  271,   45,   68,   41,  268,
  256,  257,  271,   74,  260,  256,  257,   25,   59,  260,
  256,  257,  268,   40,  260,  271,  273,  268,   36,   40,
  271,  125,  268,   94,   42,  271,   59,  256,  257,  256,
   48,  260,    1,  259,    3,   41,    5,  256,   56,  268,
  271,   59,  271,  270,  271,   41,   41,   16,  256,   59,
   68,  270,  271,  261,   41,  263,   74,  265,  266,   28,
  256,  257,   59,  271,  260,  136,   41,   59,   37,   38,
   39,  267,  268,  256,  257,  271,   94,  260,  123,   59,
  151,  152,   59,   59,  267,  268,  256,  257,  271,   59,
  260,  109,   41,  111,   41,  166,  114,  267,  268,  256,
  257,  271,  111,  260,  256,  257,  256,  256,  260,   62,
  256,  268,  256,  262,  271,  256,  268,  266,  136,  271,
  270,  271,  271,  269,  270,  271,  270,  271,  269,  270,
  271,  169,  170,  151,  152,  152,  109,  175,  176,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   -1,  166,
};
}
final static short YYFINAL=7;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","ELSE","ENDIF","PRINT","INTEGER","TO",
"ALLOW","MENOSIGUAL","UINTEGER","FUNCTION","RETURN","FOR","INTTOUINT",
"CONSTANTE","IDENTIFICADOR","CADENA","ANOTACION","MAYIGUAL","MENIGUAL",
"DOSPUNTOSIGUAL","DISTINTO",
};
final static String yyrule[] = {
"$accept : programa",
"$$1 :",
"$$2 :",
"programa : IDENTIFICADOR $$1 sentDeclarativas $$2 '{' sentEjecutables '}'",
"programa : sentDeclarativas '{' sentEjecutables '}'",
"programa : error sentDeclarativas '{' sentEjecutables '}'",
"$$3 :",
"programa : IDENTIFICADOR $$3 '{' sentEjecutables '}'",
"sentDeclarativas : sentDeclarativa",
"sentDeclarativas : sentDeclarativas sentDeclarativa",
"sentDeclarativa : conversion ';'",
"sentDeclarativa : declaracionVariables",
"sentDeclarativa : funcion",
"funcion : declaracionFuncion declaracionParametro sentDeclarativasFunc '{' retorno '}'",
"funcion : declaracionFuncion declaracionParametro '{' retorno '}'",
"sentEjecutables : sentencia",
"sentEjecutables : sentEjecutables sentencia",
"declaracionVariables : tipo listaVariables ';'",
"declaracionVariables : tipo error ';'",
"declaracionVariables : listaVariables ';'",
"declaracionVariables : tipo error",
"listaVariables : IDENTIFICADOR",
"listaVariables : listaVariables ',' IDENTIFICADOR",
"tipo : INTEGER",
"tipo : UINTEGER",
"sentencia : estructuraIF",
"sentencia : estructuraFOR",
"sentencia : asignacion ';'",
"sentencia : asignacion ';' ANOTACION",
"sentencia : imprimir ';'",
"sentencia : llamadoFuncion ';'",
"sentencia : restaAsignacion ';'",
"$$4 :",
"estructuraIF : IFCondicion IFBloqueThen $$4 IFBloqueElse",
"estructuraIF : IFCondicion IFBloqueElse",
"IFCondicion : IF '(' condicion ')'",
"IFBloqueThen : bloqueSentencias ELSE",
"IFBloqueElse : bloqueSentencias ENDIF ';'",
"$$5 :",
"estructuraFOR : FOR $$5 FORCondicion FORBloque",
"$$6 :",
"FORCondicion : '(' asignacion ';' IDENTIFICADOR comparador expresion ';' $$6 restaAsignacion ')'",
"FORBloque : bloqueSentencias",
"restaAsignacion : IDENTIFICADOR MENOSIGUAL expresion",
"restaAsignacion : IDENTIFICADOR '-' expresion",
"restaAsignacion : IDENTIFICADOR '=' expresion",
"restaAsignacion : error MENOSIGUAL expresion",
"restaAsignacion : IDENTIFICADOR MENOSIGUAL error",
"bloqueSentencias : sentencia",
"bloqueSentencias : '{' conjSentencias '}'",
"conjSentencias : sentencia",
"conjSentencias : conjSentencias sentencia",
"conjSentencias : error",
"condicion : expresion comparador expresion",
"condicion : expresion comparador error",
"condicion : comparador error",
"condicion : expresion error",
"condicion : expresion EOF",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : MENIGUAL",
"comparador : MAYIGUAL",
"comparador : DISTINTO",
"asignacion : IDENTIFICADOR DOSPUNTOSIGUAL expresion",
"asignacion : IDENTIFICADOR error expresion",
"asignacion : IDENTIFICADOR DOSPUNTOSIGUAL error",
"asignacion : IDENTIFICADOR error",
"asignacion : IDENTIFICADOR EOF",
"conversion : ALLOW tipo TO tipo",
"conversion : ALLOW tipo TO error",
"conversion : ALLOW error TO tipo",
"conversion : error tipo TO tipo",
"conversion : ALLOW error",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"expresion : INTTOUINT '(' expresion ')'",
"expresion : expresion '+' error",
"expresion : error '+' termino",
"expresion : expresion '-' error",
"expresion : error '-' termino",
"expresion : INTTOUINT '(' expresion error",
"expresion : INTTOUINT '(' error ')'",
"expresion : INTTOUINT error",
"expresion : INTTOUINT '(' expresion EOF",
"expresion : INTTOUINT '(' EOF",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"termino : termino '*' error",
"termino : error '*' factor",
"termino : termino '/' error",
"termino : error '/' factor",
"factor : IDENTIFICADOR",
"factor : CONSTANTE",
"factor : llamadoFuncion",
"imprimir : PRINT '(' CADENA ')'",
"imprimir : PRINT '(' CADENA error",
"imprimir : PRINT '(' error",
"imprimir : PRINT error",
"imprimir : IDENTIFICADOR",
"declaracionParametro : '(' ')'",
"declaracionParametro : '(' tipo IDENTIFICADOR ')'",
"declaracionParametro : '(' tipo FUNCTION IDENTIFICADOR '(' ')' ')'",
"declaracionFuncion : tipo FUNCTION IDENTIFICADOR",
"declaracionFuncion : FUNCTION tipo IDENTIFICADOR",
"sentDeclarativasFunc : sentDeclarativaFunc",
"sentDeclarativasFunc : sentDeclarativasFunc sentDeclarativaFunc",
"sentDeclarativaFunc : declaracionVariables",
"sentDeclarativaFunc : funcion",
"sentEjecutablesFunc : sentEjecutableFunc",
"sentEjecutablesFunc : sentEjecutablesFunc sentEjecutableFunc",
"sentEjecutableFunc : estructuraIF",
"sentEjecutableFunc : estructuraFOR",
"sentEjecutableFunc : asignacion ';'",
"sentEjecutableFunc : asignacion ';' ANOTACION",
"sentEjecutableFunc : imprimir ';'",
"sentEjecutableFunc : llamadoFuncion ';'",
"sentEjecutableFunc : restaAsignacion ';'",
"retorno : sentEjecutablesFunc RETURN '(' expresion ')' ';'",
"retorno : RETURN '(' expresion ')' ';'",
"declaracionParametroLlamado : '(' CONSTANTE ')'",
"declaracionParametroLlamado : '(' IDENTIFICADOR ')'",
"declaracionParametroLlamado : '(' FUNCTION IDENTIFICADOR '(' ')' ')'",
"declaracionParametroLlamado : '(' ')'",
"declaracionParametroLlamado : '(' IDENTIFICADOR error",
"declaracionParametroLlamado : '(' FUNCTION IDENTIFICADOR error",
"llamadoFuncion : IDENTIFICADOR declaracionParametroLlamado",
};

//#line 486 "Gramatica.y"

private AnalizadorLexico analizador;
private Fuente fuente;
private ArrayList<String> errores;
private ArrayList<String> reglas;
private ArrayList<String> tercetos;
private ArrayList<String> tercetosFuncion;
private String sufijo;
private ArrayList<String> tokensActuales;
private ArrayList<String> erroresSemanticos;
private PilaTercetos pilaTercetos;
private boolean inttouint = false;

public Parser(String source){
	fuente = new Fuente(source);
	analizador = new AnalizadorLexico(fuente);
	errores = new ArrayList <String>();
	reglas = new ArrayList <String>();
	tercetos = new ArrayList <String>();
	tercetosFuncion = new ArrayList <String>();
	sufijo=new String("");
	tokensActuales = new ArrayList <String>();
	erroresSemanticos = new ArrayList <String>();
	pilaTercetos = new PilaTercetos();
}

public int yylex(){
	Token token = analizador.obtenerToken();
	if (token!=null){
        yylval = new ParserVal(token);
		switch (token.getId()) {
			case "allow" : {return ALLOW;}
			case "to" : {return TO;}
			case "-=" : {return MENOSIGUAL;}
			case "<=" : {return MENIGUAL;}
			case ">=" : {return MAYIGUAL;}
			case ":=" : {return DOSPUNTOSIGUAL;}
			case "!=" : {return DISTINTO;}
			case "+" : {return '+';}
			case "-" : {return '-';}
			case "*" : {return '*';}
			case "/" : {return '/';}
			case "<" : {return '<';}
			case ">" : {return '>';}
			case "=" : {return '=';}
			case "(" : {return '(';}
			case ")" : {return ')';}	
			case ";" : {return ';';}
			case "," : {return ',';}
			case "{" : {return '{';}
			case "}" : {return '}';}
			case "if":{return IF;}
			case "endif":{return ENDIF;}
			case "else":{return ELSE;}
			case "print":{return PRINT;}
			case "integer":{return INTEGER;}
			case "constante" : {return CONSTANTE;}
			case "identificador": {return IDENTIFICADOR;}
			case "cadena":{return CADENA;}
			case "for":{return FOR;}
			case "function":{return FUNCTION;}
			case "return":{return RETURN;}
			case "inttouint":{return INTTOUINT;}
			case "anotacion":{return ANOTACION;}
			case "uinteger" :{return UINTEGER;}
    	}
    }
	return 0;
}

public void chequearDeclaracion(Token t, String s){
		if (analizador.chequearDeclaracion(t))
			agregarErrorSem(s+" no declarada.");
}

public String chequearTipoTS(Token f){
	String nombre = f.getLexemaInvertido();
	for (String s:analizador.getTS().keySet()){
		RegistroTS r=analizador.getTS().get(s);
		if (s.contains(nombre)&&r.getUso()=="parametro")
			return r.getTipo();
	}
	return null;
}

public String getNombreParametro(Token f){
	String nombre = f.getLexemaInvertido();
	for (String s:analizador.getTS().keySet()){

		RegistroTS r=analizador.getTS().get(s);
		if (s.contains(nombre)&&r.getUso()=="parametro")
			return s;
	}
	return null;
}

/*
public String agregarInvocadaEnTS(Token id){
		String invocada = id.getLexema();
		int i=invocada.indexOf("@");
		String armada=invocada.substring(0,i)+"_invocada";
		RegistroTS r=analizador.getTS().get(invocada);
		analizador.agregarEnTS(armada,"invocacion",r.getTipo());
		return armada;
}*/

public void setUso(Vector<Token> vec, String tipo){
		for (Token t: vec){
			String c = t.getLexema();
			RegistroTS r = analizador.eliminarRegistro(c);
			r.setTipo(tipo);
			analizador.getTS().put(c+sufijo,r);
		}
}

public void chequearReDeclaracion(Token t){
        if (analizador.getRegistro(t.getLexema()).getCant()> 1){
                agregarErrorSem("Variable redeclarada.");
                analizador.disminuirCant(t.getLexema());
        }
        else 
		{
			tokensActuales.add(t.getLexema());
        }
}

public void setTipoyUsoParametro(Token t, Token s){
			String tDato = s.getId();
			analizador.getTS().get(t.getLexema()).setTipo(tDato);
			analizador.getTS().get(t.getLexema()).setUso("parametro");
}

public void setearTipoYUso(Token tipo) {
        //Actualiza tipo en la tabla de simbolos
        String tDato = tipo.getId();
        for (String t: tokensActuales){
            RegistroTS r = analizador.getTS().get(t);
			r.setTipo(tDato);
			r.setUso("variable");			 
        }
        tokensActuales.clear();
}

public void setParametro(Token t, String s){
			String c = t.getLexema();
			RegistroTS r = analizador.eliminarRegistro(c);
			analizador.getTS().put(c+s,r);
			tokensActuales.add(t.getLexema()+s);
}

public void redefinirTabla(Token id, String tipo){
	analizador.redefinirTabla(id,tipo);
}

public void inicioFuncion(Token nombreFuncion){
	analizador.apilarNombre(nombreFuncion.getLexemaCorto());
}	

public void finFuncion(){
	analizador.desapilarNombre();
}

public void yyerror(String error){
	
}

public int yyparser(){
	return yyparse();
}

public ArrayList<String> getErroresSemanticos(){
        return erroresSemanticos;
}

private void agregarErrorSem(String mensaje){
	if (mensaje!=null)
		erroresSemanticos.add("Error semantico en linea "+ Fuente.lineaActual + ": "+mensaje);
}

private void agregarError(String mensaje) {
	errores.add(mensaje);
}
    
private void agregarRegla(String mensaje) {
	reglas.add(mensaje);
}
public ArrayList<String> getReglas(){
	return reglas;
}
public ArrayList<String> getErrores(){
	return errores;
}

public ArrayList<String> getTercetos(){
	return tercetos;
}

public AnalizadorLexico getAnalizador(){
	return analizador;
}

public void salidaTercetos(String mensaje){
	tercetos.add(mensaje);
}
//#line 728 "Parser.java"
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
//#line 28 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual + ": Nombre del programa."); analizador.apilarNombre(((Token)val_peek(0).obj).getLexema());}
break;
case 2:
//#line 29 "Gramatica.y"
{GeneradorAssembler.start = analizador.getNumeroTerceto();}
break;
case 3:
//#line 29 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual + ": Estructura Correcta del programa.");redefinirTabla((Token)val_peek(6).obj,"programa");}
break;
case 4:
//#line 31 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ 1 +": Falta nombre del programa.");}
break;
case 5:
//#line 32 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ 1 +": Nombre mal definido."); }
break;
case 6:
//#line 33 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual + ": Nombre del programa.");}
break;
case 7:
//#line 33 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ 1 +": Faltan sentencias Declarativas."); }
break;
case 10:
//#line 40 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia CONVERSION."); }
break;
case 13:
//#line 45 "Gramatica.y"
{Terceto aux=pilaTercetos.desapilar();
																										Terceto ret = new TReturn(((Elemento)val_peek(1).obj),aux,analizador.getNumeroTerceto()); 
																										analizador.addTerceto(ret);
																										finFuncion();}
break;
case 14:
//#line 49 "Gramatica.y"
{Terceto aux=pilaTercetos.desapilar();
																					Terceto ret = new TReturn(((Elemento)val_peek(1).obj),aux,analizador.getNumeroTerceto()); 
																					analizador.addTerceto(ret);
																					finFuncion();}
break;
case 17:
//#line 61 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual + ": Declaracion de variables."); setearTipoYUso((Token)val_peek(2).obj);}
break;
case 18:
//#line 64 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Lista de variables definida de forma incorrecta."); }
break;
case 19:
//#line 65 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Tipo mal definido."); }
break;
case 20:
//#line 66 "Gramatica.y"
{agregarError("Error Sintactico en linea "+ (Fuente.lineaActual) + ": se espera \";\".");}
break;
case 21:
//#line 69 "Gramatica.y"
{ chequearReDeclaracion((Token)val_peek(0).obj); }
break;
case 22:
//#line 70 "Gramatica.y"
{ chequearReDeclaracion((Token)val_peek(0).obj); }
break;
case 26:
//#line 80 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia FOR."); }
break;
case 27:
//#line 81 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia Asignacion."); }
break;
case 28:
//#line 82 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia ANOTADA."); 
													((TAsignacion)val_peek(2).obj).noChequear();}
break;
case 29:
//#line 84 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia PRINT."); }
break;
case 30:
//#line 85 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia llamado FUNCTION."); }
break;
case 32:
//#line 92 "Gramatica.y"
{Token aux = new Token(null,null,String.valueOf(analizador.getNumeroTerceto())); 
                                                   Terceto t = new TLabel(aux, analizador.getNumeroTerceto()); 
									               analizador.addTerceto(t);}
break;
case 33:
//#line 95 "Gramatica.y"
{agregarRegla("Linea "+ Fuente.lineaActual +": Se genero una estrucutra IF ELSE.");
								      Token aux = new Token(null,null,String.valueOf(analizador.getNumeroTerceto()));
                                      Terceto t = new TLabel(aux,analizador.getNumeroTerceto()); 
								      analizador.addTerceto(t);}
break;
case 34:
//#line 101 "Gramatica.y"
{agregarRegla("Linea "+ Fuente.lineaActual +": Se genero una estructura IF.");
									               Token aux = new Token(null,null,String.valueOf(analizador.getNumeroTerceto())); 
												   Terceto t = new TLabel(aux,analizador.getNumeroTerceto()); 
								                   analizador.addTerceto(t);}
break;
case 35:
//#line 107 "Gramatica.y"
{Terceto t = new TJump(null, null, analizador.getNumeroTerceto(), (TComparador)val_peek(1).obj); 
		  										analizador.addTerceto(t); 
		  										pilaTercetos.apilar(t);}
break;
case 36:
//#line 112 "Gramatica.y"
{Terceto aux = pilaTercetos.desapilar(); 
		 										 int suma = analizador.getNumeroTerceto()+1; 
												 aux.setT1(new Token(null,null,"Label"+suma)); 
		  										 Terceto t = new TJumpIn(null, null,analizador.getNumeroTerceto()); 
		  										 analizador.addTerceto(t); 
		  										 pilaTercetos.apilar(t);}
break;
case 37:
//#line 120 "Gramatica.y"
{Terceto aux = pilaTercetos.desapilar(); 
													  aux.setT1(new Token(null,null, "Label" + String.valueOf(analizador.getNumeroTerceto())));}
break;
case 38:
//#line 147 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Se genero una estructura FOR. ");}
break;
case 39:
//#line 148 "Gramatica.y"
{
										Terceto aux3 = pilaTercetos.desapilar();
									    int suma = analizador.getNumeroTerceto()+1;
										aux3.setT1(new Token(null,null,"Label"+suma));
										Terceto aux2 = pilaTercetos.desapilar();
										String salto="Label"+aux2.getT1().getNombre();
										Terceto tj = new TJumpIn(new Token(null,null,salto), null, analizador.getNumeroTerceto()); 
										analizador.addTerceto(tj); 
										Token aux = new Token(null,null,String.valueOf(analizador.getNumeroTerceto())); 
						 				Terceto t = new TLabel(aux,  analizador.getNumeroTerceto()); 
										analizador.addTerceto(t);}
break;
case 40:
//#line 161 "Gramatica.y"
{	chequearDeclaracion((Token)val_peek(3).obj,"Variable");
																						Token aux = new Token(String.valueOf(analizador.getNumeroTerceto()),null,String.valueOf(analizador.getNumeroTerceto())); 
																						Terceto tl= new TLabel(aux, analizador.getNumeroTerceto()); 
																						analizador.addTerceto(tl); 
																						pilaTercetos.apilar(tl);
																						Terceto t1 = new TComparador((Elemento)val_peek(3).obj, (Elemento)val_peek(1).obj, ((Token)val_peek(2).obj).getNombre(), analizador.getNumeroTerceto()); 
																						analizador.addTerceto(t1); 
																						Terceto t2 = new TJump(null, null, analizador.getNumeroTerceto(), (TComparador)t1);
																						analizador.addTerceto(t2);
																						pilaTercetos.apilar(t2);
																						agregarErrorSem(t1.getError());}
break;
case 43:
//#line 178 "Gramatica.y"
{chequearDeclaracion((Token)val_peek(2).obj,"Variable");
															Terceto aux = new TResta((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
															salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Resta de MenosIgual.");
															analizador.addTerceto((Terceto)aux);
															yyval.obj = new TAsignacion((Elemento)val_peek(2).obj, (Elemento)aux, analizador.getNumeroTerceto()); 
															analizador.addTerceto((Terceto)yyval.obj);
															salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Asignación de MenosIgual.");
															agregarErrorSem(((Terceto)yyval.obj).getError());}
break;
case 44:
//#line 187 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera '=' en la actualizacionVariable."); }
break;
case 45:
//#line 188 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera '-' antes del '=' en la actualizacionVariable."); }
break;
case 46:
//#line 189 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera una expresion antes del '-=' en la actualizacionVariable."); }
break;
case 47:
//#line 190 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": For mal definido."); }
break;
case 49:
//#line 194 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual+": Bloque de sentencias."); }
break;
case 52:
//#line 199 "Gramatica.y"
{agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Conjunto de sentencias mal definida."); }
break;
case 53:
//#line 204 "Gramatica.y"
{agregarRegla("Linea "+ Fuente.lineaActual +": Condicion.");
														yyval.obj = new TComparador((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, ((Token)val_peek(1).obj).getLexema(), analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
														salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Comparacion.");
														agregarErrorSem(((Terceto)yyval.obj).getError());}
break;
case 54:
//#line 210 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Se espera una expresion luego del operador de comparacion."); }
break;
case 55:
//#line 211 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Se espera la condicion antes del operador de comparacion."); }
break;
case 56:
//#line 212 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Condicion definida incorrectamente."); }
break;
case 57:
//#line 213 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Condicion incompleta."); }
break;
case 64:
//#line 227 "Gramatica.y"
{chequearDeclaracion((Token)val_peek(2).obj,"Variable");
																String tipo1 = ((Token)val_peek(2).obj).getPuntero().getTipo();
																
																if (tipo1!=null){
																if (!(tipo1.equals(((Elemento)val_peek(0).obj).getTipo()))&&(inttouint == false))
																	agregarErrorSem("Conversion implicita no permitida.");
																if ( (tipo1.equals("integer")) && (((Elemento)val_peek(0).obj).getTipo().equals("uinteger")))
																	agregarErrorSem("uinteger no se puede convertir a integer.");
																
																yyval.obj = new TAsignacion((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
																analizador.addTerceto((Terceto)yyval.obj);
																salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Asignación.");
																}
																}
break;
case 65:
//#line 242 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera ':=' en la asignacion."); }
break;
case 66:
//#line 243 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Expresion invalida en asignacion."); }
break;
case 67:
//#line 244 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Expresion invalida en asignacion."); }
break;
case 68:
//#line 245 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Expresion invalida en asignacion."); }
break;
case 69:
//#line 250 "Gramatica.y"
{agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia Allow.");
											if (((Token)val_peek(2).obj).getLexema().equals("integer")&&((Token)val_peek(0).obj).getLexema().equals("uinteger"))
												inttouint=true;
											else
												agregarErrorSem("Conversion no permitida.");}
break;
case 70:
//#line 256 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Falta un tipo en la conversion."); }
break;
case 71:
//#line 257 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Falta un tipo en la conversion."); }
break;
case 72:
//#line 258 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Falta ALLOW en la conversion."); }
break;
case 73:
//#line 259 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Error en la conversion."); }
break;
case 74:
//#line 264 "Gramatica.y"
{if (((Elemento)val_peek(2).obj).getTipo().equals("integer")&&((Elemento)val_peek(0).obj).getTipo().equals("uinteger")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(2).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TSuma(conversion, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												if (((Elemento)val_peek(2).obj).getTipo().equals("uinteger")&&((Elemento)val_peek(0).obj).getTipo().equals("integer")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(0).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TSuma((Elemento)val_peek(2).obj, conversion, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												else{
													yyval.obj = new TSuma((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
													analizador.addTerceto((Terceto)yyval.obj);
													salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Suma.");
													}
												}
break;
case 75:
//#line 289 "Gramatica.y"
{if (((Elemento)val_peek(2).obj).getTipo().equals("integer")&&((Elemento)val_peek(0).obj).getTipo().equals("uinteger")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(2).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TResta(conversion, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												if (((Elemento)val_peek(2).obj).getTipo().equals("uinteger")&&((Elemento)val_peek(0).obj).getTipo().equals("integer")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(0).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TResta((Elemento)val_peek(2).obj, conversion, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												else{yyval.obj = new TResta((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
													 analizador.addTerceto((Terceto)yyval.obj);
													 salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Resta.");}
												}
break;
case 77:
//#line 313 "Gramatica.y"
{agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia INTTOUINT.");
													if (inttouint == false)
														agregarErrorSem("Mal declarado Allow en las sentencias declarativas.");
													yyval.obj=new TConversion((Elemento)val_peek(1).obj,analizador.getNumeroTerceto());
													}
break;
case 78:
//#line 319 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera un termino luego del '+'."); }
break;
case 79:
//#line 320 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera una expresion antes del '+'."); }
break;
case 80:
//#line 321 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera un termino luego del '-'."); }
break;
case 81:
//#line 322 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera una expresion antes del '-'."); }
break;
case 82:
//#line 324 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera un ')'."); }
break;
case 83:
//#line 325 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera una expresion luego del '('."); }
break;
case 84:
//#line 326 "Gramatica.y"
{ agregarError("Error Sintáctico en línea "+ Fuente.lineaActual + ": Se espera un'('."); }
break;
case 85:
//#line 327 "Gramatica.y"
{ agregarError("Error Sintáctico en línea "+ Fuente.lineaActual +": Se espera una expresion."); }
break;
case 86:
//#line 328 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Se espera una expresion."); }
break;
case 87:
//#line 332 "Gramatica.y"
{if (((Elemento)val_peek(2).obj).getTipo().equals("integer")&&((Elemento)val_peek(0).obj).getTipo().equals("uinteger")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(2).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TMultiplicacion(conversion, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												if (((Elemento)val_peek(2).obj).getTipo().equals("uinteger")&&((Elemento)val_peek(0).obj).getTipo().equals("integer")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(0).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TMultiplicacion((Elemento)val_peek(2).obj, conversion, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												else{yyval.obj = new TMultiplicacion((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
													 analizador.addTerceto((Terceto)yyval.obj);
													 salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Multiplicacion.");}
											 }
break;
case 88:
//#line 355 "Gramatica.y"
{if (((Elemento)val_peek(2).obj).getTipo().equals("integer")&&((Elemento)val_peek(0).obj).getTipo().equals("uinteger")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(2).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TDivision(conversion, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												if (((Elemento)val_peek(2).obj).getTipo().equals("uinteger")&&((Elemento)val_peek(0).obj).getTipo().equals("integer")){
													if (inttouint == true){
														Terceto conversion=new TConversion((Elemento)val_peek(0).obj,analizador.getNumeroTerceto());
														analizador.addTerceto(conversion);
														yyval.obj = new TDivision((Elemento)val_peek(2).obj, conversion, analizador.getNumeroTerceto());
														analizador.addTerceto((Terceto)yyval.obj);
													}
													else agregarErrorSem("Tipos incompatibles");
												}
												else {yyval.obj = new TDivision((Elemento)val_peek(2).obj, (Elemento)val_peek(0).obj, analizador.getNumeroTerceto());	
													  analizador.addTerceto((Terceto)yyval.obj);
													  salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Division.");}
											}
break;
case 90:
//#line 380 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera un termino luego del '*'."); }
break;
case 91:
//#line 381 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera una expresion antes del '*'."); }
break;
case 92:
//#line 382 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera un termino luego del '/'."); }
break;
case 93:
//#line 383 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual + ": Se espera una expresion antes del '/'."); }
break;
case 94:
//#line 387 "Gramatica.y"
{chequearDeclaracion((Token)val_peek(0).obj,"Variable");}
break;
case 96:
//#line 389 "Gramatica.y"
{yyval.obj = val_peek(0).obj;}
break;
case 97:
//#line 395 "Gramatica.y"
{yyval.obj = new TPrint((Elemento)val_peek(1).obj, analizador.getNumeroTerceto()); 
									           analizador.addTerceto((Terceto)yyval.obj); 
									           salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Print.");}
break;
case 98:
//#line 399 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Se espera ')'."); }
break;
case 99:
//#line 400 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Se espera una cadena de caracteres luego de '('."); }
break;
case 100:
//#line 401 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Se espera '('."); }
break;
case 101:
//#line 402 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Error al llamar al print."); }
break;
case 103:
//#line 409 "Gramatica.y"
{setTipoyUsoParametro((Token)val_peek(1).obj,(Token)val_peek(2).obj);}
break;
case 105:
//#line 413 "Gramatica.y"
{inicioFuncion((Token)val_peek(0).obj);
														Terceto t = new TLabel((Token)val_peek(0).obj,  analizador.getNumeroTerceto());
														analizador.addTerceto(t);
														agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia FUNCTION."); 
														RegistroTS r = analizador.getTS().get(((Token)val_peek(0).obj).getLexema());
														r.setUso("funcion");
														analizador.agregarEnTS(((Token)val_peek(0).obj).getLexemaCorto()+"_ret", "retorno",((Token)val_peek(2).obj).getId());
														redefinirTabla((Token)val_peek(0).obj,((Token)val_peek(2).obj).getId());
														pilaTercetos.apilar(t);}
break;
case 106:
//#line 423 "Gramatica.y"
{agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Error de declaracion de funcion.");}
break;
case 110:
//#line 431 "Gramatica.y"
{yyval.obj=val_peek(0).obj;}
break;
case 114:
//#line 439 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia FOR."); }
break;
case 115:
//#line 440 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia Asignacion."); }
break;
case 116:
//#line 441 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia ANOTADA."); 
													((TAsignacion)val_peek(2).obj).noChequear();}
break;
case 117:
//#line 443 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia PRINT."); }
break;
case 118:
//#line 444 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia llamado FUNCTION."); }
break;
case 119:
//#line 445 "Gramatica.y"
{ agregarRegla("Linea "+ Fuente.lineaActual +": Sentencia Resta Asignacion."); }
break;
case 120:
//#line 448 "Gramatica.y"
{yyval.obj=val_peek(2).obj;
														salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Return.");}
break;
case 121:
//#line 450 "Gramatica.y"
{yyval.obj=val_peek(2).obj;
														salidaTercetos("Linea: " + Fuente.lineaActual + ". Generacion de codigo: Terceto Return.");}
break;
case 122:
//#line 454 "Gramatica.y"
{yyval.obj = val_peek(1).obj;}
break;
case 123:
//#line 455 "Gramatica.y"
{chequearDeclaracion((Token)val_peek(1).obj,"Variable");
													yyval.obj = val_peek(1).obj;}
break;
case 124:
//#line 458 "Gramatica.y"
{chequearDeclaracion((Token)val_peek(3).obj,"Funcion");
																		yyval.obj = val_peek(3).obj;}
break;
case 125:
//#line 460 "Gramatica.y"
{yyval.obj=null;}
break;
case 126:
//#line 462 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Llamado mal declarado.");}
break;
case 127:
//#line 463 "Gramatica.y"
{ agregarError("Error Sintactico en linea "+ Fuente.lineaActual +": Llamado mal declarado.");}
break;
case 128:
//#line 466 "Gramatica.y"
{
																	chequearDeclaracion((Token)val_peek(1).obj,"Funcion"); 				/*Se fija que si la funcion esta declarada*/
																	String tipoParamFunc=chequearTipoTS((Token)val_peek(1).obj);			/*Recupera el tipo del parametro de la funcion*/
																	if ((tipoParamFunc!=null)&&(((Token)val_peek(0).obj).getTipo()==null)||
																	   ((tipoParamFunc==null)&&(((Token)val_peek(0).obj).getTipo()!=null)))
																		agregarErrorSem("Tipo del parametro es incorrecto.");
																	String parametro=getNombreParametro((Token)val_peek(1).obj);
																	Terceto t1= new TPush(analizador.getNumeroTerceto());
																	analizador.addTerceto(t1);
																	String ret=((Token)val_peek(1).obj).getNombre();
																	int indice=ret.indexOf("@");
																	ret=ret.substring(0,indice)+"_ret"+ret.substring(indice, ret.length());
																	Terceto t3= new TLlamado(((Elemento)val_peek(1).obj),(Elemento)val_peek(0).obj, parametro, analizador.getNumeroTerceto()); 
																	analizador.addTerceto(t3);
																	Terceto t2= new TPop(analizador.getNumeroTerceto()); 
																	analizador.addTerceto(t2); 
																	yyval.obj=t3;}
break;
//#line 1453 "Parser.java"
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
