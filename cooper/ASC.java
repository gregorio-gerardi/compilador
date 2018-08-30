package compilador;

public class ASC extends AccionSemantica{
	// Consume caracteres
	public int ejecutar(char c, AnalizadorLexico a){
		if (c == '\n')
			Fuente.lineaActual++;
		return 0;
	}
}
