package compilador;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AS5 extends AccionSemantica {
	
	//Define entero y verifica rango _ui que va entre 0 y 2^16-1
	public int ejecutar(char c,AnalizadorLexico a){

		// Le sacamos _ui
		boolean error = false;
		try {
			String constante = a.getBuffer().substring(3,a.getBuffer().length());
			int entero = Integer.parseInt(constante);
			constante="constUI_"+constante;
			if (( 0 <= entero) && (entero <=  65535)){
				a.agregarEnTS(constante,"constante", "uinteger");
            }
			else
				error = true;
		}
		catch (Exception ex) {
			error = true;
            Logger.getLogger(AnalizadorLexico.class.getName()).log(Level.SEVERE, null, ex);
			}
		if (error){
			a.setResetear(true);
			a.agregarError("ERROR. Constante fuera de rango. Linea: " + a.getFuente().getLineaActual());
		}
		return 1;	
	}
}
