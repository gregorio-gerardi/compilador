package tercetos;

public class TLabel extends Terceto {

    public TLabel(Elemento e1, int n){
    	super(e1, null, "Label", n);
        operacion="Label"+e1.getNombre();
    }
    
    @Override
    public String getAssembler() {
        return "Label"+t1.getNombre()+ ":\n";
    }
    
    public String getNombre(){
    	return "Label"+t1.getNombre();
    }
    
    @Override
    public String getTipo(){
    	return null;
    }
    
}