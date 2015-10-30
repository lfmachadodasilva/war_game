package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.CondRet;
import comum.Protocolo;

public class Operacao_RespostaSairListaEspera implements IFOperacao{
	
	// -----------------------------------
	
	public void executaOperacao( Socket socketCliente, Protocolo protocolo){
		//Receber uma confirmacao. OK.
		try{
			
			CondRet cond = (CondRet)protocolo.getParametro();
			if(cond == CondRet.OK){
				Cliente.getCliente().CriaTela_Conecta();
				Cliente.getCliente().DesligaTela_Espera_Jogo();
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), 
					"Erro na operacao de resposta de sair da lista de espera.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -----------------------------------

}
