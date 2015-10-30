package servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;

import comum.CartaObjetivo;
import comum.CartaTerritorio;
import comum.ComandoOperacao;
import comum.CondRet;
import comum.Jogador;
import comum.Protocolo;
import comum.Territorio;

public class OperacaoIniciarPartida implements IFOperacao{
	
	/*
	 * Variáveis de instância
	 */

	private LinkedList<Jogador> listaJogadores ;
	
	private Servidor servidor = Servidor.getServidor() ;

	private static int contRespostasPos = 0 ;
	private static int contRespostasNeg = 0 ;


	/*
	 * Métodos públicos
	 */
	
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){

		listaJogadores = servidor.getListaJogadores() ;

		try{

			// Le os dados enviados pelo cliente
			CondRet resposta = ( CondRet ) protocolo.getParametro() ;

			// Verifica a resposta do cliente
			if( resposta.equals( CondRet.NOK ) )
				contRespostasNeg ++ ;
			
			else
				contRespostasPos ++ ;
			
			// Verifica se todos os jogadores responderam
			if( ( contRespostasNeg + contRespostasPos ) == listaJogadores.size()-1 ){
				
				if( contRespostasNeg > 0 )
					efetuaBroadcastNegativo() ;
				
				else{
					
					servidor.getThreadConexao().interrupt();
					Servidor.getServidor().setPartidaIniciada(true) ;
					iniciaJogo() ;
					
				}
				
				contRespostasNeg = 0 ;
				contRespostasPos = 0 ;
			}

		}
		catch( Exception e ){

			servidor.escreveLogServidor("Erro OperacaoIniciarPartida: " + e.getMessage() ) ;
			e.printStackTrace() ;

		}

	}

	
	/*
	 * Métodos privados
	 */
	
	private void efetuaBroadcastNegativo() throws Exception{

		ObjectOutputStream out ;

		for( Jogador jog : listaJogadores ){

			out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
			
			Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NEGACAO_INICIO_PARTIDA ) ;
			
			out.writeObject( broadcast ) ;

		}

	}

	private void efetuaBroadcastInicioJogo() throws Exception{
		
		ObjectOutputStream out ;

		for( Jogador jog : listaJogadores ){
			
			out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
			
			Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_CONFIRMACAO_INICIO_PARTIDA ) ;
			broadcast.addParametro(listaJogadores) ;
			
			out.writeObject( broadcast ) ;
			
		}
	
	}

	private void iniciaJogo() throws Exception{
		
		// Efetua os sorteios necessários
		sorteiaOrdemJogada() ;
		sorteiaObjetivos() ;
		sorteiaTerritorios() ;
		
		
		// Avisa a todos do sorteio realizado
		efetuaBroadcastInicioJogo() ;
		
		// Avisa o jogador da vez para iniciar o jogo
		Jogador jog = listaJogadores.get(Servidor.getServidor().getIndJogadorCorrente()) ;
		
		ObjectOutputStream out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
		
		Protocolo resposta = new Protocolo( ComandoOperacao.INICIA_JOGADA ) ;

		out.writeObject( resposta ) ;
		
	}

	private void sorteiaOrdemJogada(){

		Random rand = new Random() ;

		// Sorteia a ordem de jogadas de cada participante
		int indJogadorCorrente = rand.nextInt( listaJogadores.size() ) ;
		servidor.setIndJogadorCorrente( indJogadorCorrente ) ;

	}

	private void sorteiaObjetivos(){

		Random rand = new Random() ;

		LinkedList<CartaObjetivo> listaObjetivosNaoUsados = servidor.getListaCartaObjetivoNaoUsada() ;
		LinkedList<CartaObjetivo> listaObjetivosUsados = servidor.getListaCartaObjetivoUsada() ;

		// Determina o objetivo de cada participante
		for( Jogador jog : listaJogadores ){

			int indObjetivo = rand.nextInt( listaObjetivosNaoUsados.size() ) ;
			CartaObjetivo carta = listaObjetivosNaoUsados.get( indObjetivo ) ;

			jog.setCartaObjetivo( carta ) ;

			listaObjetivosUsados.add( carta ) ;

			listaObjetivosNaoUsados.remove( indObjetivo ) ;

		}

	}

	private void sorteiaTerritorios(){

		Random rand = new Random() ;

		LinkedList<CartaTerritorio> listaTerritoriosNaoUsados = servidor.getListaCartaTerritorioNaoUsada() ;
		LinkedList<CartaTerritorio> listaTerritoriosUsados = servidor.getListaCartaTerritorioUsada() ;

		// Sorteia os territórios

		// Retira as cartas curingas do conjunto de cartas territorios nao usadas
		for( int i = 0 ; i < 2 ; i ++ ){

			CartaTerritorio curinga = listaTerritoriosNaoUsados.getLast() ;
			listaTerritoriosNaoUsados.remove(curinga) ;

			listaTerritoriosUsados.add(curinga) ;
		}
		

		// Efetua o sorteio de fato
		boolean flag = false ;

		while( !flag ){

			for( Jogador jog : listaJogadores ){

				// Verifica se ainda tem carta a ser distribuida
				if( listaTerritoriosNaoUsados.size() == 0 ){
					flag = true ;
					break ;
				}

				// Sorteia o indice da carta
				int indTerritorio = rand.nextInt( listaTerritoriosNaoUsados.size() ) ;

				// Retira a carta do monte de nao usadas
				CartaTerritorio carta = listaTerritoriosNaoUsados.get( indTerritorio ) ;
				listaTerritoriosNaoUsados.remove( indTerritorio ) ;

				// Configura o territorio para o jogador sorteado
				Territorio territorio = carta.getTerritorioCartaTerritorio() ;
				territorio.setNumExercito( 1 ) ;
				territorio.setExercitoDominante( jog.getExercitoJogador() ) ;
				jog.addTerritorio( territorio ) ;

				// Coloca a carta território no monte de usadas
				listaTerritoriosUsados.add( carta ) ;

			}
		}

		// Recoloca as cartas no monte de nao usadas
		for( int i = 0 ; i < listaTerritoriosUsados.size() ; i++ ){
			CartaTerritorio carta = listaTerritoriosUsados.get( i );
			listaTerritoriosNaoUsados.add( carta );
			listaTerritoriosUsados.remove( i ) ;
		}

	}

	/*
	 * Fim da classe
	 */
}
