package comum;

import java.io.Serializable;
import java.util.LinkedList;

public class Protocolo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ComandoOperacao comando ;
	
	private LinkedList<Object> filaParametros ;
	
	public Protocolo( ComandoOperacao comando ){
		
		this.comando = comando ;
		
		filaParametros = new LinkedList<Object>() ;
		
	}
	
	public Object getParametro(){
		Object parametro = filaParametros.getFirst() ;
		
		filaParametros.removeFirst() ;
		
		return parametro ;
	}
	
	public void addParametro( Object parametro ){
		filaParametros.addLast( parametro ) ;
	}
	
	public ComandoOperacao getComando(){
		return comando ;
	}

}
