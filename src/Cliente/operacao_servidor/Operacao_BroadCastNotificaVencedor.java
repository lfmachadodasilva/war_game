package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Protocolo;

public class Operacao_BroadCastNotificaVencedor implements IFOperacao{

	// -----------------------------------------------------

	public void executaOperacao( Socket socketCliente, Protocolo protocolo){
		
		try{
			
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Jogo(),
					(String)protocolo.getParametro());
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao de broadcast que notifica o vencedor.",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	// -----------------------------------------------------
	
}
