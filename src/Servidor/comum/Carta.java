package comum;

import java.io.Serializable;

/**
 * - A classe Carta representa todas as cartas pertencentes ao jogo war.
 * - A separa��o das cartas entre cartas territ�rios e cartas objetivos se da atrav�s da
 * vari�vel idCarta, que seguir� o seguinte padr�o:
 * 		n�meros entre 0 e 14 representam cartas objetivos.
 * 		n�meros entre 15 e 59 representam cartas territ�rios.
 */
public class Carta implements Serializable{

	/*
	 * Vari�veis de inst�ncia
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
	 * M�todos p�blicos da classe Carta
	 */
	
	public int getIdCarta(){
		return idCarta ;
	}
	
	/*
	 * Fim da classe Carta
	 */
	
}
