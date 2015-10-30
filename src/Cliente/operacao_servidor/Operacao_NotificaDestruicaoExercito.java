package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Protocolo;

public class Operacao_NotificaDestruicaoExercito implements IFOperacao{
	
	// ----------------------------------
	
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){

		try{
			
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Jogo(), 
			"Ultimo exercito destruido.");
			
			Cliente.getCliente().SairPartida();
			
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao notificar destruicao exercito.",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	// ----------------------------------
	
}
