import java.util.ArrayList;

public class ListaTercetos {
    private ArrayList<Terceto> tercetos;
    private static ListaTercetos instanceOfListaDeTercetos;

    private ListaTercetos() {
        tercetos = new ArrayList<>();
    }

    public static ListaTercetos getInstanceOfListaDeTercetos() {
        if (instanceOfListaDeTercetos == null) instanceOfListaDeTercetos = new ListaTercetos();
        return instanceOfListaDeTercetos;
    }

    public int getIndice(Terceto terceto) {
        return tercetos.indexOf(terceto);
    }

    public Terceto getTerceto(int i) {
        return tercetos.get(i);
    }

    public void addTerceto(Terceto terceto) {
        tercetos.add(terceto);
    }

    public ArrayList<Terceto> getTercetos() {
        return tercetos;
    }
}
