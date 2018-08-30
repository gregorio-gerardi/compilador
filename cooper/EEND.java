package compilador;

public class EEND extends AccionSemantica{
	// ERROR SE ESPERA UN NEGATIVO O UN DIGITO	
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Se espera un negativo o un digito. Linea: " + a.getFuente().getLineaActual());
		return 1;
	}
}