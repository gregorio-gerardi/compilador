package compilador;

public class AS7 extends AccionSemantica{
	//Define Anotaciones para tiempo de ejecución
	public int ejecutar (char c,AnalizadorLexico a){
		//Guardamos /#@NC
		String constante = a.getBuffer().substring(0,5);
		a.agregarEnTS(constante, "anotacion", "nc");
		return 0;
	}

}
