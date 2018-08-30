package compilador;

public class EED extends AccionSemantica{
	// ERROR SE ESPERA UN DIGITO
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Se espera un digito. Linea: " + a.getFuente().getLineaActual());
		return 1;
	}
}