package operacao_servidor;

import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Jogador;
import comum.Protocolo;

public class Operacao_BroadCastNotificaAtualizacaoListaJogadores implements IFOperacao{
	
	// -----------------------------------------------------

	@SuppressWarnings("unchecked")
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		//Operacao de broadCast que recebe a lista atualizada de jogadores cadastrado.
		
		try{
			
			Cliente.getCliente().AtualizaJogadoresCadastrados((LinkedList<Jogador>)protocolo.getParametro());
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao de broadcast que notifica a atualizacao da lista de jogadores",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	// -----------------------------------------------------
	
}
