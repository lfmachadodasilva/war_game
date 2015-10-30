package operacao_servidor;

import java.net.Socket;

import javax.swing.JOptionPane;

import cliente.Cliente;

import comum.EstadoJogador;
import comum.Jogador;
import comum.Protocolo;
import comum.Territorio;

public class Operacao_IniciaJogada implements IFOperacao{
	
	// ----------------------------------
	
	public void executaOperacao(Socket socketCliente, Protocolo protocolo){
		
		try{
		
			Distribuir_Exercitos();
			
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Jogo(), 
			"Sua vez de jogar. Distribuir exercito.");
			
			Cliente.getCliente().AtualizaEstadoJogador(EstadoJogador.CORRENTE);

		}catch(Exception e){
			JOptionPane.showMessageDialog(Cliente.getCliente().getContainerTela_Espera_Jogo(), 
					"Erro na operacao de inicia jogada.",
					"Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	
	}
	
	//	----------------------------------
	
	private void Distribuir_Exercitos(){
		
		Cliente cliente = Cliente.getCliente();
		
		Jogador j = Cliente.getCliente().getJogador();
		
		int cont[] = new int[6];
		for(int i = 0 ; i < 6 ; i++)
			cont[i] = 0;
		
		//0 - America do Sul, 1 - America do Norte, 2 - Africa, 3 - Asia, 4 - Oceania, 5 - Europa
		
		for(Territorio t: j.getTerritoriosJogador()){
			
			if(t.getContinenteHaQuePertenceTerritorio().getNomeContinente().equals("America do Sul"))
				cont[0]++;
			else if(t.getContinenteHaQuePertenceTerritorio().getNomeContinente().equals("America do Norte"))
				cont[1]++;
			else if(t.getContinenteHaQuePertenceTerritorio().getNomeContinente().equals("Africa"))
				cont[2]++;
			else if(t.getContinenteHaQuePertenceTerritorio().getNomeContinente().equals("Asia"))
				cont[3]++;
			else if(t.getContinenteHaQuePertenceTerritorio().getNomeContinente().equals("Oceania"))
				cont[4]++;
			else if(t.getContinenteHaQuePertenceTerritorio().getNomeContinente().equals("Europa"))
				cont[5]++;
			
		}
		
		cliente.zeraAddExercito();
		
		if(cont[0] == 4)
			cliente.setAddExercito("America do Sul", 2);
		if(cont[1] == 9)
			cliente.setAddExercito("America do Norte", 5);
		if(cont[2] == 6)
			cliente.setAddExercito("Africa", 3);
		if(cont[3] == 12)
			cliente.setAddExercito("Asia", 7);
		if(cont[4] == 4)
			cliente.setAddExercito("Oceania", 2);
		if(cont[5] == 7)
			cliente.setAddExercito("Europa", 5);
			
		cliente.setQtdExercitos(j.getTerritoriosJogador().size() / 2);
		
		cliente.Atualiza_AbaDistribuirExercito();
	
	}

	//	----------------------------------
	
}
