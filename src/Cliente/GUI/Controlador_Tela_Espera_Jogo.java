package GUI;

import java.awt.Container;
import java.util.LinkedList;

import cliente.Cliente;

import comum.Jogador;

public class Controlador_Tela_Espera_Jogo {

	private String path;
	
	private Tela_Espera_Jogo frame_tela_espera_jogo;

	// -------------------------------------

	public Controlador_Tela_Espera_Jogo(String p){
		this.path = p;
	}
	
	// -------------------------------------
	
	public void Cria_Tela_Espera_Jogo(){
		frame_tela_espera_jogo = new Tela_Espera_Jogo(path, this);
		frame_tela_espera_jogo.setVisible(true);
	}
	public void Desliga_Tela_Espera_Jogo(){
		frame_tela_espera_jogo.setVisible(false);
	}
	
	// -------------------------------------
	
	public Container getContainer(){
		return frame_tela_espera_jogo.getContentPane();
	}
	
	// -------------------------------------
	
	public void AtualizaListaCadastrados(LinkedList<Jogador> lista){
		frame_tela_espera_jogo.AtualizaCadastrados(lista);
	}
	
	// -------------------------------------
	
	public void SairListaEspera(){
		Cliente.getCliente().SairEsperaJogo();
	}
	
	// -------------------------------------
	
	public void SolicitarInicioPartida(){
		Cliente.getCliente().SolicitarInicioPartida();
	}	
	
	// -------------------------------------
	
}
