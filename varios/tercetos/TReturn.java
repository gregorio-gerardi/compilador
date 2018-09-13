
package tercetos;

import compilador.*;

public class TReturn extends Terceto{
    
    public TReturn(Elemento e1, Elemento e2, int numT){
        super(e1, e2, "ret", numT);
        //e1 es la expresion e2 es donde lo voy a guardar
    }
    
    public String getNombre(){
    	return "RET";
    }

    @Override
    public String getAssembler() {
        String code = "";
        String ret=((Terceto)t2).getT1().getNombre();
        int indice=ret.indexOf("@");
        ret=ret.substring(0,indice)+"_ret"+ret.substring(indice, ret.length());
       	if (!t1.getClase().equals("Terceto")){
            String aux = Regs.getLibre();
            code += "MOV " + aux + ", " + ((Token) t1).getNombre() + "\n";
            code += "MOV " + ret + ", " + aux + "\n";
            Regs.Liberar(aux);
        }
        else 
        	{
        	code += "MOV " + ret + ", " + ((Terceto) t1).getNombre() + "\n";
        	Regs.Liberar(((Terceto) t1).getNombre());
        	}
        code += "RET\n\n";
        return code;
    }
}