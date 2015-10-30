package GUI;

import java.awt.Container;

import cliente.Cliente;

public class Controlador_Tela_Conecta {
	
	private Tela_Conecta frame_tela_conecta;
		
	private String path;
	
	// -------------------------------------
	
	public Controlador_Tela_Conecta(String p){
		path = p;
	}
	public Container getContainer(){
		return frame_tela_conecta.getContentPane();
	}
	
	// -------------------------------------
	
	public void Cria_Tela_Conecta(){
		frame_tela_conecta = new Tela_Conecta( path , this ) ;
		frame_tela_conecta.setVisible(true);
	}
	public void Desliga_Tela_Conecta(){
		frame_tela_conecta.setVisible(false);
	}
	
	// -------------------------------------
	
	public void Conecta(String ip, String porta){
		Cliente.getCliente().Conecta(ip, Integer.valueOf(porta));
	}
	
	// -------------------------------------
	
	public void Regras(){
		Cliente.getCliente().MostrarRegrasJogo();
	}

	// -------------------------------------
	
}
