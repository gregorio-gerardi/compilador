
public class ASAddBufferIncPos implements AccionSemantica {
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setBuffer(al.getBuffer()+ al.getC());
        al.incPosition();
    }
}

