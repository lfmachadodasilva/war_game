package GUI;

import java.awt.Container;

import cliente.Cliente;

public class Controlador_Tela_Cadastra {
	
	private Tela_Cadastra frame_tela_cadastra;
	
	private String cor;
	private String apelido;
	
	private String path;

	// -------------------------------------
		
	public Controlador_Tela_Cadastra(String p){
		this.path = p;
	}
	
	// -------------------------------------

	public void Cria_Tela_Cadastra(){
		frame_tela_cadastra = new Tela_Cadastra(path, this);
		frame_tela_cadastra.setVisible(true);
	}
	
	// -------------------------------------
	
	public Container getContainer(){
		return frame_tela_cadastra.getContentPane();
	}
	
	// -------------------------------------
	
	public void Desliga_Tela_Cadastra(){
		frame_tela_cadastra.setVisible(false);
	}
	
	// -------------------------------------
	
	public Container getContainer_Tela_Cadastra(){
		return frame_tela_cadastra.getContentPane();
	}
	
	// -------------------------------------
	
	public void Cadastra(String apelido, String cor){
		this.apelido = apelido;
		this.cor = cor;
		Cliente.getCliente().Cadastra(apelido, cor);
	}
	
	// -------------------------------------
	
	public String getApelido(){
		return apelido;
	}
	public String getCor(){
		return cor;
	}
	
	// -------------------------------------
	
}
