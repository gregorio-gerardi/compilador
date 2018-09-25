import java.util.HashMap;

public class TablaDeSimbolos {
    public HashMap<String, EntradasTablaSimbolos> getTablaSimbolosHashMap() {
        return tablaSimbolosHashMap;
    }

    public void setTablaSimbolosHashMap(HashMap<String, EntradasTablaSimbolos> tablaSimbolosHashMap) {
        this.tablaSimbolosHashMap = tablaSimbolosHashMap;
    }

    public HashMap<String,EntradasTablaSimbolos> tablaSimbolosHashMap = new HashMap<String, EntradasTablaSimbolos>();
}
