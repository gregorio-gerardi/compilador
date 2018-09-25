public class ASIncPos implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
    }
}
