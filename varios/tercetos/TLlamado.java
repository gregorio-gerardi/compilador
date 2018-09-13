package tercetos;

public class TLlamado extends Terceto {
    
	private String p;
	
    public TLlamado(Elemento e1, Elemento e2, String p, int numT){
        super(e1, e2, "funcion", numT); //e1=Nombre de la Funcion e2=Expresion p=parametro formal
        this.p=p; 
    }
    
    @Override
    public String getNombre(){
		return t1.getNombre();
    }
    
    @Override
    public String getAssembler(){
    	String code="";
    	if (p!=null)
    	{
            if (t2.getClase().equals("Terceto"))
            	code+= "MOV " + p + ", " + t2.getNombre() +  "\n";
            else
        	{
                String aux = Regs.getLibre();
                code += "MOV " + aux + ", " + t2.getNombre() + "\n";
                code += "MOV " + p + ", " + aux + "\n";
                Regs.Liberar(aux);
        	}
    	}
    	code+= "CALL " + "Label" + t1.getNombre() +  "\n";
        return code; 
    }
    
}
