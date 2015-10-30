package servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import comum.ComandoOperacao;
import comum.Jogador;
import comum.Protocolo;
import comum.Territorio;

public class OperacaoAtualizaTabuleiro implements IFOperacao{

	/*
	 * Variáveis de instância
	 */

	private LinkedList<Jogador> listaJogadores ;

	private LinkedList<Territorio> listaTerritorios ;


	/*
	 * Métodos público
	 */

	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){

		Servidor servidor = Servidor.getServidor() ;

		listaJogadores = servidor.getListaJogadores() ;
		listaTerritorios = servidor.getListaTerritorios() ;

		try{

			// Le o Jogador do cliente
			Jogador jogador = ( Jogador ) protocolo.getParametro() ;

			// Pega a lista de territorios desse jogador
			LinkedList<Territorio> listaTerr = jogador.getTerritoriosJogador() ;

			// Pega o jogador correspondente da lista do servidor
			Jogador jog = servidor.buscaJogador( jogador.getApelidoJogador() ) ;

			// Para cada territorio do jogador do cliente
			for( Territorio terr : listaTerr ){

				// Pega o territorio do servidor
				String nomeTerr = terr.getNomeTerritorio() ;
				Territorio territorio = servidor.buscaTerritorio(nomeTerr) ;

				// Altera os dados do territorio do servidor
				territorio.setExercitoDominante( terr.getExercitoDominante() ) ;
				territorio.setNumExercito( terr.getNumExercito() ) ;

				// Atualiza o jogador do servidor
				jog.addTerritorio(territorio) ;

			}
			
			// Efetua o broadcast avisando a atualização do tabuleiro para os demais clientes
			efetuaBroadcast( jogador.getApelidoJogador() ) ;

		}
		catch( Exception e ){

			servidor.escreveLogServidor("Erro OperacaoAtualizaTabuleiro: " + e.getMessage() ) ;
			e.printStackTrace() ;

		}
	}


	/*
	 * Métodos privados
	 */

	private void efetuaBroadcast( String apelido ) throws Exception{

		ObjectOutputStream out ;
		
		for(int i = 0 ; i < listaJogadores.size() ; i++){
			Jogador j = listaJogadores.get(i);
			
			out = new ObjectOutputStream( j.getSocketJogador().getOutputStream() ) ;
			
			Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NOTIFICA_ATUALIZACAO_LISTA_JOGADORES ) ;
			broadcast.addParametro( listaJogadores ) ;
			
			out.writeObject( broadcast ) ;
		}
		
	}


	/*
	 * Fim da classe
	 */

}
