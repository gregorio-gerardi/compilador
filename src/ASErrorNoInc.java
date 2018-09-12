public class ASErrorNoInc implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setTokenActual(-1);
    }
}
