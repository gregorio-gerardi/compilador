import com.sun.istack.internal.Nullable;

public class Terceto implements Operando {

    private String operador;
    private Operando operando1;
    private Operando operando2;

    private String tipoResultante;

    public Terceto(String operador, @Nullable Operando operando1, @Nullable Operando operando2) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
    }

    public Terceto(String operador, @Nullable Operando operando1) {
        this.operador = operador;
        this.operando1 = operando1;
    }

    public Terceto(String operador) {
        this.operador = operador;
    }

    public String getOperador() {
        return operador;
    }

    public Operando getOperando1() {
        return operando1;
    }

    public Operando getOperando2() {
        return operando2;
    }

    public String getTipoResultante() {
        return tipoResultante;
    }


}
