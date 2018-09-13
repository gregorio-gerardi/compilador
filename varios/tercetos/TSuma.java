package tercetos;

public class TSuma extends Terceto implements Chequeable {

    public TSuma(Elemento e1, Elemento e2, int numT){
        super(e1, e2, "+", numT);
    }

    @Override
    public String getAssembler() {
        String code = "";
        if ((t1.getClase().equals("Terceto")) && (t2.getClase().equals("Terceto"))){ 
            code += "ADD " + t1.getNombre() + ", " + t2.getNombre() + "\n";
            this.registroAsignado = t1.getNombre();
            Regs.Liberar(t2.getNombre());
        }
        else if (t1.getClase().equals("Terceto")) {
            code += "ADD " + t1.getNombre() + ", " + t2.getNombre() + "\n";
            this.registroAsignado = t1.getNombre();
        }
        else if (t2.getClase().equals("Terceto")) {
            code += "ADD " + t2.getNombre() + ", " + t1.getNombre() + "\n";
            this.registroAsignado = t2.getNombre();
        }
        else {
            this.registroAsignado = Regs.getLibre();
            code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
            code += "ADD " + this.registroAsignado + ", " + t2.getNombre() + "\n";
        }
        return code;
    }
	
    @Override
    public void noChequear(){
    	//Esto se podria completar si chequearamos overflow en la suma
    	return;
    }
    
}
