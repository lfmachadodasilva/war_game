package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

import comum.*;

public class OperacaoCadastrar implements IFOperacao{
	
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
			
			// Le o apelido e o exercito que o cliente deseja
			String apelido = ( String ) protocolo.getParametro() ;
			String exercito = ( String ) protocolo.getParametro() ;
			
			// Verifica o Cadastro do jogador
			int flag = verificaCadastro( apelido , exercito ) ;
			
			// Manda a resposta para o cliente
			Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_CADASTRAR ) ;
			
			if( flag == 0 ){
				
				Jogador jogador = new Jogador( apelido , exercito , socketCliente ) ;
				listaJogadores.add( jogador ) ;
				
				resposta.addParametro( CondRet.OK ) ;
				resposta.addParametro( listaJogadores ) ;
				
				// Escreve Log
				servidor.escreveJogadoresConectados("Jogador " + apelido + " com exercito " + exercito ) ;
				
				// Atualiza a lista de jogadores de todos os clientes
				efetuaBroadCast( apelido ) ;
				
				
			}
			else{
				
				resposta.addParametro( CondRet.NOK ) ;
				
				if( flag == 1 )
					resposta.addParametro( "Apelido já existe, favor escolher outro." ) ;
				else
					resposta.addParametro( "Exercito já existe, favor escolher outro." ) ;
			
			}
			
			
			// Responde ao cliente
			ObjectOutputStream	out	= new ObjectOutputStream( socketCliente.getOutputStream() ) ;
			
			out.writeObject( resposta ) ;
			
		}
		catch(Exception e){
			
			servidor.escreveLogServidor("Erro OperacaoCadastrar: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	
	/*
	 * Métodos privados
	 */
	
	private int verificaCadastro( String apelido , String exercito ){
		
		// Verifica se já existe na lista de jogadores do servidor
		int flag = 0 ;
		
		for( Jogador jog : listaJogadores ){
			
			//Verifica apelido repetido.
			if( jog.getApelidoJogador().equals(apelido) ){
				flag = 1 ;
				break ;
			}
			
			//Verifica cor do exercito repetido.
			else if( jog.getExercitoJogador().equals(exercito) ){
				flag = 2 ;
				break ;
			}
			
		}
		
		return flag ;
	}

	private void efetuaBroadCast( String apelido ) throws Exception{

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

}
	
	/*
	 * Fim da classe
	 */

