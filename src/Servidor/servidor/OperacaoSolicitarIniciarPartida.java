package servidor;

import java.net.*;
import java.io.*;
import java.util.*;
import comum.*;

public class OperacaoSolicitarIniciarPartida implements IFOperacao{
	
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
			
			//Escreve Log
			servidor.escreveLogServidor( "Jogador " + apelido + " solicitou inicio de partida." ) ;
			
			// Executa a operacao
			Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_SOLICITAR_INICIAR_PARTIDA ) ;
			
			if( listaJogadores.size() < 3 ){
				
				resposta.addParametro( "Números de jogadores menor que o limite mínimo permitido pelas regras do WAR." ) ;
				
			}
			else{
				
				efetuaBroadcast( apelido ) ;
				
				resposta.addParametro( "Aguardando a confirmação dos outros jogadores." ) ;
				
			}
			
			// Responde ao cliente
			ObjectOutputStream out = new ObjectOutputStream( socketCliente.getOutputStream() ) ;
			
			out.writeObject( resposta ) ;
	
		}
		catch( Exception e ){
			
			servidor.escreveLogServidor("Erro OperacaoSolicitaIniciarPartida: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	
	/*
	 * Métodos privados
	 */
	
	private void efetuaBroadcast( String apelido ) throws Exception{
		
		ObjectOutputStream out ;
		
		for( Jogador jog : listaJogadores ){
			
			if( !jog.getApelidoJogador().equals( apelido ) ){
				
				out = new ObjectOutputStream( jog.getSocketJogador().getOutputStream() ) ;
				
				Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_CALLBACK_INICIO_PARTIDA ) ;
				broadcast.addParametro( "Jogador " + apelido + " deseja iniciar uma nova partida." ) ;
				
				out.writeObject( broadcast ) ;
				
			}
			
		}
		
	}
	
	
	/*
	 * Fim da classe
	 */

}
