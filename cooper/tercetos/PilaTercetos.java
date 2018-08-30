package tercetos;

import java.util.Vector;

public class PilaTercetos {
    private static Vector<Terceto> tercetos = new Vector<Terceto>();
    public void apilar(Terceto t){
        tercetos.add(t);
    }
    public Terceto desapilar(){
        return tercetos.remove(tercetos.size()-1);
    }
}