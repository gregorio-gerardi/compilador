package tercetos;

import java.util.Vector;

public class ListaTercetos {
    private Vector<Elemento> LTercetos;
    
    public ListaTercetos () {
        LTercetos = new Vector<Elemento>();
    }
    
    public void addTerceto (Elemento elem) {
        LTercetos.addElement(elem);
    }
    
    public int getSize() {
        return LTercetos.size();
    }
}