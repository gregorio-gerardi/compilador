package compilador;

public class AS6 extends AccionSemantica{
	
	//Define comparadores, operadores y especiales
	public int ejecutar (char c, AnalizadorLexico a){
		a.setTokenActual(a.getBuffer(),null);
		return 1;
	}
}
