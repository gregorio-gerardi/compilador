public class ASFinFloat implements AccionSemantica{
    @Override
    public void ejecutar(AnalizadorLexico al) {
        al.incPosition();
        al.setTokenActual(al.getIDforPR("SINGLE"));

        //convierto a compatible con java
        String[] separados = new String[2];
        String buffer = al.getBuffer();
        double single=0;
        if (String.valueOf(buffer).contains("F")) {
            separados = String.valueOf(buffer).split("(?<=F)");
            single = Double.valueOf(separados[0]);
            if (separados[1] != null) {
                single = single * Math.pow(10, Double.valueOf(separados[1]));
            }
        }else {
            buffer = String.valueOf(buffer)+"f";
            single = Double.valueOf(buffer);
        }
        //me fijo el rango
        if (single>Float.MAX_VALUE) {
            single = Float.MAX_VALUE;
        } else if (single<Float.MIN_VALUE){
            single = Float.MIN_VALUE;
        }

        //TODO liquidar referencia tabla simbolos

    }
}
