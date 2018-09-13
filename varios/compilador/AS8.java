package compilador;

public class AS8 extends AccionSemantica{
	//Define cadena de caracteres
	public int ejecutar(char c, AnalizadorLexico a){
		a.setBuffer(a.getBuffer());
		a.agregarEnTS(a.getBuffer(),"cadena", "cadena");
		return 0;
	}
}