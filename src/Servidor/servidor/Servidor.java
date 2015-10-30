package servidor;

import comum.*;
import gui.*;

import java.net.*;
import java.util.*;


public class Servidor {
	
	/*
	 * Variáveis de instância
	 */
	
	private static Servidor 			servidor = null ;
	
	private ServerSocket				socketServidor  ; 
	
	private LinkedList<Jogador>			listaJogadores 	;
	
	private LinkedList<CartaObjetivo>   listaCartaObjetivoNaoUsada , listaCartaObjetivoUsada ;
	
	private LinkedList<CartaTerritorio>	listaCartaTerritorioNaoUsada , listaCartaTerritorioUsada ;
	
	private LinkedList<Continente>		listaContinentes ;
	
	private LinkedList<Territorio>		listaTerritorios ;
	
	private LinkedList<Thread>			listaThreadEscuta ;
	
	private Thread						threadConexao ;
	
	private int							indJogadorCorrente ;
	
	private ControladorPainelPorta		controladorPainelPorta ;
	
	private ControladorPainelServidor	controladorPainelServidor ;
	
	private Persistencia				persistencia ;
	
	private boolean						partidaIniciada ;
	
	private String						ultimoExercitoDestruido ;

	
	/*
	 * Construtores
	 */
	
	private Servidor(){
			
		// Instancia todas as estruturas de dados necessárias
		listaJogadores = new LinkedList<Jogador>() ;
		
		listaCartaObjetivoNaoUsada = new LinkedList<CartaObjetivo>();
		listaCartaObjetivoUsada = new LinkedList<CartaObjetivo>();
		
		listaCartaTerritorioNaoUsada = new LinkedList<CartaTerritorio>();
		listaCartaTerritorioUsada = new LinkedList<CartaTerritorio>();
		
		listaContinentes = new LinkedList<Continente>() ;
		
		listaTerritorios = new LinkedList<Territorio>() ;	
		
		listaThreadEscuta = new LinkedList<Thread>() ;
		
		controladorPainelPorta = new ControladorPainelPorta() ;
		
		controladorPainelServidor = new ControladorPainelServidor() ;
		
		persistencia = new Persistencia() ;
		
		setPartidaIniciada(false);
			
	}
	
	
	
	public static Servidor getServidor(){
		if ( servidor == null )
			servidor = new Servidor();
		return servidor ;
	}



	public void setSocketServidor(ServerSocket socketServidor) {
		this.socketServidor = socketServidor;
	}



	public ServerSocket getSocketServidor() {
		return socketServidor;
	}



	public void setListaJogadores(LinkedList<Jogador> listaJogadores) {
		this.listaJogadores = listaJogadores;
	}



	public LinkedList<Jogador> getListaJogadores() {
		return listaJogadores;
	}



	public void setListaContinentes(LinkedList<Continente> listaContinentes) {
		this.listaContinentes = listaContinentes;
	}



	public LinkedList<Continente> getListaContinentes() {
		return listaContinentes;
	}



	public void setListaTerritorios(LinkedList<Territorio> listaTerritorios) {
		this.listaTerritorios = listaTerritorios;
	}



	public LinkedList<Territorio> getListaTerritorios() {
		return listaTerritorios;
	}


	public Continente buscaContinente( String nomeContinente ){
		
		for( Continente continente : listaContinentes ){
			
			String nome = continente.getNomeContinente() ;
			
			if( nome.equals( nomeContinente ) )
				return continente ;
		}
		
		return null ;
	}

	public Territorio buscaTerritorio( String nomeTerritorio ){
		
		for( Territorio territorio : listaTerritorios ){
			
			String nome = territorio.getNomeTerritorio() ;
			
			if( nome.equals(nomeTerritorio) )
				return territorio ;
		}
		
		return null ;
	}



	public void setListaCartaObjetivoNaoUsada(
			LinkedList<CartaObjetivo> listaCartaObjetivoNaoUsada) {
		this.listaCartaObjetivoNaoUsada = listaCartaObjetivoNaoUsada;
	}



	public LinkedList<CartaObjetivo> getListaCartaObjetivoNaoUsada() {
		return listaCartaObjetivoNaoUsada;
	}



	public void setListaCartaObjetivoUsada(LinkedList<CartaObjetivo> listaCartaObjetivoUsada) {
		this.listaCartaObjetivoUsada = listaCartaObjetivoUsada;
	}



	public LinkedList<CartaObjetivo> getListaCartaObjetivoUsada() {
		return listaCartaObjetivoUsada;
	}



	public void setListaCartaTerritorioNaoUsada(
			LinkedList<CartaTerritorio> listaCartaTerritorioNaoUsada) {
		this.listaCartaTerritorioNaoUsada = listaCartaTerritorioNaoUsada;
	}



	public LinkedList<CartaTerritorio> getListaCartaTerritorioNaoUsada() {
		return listaCartaTerritorioNaoUsada;
	}



	public void setListaCartaTerritorioUsada(
			LinkedList<CartaTerritorio> listaCartaTerritorioUsada) {
		this.listaCartaTerritorioUsada = listaCartaTerritorioUsada;
	}



	public LinkedList<CartaTerritorio> getListaCartaTerritorioUsada() {
		return listaCartaTerritorioUsada;
	}



	public void setIndJogadorCorrente(int indJogadorCorrente) {
		this.indJogadorCorrente = indJogadorCorrente;
	}



	public int getIndJogadorCorrente() {
		return indJogadorCorrente;
	}
	
	public Jogador getProximoJogadorCorrente(){
		
		if( indJogadorCorrente == ( listaJogadores.size() - 1 ) )
			indJogadorCorrente = 0 ;
		else
			indJogadorCorrente ++ ;
		
		return listaJogadores.get( indJogadorCorrente ) ;
	}
	
	public Jogador buscaJogador( String apelido ){
		
		for( Jogador jog : listaJogadores )
			if( jog.getApelidoJogador().equals( apelido ) )
				return jog ;
		return null ;
	}
	
	public boolean iniciaServidor( int porta ){
		
		try{
			
			socketServidor = new ServerSocket( porta ) ;
			
			threadConexao = new Thread( new TarefaConexao( socketServidor ) ) ;
			
			threadConexao.start() ;
			
			return true ;
			
		}
		catch( Exception e ){
			
			System.out.println( "Erro ao iniciar o servidor." ) ;
			return false ;
			
		}
		
	}
	
	public void addThreadEscuta( Thread threadEscuta ){
		
		listaThreadEscuta.add(threadEscuta) ;
		
	}
	
	public void abrePainelPorta(){
		controladorPainelPorta.abrePainelPorta() ;
	}
	
	public void fechaPainelPorta(){
		controladorPainelPorta.fechaPainelPorta() ;
	}
	
	public void abrePainelServidor(){
		controladorPainelServidor.abrePainelServidor() ;
	}
	
	public void carregaDadosServidor(){
		
		persistencia = new Persistencia() ;
		persistencia.leContinente("persistencia/inicializaContinente.txt") ;
		persistencia.leTerritorio("persistencia/inicializaTerritorio.txt") ;
		persistencia.leFronteira("persistencia/inicializaFronteiras.txt") ;
		persistencia.leCartaObjetivo("persistencia/inicializaCartaObjetivo.txt") ;
		persistencia.leCartaTerritorio("persistencia/inicializaCartaTerritorio.txt") ;
		persistencia.leCoordenadasTerritorio("persistencia/inicializaCoordenadaTerritorio.txt");
		
	}
	
	public Thread getThreadConexao(){
		return threadConexao ;
	}
	
	public int getTamanhoListaThreadEscuta(){
		return listaThreadEscuta.size() ;
	}
	
	public Thread buscaThreadEscuta( Socket socket ){
		
		for( Thread t : listaThreadEscuta ){
			
			if( t.getName().equals(socket.getLocalAddress().toString() ) )
				return t ;
		}
		
		return null ;
	}
	
	public void removeThreadEscuta( Socket socket ){

		for( Thread t : listaThreadEscuta ){

			if( t.getName().equals(socket.getLocalAddress().toString() ) ){
				listaThreadEscuta.remove(t) ;
				break ;
			}
		}
	}
	
	public void limpaCasa(){
		
	}
	
	public void escreveJogadoresConectados( String texto ){
		
		controladorPainelServidor.escreveJogadoresConectados(texto) ;
		
	}
	
	public void escreveLogServidor( String texto ){
		
		controladorPainelServidor.escreveLogServidor(texto) ;
		
	}
	
	public CartaTerritorio buscaCartaTerritorioUsada( int id ){
		
		for( CartaTerritorio carta : listaCartaTerritorioUsada ){
			if( carta.getIdCarta() == id )
				return carta ;
		}
		return null ;
	}



	public void setPartidaIniciada(boolean partidaIniciada) {
		this.partidaIniciada = partidaIniciada;
	}



	public boolean isPartidaIniciada() {
		return partidaIniciada;
	}



	public void setUltimoExercitoDestruido(String ultimoExercitoDestruido) {
		this.ultimoExercitoDestruido = ultimoExercitoDestruido;
	}



	public String getUltimoExercitoDestruido() {
		return ultimoExercitoDestruido;
	}
	
	public Jogador buscaJogadorPorExercito( String exercito ){
		
		for( Jogador jog : listaJogadores ){
			
			if( jog.getExercitoJogador().equals(exercito) )
				return jog ;
		}
		return null ;
	}

	
	/*
	 * Fim da classe Servidor
	 */

}
