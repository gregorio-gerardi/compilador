package tercetos;

public class TJumpIn extends Terceto{
   
	public TJumpIn(Elemento e1, Elemento e2, int numT){
        super(e1, e2, "JMP", numT);
    }
    
    @Override
    public String getAssembler() {
        return "JMP " + t1.getNombre()+"\n";
    }
    
    @Override
    public String getTipo(){
    	return null;
    }
}
