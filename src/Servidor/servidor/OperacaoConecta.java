package servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;

import comum.ComandoOperacao;
import comum.CondRet;
import comum.Protocolo;

public class OperacaoConecta implements IFOperacao{
	
	private static int contConexoes = 0 ;
	
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){
		
		Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_CONEXAO ) ;
		
		if( contConexoes == 6 ){
			resposta.addParametro(CondRet.NOK) ;
			resposta.addParametro("Servidor lotado.") ;
			
			//TODO CUIDADO AQUI SO EM MAQUINAS DIFERENTES
			//Servidor.getServidor().buscaThreadEscuta(socketCliente).interrupt() ;
			//Servidor.getServidor().removeThreadEscuta(socketCliente) ;
		}
		else if( Servidor.getServidor().isPartidaIniciada() ){
			
			resposta.addParametro(CondRet.NOK) ;
			resposta.addParametro("Partida iniciada, tente se conectar mais tarde.") ;
			
		}
		else{
			
			resposta.addParametro(CondRet.OK) ;
			
			contConexoes ++ ;
		}
		
		try{
			ObjectOutputStream out = new ObjectOutputStream( socketCliente.getOutputStream() ) ;
			
			out.writeObject( resposta ) ;
			
		}
		catch( Exception e ){
			System.out.println( "Erro na OperacaoConecta" ) ;
			e.printStackTrace() ;
		}
		
	}
	
	public static void setContConexoes(){
		contConexoes -- ;
	}

}
