public class ASFinSimboloIncPos implements AccionSemantica{

    //retorna -1 en caso de no reconocerse en la tabla de simbolos
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.setBuffer(al.getBuffer()+ al.getChar());
        al.incPosition();
        //int idToken=(al.getIDforPR()); estaba al pedo
        al.setTokenActual(al.getIDforPR(al.getBuffer()));
    }
}
