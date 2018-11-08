import com.sun.istack.internal.Nullable;

public class Terceto implements Operando {

    private String operador;

    public void setOperando1(Operando operando1) {
        this.operando1 = operando1;
    }

    public void setOperando2(Operando operando2) {
        this.operando2 = operando2;
    }

    private Operando operando1;
    private Operando operando2;

    private String tipoResultante;

    public void setTipoResultante(String tipoResultante) {
        this.tipoResultante = tipoResultante;
    }

    public Terceto(String operador, @Nullable Operando operando1, @Nullable Operando operando2) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        tipoResultante="desconocido";
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

    public String getTipo() {
        return tipoResultante;
    }


}
