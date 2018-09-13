package compilador;

public class ECI extends AccionSemantica{
	// ERROR CARACTER INVALIDO
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Caracter invalido. Linea: " + a.getFuente().getLineaActual());
		return 0;
	}
}