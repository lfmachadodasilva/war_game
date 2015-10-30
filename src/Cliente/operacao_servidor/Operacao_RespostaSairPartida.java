package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.CondRet;
import comum.Protocolo;

public class Operacao_RespostaSairPartida implements IFOperacao{
	
	// -----------------------------------
	
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		//recebe OK
		try{
			
			CondRet cond = (CondRet)protocolo.getParametro();
			
			if(CondRet.OK == cond){
				Cliente.getCliente().DesligaTela_Jogo();
				Cliente.getCliente().CriaTela_Conecta();
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), 
					"Erro na operacao de sair da partida.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		//Sair do jogo.
	}
	
	// -----------------------------------

}
