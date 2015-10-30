package servidor;

import java.net.*;
import java.io.*;
import java.util.*;
import comum.*;

public class OperacaoSolicitarIniciarPartida implements IFOperacao{
	
	/*
	 * Vari�veis de inst�ncia
	 */
	private LinkedList<Jogador> listaJogadores ;
	
	
	/*
	 * M�todos p�blicos
	 */
	
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){
		
		Servidor servidor = Servidor.getServidor() ;
		
		listaJogadores = servidor.getListaJogadores() ;
		
		try{
			
			// Pega os dados necess�rios
			String apelido = ( String ) protocolo.getParametro() ;
			
			//Escreve Log
			servidor.escreveLogServidor( "Jogador " + apelido + " solicitou inicio de partida." ) ;
			
			// Executa a operacao
			Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_SOLICITAR_INICIAR_PARTIDA ) ;
			
			if( listaJogadores.size() < 3 ){
				
				resposta.addParametro( "N�meros de jogadores menor que o limite m�nimo permitido pelas regras do WAR." ) ;
				
			}
			else{
				
				efetuaBroadcast( apelido ) ;
				
				resposta.addParametro( "Aguardando a confirma��o dos outros jogadores." ) ;
				
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
	 * M�todos privados
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
