package tercetos;


public class TDivision extends Terceto implements Chequeable {
    
    public TDivision(Elemento e1, Elemento e2, int numT){
        super(e1, e2, "/", numT);
    }
    private boolean chequear=true;
	private String operacion="DIV ";
    
    @Override
    public String getAssembler() {
        String code = "";
        String resultado="";
        code += "MOV DX, 0\n"; //Limpia el resto
        this.registroAsignado = Regs.getLibreAX();
        this.registroAsignado = "AX";
        if (t1.getTipo().equals("integer")){
        	operacion="IDIV ";
        	resultado += "CWD\n";//Si es signada, extiende el signo
        }
        if (chequear){
            code +="CMP " + t2.getNombre() + ", 0\n";
            code +="JE errorDivCero\n";}
        if ((t1.getClase().equals("Terceto")) && (t2.getClase().equals("Terceto"))){
                code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
                Regs.Liberar(t1.getNombre());
                code += resultado + operacion + t2.getNombre() + "\n";
                Regs.Liberar(t2.getNombre());
        }
        else if (t1.getClase().equals("Terceto")) {
                code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n" +resultado;
                Regs.Liberar(t1.getNombre());
                String auxReg = Regs.getLibre();
                code += "MOV " + auxReg + ", " + t2.getNombre() + "\n";
                code += operacion + auxReg + "\n";
                Regs.Liberar(auxReg);
        }
        else if (t2.getClase().equals("Terceto")) {
                code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
                code += resultado+operacion + t2.getNombre() + "\n";
                Regs.Liberar(t2.getNombre());
        }
        else {
            String auxReg = Regs.getLibre();
            code += "MOV " + this.registroAsignado + ", " + t1.getNombre() + "\n";
            code += resultado+"MOV " + auxReg + ", " + t2.getNombre() + "\n";
            code += operacion + auxReg + "\n";
            Regs.Liberar(auxReg);
        }
        if (this.registroAsignado.equals("AX")){
            this.registroAsignado = Regs.getLibre();
            code += "MOV " + this.registroAsignado + ", AX\n";
            Regs.Liberar("AX");
        }
        return code;
    }
    
    @Override
    public void noChequear(){
    	chequear=false;
    }
}
