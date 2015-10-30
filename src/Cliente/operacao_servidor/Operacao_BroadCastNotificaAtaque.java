package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Protocolo;

public class Operacao_BroadCastNotificaAtaque implements IFOperacao{
	
	// ----------------------------------
	
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		//recebe um string contendo o apelido do jogador que esta atacando e do
		//jogador que está sendo atacado.
		String msg = null;
		try{
			
			msg = (String)protocolo.getParametro();
			
			Cliente.getCliente().AtualizaLog_Tela_Jogo(msg);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Jogo(), 
					"Erro na opercao de broadcast de notificar ataque.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ----------------------------------
	
}
