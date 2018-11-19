%{
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
%}

%token ID ASIGNACION COMP_MAYOR_IGUAL COMP_MENOR_IGUAL COMP_DIFERENTE IF ELSE END_IF PRINT LINTEGER SINGLE WHILE LET MUT CADENA CTE
%left '+' '-'
%left '*' '/'
%start programa


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
  LET MUT tipo listaVariables                         {addReglaSintacticaReconocida(String.format("sentencia declarativa reconocida en linea %1$d",al.getLinea()));
  for(EntradaTablaSimbolos e: ((ArrayList<EntradaTablaSimbolos>)$4.obj)){
    //redeclaracion de variables ya revisado en regla del identificadorDec
    //seteo el tipo de la variable, o de lo apuntado en caso de punteros.
      if (e instanceof EntradaTablaDeSimbolosPuntero){
          ((EntradaTablaDeSimbolosPuntero)e).setTipoApuntable((String) $3.sval);
      }
      else {
          e.setTipo((String) $3.sval);
      }
    //las marco como mutables
    e.setMutable(true);
  }
}

        |
  LET error tipo listaVariables                       {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
  error MUT tipo listaVariables                       {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
  LET MUT error listaVariables                        {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
  LET MUT tipo error                                  {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
  LET tipo asignacionCte                              {addReglaSintacticaReconocida(String.format("sentencia declarativa en linea %1$d",al.getLinea()));
  //redeclaracion de variables ya revisado en regla del identificadorDec
  EntradaTablaSimbolos ets = ((ParEntradas)$3.obj).e1;
  //seteo el tipo del identificador o de la variable apuntada en caso de un puntero
  if (ets instanceof EntradaTablaDeSimbolosPuntero){
    ((EntradaTablaDeSimbolosPuntero)ets).setTipoApuntable((String) $2.sval);
  }
  else {
    ets.setTipo((String) $2.sval);
  }
  //lo marco como inmutable
  ets.setMutable(false);
  //chequeo compatibilidad de tipos en la asignacion, o de punteros
  //en caso de asignar a un puntero una referencia a la direccion de memoria de otra variable
  EntradaTablaSimbolos etsCte = ((ParEntradas)$3.obj).e2;
  if (ets instanceof EntradaTablaDeSimbolosPuntero) {
    //corroboro se intente asignar una direccion de memoria
    if (!(etsCte instanceof EntradaTablaDeSimbolosReferenciaAMemoria)) {
      addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
    } else {
      //debo corroborar que el tipo del puntero y el de la variable referenciada sean el mismo
      if (((EntradaTablaDeSimbolosPuntero)ets).getReferenciadoTipo() == ((EntradaTablaDeSimbolosReferenciaAMemoria)etsCte).getReferenciadoTipo()) {
        ((EntradaTablaDeSimbolosPuntero) ets).setApuntado(((EntradaTablaDeSimbolosReferenciaAMemoria) etsCte).getReferenciado());
        //creo un terceto para la asignacion y lo agrego al listado
        Terceto terceto = new Terceto("ASIGNACION", ets, etsCte);
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
      } else
        addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
    }
  }
  else {
    //en caso de no tratarse de un puntero debo checkear que el r-value sea una variable (no referencia a memoria) y del mismo tipo que el l-value
    if ((etsCte instanceof EntradaTablaDeSimbolosReferenciaAMemoria)) {
      addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
    }
    else {
      //chequeo compatibilidad de tipos
      if (ets.getTipo() != etsCte.getTipo()) {
        addErrorSemantico(String.format("Asignacion cte de tipos incompatibles en linea %1$d", al.getLinea()));
      }
      else{
        //creo un terceto para la asignacion y lo agrego al listado
        Terceto terceto = new Terceto("ASIGNACION", ets, etsCte);
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
      }
    }
  }
}
        |
  error tipo asignacionCte                            {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
  LET error asignacionCte                             {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
        |
  LET tipo error                                      {addErrorSintactico(String.format("sentencia declarativa mal declarada en linea %1$d",al.getLinea()));}
  ;

  tipo:
  LINTEGER                                            {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));
  $$=new ParserVal("Linteger");
}
        |
  SINGLE                                              {addReglaSintacticaReconocida(String.format("tipo reconocida en linea %1$d",al.getLinea()));
  $$=new ParserVal("Single");
}
  ;

  listaVariables:
  identificadorDec                                          {addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));
  ArrayList<EntradaTablaSimbolos> listaIds = new ArrayList<>();
  listaIds.add(((EntradaTablaSimbolos)$1.obj));
  $$=new ParserVal(listaIds);
}
        |
  listaVariables ';' identificadorDec                       {addReglaSintacticaReconocida(String.format("lista de variables reconocida en linea %1$d",al.getLinea()));
  ((ArrayList<EntradaTablaSimbolos>)$1.obj).add((EntradaTablaSimbolos) $3.obj);
  $$= $1;
}
        |
  listaVariables error identificadorDec                     {addErrorSintactico(String.format(" declaracion de lista de variables esperaba un ; entre variables en linea %1$d",al.getLinea()));}
  ;

  identificador:
        '*' ID                                              {/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
  EntradaTablaSimbolos ets=(EntradaTablaSimbolos) $2.obj;
  String lexema = ets.getLexema();
  //chequeo que la variable ya halla sido declarada
  if (!listaDeLexemasDeclarados.contains(lexema))
    addErrorSemantico(String.format("variable redeclarada, en linea %1$d", al.getLinea()));
  else {
    //chequeo que si estoy tratando de desreferenciar a un puntero la variable sea efectivamente de tipo puntero
    if (!(ets.getTipo() == "ReferenciaAMemoria")) {
      addErrorSemantico(String.format("variable desreferenciada no es de tipo puntero, en linea %1$d", al.getLinea()));
    } else {
      //chequeo que halla sido inicializado el puntero
      if ((((EntradaTablaDeSimbolosPuntero) ets).getApuntado()) == null) {
        addErrorSemantico(String.format("variable desreferenciada no apunta a nada, en linea %1$d", al.getLinea()));
      } else {
        //retorno la variable apuntada por el puntero
        $$ = new ParserVal(((EntradaTablaDeSimbolosPuntero) ets).getApuntado());
      }
    }
  }
}
        |
  ID

  {/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
    String lexema = ((EntradaTablaSimbolos) $1.obj).getLexema();
    //chequeo que la variable ya halla sido declarada
    if (!listaDeLexemasDeclarados.contains(lexema))
      addErrorSemantico(String.format("variable no declarada, en linea %1$d", al.getLinea()));
    else {
      //si fue declarada solo retorno su entrada en la tabla de simbolos
      $$ = $1;
    }
  }

  identificadorDec:
        '*' ID                                              {/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
  //si ya fue declarada la variable o un puntero con el mismo nombre
  String lexema = ((EntradaTablaSimbolos) $2.obj).getLexema();
  if (listaDeLexemasDeclarados.contains(lexema))
    addErrorSemantico(String.format("variable redeclarada, en linea %1$d", al.getLinea()));
  else {
    //creo una entrada para el puntero, apuntando a nada por el momento -null-
    EntradaTablaSimbolos ets = new EntradaTablaDeSimbolosPuntero(lexema, "ReferenciaAMemoria",null);
    //la marco como ya declarada
    listaDeLexemasDeclarados.add(lexema);
    //elimino la entrada de la tabla de simbolos del identificador que no era puntero y lo reemplazo por el puntero
    al.getTablaDeSimbolos().remove(((EntradaTablaSimbolos) $2.obj).getLexema());
    al.getTablaDeSimbolos().put(ets.getLexema(),ets);
    //retorno la nueva entrada
    $$=new ParserVal(ets);
  }
}
        |
  ID                                                  {/*addReglaSintacticaReconocida(String.format("identificador reconocida en linea %1$d",al.getLinea()));*/
  String lexema = ((EntradaTablaSimbolos) $1.obj).getLexema();
  //si ya fue declarada la variable o un puntero con el mismo nombre
  if (listaDeLexemasDeclarados.contains(lexema))
    addErrorSemantico(String.format("variable redeclarada, en linea %1$d", al.getLinea()));
  else{
    //la marco como ya declarada
    listaDeLexemasDeclarados.add(lexema);
    $$=$1;
  }
}
  ;

  asignacionCte:
  identificadorDec ASIGNACION cte                           {addReglaSintacticaReconocida(String.format("asign cte reconocida en linea %1$d",al.getLinea()));
  $$=new ParserVal(new ParEntradas((EntradaTablaSimbolos)$1.obj,(EntradaTablaSimbolos)$3.obj));
}
        |
  identificadorDec error cte                                {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
        |
  identificadorDec ASIGNACION error                         {addErrorSintactico(String.format("asign cte mal definida en linea %1$d",al.getLinea()));}
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
  encabezadoIf cuerpoIf                               {addReglaSintacticaReconocida(String.format("if reconocida en linea %1$d",al.getLinea()));
  ListaTercetos lt = ListaTercetos.getInstanceOfListaDeTercetos();
  Terceto acompletar = lt.getTerceto(lt.getPilaTercetos().pop());
  acompletar.setOperando2(new TercetoDestino(lt.getTercetos().size()));
}
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
  cuerpoThen ELSE cuerpoElse END_IF
        |
  error ELSE cuerpoElse END_IF                          {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
  cuerpoThen error cuerpoElse END_IF              {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
  cuerpoThen ELSE error END_IF                          {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
  cuerpoThen ELSE cuerpoElse error                {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
  cuerpoThen END_IF
        |
  cuerpoThen error                                      {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
        |
  error END_IF                                                {addErrorSintactico(String.format("cuerpo del if mal definido en linea %1$d",al.getLinea()));}
  ;

  cuerpoThen: bloqueSentencias {ListaTercetos lt = ListaTercetos.getInstanceOfListaDeTercetos();
  Terceto tercetoACompletar = lt.getTerceto(lt.getPilaTercetos().pop());
  tercetoACompletar.setOperando2(new TercetoDestino(lt.getTercetos().size()+1));
  Terceto incondicionalAcompletar = new Terceto("BI");
  lt.addTerceto(incondicionalAcompletar);
  lt.getPilaTercetos().push(lt.getTercetos().size()-1);
}
  ;

  cuerpoElse: bloqueSentencias
  ;

  sentenciaWhile:
  inicioWhile '(' condicion ')' bloqueSentencias                    {addReglaSintacticaReconocida(String.format("while reconocida en linea %1$d",al.getLinea()));
  ListaTercetos lt=ListaTercetos.getInstanceOfListaDeTercetos();
  Terceto tercetoIncompleto = lt.getTerceto(lt.getPilaTercetos().pop());
  tercetoIncompleto.setOperando2(new TercetoDestino(lt.getTercetos().size()+1));
  Terceto saltoAlInicio= new Terceto("BI");
  saltoAlInicio.setOperando2(new TercetoDestino(lt.getPilaTercetos().pop()));
  lt.addTerceto(saltoAlInicio);
}
        |
  inicioWhile  error condicion ')' bloqueSentencias                  {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
        |
  inicioWhile  '(' error ')' bloqueSentencias                        {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
        |
  inicioWhile  '(' condicion error bloqueSentencias                  {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
        |
  inicioWhile  '(' condicion ')' error                               {addErrorSintactico(String.format("while mal definido en linea %1$d",al.getLinea()));}
  ;

  inicioWhile: WHILE { ListaTercetos.getInstanceOfListaDeTercetos().getPilaTercetos().push(ListaTercetos.getInstanceOfListaDeTercetos().getTercetos().size());}
  ;

  asignacion:
  identificador ASIGNACION expresion                             {
  addReglaSintacticaReconocida(String.format("asignacion reconocida en linea %1$d",al.getLinea()));
  //chequeo compatibilidad de tipos
  if (((Operando)$1.obj).getTipo()!=((Operando)$3.obj).getTipo()){
    addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
  }
  else{
    //chequeo el el l-value sea mutable
    if (!((EntradaTablaSimbolos)$1.obj).isMutable()){
      addErrorSemantico(String.format("asignacion a variable inmutable en linea %1$d",al.getLinea()));
    }
    else{
      //chequeo el caso de una asignacion entre punteros, para que ademas de ser ambos de tipo puntero ambos apunten a elementos del mismo tipo
      //el r-value puede no ser un puntero, sino una suma de direcciones de memoria usadas como variables de tipo puntero o &variable (no tipo puntero)
      if (((Operando) $1.obj).getTipo() == "ReferenciaAMemoria") {
        if (((ReferenciaAMemoria) $1.obj).getReferenciadoTipo() != (((ReferenciaAMemoria) $3.obj).getReferenciadoTipo())) {
          addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
        } else {
        //chequeo que el r-value sea un puntero o una direccion de memoria de una sola variable, y no una operacion entre direcciones de memoria
        if (($3.obj instanceof EntradaTablaDeSimbolosReferenciaAMemoria)||($3.obj instanceof EntradaTablaDeSimbolosPuntero)){
          ((EntradaTablaDeSimbolosPuntero) $1.obj).setApuntado(((ReferenciaAMemoriaSimple) $3.obj).getReferenciado());
          Terceto terceto = new Terceto("ASIGNACION", (Operando) $1.obj, (Operando) $3.obj);
          ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
          $$ = new ParserVal(terceto);
          }
          else {
                    addErrorSemantico(String.format("r-value asigando al puntero no es un puntero o una direccion de una variable simple, en linea %1$d", al.getLinea()));
          }
        }
      }
      else {
        //creo un nuevo terceto para la asignacion y lo retorno
        Terceto terceto = new Terceto("ASIGNACION", (Operando) $1.obj, (Operando) $3.obj);
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
        $$ = new ParserVal(terceto);
      }
    }
  }
}
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
  expresion comparador expresion              {
  addReglaSintacticaReconocida(String.format("condicion reconocida en linea %1$d",al.getLinea()));
  //chequeo compatibilidad de tipos
  if (((Operando)$1.obj).getTipo()!=((Operando)$3.obj).getTipo()){
    addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
  }
  else {
    //chequeo que el tipo sea entero largo, es el unico permitido para condiciones
    if (((Operando) $1.obj).getTipo() != ("Linteger")) {
      addErrorSemantico(String.format("tipo en condicion debe ser entero. linea %1$d", al.getLinea()));
    }
    //creo un terceto para la comparacion, lo añao a la lista
    Terceto terceto = new Terceto($2.sval, (Operando)$1.obj, (Operando)$3.obj);
    ListaTercetos lt= ListaTercetos.getInstanceOfListaDeTercetos();
    lt.addTerceto(terceto);
    //añado un terceto para indicar el branch por falso y apilo el terceto recien creado incompleto para completar luego
    lt.addTerceto(new Terceto("BF",lt.getTerceto(lt.getTercetos().size()-1)));
    lt.getPilaTercetos().push(lt.getTercetos().size()-1);
  }
}
        |
  error comparador expresion                  {addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
        |
  expresion error expresion                   {addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
        |
  expresion comparador error                  {addErrorSintactico(String.format("condicion mal definido en linea %1$d",al.getLinea()));}
  ;

  expresion:
  expresion '+' termino                       {addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
  //Chequeo compatibilidad de tipos para operar
  if (((Operando)$1.obj).getTipo()!=((Operando)$3.obj).getTipo()){
    addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
  }
  else {
    //chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo
    if (((Operando) $1.obj).getTipo() == "ReferenciaAMemoria") {
      if (((ReferenciaAMemoria) $1.obj).getReferenciadoTipo() != (((ReferenciaAMemoria) $3.obj).getReferenciadoTipo())) {
        addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
      } else {
        TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("+", (Operando) $1.obj, (Operando) $3.obj);
        terceto.setTipoResultante("ReferenciaAMemoria");
        terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) $1.obj).getReferenciadoTipo());
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
        $$ = new ParserVal(terceto);
      }
    } else {
      //en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno
      Terceto terceto = new Terceto("+", (Operando) $1.obj, (Operando) $3.obj);
      terceto.setTipoResultante(((Operando) $1.obj).getTipo());
      ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
      $$ = new ParserVal(terceto);
    }
  }
}
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
  //Chequeo compatibilidad de tipos para operar
  if (((Operando)$1.obj).getTipo()!=((Operando)$3.obj).getTipo()){
    addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
  }
  else {
    //chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo
    if (((Operando) $1.obj).getTipo() == "ReferenciaAMemoria") {
      if (((ReferenciaAMemoria) $1.obj).getReferenciadoTipo() != (((ReferenciaAMemoria) $3.obj).getReferenciadoTipo())) {
        addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
      } else {
        TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("-", (Operando) $1.obj, (Operando) $3.obj);
        terceto.setTipoResultante("ReferenciaAMemoria");
        terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) $1.obj).getReferenciadoTipo());
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
        $$ = new ParserVal(terceto);
      }
    } else {
      //en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno
      Terceto terceto = new Terceto("-", (Operando) $1.obj, (Operando) $3.obj);
      terceto.setTipoResultante(((Operando) $1.obj).getTipo());
      ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
      $$ = new ParserVal(terceto);
    }
  }
}
        |
  termino                                     {addReglaSintacticaReconocida(String.format("expresion reconocida en linea %1$d",al.getLinea()));
  $$=$1;}
  ;

  termino:
  termino '*' factor                          {
  /*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
  //Chequeo compatibilidad de tipos para operar
  if (((Operando)$1.obj).getTipo()!=((Operando)$3.obj).getTipo()){
    addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
  }
  else {
    //chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo
    if (((Operando) $1.obj).getTipo() == "ReferenciaAMemoria") {
      if (((ReferenciaAMemoria) $1.obj).getReferenciadoTipo() != (((ReferenciaAMemoria) $3.obj).getReferenciadoTipo())) {
        addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
      } else {
        TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("*", (Operando) $1.obj, (Operando) $3.obj);
        terceto.setTipoResultante("ReferenciaAMemoria");
        terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) $1.obj).getReferenciadoTipo());
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
        $$ = new ParserVal(terceto);
      }
    } else {
      //en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno
      Terceto terceto = new Terceto("*", (Operando) $1.obj, (Operando) $3.obj);
      terceto.setTipoResultante(((Operando) $1.obj).getTipo());
      ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
      $$ = new ParserVal(terceto);
    }
  }
}

        |
  termino '/' factor                          {/*addReglaSintacticaReconocida(String.format("termino reconocida en linea %1$d",al.getLinea()));*/
  //Chequeo compatibilidad de tipos para operar
  if (((Operando)$1.obj).getTipo()!=((Operando)$3.obj).getTipo()){
    addErrorSemantico(String.format("tipos incompatibles en linea %1$d",al.getLinea()));
  }
  else {
    //chequeo operaciones entre referencias a memoria que apunten a variables del mismo tipo
    if (((Operando) $1.obj).getTipo() == "ReferenciaAMemoria") {
      if (((ReferenciaAMemoria) $1.obj).getReferenciadoTipo() != (((ReferenciaAMemoria) $3.obj).getReferenciadoTipo())) {
        addErrorSemantico(String.format("tipos referenciados con la direccion de memoria incompatibles en linea %1$d", al.getLinea()));
      } else {
        TercetoReferenciaAMemoria terceto = new TercetoReferenciaAMemoria("/", (Operando) $1.obj, (Operando) $3.obj);
        terceto.setTipoResultante("ReferenciaAMemoria");
        terceto.setTipoResultanteApuntado(((ReferenciaAMemoria) $1.obj).getReferenciadoTipo());
        ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
        $$ = new ParserVal(terceto);
      }
    } else {
      //en caso de no ser referencias a memoria creo un terceto comun con sus operandos y lo retorno
      Terceto terceto = new Terceto("/", (Operando) $1.obj, (Operando) $3.obj);
      terceto.setTipoResultante(((Operando) $1.obj).getTipo());
      ListaTercetos.getInstanceOfListaDeTercetos().addTerceto(terceto);
      $$ = new ParserVal(terceto);
    }
  }
}

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
  CTE                                         {
  EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) ($1.obj);
  if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
    //chequeo si la cte positiva es mayor al maximo permitido-un valor por encima por si era negativa-
    if ((Long.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
      //si lo es, lo informo y utilizo reemplazo para bajarla al maximo permitido
      addErrorSintactico(String.format("warning linteger cte positiva mayor al maximo permitido en linea %1$d", al.getLinea()));
      String nuevoLexema = String.valueOf(AnalizadorLexico.MAX_LONG - 1);
      al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
      entradaTablaSimbolos.setLexema(nuevoLexema);
      al.getTablaDeSimbolos().put(entradaTablaSimbolos.getLexema(), entradaTablaSimbolos);
    }
  }
  /*addReglaSintacticaReconocida(String.format("cte reconocida en linea %1$d", al.getLinea()));*/
  entradaTablaSimbolos.incUsos();
  $$=$1;
}
        |
                '-'CTE %prec '*'                            {
  EntradaTablaSimbolos entradaTablaSimbolos = (EntradaTablaSimbolos) ($2.obj);
  String lexema = "-" + (entradaTablaSimbolos.getLexema());
  // no esta en tabla, agrega a TS
  if (!al.estaEnTabla(lexema)) {
    EntradaTablaSimbolos elementoTS = new EntradaTablaSimbolos(lexema, entradaTablaSimbolos.getTipo());
    al.agregarATablaSimbolos(elementoTS);
  }
  /*addReglaSintacticaReconocida(String.format("ctenegativa  reconocida en linea %1$d", al.getLinea()));*/
  //si el tipo es long debo chequear que su contraparte positiva que queda en la tabla de simbolos no sea mayor al maximo,
  if (entradaTablaSimbolos.getTipo() == EntradaTablaSimbolos.LONG) {
    if ((Long.valueOf(entradaTablaSimbolos.getLexema())) == AnalizadorLexico.MAX_LONG) {
      al.getTablaDeSimbolos().remove(entradaTablaSimbolos.getLexema());
    }
  }
  //al.getTablaDeSimbolos().get(lexema).incUsos();
  //retorno la entrada de la nueva cte negativa de la tabla de simbolos
  $$=new ParserVal(al.getTablaDeSimbolos().get(lexema));
}
        |
                '&'ID                                       {/*addReglaSintacticaReconocida(String.format("cte direccion de id reconocida en linea %1$d", al.getLinea())); */
  EntradaTablaSimbolos ets = (EntradaTablaSimbolos)$2.obj;
  //chequeo que ID sea una variable ya declarada
  if (!listaDeLexemasDeclarados.contains(ets.getLexema())){
    addErrorSemantico(String.format("variable nunca declarada en linea %1$d", al.getLinea()));
  }
  else {
    HashMap<String, EntradaTablaSimbolos> ts = al.getTablaDeSimbolos();
    // String lexemaRefMemoria = "&" + ets.getLexema();
    // si es la primera vez que referencio a ID como direccion de memoria lo doy de alta como cte en la tabla de simbolos
    // creo que no es necesario darlo de alta, solo elevar una entrada como ref a memoria
    // if (!ts.containsKey(lexemaRefMemoria))ts.put(lexemaRefMemoria, new EntradaTablaDeSimbolosReferenciaAMemoria(lexemaRefMemoria, ets.getTipo(),ets));
    $$=new ParserVal(new EntradaTablaDeSimbolosReferenciaAMemoria(ets.getLexema(),"ReferenciaAMemoria",ets));
  }

}
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