package tercetos;

public class TPush extends Terceto {
    
    public TPush(int numT){
        super(null, null, "PUSH", numT);
    }

    @Override
    public String getAssembler() {
        String code = "";
        code += "PUSH AX \n";
        code += "PUSH BX \n";
        code += "PUSH CX \n";
        code += "PUSH DX \n";
        Regs.push();
        Regs.clear();
        return code;
    }
    
}
