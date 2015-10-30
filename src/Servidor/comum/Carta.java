package comum;

import java.io.Serializable;

/**
 * - A classe Carta representa todas as cartas pertencentes ao jogo war.
 * - A separação das cartas entre cartas territórios e cartas objetivos se da através da
 * variável idCarta, que seguirá o seguinte padrão:
 * 		números entre 0 e 14 representam cartas objetivos.
 * 		números entre 15 e 59 representam cartas territórios.
 */
public class Carta implements Serializable{

	/*
	 * Variáveis de instância
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1005956527210533630L;
	
	private int idCarta ;
	
	
	/*
	 * Construtores
	 */
	
	public Carta( int idCarta ){
		this.idCarta = idCarta ;
	}
	
	
	/*
	 * Métodos públicos da classe Carta
	 */
	
	public int getIdCarta(){
		return idCarta ;
	}
	
	/*
	 * Fim da classe Carta
	 */
	
}
