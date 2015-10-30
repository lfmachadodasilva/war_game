package gui;

import servidor.* ;

public class ControladorPainelPorta {
	
	/*
	 * Variáveis de instância
	 */
	
	private PainelPorta painel ;
	
	
	/*
	 * Construtores
	 */
	
	public ControladorPainelPorta(){

		painel = new PainelPorta( this ) ;
		
	}
	
	
	/*
	 * Métodos públicos
	 */
	
	public void abrePainelPorta(){
		
		painel.setVisible(true) ;
		
	}
	
	public void fechaPainelPorta(){
		
		painel.setVisible(false) ;
		Servidor.getServidor().abrePainelServidor() ;
		
	}
	
	public boolean trataConexao( String porta ){
		
		int p = Integer.parseInt( porta ) ;
		
		return Servidor.getServidor().iniciaServidor(p) ;

	}

	
	/*
	 * Fim da Classe
	 */
	
}
