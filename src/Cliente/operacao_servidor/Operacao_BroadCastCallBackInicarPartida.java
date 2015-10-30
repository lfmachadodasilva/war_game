package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.CondRet;
import comum.Protocolo;

public class Operacao_BroadCastCallBackInicarPartida implements IFOperacao{
	
	// -----------------------------------------------------

	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		//perguntar se pode comecar o jogo(string),
		String msg = null;
		try{
			
			msg = (String)protocolo.getParametro();
			
			//Pergunta ao cliente se ele deseja inicar o jogo.
			int resp = JOptionPane.showConfirmDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					msg, "Inicar Partida", JOptionPane.YES_NO_OPTION);
			
			if(resp == JOptionPane.YES_OPTION)
				Cliente.getCliente().IniciarPartida(CondRet.OK);
			else
				Cliente.getCliente().IniciarPartida(CondRet.NOK);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), 
					"Erro na operacao de broadcast de callback de inicar aprtida.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -----------------------------------------------------
	
}
