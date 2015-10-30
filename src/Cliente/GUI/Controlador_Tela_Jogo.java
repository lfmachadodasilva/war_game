package GUI;

import java.awt.Container;
import java.util.LinkedList;

import cliente.Cliente;

import comum.CartaTerritorio;
import comum.EstadoJogador;

public class Controlador_Tela_Jogo {
	
	// -------------------------------------

	private String path;
	
	private Tela_Jogo frame_tela_jogo = null;

	// -------------------------------------

	public Controlador_Tela_Jogo(String p){
		this.path = p;
	}
	
	// -------------------------------------
	
	public void Cria_Tela_Jogo(){
		frame_tela_jogo = new Tela_Jogo(path, this);
		frame_tela_jogo.setVisible(true);
	}
	public void Desliga_Tela_Jogo(){
		frame_tela_jogo.setVisible(false);
	}
	public Container getContainer(){
		return frame_tela_jogo.getContentPane();
	}
	
	// -------------------------------------
	
	public void ConsultarTerritorio(int x, int y){
		Cliente.getCliente().ConsultarTerritorio(x, y);
	}
	public String PaisSerAtacado(int x, int y){
		return Cliente.getCliente().PaisSerAtacado(x, y);
	}
	public String PaisAtaca(int x, int y){
		return Cliente.getCliente().PaisAtaca(x, y);
	}
	
	// -------------------------------------
	
	public void AtualizaCartaTerritorios(LinkedList<CartaTerritorio> lista){
		
		String[] s = new String[lista.size()];
		int i = 0;
		for(CartaTerritorio t: lista){
			s[i] = t.getTerritorioCartaTerritorio().getNomeTerritorio();
			i++;
		}
		
		frame_tela_jogo.AtualizarCartaTerritorios(s);
	}
	
	// -------------------------------------
	
	public void AtualizaObjetivo(int id, String objetivo){
		frame_tela_jogo.AtualizaObjetivo(id, objetivo);
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaMeusDados(String apelido, String cor, String qtdTerritorios, String qtdCartasTerritorio, String estado){
		frame_tela_jogo.Atualiza_AbaMeusDados(apelido, cor, qtdTerritorios, qtdCartasTerritorio, estado);
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaAtacar_LisgaDesligaBotoes(){
		frame_tela_jogo.Atualizar_AbaAtacar_LigaDesligaBotoes();
	}
	public void Atualiza_AbaAtacar_QtdDados(LinkedList<String> s){
		frame_tela_jogo.Atualizar_AbaAtacar_QtdDados(s);
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaMeusTerritorios(LinkedList<String> lista){	
		frame_tela_jogo.Atualiza_AbaMeusTerritorios(lista);
	}
	public void Atualiza_AbaMeusTerriorios(String s){
		Cliente.getCliente().Atualiza_AbaMeusTerriorios(s);
	}
	public void Atualiza_AbaMeusTerriorios(String continente, String exer, String qtdExer){
		frame_tela_jogo.Atualiza_AbaMeusTerritorios(continente, exer, qtdExer);
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaCartasTerritorio(LinkedList<String> lista){
		frame_tela_jogo.Atualiza_AbaCartaTerritorio(lista);
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaDeslocarExercito(LinkedList<String> lista){
		frame_tela_jogo.Atualizar_AbaDeslocarExercito(lista);
	}
	public void Atualiza_AbaDeslocarExercito(String territorio){
		if(territorio == null)
			return;
		LinkedList<String> listaTerritorios_para = Cliente.getCliente().getListaFronteiras(territorio);
		LinkedList<String> listaQtd              = Cliente.getCliente().getListaQuantidadeExercitos(territorio);
		
		frame_tela_jogo.Atualizar_AbaDeslocarExercito(listaTerritorios_para, listaQtd);
	}
	public void DeslocaExercito(String de, String para, Integer qtd){
		Cliente.getCliente().DeslocaExercito(de, para, qtd);
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaCartaObjetivo(String carta, String objetivo){
		frame_tela_jogo.Atualiza_AbaCartaObjetivo(carta, objetivo);
	}
	// -------------------------------------
	
	public void AtualizaLog(String s){
		frame_tela_jogo.AtualizaLog(s);
	}
	
	// -------------------------------------
	
	public void Atacar(int qtdDados){
		Cliente.getCliente().Atacar(qtdDados);
	}
	public void FinalizarJogada(){
		Cliente.getCliente().FinalizarJogada();
	}
	
	// -------------------------------------
	
	public void AtualizaEstadoJogador(EstadoJogador e){
		frame_tela_jogo.AtualizaEstadoJogador(e);
	}
	
	// -------------------------------------
	
	public void Atualiza_Tela_Jogo(){
		
		frame_tela_jogo.Atualiza_AbaDistribuirExercito_LigaDesligaBotoes();
		
	}
	public void Atualiza_Tabuleiro(){
		
		Cliente.getCliente().Atualizar_Tabuleiro();
		
	}
	
	// -------------------------------------
	
	public void Atualiza_AbaDistribuirExercito_cont(LinkedList<String> lista){
		
		frame_tela_jogo.Atualiza_AbaDistribuirExercito_cont(lista);
		
	}
	public void Atualiza_AbaDistribuirExercito_qtdMundo(){
		
		String qtd = String.valueOf(Cliente.getCliente().getQtdExercitos());
		frame_tela_jogo.Atualiza_AbaDistribuirExercito_qtdMundo(qtd);
		
	}
	public void Atualiza_AbaDistribuirExercito_terr(String continente){
		
		LinkedList<String> lista = Cliente.getCliente().getListaTerritoriosJogador(continente);
		frame_tela_jogo.Atualiza_AbaDistribuirExercito_terr(lista);
		
	}
	public void Atualiza_AbaDistribuirExercito_qtdCont(String continente){
		
		String s = String.valueOf(Cliente.getCliente().getAddExercito(continente));
		frame_tela_jogo.Atualiza_AbaDistribuirExercito_qtdCont(s);
		
	}
	public void DistribuirExercito(String terr, String mundo, String cont){
		Cliente.getCliente().DistribuirExercito(terr, mundo, cont);
	}
	
	// -------------------------------------
	
	public void Atualiza_LigaDesligaBotoes(){
		frame_tela_jogo.Atualiza_AbaDistribuirExercito_LigaDesligaBotoes();
		frame_tela_jogo.Atualizar_AbaAtacar_LigaDesligaBotoes();
		frame_tela_jogo.Atualizar_AbaDeslocarExercito_LigaDesligaBotoes();
		
		Atualiza_Tela_Jogo();
	}
	
	// -------------------------------------
	
}
