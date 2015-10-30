package servidor;

import java.net.*;

public class TarefaConexao implements Runnable{
	
	/*
	 * Variáveis de instância
	 */
	
	private ServerSocket socketServidor ;
	
	
	/*
	 * Construtores
	 */
	
	public TarefaConexao( ServerSocket socket ){
		
		socketServidor = socket ;
		
	}
	
	
	/*
	 * Métodos públicos
	 */
	
	public void run(){
		
		Servidor servidor = Servidor.getServidor() ;

		try{

			while(true){

				Socket cliente = socketServidor.accept() ;

				TarefaEscuta tarefaEscuta = new TarefaEscuta( cliente ) ;

				Thread t = new Thread( tarefaEscuta ) ;

				// Seta o nome da thread para buscar posteriormente na lista
				t.setName( cliente.getLocalAddress().toString() ) ;
				
				
				servidor.addThreadEscuta(t) ;

				t.start() ;

			}

		}
		catch(Exception e){

			Servidor.getServidor().escreveLogServidor("Erro TarefaConexao: " + e.getMessage() ) ;
			e.printStackTrace();

		}
		
	}

	
	/*
	 * Fim da classe
	 */

}
