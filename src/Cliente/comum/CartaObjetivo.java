package comum;

import java.io.Serializable;

public class CartaObjetivo extends Carta implements Serializable {
	
	/*
	 * Variáveis de instância
	 */
	
	private static final long serialVersionUID = 2225489978325059887L;
	
	private String descricaoObjetivo ;
	
	
	/*
	 * Construtores
	 */
	
	public CartaObjetivo( int idCarta , String descricaoObjetivo ){
		super(idCarta) ;
		
		this.descricaoObjetivo = descricaoObjetivo ;
	}
	
	
	/*
	 * Métodos públicos da classe CartaObjetivo
	 */
	
	public String getDescricaoObjetivo(){
		return descricaoObjetivo ;
	}
	
	/*
	 * Fim da classe CartaObjetivo
	 */

}
