package servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;

import comum.ComandoOperacao;
import comum.Jogador;
import comum.Protocolo;
import comum.Territorio;


public class OperacaoAtaque implements IFOperacao{

	/*
	 * Variáveis de instância
	 */
	
	private LinkedList<Jogador> listaJogadores ;

	private Jogador jogAtacante , jogAtacado ;
	
	private Territorio terrAtacante , terrAtacado ;
	
	private String dadosAtaque = "" ;
	
	private String dadosDefesa = "" ;
	
	private String resposta ;
	
	
	/*
	 * Métodos públicos
	 */
	
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){
		
		// Pega a lista de jogadores do servidor
		listaJogadores = Servidor.getServidor().getListaJogadores() ;

		try{

			// Le os dados enviados pelo cliente
			String nomeTerritorioOrigem = ( String ) protocolo.getParametro() ;
			String nomeTerritorioDestino = ( String ) protocolo.getParametro() ;
			Integer numDadosAtaque = ( Integer ) protocolo.getParametro() ;

			// Avisa ao jogador atacado que ele está sendo atacado
			avisaJogadorAtacado( nomeTerritorioOrigem , nomeTerritorioDestino , numDadosAtaque.intValue() ) ;

			// Avisa a todos, menos ao atacante e ao atacado, a execução do ataque
			efetuaBroadcastAtaque( nomeTerritorioOrigem , nomeTerritorioDestino , numDadosAtaque.intValue() ) ;

			// Avisa a todos o resultado do ataque
			efetuaBroadcastResultado( nomeTerritorioOrigem , nomeTerritorioDestino , numDadosAtaque.intValue() ) ;

		}
		catch( Exception e ){
			
			System.out.println( "Erro OperacaoAtaque." ) ;
			e.printStackTrace() ;
			
		}

	}

	private void avisaJogadorAtacado( String nomeTerritorioOrigem , String nomeTerritorioDestino , int numDados ) throws Exception{

		ObjectOutputStream out ;

		terrAtacante = Servidor.getServidor().buscaTerritorio(nomeTerritorioOrigem) ;
		terrAtacado = Servidor.getServidor().buscaTerritorio(nomeTerritorioDestino) ;
		
		jogAtacante = Servidor.getServidor().buscaJogadorPorExercito(terrAtacante.getExercitoDominante()) ;
		jogAtacado = Servidor.getServidor().buscaJogadorPorExercito(terrAtacado.getExercitoDominante()) ;

		out = new ObjectOutputStream( jogAtacado.getSocketJogador().getOutputStream() ) ;
		
		Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_DEFESA ) ;
		resposta.addParametro( "Jogador " + jogAtacante.getApelidoJogador() + " está atacando do " + nomeTerritorioOrigem + " para " + nomeTerritorioDestino + " com " + numDados + " dados." ) ;
		
		out.writeObject( resposta ) ;

	}

	private void efetuaBroadcastAtaque( String nomeTerritorioOrigem , String nomeTerritorioDestino , int numDados ) throws Exception{

		ObjectOutputStream out ;

		for( Jogador jog : listaJogadores ){

			if( ( !jog.equals(jogAtacante ) ) && ( !jog.equals(jogAtacado) ) ){

				out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
				
				Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NOTIFICA_ATAQUE ) ;
				broadcast.addParametro( "Jogador " + jogAtacante.getApelidoJogador() + " está atacando o jogador " + jogAtacado.getApelidoJogador() + " do " + nomeTerritorioOrigem + " para " + nomeTerritorioDestino + " com " + numDados + " dados." ) ;
				
				out.writeObject( broadcast ) ;

			}

		}

	}

	private void efetuaBroadcastResultado( String nomeTerritorioOrigem , String nomeTerritorioDestino , int numDados ) throws Exception{

		ObjectOutputStream out ;

		int dados ;

		if( terrAtacado.getNumExercito() >= 3 )
			dados = 3 ;

		else
			dados = terrAtacado.getNumExercito() ;

		jogaDados( numDados , dados ) ;
		
		for( Jogador jog : listaJogadores ){
			
			out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
			
			Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NOTIFICA_RESULTADO_ATAQUE ) ;
			broadcast.addParametro( dadosAtaque ) ;
			broadcast.addParametro( dadosDefesa ) ;
			broadcast.addParametro( resposta ) ;
			broadcast.addParametro( listaJogadores.clone() ) ;
			
			out.writeObject( broadcast ) ;
			
		}
	}

	private void jogaDados( int numDadosAtaque , int numDadosDefesa ){

		Integer contAtaque = 0 ;
		Integer contDefesa = 0 ;

		LinkedList<Integer> resultAtaque = new LinkedList<Integer>() ;
		LinkedList<Integer> resultDefesa = new LinkedList<Integer>() ;
		

		Random rand = new Random() ;

		for( int i = numDadosAtaque ; i > 0 ; i-- ){
			int num = rand.nextInt(7) ;
			if( num == 0 ) num ++ ;
			dadosAtaque = dadosAtaque + " " + String.valueOf(num);
			resultAtaque.add(num) ;
		}

		for( int j = numDadosDefesa ; j > 0 ; j-- ){
			int num2 = rand.nextInt(7) ;
			if( num2 == 0) num2 ++ ;
			dadosDefesa = dadosDefesa + " " + String.valueOf(num2);
			resultDefesa.add(num2) ;
		}

		while( ( resultAtaque.size() != 0) && ( resultDefesa.size() != 0 ) ){

			Integer maiorAtaque = pegaMaior( resultAtaque ) ;
			Integer maiorDefesa = pegaMaior( resultDefesa ) ;

			// Compara
			int result = maiorAtaque.compareTo(maiorDefesa) ;

			if( ( result == 0 ) || result < 0 )
				contDefesa++ ;

			else
				contAtaque++ ;
		}
		
		terrAtacado.addNumExercito( - contAtaque ) ;
		terrAtacante.addNumExercito( - contDefesa ) ;
		
		// Atacante conquistou o territorio
		if( terrAtacado.getNumExercito() <= 0 ){
			
			jogAtacado.getTerritoriosJogador().remove(terrAtacado) ;
			jogAtacante.addTerritorio(terrAtacado) ;
			
			terrAtacado.setExercitoDominante( jogAtacante.getExercitoJogador() ) ;
			terrAtacante.addNumExercito(-1) ;
			terrAtacado.addNumExercito( 1 - terrAtacado.getNumExercito() ) ;
			
			resposta = "Jogador " + jogAtacante.getApelidoJogador() + " conquistou o(a) " + terrAtacado.getNomeTerritorio() + "." ;
			
			jogAtacante.setConquista(true) ;
			jogAtacante.setUltimaConquista(terrAtacado.getExercitoDominante()) ;
			
			if( jogAtacado.getTerritoriosJogador().size() == 0 ){
				
				Servidor.getServidor().setUltimoExercitoDestruido( jogAtacado.getExercitoJogador() ) ;
				Servidor.getServidor().getListaJogadores().remove(jogAtacado) ;
				
				try{
					Protocolo prot = new Protocolo(ComandoOperacao.NOTIFICA_DESTRUICAO_EXERCITO) ;

					ObjectOutputStream out = new ObjectOutputStream( jogAtacado.getSocketJogador().getOutputStream() ) ;

					out.writeObject( prot ) ;
					
				}
				catch( Exception e ){
					Servidor.getServidor().escreveLogServidor("Erro na opecao de destrir exercito.");
				}
				
			}
			
		}
		else{
			
			resposta = "Jogador " + jogAtacante.getApelidoJogador() + " perdeu " + contDefesa + " exercitos no(a) " + terrAtacante.getNomeTerritorio() + ".\n" + "Jogador " + jogAtacado.getApelidoJogador() + " perdeu " + contAtaque + " exércitos no(a) " + terrAtacado.getNomeTerritorio() + "." ;
			jogAtacante.setConquista(false) ;
			jogAtacante.setUltimaConquista(null) ;
			
		}
			
		
	}

	private Integer pegaMaior( LinkedList<Integer> lista ){

		Integer maior = -1 ;
		for( Integer i : lista ){
			if( i > maior )
				maior = i ;
		}

		lista.remove(maior) ;
		return maior ;
	}
}
