package servidor;

import java.io.*;
import java.net.*;

import comum.*;

public class TarefaEscuta implements Runnable{
	
	/*
	 *  Variáveis de instância
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
	 * Métodos públicos
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
