package servidor;

import java.io.ObjectOutputStream;
import java.net.*;
import java.util.LinkedList;
import comum.*;

public class OperacaoSairPartida implements IFOperacao{
	
	private LinkedList<Jogador> listaJogadores ;
	
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){
		
		Servidor servidor = Servidor.getServidor() ;
		
		listaJogadores = servidor.getListaJogadores() ;
		
		try{
			
			String apelido = ( String ) protocolo.getParametro() ;
			
			efetuaBroadcast( apelido ) ;
			
			ObjectOutputStream out = new ObjectOutputStream( socketCliente.getOutputStream() ) ;
			
			Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_SAIR_PARTIDA ) ;
			resposta.addParametro( CondRet.OK ) ;
			
			out.writeObject( resposta ) ;
			
		}
		catch( Exception e ){
		
			Servidor.getServidor().escreveLogServidor("Erro OperacaoSairPartida: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}

	}
	
	private void efetuaBroadcast( String apelido ) throws Exception{
		
		ObjectOutputStream out ;
		
		for( Jogador jogador : listaJogadores ){
			
			if( !jogador.getApelidoJogador().equals( apelido ) ){
				
				out = new ObjectOutputStream( jogador.getSocketJogador().getOutputStream() ) ;
				
				Protocolo broadcast = new Protocolo( ComandoOperacao.BROADCAST_NOTIFICA_SAIDA_PARTIDA ) ;
				broadcast.addParametro( "Jogador " + apelido + " saiu da partida. O jogo irá terminar." ) ;
				
				out.writeObject( broadcast ) ;
				
			}
			
		}
		
	}

}
