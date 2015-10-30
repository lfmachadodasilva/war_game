package operacao_servidor;

import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Jogador;
import comum.Protocolo;

public class Operacao_BroadCastNotificaResultadoAtaque implements IFOperacao{
	
	// -----------------------------------
	
	@SuppressWarnings("unchecked")
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		try{
			
			Cliente.getCliente().AtualizaLog_Tela_Jogo("Resultado do dado de ataque: "+
					(String)protocolo.getParametro());
			
			Cliente.getCliente().AtualizaLog_Tela_Jogo("Resultado do dado de defesa: "+
					(String)protocolo.getParametro());
			
			Cliente.getCliente().AtualizaLog_Tela_Jogo((String)protocolo.getParametro());
			
			Cliente.getCliente().AtualizaJogadores((LinkedList<Jogador>)protocolo.getParametro());
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao de broadcast que notifica o resultado do ataque.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// -----------------------------------
	
}
