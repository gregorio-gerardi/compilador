package tercetos;

public class TComparador extends Terceto {
    
    public TComparador(Elemento e1, Elemento e2, String op, int numT){
        super(e1, e2, op, numT);
    }
    
    @Override
    public String getAssembler() {
    	String code="";
    	String reg = Regs.getLibre();
    	code+="MOV "+reg+", "+t1.getNombre() + "\n";
    	code+="CMP " + reg + ", " +t2.getNombre() + "\n";
    	Regs.Liberar(reg);
        return code;
    }
    
    public String getJump(){
        String op = this.getOperacion();
        if (op.equals(">"))
            return "JBE";
        if (op.equals("<"))
            return "JAE";
        if (op.equals(">="))
            return "JB";
        if (op.equals("<="))
            return "JA";
        if (op.equals("=="))
            return "JNE";
        if (op.equals("!="))
            return "JE";
        return "";
    } 


}