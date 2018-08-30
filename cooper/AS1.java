package compilador;

public class AS1 extends AccionSemantica{
	
	//INCIALIZO EL BUFFER EN VACIO 
	public int ejecutar(char c,AnalizadorLexico a){
		a.setBuffer (""+c);
		return 0;
	}
}
