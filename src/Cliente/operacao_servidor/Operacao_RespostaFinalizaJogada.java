package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.CondRet;
import comum.Protocolo;

public class Operacao_RespostaFinalizaJogada implements IFOperacao{
	
	// ----------------------------------
	
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		try{
			
			CondRet cond = (CondRet)protocolo.getParametro();
			
			if(cond == CondRet.OK){
				
				JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Jogo(),
						(String)protocolo.getParametro());
				
				Cliente.getCliente().AbortarPrograma();
			}
			else
				Cliente.getCliente().AtualizaLog_Tela_Jogo((String)protocolo.getParametro());
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Jogo(), 
					"Erro na opercao de resposta de finalizacao de jogada.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// ----------------------------------

}
