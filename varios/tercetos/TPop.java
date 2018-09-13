package tercetos;

import compilador.*;

public class TPop extends Terceto {
    
    public TPop(int numT){
        super(null, null, "POP", numT);
    }

    @Override
    public String getAssembler() {
        String code = "";
        code += "POP DX \n";
        code += "POP CX \n";
        code += "POP BX \n";
        code += "POP AX \n";
        Regs.pop();
        return code;
    }
    
}
