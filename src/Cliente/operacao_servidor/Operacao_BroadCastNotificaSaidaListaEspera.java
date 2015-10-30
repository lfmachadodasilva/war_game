package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Protocolo;

public class Operacao_BroadCastNotificaSaidaListaEspera implements IFOperacao{

	// -----------------------------------------------------

	public void executaOperacao( Socket socketCliente, Protocolo protocolo){
		//Mostra quem deixou a lista de espera.
		String msg = null;
		try{
			
			msg = (String)protocolo.getParametro();
			
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), msg);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao de broadcast da saida de um jogador da lista de espera.",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	// -----------------------------------------------------
	
}
