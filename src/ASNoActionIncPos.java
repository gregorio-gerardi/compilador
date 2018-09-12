
public class ASNoActionIncPos implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico analizadorLexico) {
        analizadorLexico.incPosition();
    }
}

