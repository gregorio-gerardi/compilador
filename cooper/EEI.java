package compilador;

public class EEI extends AccionSemantica{
	// ERROR SE ESPERA UNA i DESPUES DE UN '_u'
	public int ejecutar(char c, AnalizadorLexico a){
		a.agregarError("ERROR. Se espera una 'i' despues del '_u'. Linea: " + a.getFuente().getLineaActual());
		return 1;
	}
}