package tercetos;

public class TConversion extends Terceto {

	public TConversion(Elemento e, int n){
	       	super(e, null, "Conversion", n);
	}
	@Override
	public String getAssembler() {
		String code="";
		String aux="";
		if(!t1.getClase().equals("Terceto")){
			aux=Regs.getLibre();
			this.registroAsignado= aux;
			code+= "MOV " + aux + ", " + t1.getNombre() + "\n";
			code+="CMP " + aux + ", 0\n";
		}
		else
			code+="CMP " + t1.getNombre() + ", 0\n";
		code+="JL errorConversiones\n";
		return code;
	}
}
