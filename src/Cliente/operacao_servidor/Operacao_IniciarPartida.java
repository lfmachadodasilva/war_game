package operacao_servidor;

import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.Jogador;
import comum.Protocolo;

public class Operacao_IniciarPartida implements IFOperacao{
	
	@SuppressWarnings("unchecked")
	public void executaOperacao( Socket socketCliente, Protocolo protocolo){
		
		try{
			
			Cliente.getCliente().CriaTeta_Jogo();
			
			Cliente.getCliente().AtualizaJogadores((LinkedList<Jogador>)protocolo.getParametro());
			
			Cliente.getCliente().AtualizaLog_Tela_Jogo("Jogo iniciado.");
	
		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(),
					"Erro na operacao iniciar partida.",
					"Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
