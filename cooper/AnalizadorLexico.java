package compilador;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import tercetos.*;


public class AnalizadorLexico {
    public class RegistroTS{//Estructura interna
    	private int cantidad;
    	String tipo;
    	String uso;
        public RegistroTS(String tipo){ 
        	this.tipo=tipo;
        	this.cantidad=1;
        }
        public void aumentarCant(){
        	cantidad++;
        }
        public int getCant(){
        	return cantidad;
        }
        public void disminuirCant(){
        	cantidad--;
        }
        public void setTipo(String t){
        	tipo = t;
        }
        
        public String getTipo(){
        	return tipo;
        }
        public void setUso(String uso){
        	this.uso=uso;
        }
        
        public String getUso(){
        	return uso;
        	
        }
    }
        
    //variables
	private String buffer;
    private static Hashtable<String,RegistroTS> TS = new Hashtable<String,RegistroTS>();
    private ArrayList<String> lPR = new ArrayList<String>();
    private ArrayList<String> lErrores = new ArrayList<String>();
    private ArrayList<String> lTokens = new ArrayList<String>();
    private Token tokenActual;
    private boolean resetear;
    private static Stack<String> nombres;

	private Fuente f;
	
    private int[][] mTE = {//Matriz de transición de estados.
    		//0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29
    		{ 1, 1, 0, 0, 0, 2, 1, 1,10, 9, 9, 9, 9, 9, 9, 9, 9,15,11,14,13,13,16, 0, 0, 0, 0, 0,23, 0},//
    		{ 1, 1, 1,-1,-1, 1, 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 1, 1,-1,-1,-1},//
    		{ 0, 0, 0, 0, 0, 0, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{ 0, 0, 8, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{ 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{ 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{-1,-1, 6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{ 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{-1,-1,	8,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{ 0, 0,	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,15,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{ 0, 0,	0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},//
    		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},//
    		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,17,-1,-1,-1,-1,-1,-1},//
    		{18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,20,18,18,18,18,18},//
    		{18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,19,18,18,18,18,18,18},//
    		{18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18, 0,18,18,18,18,18,18,18},//
    		{18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,21,18,18,18,18},//
    		{18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,22,18,18,18},//
    		{22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,22,-1,22,22},//
    		{24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24, 0, 0,24},//
    		{24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24, 0,-1,24}};//
    
    private AccionSemantica as1=new AS1();
    private AccionSemantica as2=new AS2();
    private AccionSemantica as3=new AS3();
    private AccionSemantica as4=new AS4();
    private AccionSemantica as5=new AS5();
    private AccionSemantica as6=new AS6();
    private AccionSemantica as7=new AS7();
    private AccionSemantica as8=new AS8();
    private AccionSemantica asc=new ASC();
    private AccionSemantica eci=new ECI();
    private AccionSemantica eeui=new EEUI();
    private AccionSemantica eend=new EEND();
    private AccionSemantica eei=new EEI();
    private AccionSemantica eed=new EED();
    private AccionSemantica eeig=new EEIG();
    private AccionSemantica ecl=new ECL();
    
    private AccionSemantica[][] mAS = {//matriz de acciones semanticas
    		{as1,as1,eci,asc,asc,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,eci,eci,eci,eci,asc,asc,eci},
    		{as2,as2,as2,as3,as3,as2,as2,as2,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as3,as2,as2,as3,as3,as3},
    		{eeui,eeui,eeui,eeui,eeui,eeui,as2,as2,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui,eeui},
    		{eend,eend,as2,eend,eend,eend,eend,eend,as2,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend,eend},
    		{eei,eei,eei,eei,eei,eei,as2,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei,eei},
    		{eed,eed,as2,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed},
    		{as5,as5,as2,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5,as5},
    		{eed,eed,as2,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed,eed},
    		{as4,as4,as2,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4,as4},
    		{as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
    		{as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as2,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
    		{eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,as2,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig},
    		{as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
    		{as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as2,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
    		{eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,as2,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig,eeig},
    		{as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6},
    		{as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as6,as2,as6,as6,as6,as6,as6,as6},
    		{asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,asc,asc,asc,asc,asc},
    		{asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc},
    		{asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc},
    		{asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,asc,asc,asc,asc},
    		{asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,asc,asc,asc},
    		{asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,asc,as2,as7,asc,asc},
    		{as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,as1,ecl,ecl,as1},
    		{as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,as2,ecl,as8,as2}};

  //Tercetos
    private static Vector<Elemento> ltercetos;
    private static Vector<Elemento> ltercetosFunc;
    
	public AnalizadorLexico(Fuente f){
		ltercetos = new Vector<Elemento>();
		ltercetosFunc = new Vector<Elemento>();
		buffer = new String();
		this.f=f;
		//INICIALIZO LISTA DE PALABRAS RESERVADAS
		lPR.add("IF"
				+ "THEN"
				+ "ELSE"
				+ "END_IF"
				+ "BEGIN"
				+ "END"
				+ "OUT"
				+ "SWITCH"
				+ "CASE"
				+ "FUNCTION"
				+ "MOVE"
				+ "RETURN"
				+ "UINT"
				+ "DOUBLE");
		resetear = false;
		nombres=new Stack<String>();
	}
	public void addTerceto(Elemento e) {
		if (enFuncion())
			ltercetosFunc.addElement(e);
		else
			ltercetos.addElement(e);
     }
		
	public Vector<Elemento> getTercetos() {
		return ltercetos;
		
	}
	
	public Vector<Elemento> getTercetosFuncImp() {
		return ltercetosFunc;
	}
	
	public Vector<Elemento> getTercetosFunc() {
		return reordenarTercetos(ltercetosFunc.iterator());
	}
	
	public Vector<Elemento> reordenarTercetos(Iterator<Elemento> i){
		Vector<Elemento> aux=new Vector<Elemento>();
		Vector<Elemento> ordenados=new Vector<Elemento>();
		while (i.hasNext()){
			Elemento e=(Elemento)i.next();
			if (e.getNombre().matches("Label\\D(.*)")){
				aux.add(e);
				aux.addAll(reordenarTercetos(i));}
			else if (e.getNombre().matches("RET")){
				ordenados.add(e);
				ordenados.addAll(aux);
				return ordenados;
			}
			else 
				ordenados.add(e);
		}
		ordenados.addAll(aux);
		return ordenados;
	}
	
	public int getNumeroTerceto(){
		if (enFuncion())
			return ltercetosFunc.size();
		return ltercetos.size();
	}
	
	//Setea el buffer
	public void setBuffer(String buffer){
		this.buffer=buffer;
	}
	
	//Devuelve el Buffer
	public String getBuffer(){
		return buffer;
	}
	
	//Devuelve la fuente
	public Fuente getFuente(){
		return f;
	}
	
	//Devuelve lista Palabras Reservadas
	public ArrayList<String> getLPR(){
		return lPR;
	}
	
	//Devuelve la lista de errores
	public ArrayList<String> getErrores(){
		return lErrores;
	}
	
	//Devuelve la lista de Tokens
	public ArrayList<String> getTokens(){
		return lTokens;
	}
	
	//Devuelve la tabla de simbolos
	public Hashtable<String,RegistroTS> getTS(){
		return TS;
	}
	
	public RegistroTS getRegistro(String s){
		return TS.get(s);
	}
	
	public void disminuirCant(String s){
		TS.get(s).disminuirCant();
	}
	
	public RegistroTS eliminarRegistro(String s){
		return(TS.remove(s));
	}
	
	public boolean chequearDeclaracion(Token t){
		//Devuelve true si ya está declarada y su tipo es null
        if (TS.get(t.getLexema()).getTipo() == null){
        	return true;
        }
        return false;
	}
	
	//Devuelve Token Palabra Reservada
	public void devolverTokenPR(String palabraReservada){
		 tokenActual = new Token (palabraReservada,null,palabraReservada);
	}
	
	public void apilarNombre(String s){
		nombres.push(s);
	}
	
	public boolean enFuncion(){
		return (nombres.size()>1);
	}
	
	public void desapilarNombre(){
		nombres.pop();
	}
	
	public static boolean existe(String s){
		return(TS.contains(s));
	}
	
	private String crearNombre(String lexema){
		String aux="";
		for (String s: nombres){
			aux=aux+"@"+s;
		}
		return (lexema+aux);
	}
	
	//Agrega en la tabla de simbolos
    public void agregarEnTS(String lexema, String id, String tipo){
    	String aux=crearNombre(lexema);
    	if (id.equals("retorno"))
    		aux=aux.substring(0,aux.lastIndexOf("@"));
    	if (TS.containsKey(aux))
    		TS.get(aux).aumentarCant();
    		else{
    		RegistroTS nuevoRegistro = new RegistroTS(tipo);
    		nuevoRegistro.setUso(id);
    		
    		//System.out.println("Entro else. Mete: "+ aux);
    		TS.put(aux,nuevoRegistro);
       	}
    	tokenActual = new Token(id,TS.get(aux),aux);
    }
    
    public void redefinirTabla(Token t, String s){
    	TS.get(t.getLexema()).setTipo(s);
    }
    
    public void setTokenActual(String id, RegistroTS puntero ){
    	tokenActual = new Token(id,puntero,id);
    }
    
    //Agrega un error a la lista de Errores
    public void agregarError(String error){
 	   lErrores.add(error);
    }
    
    //Resetea boolean
    public void setResetear(boolean b){
    	resetear = b;
    }
    
    //Devuelve token
    public Token obtenerToken(){
    	tokenActual = null;
        int estado = 0; //Estado inicial.
        while ((estado != -1) && (f.noEsFinal())){ 
            char c = f.getCaracter();
            //System.out.println("El caracter que vino es " + c);
            AccionSemantica aS = mAS[estado][f.getNroSimbolo(c)]; //Acción semantica a realizar [Estado][Simbolo]
            if (aS.ejecutar(c, this) == 0){ //0 consume.
            	f.incrPosicion();
            }
            if (!resetear){
  	            estado = mTE [estado][f.getNroSimbolo(c)]; //Dame el nuevo estado
	            //System.out.println("El nuevo Estado es " + estado);
	            if (estado == -1){
	            	lTokens.add(String.format("%-20s%s",tokenActual.getId(),tokenActual.getLexema()));
	                return tokenActual;
	            }
            }
            else { //resetea buffer despues de haber reconocido un error
            	estado = 0;
            	buffer = "";
            	resetear = false;
            }
        }
        return null;    
    }
    
    //Disminuye la cantidad de apariciones en la tabla, si es 0 se elimina
    public void disminuirRegistro(String lexema){
    	RegistroTS r = TS.get(lexema);
    	r.disminuirCant();
    	if (r.getCant() == 0){
    		TS.remove(lexema);
    	}
    }
    /*
    public void setNegativo(String lexema){
    	RegistroTS r = TS.get(lexema);
    	String tipo = r.tipo;
    	String lexemaNeg = "-" + lexema;
    	//Comprobar si ya se encuentra ese lexema, agregar si no está.
    	if (TS.containsKey(lexemaNeg))
    		TS.get(lexemaNeg).aumentarCant();
    	else {
    		RegistroTS nuevoRegistro = new RegistroTS(tipo);
    		TS.put(lexemaNeg,nuevoRegistro);
       	}
    	
    	//Se disminuye la cantidad del lexema original.
    	disminuirRegistro(lexema);
    }*/
}
