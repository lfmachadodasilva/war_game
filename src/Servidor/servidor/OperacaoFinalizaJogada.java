package servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;

import comum.CartaTerritorio;
import comum.ComandoOperacao;
import comum.CondRet;
import comum.Jogador;
import comum.Protocolo;
import comum.Territorio;

public class OperacaoFinalizaJogada implements IFOperacao{

	private Protocolo resposta ;
	
	private int flag = 0 ;

	public void executaOperacao( Socket socket , Protocolo protocolo ){

		String apelido = ( String ) protocolo.getParametro() ;

		int numObjetivo = Servidor.getServidor().buscaJogador(apelido).getObjetivoJogador().getIdCarta() ;

		resposta = new Protocolo(ComandoOperacao.RESPOSTA_FINALIZA_JOGADA) ;

		switch( numObjetivo ){

		case 1: 
			verificaObjetivo1(apelido) ;
			break ;

		case 2:
			verificaObjetivo2(apelido) ;
			break ;

		case 3:
			verificaObjetivo3(apelido) ;
			break ;

		case 4:
			verificaObjetivo4(apelido) ;
			break ;

		case 5:
			verificaObjetivo5(apelido) ;
			break ;

		case 6:
			verificaObjetivo6(apelido) ;
			break ;

		case 7:
			verificaObjetivo7(apelido) ;
			break ;

		case 8:
			verificaObjetivo8(apelido) ;
			break ;

		case 9:
			verificaObjetivo9(apelido) ;
			break ;

		case 10:
			verificaObjetivo10(apelido) ;
			break ;

		case 11:
			verificaObjetivo11(apelido) ;
			break ;

		case 12:
			verificaObjetivo12(apelido) ;
			break ;

		case 13:
			verificaObjetivo13(apelido) ;
			break ;

		case 14:
			verificaObjetivo14(apelido) ;
			break ;

		}
		
		if( flag == 0 ){
			try{
				
				Jogador jog = Servidor.getServidor().getProximoJogadorCorrente() ;
				
				ObjectOutputStream out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
				
				Protocolo resp = new Protocolo(ComandoOperacao.INICIA_JOGADA) ;
				
				out.writeObject(resp) ;
				
			}
			catch( Exception e ){
				
				System.out.println( "Erro ao enviar INICIA_JOGADA na OperacaoFinalizaJogada" ) ;
				e.printStackTrace() ;
				
			}
			
		}
		
	}

	private void verificaObjetivo1( String apelido ){

		int contEuropa = 0 ;
		int contAS = 0 ;
		int contAN = 0 ;
		int contAsia = 0 ;
		int contOceania = 0 ;
		int contAfrica = 0 ;
		
		Jogador jogador = Servidor.getServidor().buscaJogador( apelido ) ;


		// Conta quantos territorios esse jogador possui em cada continente
		for( Territorio terr : jogador.getTerritoriosJogador() ){

			String nomeContinente = terr.getContinenteHaQuePertenceTerritorio().getNomeContinente() ;

			if( nomeContinente.equals("Europa") )
				contEuropa ++ ;

			if( nomeContinente.equals("America do Sul") )
				contAS ++ ;

			if( nomeContinente.equals("America do Norte") )
				contAN ++ ;

			if( nomeContinente.equals("Asia") )
				contAsia ++ ;

			if( nomeContinente.equals("Oceania") )
				contOceania ++ ;

			if( nomeContinente.equals("Africa") )
				contAfrica ++ ;

		}

		// Verifica se o Objetivo foi executado e monta resposta
		if( ( contEuropa == 7 ) && ( contAS == 4) ){

			if( contAN == 9){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, América do Sul e América do Norte." ) ;
				flag = 1 ;
			}

			else if( contAsia == 12){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, América do Sul e Ásia." ) ;
				flag = 1 ;
			}

			else if( contOceania == 4){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, América do Sul e Oceania." ) ;
				flag = 1 ;
			}
			else if( contAfrica == 6 ){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, América do Sul e Africa." ) ;
				flag = 1 ;
			}
		}
		else{
			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro( "Jogada finalizada." ) ;
		}


		// Envia resposta
		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1 ){	
				out.writeObject( resposta ) ;
				efetuaBroadcast( apelido ) ;
			}

			else
				out.writeObject( resposta ) ;

		}
		catch( Exception e ){

			System.out.println( "Erro ao executar verificacao objetivo 1" );
			e.printStackTrace() ;

		}

	}

	private void verificaObjetivo2( String apelido ){

		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;

		if( jogador.getTerritoriosJogador().size() >= 24 ){
			resposta.addParametro(CondRet.OK) ;
			resposta.addParametro("Parabéns, você venceu. Conquistou 24 territórios") ;
			flag = 1 ;
		}
		else{
			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro("Jogada Finalizada") ;
		}

		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
			}
			else
				out.writeObject( resposta ) ;
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 2" ) ;
			e.printStackTrace() ;
		}

	}

	private void verificaObjetivo3( String apelido ){
		
		int flagExercito = 0 ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		// verifica se ainda existe exercito amarelo.
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( jog.getExercitoJogador().equals("Amarelo") ){
				flagExercito = 1 ;
				break ;
			}
		}
		
		if( flagExercito == 1 ){
			
			// Verifico se os exercitos amarelos sao dele.
			if( jogador.getApelidoJogador().equals("Amarelo") )
				verificaObjetivo2(apelido) ;
			
			else{
				
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					
					if( jogador.getTerritoriosJogador().size() < 5 ){
						
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
						
					}
					
				}
				
				resposta.addParametro("Jogada Finalizada") ;
				
			}
			
		}
		
		else{
			
			// Verifica se o ultimo exercito destruido foi o Amarelo
			String ultimoDestruido = Servidor.getServidor().getUltimoExercitoDestruido() ;
			if( ultimoDestruido.equals("Amarelo") ){
				
				Jogador jog = Servidor.getServidor().buscaJogador(apelido) ;
				if( jog.getUltimaConquista().equals("Amarelo") )
					flag = 1 ;
				
				else{
					
					verificaObjetivo2( apelido ) ;
					return ;
					
				}
				
			}
			else{
				
				verificaObjetivo2( apelido ) ;
				return ;
				
			}
			
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
				
			}
			
			else
				
				out.writeObject( resposta ) ;
			
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 3" ) ;
			e.printStackTrace() ;
			
		}
	
	}

	private void verificaObjetivo4( String apelido ){

		int contAN = 0 ;
		int contAsia = 0 ;

		Jogador jogador = Servidor.getServidor().buscaJogador( apelido ) ;


		// Conta quantos territorios esse jogador possui em cada continente
		for( Territorio terr : jogador.getTerritoriosJogador() ){

			String nomeContinente = terr.getContinenteHaQuePertenceTerritorio().getNomeContinente() ;

			if( nomeContinente.equals("America do Norte") )
				contAN ++ ;

			if( nomeContinente.equals("Asia") )
				contAsia ++ ;

		}

		// Verifica se o Objetivo foi executado e monta resposta
		if( ( contAN == 9 ) && ( contAsia == 12) ){

			resposta.addParametro(CondRet.OK) ;
			resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a América do Norte e a Ásia." ) ;
			flag = 1 ;
		}


		else{

			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro( "Jogada finalizada." ) ;

		}


		// Envia resposta
		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1 ){	
				out.writeObject( resposta ) ;
				efetuaBroadcast( apelido ) ;
			}

			else
				out.writeObject( resposta ) ;

		}
		catch( Exception e ){

			System.out.println( "Erro ao executar verificacao objetivo 4" );
			e.printStackTrace() ;

		}

	}

	private void verificaObjetivo5( String apelido ){
		
		int contEuropa = 0 ;
		int contAS = 0 ;
		int contAN = 0 ;
		int contAsia = 0 ;
		int contOceania = 0 ;
		int contAfrica = 0 ;

		Jogador jogador = Servidor.getServidor().buscaJogador( apelido ) ;


		// Conta quantos territorios esse jogador possui em cada continente
		for( Territorio terr : jogador.getTerritoriosJogador() ){

			String nomeContinente = terr.getContinenteHaQuePertenceTerritorio().getNomeContinente() ;

			if( nomeContinente.equals("Europa") )
				contEuropa ++ ;

			if( nomeContinente.equals("America do Sul") )
				contAS ++ ;

			if( nomeContinente.equals("America do Norte") )
				contAN ++ ;

			if( nomeContinente.equals("Asia") )
				contAsia ++ ;

			if( nomeContinente.equals("Oceania") )
				contOceania ++ ;

			if( nomeContinente.equals("Africa") )
				contAfrica ++ ;

		}

		// Verifica se o Objetivo foi executado e monta resposta
		if( ( contEuropa == 7 ) && ( contOceania == 4) ){

			if( contAN == 9){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, Oceania e América do Norte." ) ;
				flag = 1 ;
			}

			else if( contAsia == 12){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, Oceania e Ásia." ) ;
				flag = 1 ;
			}

			else if( contAS == 4){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, Oceania e América do Sul." ) ;
				flag = 1 ;
			}
			else if( contAfrica == 6 ){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a Europa, Oceani e Africa." ) ;
				flag = 1 ;
			}
		}
		else{
			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro( "Jogada finalizada." ) ;
		}


		// Envia resposta
		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1 ){	
				out.writeObject( resposta ) ;
				efetuaBroadcast( apelido ) ;
			}

			else
				out.writeObject( resposta ) ;

		}
		catch( Exception e ){

			System.out.println( "Erro ao executar verificacao objetivo 1" );
			e.printStackTrace() ;

		}
	}
	
	private void verificaObjetivo6( String apelido ){
		
		int contAN = 0 ;
		int contOceania = 0 ;

		Jogador jogador = Servidor.getServidor().buscaJogador( apelido ) ;


		// Conta quantos territorios esse jogador possui em cada continente
		for( Territorio terr : jogador.getTerritoriosJogador() ){

			String nomeContinente = terr.getContinenteHaQuePertenceTerritorio().getNomeContinente() ;

			if( nomeContinente.equals("America do Norte") )
				contAN ++ ;

			if( nomeContinente.equals("Oceania") )
				contOceania ++ ;

		}

		// Verifica se o Objetivo foi executado e monta resposta
		if( ( contAN == 9 ) && ( contOceania == 4) ){

			resposta.addParametro(CondRet.OK) ;
			resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a América do Norte e a Oceania." ) ;
			flag = 1 ;
		}


		else{

			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro( "Jogada finalizada." ) ;

		}


		// Envia resposta
		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1 ){	
				out.writeObject( resposta ) ;
				efetuaBroadcast( apelido ) ;
			}

			else
				out.writeObject( resposta ) ;

		}
		catch( Exception e ){

			System.out.println( "Erro ao executar verificacao objetivo 4" );
			e.printStackTrace() ;

		}

	}
	
	private void verificaObjetivo7( String apelido ){
		
		int flagExercito = 0 ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		// verifica se ainda existe exercito amarelo.
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( jog.getExercitoJogador().equals("Verde") ){
				flagExercito = 1 ;
				break ;
			}
		}
		
		if( flagExercito == 1 ){
			
			// Verifico se os exercitos amarelos sao dele.
			if( jogador.getApelidoJogador().equals("Verde") )
				verificaObjetivo2(apelido) ;
			
			else{
				
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					
					if( jogador.getTerritoriosJogador().size() < 5 ){
						
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
						
					}
					
				}
				
				resposta.addParametro("Jogada Finalizada") ;
				
			}
			
		}
		
		else{
			
			// Verifica se o ultimo exercito destruido foi o Amarelo
			String ultimoDestruido = Servidor.getServidor().getUltimoExercitoDestruido() ;
			if( ultimoDestruido.equals("Verde") ){
				
				Jogador jog = Servidor.getServidor().buscaJogador(apelido) ;
				if( jog.getUltimaConquista().equals("Verde") )
					flag = 1 ;
				
				else{
					
					verificaObjetivo2( apelido ) ;
					return ;
					
				}
				
			}
			else{
				
				verificaObjetivo2( apelido ) ;
				return ;
				
			}
			
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
				
			}
			
			else
				
				out.writeObject( resposta ) ;
			
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 3" ) ;
			e.printStackTrace() ;
			
		}
		
	}

	private void verificaObjetivo8( String apelido ){
		
		int flagExercito = 0 ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		// verifica se ainda existe exercito amarelo.
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( jog.getExercitoJogador().equals("Azul") ){
				flagExercito = 1 ;
				break ;
			}
		}
		
		if( flagExercito == 1 ){
			
			// Verifico se os exercitos amarelos sao dele.
			if( jogador.getApelidoJogador().equals("Azul") )
				verificaObjetivo2(apelido) ;
			
			else{
				
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					
					if( jogador.getTerritoriosJogador().size() < 5 ){
						
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
						
					}
					
				}
				
				resposta.addParametro("Jogada Finalizada") ;
				
			}
			
		}
		
		else{
			
			// Verifica se o ultimo exercito destruido foi o Amarelo
			String ultimoDestruido = Servidor.getServidor().getUltimoExercitoDestruido() ;
			if( ultimoDestruido.equals("Azul") ){
				
				Jogador jog = Servidor.getServidor().buscaJogador(apelido) ;
				if( jog.getUltimaConquista().equals("Azul") )
					flag = 1 ;
				
				else{
					
					verificaObjetivo2( apelido ) ;
					return ;
					
				}
				
			}
			else{
				
				verificaObjetivo2( apelido ) ;
				return ;
				
			}
			
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
				
			}
			
			else
				
				out.writeObject( resposta ) ;
			
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 3" ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	private void verificaObjetivo9( String apelido ){
		
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		LinkedList<Territorio> listaTerritorios = jogador.getTerritoriosJogador() ;
		
		int cont = 0 ;
		
		if( listaTerritorios.size() >= 18 ){
			
			for( Territorio terr : listaTerritorios ){
				
				int numExercitos = terr.getNumExercito() ;
				if( numExercitos >= 2 )
					cont++ ;
			}
			
			if( cont >= 18 ){
				resposta.addParametro(CondRet.OK) ;
				resposta.addParametro("Parabéns, você venceu. Conquistou 18 ou mais territórios com pelo menos 2 exércitos cada." ) ;
				flag = 1;
			}
			else{
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					if( jogador.getCartasTerritorioJogador().size() < 5 ){
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
					}
				}
				resposta.addParametro("Jogada Finalizada.") ;
			}
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;
			
			if( flag == 1){
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
			}
			else
				out.writeObject( resposta ) ;
		}
		catch( Exception e ){
			
			System.out.println( "Erro ao verifica objetivo 9" ) ;
			e.printStackTrace() ;
			
		}
		
	}

	private void verificaObjetivo10( String apelido ){
		
		int flagExercito = 0 ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		// verifica se ainda existe exercito amarelo.
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( jog.getExercitoJogador().equals("Vermelho") ){
				flagExercito = 1 ;
				break ;
			}
		}
		
		if( flagExercito == 1 ){
			
			// Verifico se os exercitos amarelos sao dele.
			if( jogador.getApelidoJogador().equals("Vermelho") )
				verificaObjetivo2(apelido) ;
			
			else{
				
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					
					if( jogador.getTerritoriosJogador().size() < 5 ){
						
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
						
					}
					
				}
				
				resposta.addParametro("Jogada Finalizada") ;
				
			}
			
		}
		
		else{
			
			// Verifica se o ultimo exercito destruido foi o Amarelo
			String ultimoDestruido = Servidor.getServidor().getUltimoExercitoDestruido() ;
			if( ultimoDestruido.equals("Vermelho") ){
				
				Jogador jog = Servidor.getServidor().buscaJogador(apelido) ;
				if( jog.getUltimaConquista().equals("Vermelho") )
					flag = 1 ;
				
				else{
					
					verificaObjetivo2( apelido ) ;
					return ;
					
				}
				
			}
			else{
				
				verificaObjetivo2( apelido ) ;
				return ;
				
			}
			
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
				
			}
			
			else
				
				out.writeObject( resposta ) ;
			
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 3" ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	private void verificaObjetivo11( String apelido ){
		
		int flagExercito = 0 ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		// verifica se ainda existe exercito amarelo.
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( jog.getExercitoJogador().equals("Branco") ){
				flagExercito = 1 ;
				break ;
			}
		}
		
		if( flagExercito == 1 ){
			
			// Verifico se os exercitos amarelos sao dele.
			if( jogador.getApelidoJogador().equals("Branco") )
				verificaObjetivo2(apelido) ;
			
			else{
				
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					
					if( jogador.getTerritoriosJogador().size() < 5 ){
						
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
						
					}
					
				}
				
				resposta.addParametro("Jogada Finalizada") ;
				
			}
			
		}
		
		else{
			
			// Verifica se o ultimo exercito destruido foi o Amarelo
			String ultimoDestruido = Servidor.getServidor().getUltimoExercitoDestruido() ;
			if( ultimoDestruido.equals("Branco") ){
				
				Jogador jog = Servidor.getServidor().buscaJogador(apelido) ;
				if( jog.getUltimaConquista().equals("Branco") )
					flag = 1 ;
				
				else{
					
					verificaObjetivo2( apelido ) ;
					return ;
					
				}
				
			}
			else{
				
				verificaObjetivo2( apelido ) ;
				return ;
				
			}
			
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
				
			}
			
			else
				
				out.writeObject( resposta ) ;
			
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 3" ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	private void verificaObjetivo12( String apelido ){
		
		int flagExercito = 0 ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		// verifica se ainda existe exercito amarelo.
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( jog.getExercitoJogador().equals("Preto") ){
				flagExercito = 1 ;
				break ;
			}
		}
		
		if( flagExercito == 1 ){
			
			// Verifico se os exercitos amarelos sao dele.
			if( jogador.getApelidoJogador().equals("Preto") )
				verificaObjetivo2(apelido) ;
			
			else{
				
				resposta.addParametro(CondRet.NOK) ;
				if( jogador.isConquista() ){
					
					if( jogador.getTerritoriosJogador().size() < 5 ){
						
						CartaTerritorio c = sorteiaCartaTerritorio() ;
						resposta.addParametro(c) ;
						jogador.setConquista(false) ;
						jogador.addCartaTerritorio(c) ;
						
					}
					
				}
				
				resposta.addParametro("Jogada Finalizada") ;
				
			}
			
		}
		
		else{
			
			// Verifica se o ultimo exercito destruido foi o Amarelo
			String ultimoDestruido = Servidor.getServidor().getUltimoExercitoDestruido() ;
			if( ultimoDestruido.equals("Preto") ){
				
				Jogador jog = Servidor.getServidor().buscaJogador(apelido) ;
				if( jog.getUltimaConquista().equals("Preto") )
					flag = 1 ;
				
				else{
					
					verificaObjetivo2( apelido ) ;
					return ;
					
				}
				
			}
			else{
				
				verificaObjetivo2( apelido ) ;
				return ;
				
			}
			
		}
		
		try{
			
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1){
				
				out.writeObject( resposta ) ;
				efetuaBroadcast(apelido) ;
				
			}
			
			else
				
				out.writeObject( resposta ) ;
			
		}
		catch(Exception e){

			System.out.println( "Erro ao executar verificacao do objetivo 3" ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	private void verificaObjetivo13( String apelido ){
		
		int contAS = 0 ;
		int contAsia = 0 ;

		Jogador jogador = Servidor.getServidor().buscaJogador( apelido ) ;


		// Conta quantos territorios esse jogador possui em cada continente
		for( Territorio terr : jogador.getTerritoriosJogador() ){

			String nomeContinente = terr.getContinenteHaQuePertenceTerritorio().getNomeContinente() ;

			if( nomeContinente.equals("America do Sul") )
				contAS ++ ;

			if( nomeContinente.equals("Asia") )
				contAsia ++ ;

		}

		// Verifica se o Objetivo foi executado e monta resposta
		if( ( contAS == 4 ) && ( contAsia == 12) ){

			resposta.addParametro(CondRet.OK) ;
			resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a América do Sul e a Ásia." ) ;
			flag = 1 ;
		}


		else{

			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro( "Jogada finalizada." ) ;

		}


		// Envia resposta
		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1 ){	
				out.writeObject( resposta ) ;
				efetuaBroadcast( apelido ) ;
			}

			else
				out.writeObject( resposta ) ;

		}
		catch( Exception e ){

			System.out.println( "Erro ao executar verificacao objetivo 4" );
			e.printStackTrace() ;

		}

	}
	
	private void verificaObjetivo14( String apelido ){
		
		int contAfrica = 0 ;
		int contAsia = 0 ;

		Jogador jogador = Servidor.getServidor().buscaJogador( apelido ) ;


		// Conta quantos territorios esse jogador possui em cada continente
		for( Territorio terr : jogador.getTerritoriosJogador() ){

			String nomeContinente = terr.getContinenteHaQuePertenceTerritorio().getNomeContinente() ;

			if( nomeContinente.equals("Africa") )
				contAfrica ++ ;

			if( nomeContinente.equals("Asia") )
				contAsia ++ ;

		}

		// Verifica se o Objetivo foi executado e monta resposta
		if( ( contAfrica == 6 ) && ( contAsia == 12) ){

			resposta.addParametro(CondRet.OK) ;
			resposta.addParametro("Parabéns, você venceu o jogo. Conquistou a África e a Ásia." ) ;
			flag = 1 ;
		}


		else{

			resposta.addParametro(CondRet.NOK) ;
			if( jogador.isConquista() ){
				if( jogador.getTerritoriosJogador().size() < 5 ){
					CartaTerritorio c = sorteiaCartaTerritorio() ;
					resposta.addParametro(c) ;
					jogador.setConquista(false) ;
					jogador.addCartaTerritorio(c) ;
				}
			}
			resposta.addParametro( "Jogada finalizada." ) ;

		}


		// Envia resposta
		try{
			ObjectOutputStream out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;

			if( flag == 1 ){	
				out.writeObject( resposta ) ;
				efetuaBroadcast( apelido ) ;
			}

			else
				out.writeObject( resposta ) ;

		}
		catch( Exception e ){

			System.out.println( "Erro ao executar verificacao objetivo 4" );
			e.printStackTrace() ;

		}

	}

	private void efetuaBroadcast( String apelido ) throws Exception{
		
		for( Jogador jog : Servidor.getServidor().getListaJogadores() ){
			
			if( !jog.getApelidoJogador().equals(apelido) ){
				
				ObjectOutputStream out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
				
				Protocolo broadcast = new Protocolo(ComandoOperacao.BROADCAST_NOTIFICA_VENCEDOR) ;
				broadcast.addParametro("Jogador " + apelido + " venceu a partida.") ;
				
				out.writeObject( broadcast ) ;
				
			}
		}
	}

	private CartaTerritorio sorteiaCartaTerritorio(){
		
		LinkedList<CartaTerritorio> listaNaoUsadas = Servidor.getServidor().getListaCartaTerritorioNaoUsada() ;
		LinkedList<CartaTerritorio> listaUsadas = Servidor.getServidor().getListaCartaTerritorioUsada() ;
		
		Random rand = new Random() ;
		
		int id = rand.nextInt(listaNaoUsadas.size()) ;
		
		CartaTerritorio carta = listaNaoUsadas.get(id) ;
		
		listaNaoUsadas.remove(id) ;
		
		listaUsadas.add(carta) ;
		
		return carta ;
		
	}
}
