package operacao_servidor;

import java.net.Socket;
import java.util.LinkedList;

import cliente.Cliente;

import comum.Jogador;
import comum.Protocolo;

public class Operacao_BroadCastConfirmacaoInicioPartida implements IFOperacao{
	
	// -----------------------------------------------------

	@SuppressWarnings("unchecked")
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		try{
			
			Cliente.getCliente().AtualizaJogadores((LinkedList<Jogador>)protocolo.getParametro());
			
		}catch(Exception e){
			
		}
		
		Cliente.getCliente().CriaTeta_Jogo();
	}

	//receber lista de jogadores(atualizada ->
	
	//jogador ele vai ter seu objetivo, lista de territorios iniciais. Ler do 
	//arquivo as coordenadas dos territorios.
	
	
	
}
