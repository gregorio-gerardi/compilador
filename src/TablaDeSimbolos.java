import java.util.HashMap;

public class TablaDeSimbolos {
    public HashMap<String, EntradaTablaSimbolos> getTablaSimbolosHashMap() {
        return tablaSimbolosHashMap;
    }

    public void setTablaSimbolosHashMap(HashMap<String, EntradaTablaSimbolos> tablaSimbolosHashMap) {
        this.tablaSimbolosHashMap = tablaSimbolosHashMap;
    }

    public HashMap<String, EntradaTablaSimbolos> tablaSimbolosHashMap = new HashMap<String, EntradaTablaSimbolos>();
}
