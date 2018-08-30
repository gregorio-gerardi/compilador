package tercetos;

public class TResta extends Terceto implements Chequeable {
	
	private boolean chequear=true;
	
	public TResta(Elemento e1, Elemento e2, int numT){
        super(e1, e2, "-", numT);
    }

    @Override
    public String getAssembler() {
        String code = "";
        if ((t1.getClase().equals("Terceto")) && (t2.getClase().equals("Terceto"))){ 
            code += "SUB " + t1.getNombre() + ", " + t2.getNombre() + "\n";
            this.registroAsignado = t1.getNombre();
            Regs.Liberar(t2.getNombre());
        }
        else if (t1.getClase().equals("Terceto")) {
            this.registroAsignado = t1.getNombre();
            code += "SUB " + t1.getNombre() + ", " + t2.getNombre() + "\n";
        }
        else {
            this.registroAsignado = Regs.getLibre();
            code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
            code += "SUB " + this.registroAsignado + ", " + t2.getNombre() + "\n";
            if (t2.getClase().equals("Terceto"))
                Regs.Liberar(t2.getNombre());
        }
        if (chequear)
        	code += "JS errorResta\n";
        return code;
    }

    @Override
	public void noChequear(){
		chequear=false;
	}
}
