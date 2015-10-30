package comum;

import java.util.*;
import java.io.Serializable;
import java.net.*;

public class Jogador implements Serializable{

	/*
	 * Variáveis de instância 
	 */
	
	private static final long 			serialVersionUID = -5937135886135652369L;

	private String 						apelidoJogador			;
	
	private String 						exercitoJogador			;
	
	private CartaObjetivo 				objetivoJogador 		;
	
	private LinkedList<CartaTerritorio> cartasTerritorioJogador ;
	
	private LinkedList<Territorio> 		territoriosJogador 		;
	
	private transient Socket			socketJogador			;
	
	private EstadoJogador				estadoJogador			;
	
	private boolean						conquista				;
	
	private String						ultimaConquista			;
	

	/*
	 * Construtores
	 */
	
	public Jogador( String apelidoJogador , String exercitoJogador , Socket socketJogador ){
		this.apelidoJogador 	= apelidoJogador	;
		this.exercitoJogador 	= exercitoJogador 	;
		this.socketJogador		= socketJogador		;
		
		cartasTerritorioJogador = new LinkedList<CartaTerritorio>()	;
		territoriosJogador 		= new LinkedList<Territorio>()		;
		conquista = false ;
		setUltimaConquista(null);
	}
	
	public Jogador( String apelidoJogador , String exercitoJogador ){
		this.apelidoJogador = apelidoJogador ;
		this.exercitoJogador = exercitoJogador ;
	}
	
	
	/* 
	 * Métodos públicos da classe Jogador
	 */
	
	public String getApelidoJogador(){
		return apelidoJogador ;	
	}
	
	public String getExercitoJogador(){	
		return exercitoJogador ;
	}
	
	public CartaObjetivo getObjetivoJogador(){
		return objetivoJogador ;
	}
	
	public LinkedList<CartaTerritorio> getCartasTerritorioJogador(){
		return cartasTerritorioJogador ;
	}
	
	public LinkedList<Territorio> getTerritoriosJogador(){
		return territoriosJogador ;
	}
	
	public Socket getSocketJogador(){
		return socketJogador ;
	}
	
	public EstadoJogador getEstadoJogador() {
		return estadoJogador;
	}
	
	public void setCartaObjetivo( CartaObjetivo objetivoJogador ){
		this.objetivoJogador = objetivoJogador ;
	}
	
	public void setEstadoJogador(EstadoJogador estadoJogador) {
		this.estadoJogador = estadoJogador;
	}
	
	public void addCartaTerritorio( CartaTerritorio cartaTerritorioJogador ){
		cartasTerritorioJogador.add(cartaTerritorioJogador) ;
	}
	
	public void addTerritorio( Territorio territorioJogador ){
		
		for(int i = 0 ; i < territoriosJogador.size() ; i++){
			Territorio terr = territoriosJogador.get(i);
			if( territorioJogador.getNomeTerritorio().equals( terr.getNomeTerritorio() ) ){
				territoriosJogador.remove(terr) ;
			}
		}
		territoriosJogador.add(territorioJogador) ;
		
	}
	
	public Territorio buscaTerritorio( String nomeTerritorio ){
		
		for( Territorio terr : territoriosJogador ){
			
			if( terr.getNomeTerritorio().equals( nomeTerritorio ) )
				return terr ;
		}
		return null ;
	}

	public void setConquista(boolean conquista) {
		this.conquista = conquista;
	}

	public boolean isConquista() {
		return conquista;
	}
	
	public void removeCartaTerritorio( CartaTerritorio carta ){
		cartasTerritorioJogador.remove( carta ) ;
	}

	public void setUltimaConquista(String ultimaConquista) {
		this.ultimaConquista = ultimaConquista;
	}

	public String getUltimaConquista() {
		return ultimaConquista;
	}
	
	/*
	 * Fim da classe Jogador 
	 */
	
}
