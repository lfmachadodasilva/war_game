package servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import comum.CartaTerritorio;
import comum.ComandoOperacao;
import comum.Jogador;
import comum.Protocolo;

public class OperacaoAtualizaCartaTerritorio implements IFOperacao{
	

	@SuppressWarnings("unchecked")
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ){
		
		String apelido = ( String ) protocolo.getParametro() ;
		LinkedList<CartaTerritorio> listaTrocadas = ( LinkedList<CartaTerritorio> ) protocolo.getParametro() ;
		
		LinkedList<CartaTerritorio> listaNaoUsada = Servidor.getServidor().getListaCartaTerritorioNaoUsada() ;
		LinkedList<CartaTerritorio> listaUsada = Servidor.getServidor().getListaCartaTerritorioUsada() ;
		Jogador jogador = Servidor.getServidor().buscaJogador(apelido) ;
		
		for( CartaTerritorio carta : listaTrocadas ){
			
			CartaTerritorio c = Servidor.getServidor().buscaCartaTerritorioUsada( carta.getIdCarta() ) ;
			
			listaUsada.remove(c) ;
			jogador.removeCartaTerritorio(c) ;
			
			listaNaoUsada.add(c) ;
			
		}
		
		Protocolo resposta = new Protocolo( ComandoOperacao.RESPOSTA_ATUALIZA_CARTA_TERRITORIO ) ;
		
		try{
			ObjectOutputStream out = new ObjectOutputStream( socketCliente.getOutputStream() ) ;
			
			out.writeObject( resposta ) ;
		}
		catch( Exception e ){
			
			System.out.println( "Erro OperacaoAtualizaCartaTerritorio" ) ;
			e.printStackTrace() ;
			
		}
	}

}
