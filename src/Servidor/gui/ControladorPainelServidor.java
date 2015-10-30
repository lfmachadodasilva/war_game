package gui;

public class ControladorPainelServidor {
	
	/*
	 * Vari�veis de inst�ncia
	 */
	
	private PainelServidor painel ;
	
	
	/*
	 * Construtores
	 */
	
	public ControladorPainelServidor(){
		
		painel = new PainelServidor( this ) ;
		
	}
	
	
	/*
	 * M�todos p�blicos
	 */
	
	public void abrePainelServidor(){
		
		painel.setVisible(true) ;
		
	}
	
	public void escreveJogadoresConectados( String texto ){
		
		painel.escreveJogadoresConectados(texto) ;
		
	}
	
	public void escreveLogServidor( String texto ){
		
		painel.escreveLogServidor(texto) ;
		
	}
	
	
	/*
	 * Fim da classe
	 */

}
