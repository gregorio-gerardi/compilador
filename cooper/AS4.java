package compilador;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AS4 extends AccionSemantica{
	
	//Define entero y verifica rango _i que va entre -2^15 y 2^15-1.
	public int ejecutar(char c, AnalizadorLexico a){
		
		// Le sacamos el _i
		boolean error = false;
		try {
			String constante = a.getBuffer().substring(2,a.getBuffer().length());
			int entero = Integer.parseInt(constante);
			if (entero<0)
				constante="constI_n"+constante.substring(1,constante.length());
			else
				constante="constI_"+constante;
			
			//Verificamos Rango
			if ((-32768 <= entero) && (entero <=  32767)){
				a.agregarEnTS(constante,"constante", "integer");
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
