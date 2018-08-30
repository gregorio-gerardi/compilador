package compilador;

import java.util.Set;
import java.util.Vector;

import tercetos.Elemento;

public class GeneradorAssembler {
    @SuppressWarnings("unused")
	private String data, asm, code, libraries;
    private AnalizadorLexico a;
    public static int start = 0;
    
    public GeneradorAssembler(Parser p){
    	a=p.getAnalizador();
        data = "";
        asm = "";
        code = "";
        libraries = ".386 \n.model flat, stdcall \noption casemap :none  \n" +
                "include \\masm32\\include\\windows.inc \ninclude \\masm32\\include\\kernel32.inc \ninclude \\masm32\\include\\masm32.inc  \n" +
                "includelib \\masm32\\lib\\kernel32.lib \nincludelib \\masm32\\lib\\masm32.lib\n" +
                "include \\masm32\\include\\user32.inc \n" +
                "includelib \\masm32\\lib\\user32.lib \n";
    }
    
    private String genTabla(){
        String tabla = "\n.data\n"
                + "errorMsgMult db \"Error: Overflow en la multiplicacion.\", 0\n"
                + "errorMsgResta db \"Error: La resta dio como resultado un numero negativo.\", 0\n"
        		+ "errorMsgDivCero db \"Error: La division por cero no esta definida.\", 0\n"
				+ "errorMsgConversiones db \"Error: No se puede hacer la conversion.\", 0\n";
                
        Set<String> claves = a.getTS().keySet();
        for (String c: claves){  	
        	RegistroTS aux=a.getTS().get(c);
        	if (!aux.getUso().equals("funcion")&&!aux.getUso().equals("identificador"))
        	{
        		int indice=0;
        		String valor="";
        		if (aux.getUso().equals("constante"))
        		{
        			indice=c.indexOf("@");
        			int indice2=c.indexOf("_");
        			if (indice!=-1)
            	    {
        				valor = c.substring(indice2+1,indice);      					
            	        tabla += c + " dw " + valor.replaceFirst("n", "-") +"\n";
            	    }
            	}	        			
        		else if (aux.getTipo().equals("cadena")){
        			indice=c.indexOf("@");
        			if (indice!=-1)
            	    {
            	    valor = c.substring(0,indice);
            	    tabla += c.replaceAll(" ", "") + " db \"" + valor + "\", 0\n";
            	    }
        		}
        		else
	                tabla += c + " dw 0\n";
        	}
        }
        return tabla;
    }
    
    private String genCode(){
        code = "\n.code\n"
                + "errorMult:\n"
                + "invoke MessageBox, NULL, addr errorMsgMult, addr errorMsgMult, MB_OK"  + "\n"
                + "invoke ExitProcess, 1\n"
                + "errorDivCero:\n"
                + "invoke MessageBox, NULL, addr errorMsgDivCero, addr errorMsgDivCero, MB_OK"  + "\n"
                + "invoke ExitProcess, 1\n"
                + "errorResta:\n"
                + "invoke MessageBox, NULL, addr errorMsgResta, addr errorMsgResta, MB_OK"  + "\n"
                + "invoke ExitProcess, 1\n"
			    + "errorConversiones:\n"
			    + "invoke MessageBox, NULL, addr errorMsgConversiones, addr errorMsgConversiones, MB_OK"  + "\n"
			    + "invoke ExitProcess, 1\n";
        Vector<Elemento> ter = a.getTercetos();
        Vector<Elemento> terFunc = a.getTercetosFunc();

        for (Elemento e: terFunc)
        	code += e.getAssembler();
        
        code += "start:\n";
        for (Elemento e: ter){
            code += e.getAssembler();
        }

        code += "invoke ExitProcess, 0\nend start\n\n";
        return code;
    }
    
    public String getAsm(){
        return asm = this.libraries + this.genTabla() + this.genCode();
    }

//	public void getAssemblerSuma() {
//		 String code = "";
//	        if (t2.getClase().equals("Terceto")){
//	            code += "MOV " + t1.getNombre() + ", " + t2.getNombre() + "\n";
//	            Regs.Liberar(t2.getNombre());
//	        }
//	        else {
//	            String aux = Regs.getLibre();
//	            code += "MOV " + aux + ", " + t2.getNombre() + "\n";
//	            code += "MOV " + t1.getNombre() + ", " + aux + "\n";
//	            Regs.Liberar(aux);
//	        }
//	        return code;
//	}
	
	
}
