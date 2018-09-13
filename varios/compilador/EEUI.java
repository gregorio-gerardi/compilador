package compilador;

public class EEUI extends AccionSemantica{
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Se espera una 'i' o 'u' después del '_'. Linea: " + a.getFuente().getLineaActual());
		return 1;
	}
}