
public class ASAddBufferIncPos implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico analizadorLexico) {
        analizadorLexico.setBuffer(analizadorLexico.getBuffer()+analizadorLexico.getC());
        analizadorLexico.incPosition();
    }
}

