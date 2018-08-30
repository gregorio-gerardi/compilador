package compilador;

public class ECL extends AccionSemantica{
	// ERROR SE ESPERA UNA CADENA DE UNA UNICA LINEA
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Las cadenas no pueden ser multilinea. Linea: " + a.getFuente().getLineaActual());
		return 1;
	}
}