package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Protocolo;

public class Operacao_BroadCastNegacaoInicioPartida implements IFOperacao{
	
	// -----------------------------------------------------

	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		//Partida nao inicada. Mostrar que não quis inicar a apartida.
		try{
		
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Jogo não iniciado.\nAlgum jogador nao aceitou iniciar o jogo.");
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), 
					"Erro na operacao de negacao de inicio de partida",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	}
	
	// -----------------------------------------------------
	
}
