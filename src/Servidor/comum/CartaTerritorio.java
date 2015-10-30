package comum;

import java.io.Serializable;

public class CartaTerritorio extends Carta implements Serializable{
	
	/*
	 * Variáveis de instância 
	 */

	private static final long serialVersionUID = -5373441295129604779L;

	private String			 	figuraGeometricaCartaTerritorio 	;
	
	private Territorio 			territorioCartaTerritorio	 		;
	
	private boolean				cartaCuringa						;
	
	
	/*
	 * Contrutores
	 */
	
	public CartaTerritorio( int idCarta , String figuraGeometricaCarta , Territorio territorioCarta ){
		super(idCarta) ;
		
		this.figuraGeometricaCartaTerritorio 	= figuraGeometricaCarta ;
		this.territorioCartaTerritorio	 		= territorioCarta 		;
		this.cartaCuringa 						= false 				;
	}
	
	public CartaTerritorio( int idCarta ){
		super(idCarta) ;
		
		this.cartaCuringa = true ;
	}
	
	
	/*
	 * Métodos públicos da classe CartaTerritorio
	 */
	
	public String getFiguraGeometricaCartaTerritorio(){
		return figuraGeometricaCartaTerritorio ;
	}
	
	public Territorio getTerritorioCartaTerritorio(){
		return territorioCartaTerritorio ;
	}
	
	public boolean isCartaCoringa(){
		return cartaCuringa ;
	}
	
	public void setCartaCuringa( boolean cond ){
		cartaCuringa = cond ;
	}
	
	/*
	 * Fim da classe CartaTerritorio
	 */

}
