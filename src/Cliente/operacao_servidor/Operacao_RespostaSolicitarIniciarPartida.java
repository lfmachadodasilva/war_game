package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Protocolo;

public class Operacao_RespostaSolicitarIniciarPartida implements IFOperacao{
	
	// ----------------------------------
	
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
	
		//NOK, quantidade menor do que 3 pessoas, ainda n pode iniciar o jogo.(String)
		//OK, pode inicar a partida.
		String msg;
		try{
			
			msg  = (String)protocolo.getParametro();
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), 
					"Erro na operacao de resposta da solicitacao de inicio de partida",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), msg);
		
	}
	
	// ----------------------------------
	
}
