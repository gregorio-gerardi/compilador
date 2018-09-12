public class ASSumaLinea implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.incLinea();
    }
}
