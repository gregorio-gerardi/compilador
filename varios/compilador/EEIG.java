package compilador;

public class EEIG extends AccionSemantica{
	// ERROR SE ESPERA UN IGUAL
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Se espera un '='. Linea: " + a.getFuente().getLineaActual());
		return 1;
	}
}