package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.CondRet;
import comum.Protocolo;

public class Operacao_RespostaConecta implements IFOperacao{
	
	// -------------------------------------
	
	public void executaOperacao( Socket socket, Protocolo protocolo){
	
		try{

			CondRet resp = (CondRet) protocolo.getParametro();

			if(resp == CondRet.OK)
				Cliente.getCliente().CriaTela_Cadastra();
			else{

				String msg = (String) protocolo.getParametro();

				JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Conecta(), msg, 
						"Erro na operacao de conexao.", JOptionPane.ERROR_MESSAGE);

			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao iniciar partida.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -------------------------------------

}
