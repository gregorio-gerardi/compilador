package tercetos;

public abstract class Terceto extends Elemento{
    protected Elemento t1, t2;
    protected String operacion;
    protected int numTerceto;
    protected String registroAsignado="";
    
    public abstract String getAssembler();
    
    public Terceto(Elemento t1, Elemento t2, String operacion, int numTerceto){
        this.t1 = t1;
        this.t2 = t2;
        this.operacion = operacion;
        this.numTerceto = numTerceto;
    }
    
    @Override
    public String getClase(){
        return "Terceto";
    }
    
    public void asignarRegistro(String registro){
    	registroAsignado=registro;
    }
        
    @Override
    public String getNombre(){
    	return registroAsignado;
    }
    
    public Elemento getT1() {
        return t1;
    }

    public Elemento getT2() {
        return t2;
    }

    public String getOperacion() {
        return operacion;
    }

    public int getNumTerceto() {
        return numTerceto;
    }

    public void setNumTerceto(int numTerceto) {
        this.numTerceto = numTerceto;
    }
    
    public void setT1(Elemento t1) {
        this.t1 = t1;
    }

    public void setT2(Elemento t2) {
        this.t2 = t2;
    }
    
    public String getOutput(){

        if (t1 == null)
            return numTerceto + ":  (" + operacion + ", null , null)";
        if (t2 == null) {
            if (t1.getClase().equals("Terceto"))
                return numTerceto + ":  (" + operacion + ", [" +((Terceto) t1).getNumTerceto() + "], null" + ")";
            else
                return numTerceto + ":  (" + operacion + ", " + t1.getOutput() + ", " + "null)";
        }
        if ((t1.getClase().equals("Terceto")) && (t2.getClase().equals("Terceto"))){ 
            return numTerceto + ":  (" + operacion + ", [" +((Terceto) t1).getNumTerceto() + "], [" + ((Terceto) t2).getNumTerceto()+ "])";
        } 
        else {
            if ((t1.getClase().equals("Terceto")) || (t2.getClase().equals("Terceto"))){ 
                if (t1.getClase().equals("Terceto"))  
                    return numTerceto + ":  (" + operacion + ", [" +((Terceto) t1).getNumTerceto() + "], " + t2.getOutput()+ ")";
                else 
                    return numTerceto + ":  (" + operacion + ", " + t1.getOutput()+", ["+ ((Terceto) t2).getNumTerceto()+ "])";
            }
            else
                return numTerceto + ":  (" + operacion + ", " + t1.getOutput()+", "+ t2.getOutput()+ ")";
        }
    }
    
    public String getTipo(){
    	return t1.getTipo();
    }
    
    public String getError(){
    	if ((t1.getTipo()!= null)&&(t2.getTipo()!=null))
	    	if (!(t1.getTipo()).equals(t2.getTipo()))
	    		return "Tipos incompatibles.";
    	return null;
    }

	public String getRegistroAsignado() {
		return registroAsignado;
	}
}
