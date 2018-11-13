import com.sun.istack.internal.Nullable;

public class Terceto implements Operando {

    private String operador;
    private Operando operando1;
    private Operando operando2;
    private String tipoResultante;
    private String auxResultado;

    public void setAuxResultado(String aux){
        this.auxResultado=aux;
    }

    public void setOperando1(Operando operando1) {
        this.operando1 = operando1;
    }

    public void setOperando2(Operando operando2) {
        this.operando2 = operando2;
    }

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
        this.operando2 = null;
        tipoResultante="desconocido";
    }

    public Terceto(String operador) {
        this.operador = operador;
        this.operando1 = null;
        this.operando2 = null;
        tipoResultante="desconocido";
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

    public Object getOperando1ForAssembler() {
        if (operando1 instanceof Terceto)
            return (((Terceto) operando1).getAuxResultado());
        return operando1;
    }

    public Object getOperando2ForAssembler() {
        if (operando2 instanceof Terceto)
            return (((Terceto) operando2).getAuxResultado());
        return operando2;
    }


    public String getTipo() {
        return tipoResultante;
    }


    public String getAuxResultado() {
        return auxResultado;
    }
}
