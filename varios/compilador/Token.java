package compilador;

import tercetos.Elemento;

public class Token extends Elemento{
   
	private String id;
    private String lexema;
    private RegistroTS puntero;
    public Token(String id, RegistroTS puntero, String lexema){
    	this.id = id;
    	this.puntero = puntero;
    	this.lexema = lexema;
    }
    public String getLexema(){
    	return lexema;
    }
    
    public String getLexemaInvertido(){
    	int indice=lexema.indexOf("@");
    	String cola = lexema.substring(0,indice);
    	return lexema.substring(indice)+"@"+cola;
    }
    
    public void setLexema(String lexema){
    	this.lexema = lexema;
    }
    
    public String getLexemaCorto(){
    	int indice=lexema.indexOf("@");
    	if (indice!=-1){
    		return lexema.substring(0,indice);
    	}
    	return lexema;
    }
    
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return lexema;
    }
    
    public RegistroTS getPuntero() {
        return puntero;
    }
	
	@Override
    public String getOutput(){
        return lexema;
    }
	
    public String getOutputCorto(){
        return lexema;
    }
    
    @Override
    public String getClase() {
        return "Token";
    }

    @Override
    public String getAssembler() {
        return "";
    }
    
    @Override
    public String getTipo(){
    	return this.getPuntero().getTipo();
    }
    
}
