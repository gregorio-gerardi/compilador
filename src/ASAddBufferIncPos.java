public class ASAddBufferIncPos implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setBuffer(al.getBuffer() + al.getChar());
        al.incPosition();
    }
}

