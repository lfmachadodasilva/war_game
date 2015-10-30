package servidor;

import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;
import comum.*;

public class OperacaoSairListaEspera implements IFOperacao{

	/*
	 * Variáveis de instância
	 */

	private LinkedList<Jogador> listaJogadores ;


	/*
	 * Métodos públicos
	 */

	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){

		Servidor servidor = Servidor.getServidor() ;

		listaJogadores = servidor.getListaJogadores() ;

		try{

			// Pega os dados necessários
			String apelido = ( String ) protocolo.getParametro() ;



			// Executa as operacoes necessárias 

			// 1) Remove a thread dele da lista
			servidor.buscaThreadEscuta(socketCliente).interrupt() ;
			servidor.removeThreadEscuta(socketCliente) ;

			// 2) Notifica a todos que o cliente está deixando a lista de espera e remove ele da lista
			int indJogador = efetuaBroadcast( apelido ) ;
			listaJogadores.remove( indJogador ) ;

			// 3) Notifica a thread de conexao que abriu uma vaga no cliente
			OperacaoConecta.setContConexoes() ;

			// 4) Atualiza a lista de cadastros
			efetuaBroadcastLista( apelido ) ;

			// Fim da execução das operacoes


			// Responde a solicitação do cliente 
			ObjectOutputStream out = new ObjectOutputStream( socketCliente.getOutputStream() ) ;

			Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_SAIR_LISTA_ESPERA ) ;
			resposta.addParametro( CondRet.OK ) ;

			out.writeObject( resposta ) ;

			// Escreve o log
			servidor.escreveLogServidor( "Jogador " + apelido + " saiu da lista de espera." ) ;

		}
		catch( Exception e ){

			servidor.escreveLogServidor("Erro OperacaoSairListaEspera: " + e.getMessage() ) ;
			e.printStackTrace() ;

		}

	}


	/*
	 * Métodos privados
	 */

	private int efetuaBroadcast( String apelido ) throws Exception{

		ObjectOutputStream out ;

		int indJogador = -1 ;

		for( int i = 0 ; i < listaJogadores.size() ; i++ ){

			Jogador jog = listaJogadores.get(i) ;

			if( jog.getApelidoJogador().equals( apelido ) )
				indJogador = i ;

			else{

				out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;

				Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NOTIFICA_SAIDA_LISTA_ESPERA ) ;
				broadcast.addParametro( "Jogador " + apelido + " saiu da lista de espera." ) ;

				out.writeObject( broadcast ) ;

			}

		}

		return indJogador ;

	}

	private void efetuaBroadcastLista( String apelido ) throws Exception{

		ObjectOutputStream out ;

		for( Jogador jog : listaJogadores ){

			if( !jog.getApelidoJogador().equals( apelido ) ){

				out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;

				Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NOTIFICA_ATUALIZACAO_LISTA_JOGADORES ) ;
				broadcast.addParametro( listaJogadores ) ;

				out.writeObject( broadcast ) ;

			}

		}

	}


	/*
	 * Fim da classe
	 */
}
