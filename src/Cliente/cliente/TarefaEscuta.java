package cliente;

import java.io.ObjectInputStream;
import java.net.Socket;

import operacao_servidor.IFObservador;
import operacao_servidor.Observador;

import comum.Protocolo;

public class TarefaEscuta implements Runnable{
	
	/*
	 *  Vari�veis de inst�ncia
	 */
	
	private IFObservador 		observador 		;
	
	private Socket				socketCliente 	;
	
	private ObjectInputStream 	in 				;
	
	private Protocolo		 	protocolo 		;
	
	
	
	/*
	 * Construtores
	 */
	
	public TarefaEscuta( Socket socketCliente ){
		
		this.socketCliente = socketCliente ;
		
		observador = new Observador() ;
		
	}
	
	
	/*
	 * M�todos p�blicos
	 */
	
	public void run(){
			
		try{

			while(true){
				in = new ObjectInputStream( socketCliente.getInputStream() ) ;
				protocolo = ( Protocolo ) in.readObject();
				observador.notifica( protocolo , socketCliente ) ;
			}
			
		}
		catch( Exception e ){

			System.out.println( "Erro TarefaEscuta." ) ;
			e.printStackTrace() ;

		}
	}

	
	/*
	 * Fim da Classe
	 */

}
