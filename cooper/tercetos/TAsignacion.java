package tercetos;

public class TAsignacion extends Terceto implements Chequeable {
    
    public TAsignacion (Elemento e1, Elemento e2, int numT){
        super(e1, e2, ":=", numT);
    }
    
    @Override
    public String getAssembler() {
        String code = "";
        if (t2.getClase().equals("Terceto")){
            code += "MOV " + t1.getNombre() + ", " + t2.getNombre() + "\n";
            Regs.Liberar(t2.getNombre());
        }
        else {
            String aux = Regs.getLibre();
            code += "MOV " + aux + ", " + t2.getNombre() + "\n";
            code += "MOV " + t1.getNombre() + ", " + aux + "\n";
            Regs.Liberar(aux);
        }
        return code;
    }
    
    @Override
    public String getTipo(){
    	return null;
    }
    
    public void noChequear(){
    	if (t2!=null)
    		if (t2.getClase().equals("Terceto"))
    			((Chequeable)t2).noChequear();
    }

}