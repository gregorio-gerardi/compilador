package compilador;

import java.util.ArrayList;

public class AS3 extends AccionSemantica{
	
	//Define identificador o palabra reservada y chequea rango identificador
	public int ejecutar(char c,AnalizadorLexico a){
		String pr = a.getBuffer();
		//Pregunto si se encuentra en la lista de simbolos
		if (a.getLPR().contains(a.getBuffer())){
			//Esta en la lista de palabras reservadas
			a.devolverTokenPR(pr);
			return 1;
		}
		//No esta en la lista de palabras reservadas
		else
		{
			if (pr.length()>15){
				pr = (pr.substring(0, 14)); //Si es mayor tomo de 0 a 19
				a.agregarError("Warning: identificador demasiado largo. Linea: " + a.getFuente().getLineaActual()); //Agrego Warning
			}
			a.agregarEnTS(pr,"identificador",null); //lo agrego en la tabla de simbolos fijandome si está o no.
			return 1; 
		}
	}
}