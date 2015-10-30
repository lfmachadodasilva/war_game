package gui;

public class ControladorPainelServidor {
	
	/*
	 * Variáveis de instância
	 */
	
	private PainelServidor painel ;
	
	
	/*
	 * Construtores
	 */
	
	public ControladorPainelServidor(){
		
		painel = new PainelServidor( this ) ;
		
	}
	
	
	/*
	 * Métodos públicos
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
