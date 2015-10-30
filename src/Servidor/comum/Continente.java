package comum;

import java.io.Serializable;
import java.util.LinkedList;

public class Continente implements Serializable{
	
	/*
	 * Variáveis de instância 
	 */

	private static final long 		serialVersionUID = 4472922266416552202L;

	private String 					nomeContinente 						;
	
	private int 					numTerritorios						;
	
	private LinkedList<Territorio>	territoriosPertencentesContinente 	;
	
	
	
	/*
	 * Construtores
	 */
	
	public Continente( String nomeContinente , int numTerritorios ){
		
		this.nomeContinente 				= nomeContinente 				;
		
		this.numTerritorios					= numTerritorios 				;
		
		territoriosPertencentesContinente 	= new LinkedList<Territorio>()	;
		
	}
	
	
	/*
	 * Métodos públicos da classe Continente
	 */
	
	public String getNomeContinente(){
		return nomeContinente ;
	}
	
	public LinkedList<Territorio> getTerritoriosPertencentesContinente(){
		return territoriosPertencentesContinente ;
	}
	
	public void addTerritorioPertencenteContinente( Territorio territorioPertencenteContinente ){
		territoriosPertencentesContinente.add(territorioPertencenteContinente) ;
	}


	public void setNumTerritorios(int numTerritorios) {
		this.numTerritorios = numTerritorios;
	}


	public int getNumTerritorios() {
		return numTerritorios;
	}
	
	
	/*
	 * Fim da classe Continente
	 */
	
}
