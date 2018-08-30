package tercetos;

public class TJump extends Terceto {
    
    private TComparador cmp;
    
    public TJump(Elemento e1, Elemento e2, int numT, TComparador c){
        super(e1, e2, c.getJump(), numT);
        cmp = c;
    }
    
    @Override
    public String getAssembler(){
        return cmp.getJump() + " " + t1.getNombre()+"\n";
    }
    
    @Override
    public String getTipo(){
    	return null;
    }
}
