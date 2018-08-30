package tercetos;

public class TMultiplicacion extends Terceto implements Chequeable {
	
	private boolean chequear=true;
	private String operacion="MUL ";
    
    public TMultiplicacion(Elemento e1, Elemento e2, int numT){
        super(e1, e2, "*", numT);
    }

    @Override
    public String getAssembler() {
        String code = "";
        this.registroAsignado = Regs.getLibreAX();
        this.registroAsignado = "AX";
        if (t1.getTipo().equals("integer"))
        	operacion="IMUL ";
        if ((t1.getClase().equals("Terceto")) && (t2.getClase().equals("Terceto"))){ 
                code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
                Regs.Liberar(t1.getNombre());
                code += operacion + t2.getNombre() + "\n";
                Regs.Liberar(t2.getNombre());
        }
        else if (t1.getClase().equals("Terceto")) {
                code += "MOV " + this.registroAsignado + ", " + t2.getNombre() + "\n";
                code += operacion + t1.getNombre() + "\n";
                Regs.Liberar(t1.getNombre());
        }
        else if (t2.getClase().equals("Terceto")) {
                code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
                code += operacion + t2.getNombre() + "\n";
                Regs.Liberar(t2.getNombre());
        }
        else  {
            String auxReg = Regs.getLibre();
            code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
            code += "MOV " + auxReg + ", " + t2.getNombre() + "\n";
            code += operacion + auxReg + "\n";
            Regs.Liberar(auxReg);
        }
        if (this.registroAsignado.equals("AX")){
            this.registroAsignado = Regs.getLibre();
            code += "MOV " + this.registroAsignado + ", AX\n";
            Regs.Liberar("AX");
        }
        if (chequear)
        	code += "JO errorMult\n";
        return code;
    }
    
    public void noChequear(){
    	chequear=false;
    }
}
