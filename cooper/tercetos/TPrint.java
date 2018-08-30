package tercetos;

public class TPrint extends Terceto {
    
    public TPrint(Elemento e1, int numT){
        super(e1, null, "print", numT);
    }
    

    @Override
    public String getAssembler() {
        String out = "";
        out += "invoke MessageBox, NULL, addr " + t1.getNombre().replaceAll(" ", "") + ", addr " + t1.getNombre().replaceAll(" ", "") + ", MB_OK"  + "\n";
        return out;
    }
    
    @Override
    public String getTipo(){
    	return "cadena";
    }
    
}
