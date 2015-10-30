package operacao_servidor;

import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.CondRet;
import comum.Jogador;
import comum.Protocolo;


public class Operacao_RespostaCadastrar implements IFOperacao{
	
	// -----------------------------------------------------

	@SuppressWarnings("unchecked")
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		CondRet cond = null;
		try{
			cond = (CondRet)protocolo.getParametro();
			
			if(cond == CondRet.OK)
			{
				
				Cliente.getCliente().CriaTela_Espera_Jogo();
				//Receber lista de jogadores cadastrados.
				Cliente.getCliente().AtualizaJogadoresCadastrados((LinkedList<Jogador>)protocolo.getParametro());
				
			}
			else{
				JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Cadastra(),
						(String)protocolo.getParametro());
			}
		
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Cadastra(), 
					"Erro na operacao resposta cadastrar",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -----------------------------------------------------

}
