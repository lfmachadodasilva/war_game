package cliente;

import java.awt.Container;
import java.awt.Desktop;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import GUI.Controlador_Tela_Cadastra;
import GUI.Controlador_Tela_Conecta;
import GUI.Controlador_Tela_Espera_Jogo;
import GUI.Controlador_Tela_Jogo;

import comum.CartaObjetivo;
import comum.CartaTerritorio;
import comum.ComandoOperacao;
import comum.CondRet;
import comum.Continente;
import comum.EstadoJogador;
import comum.Jogador;
import comum.Protocolo;
import comum.Territorio;

public class Cliente {

	private static Cliente cliente = null;
	
	private LinkedList<Jogador>          listaJogadores               = null;
	private LinkedList<Continente>       listaContinentes             = null;
	private LinkedList<Territorio>       listaTerritorios             = null;
	private LinkedList<CartaTerritorio>  listaCartaTerritorio         = null;
	private LinkedList<CartaObjetivo>    listaCartaObjetivo           = null;
	
	private Controlador_Tela_Conecta     controlador_tela_conecta     = null;
	private Controlador_Tela_Cadastra    controlador_tela_cadastra    = null;
	private Controlador_Tela_Espera_Jogo controlador_tela_espera_jogo = null;
	private Controlador_Tela_Jogo        controlador_tela_jogo        = null;
	
	private Jogador jogador;
	private Socket  socket;
	@SuppressWarnings("unused")
	private String cor;
	private String apelido;
	
	private Territorio paisAtaca;
	private Territorio paisSerAtacado;
	
	private Protocolo protocolo ;
	
	private String path_Figuras = "Figuras/";
	
	private int[] addExercitos = new int[6];
	private int   qtdExercitos = 0;
	
	// --------------------------------------------------
	
	public static Cliente getCliente (){
		if (cliente == null){
			cliente = new Cliente();
		}
		return cliente;
	}
	
	public String getApelido(){
		return apelido;
	}
	
	// --------------------------------------------------
	
	public Cliente(){
		//inicia as listas.
		listaJogadores               = new LinkedList<Jogador>();
		
		//inicia os controladores.
		controlador_tela_conecta  	 = new Controlador_Tela_Conecta(path_Figuras);
		controlador_tela_cadastra    = new Controlador_Tela_Cadastra(path_Figuras);
		controlador_tela_espera_jogo = new Controlador_Tela_Espera_Jogo(path_Figuras);
		controlador_tela_jogo        = new Controlador_Tela_Jogo(path_Figuras);
	}

	// --------------------------------------------------

	public void CriaTela_Conecta(){
		controlador_tela_conecta.Cria_Tela_Conecta();
	}
	public Container getContainerTela_Conecta(){
		return controlador_tela_conecta.getContainer();
	}
	
	// --------------------------------------------------
	
	public void CriaTela_Cadastra(){
		controlador_tela_cadastra.Cria_Tela_Cadastra();
		controlador_tela_conecta.Desliga_Tela_Conecta();
	}
	public Container getContainerTela_Cadastra(){
		return controlador_tela_cadastra.getContainer();
	}
	
	// --------------------------------------------------
	
	public void CriaTela_Espera_Jogo(){
		controlador_tela_espera_jogo.Cria_Tela_Espera_Jogo();
		controlador_tela_cadastra.Desliga_Tela_Cadastra();
	}
	public void DesligaTela_Espera_Jogo(){
		controlador_tela_espera_jogo.Desliga_Tela_Espera_Jogo();
	}
	public Container getContainerTela_Espera_Jogo(){
		return controlador_tela_espera_jogo.getContainer();
	}
	
	// --------------------------------------------------
	
	public void CriaTeta_Jogo(){
		controlador_tela_jogo.Cria_Tela_Jogo();
		controlador_tela_espera_jogo.Desliga_Tela_Espera_Jogo();
		//CarregaDados();
	}
	public void DesligaTela_Jogo(){
		controlador_tela_jogo.Desliga_Tela_Jogo();
	}
	public Container getContainerTela_Jogo(){
		return controlador_tela_jogo.getContainer();
	}
	public void AtualizaLog_Tela_Jogo(String s){
		controlador_tela_jogo.AtualizaLog(s);
	}
	
	// --------------------------------------------------
	
	public void setListaContinentes(LinkedList<Continente> lista){
		this.listaContinentes = lista;
	}
	public LinkedList<Continente> getListaContinentes(){
		return listaContinentes;
	}
	public Continente buscaContinente( String nomeContinente ){
		for( Continente continente : listaContinentes ){
			String nome = continente.getNomeContinente() ;
			if( nome.equals( nomeContinente ) )
				return continente ;
		}
		return null ;
	}

	// --------------------------------------------------
	
	public void setListaTerritorios(LinkedList<Territorio> lista){
		this.listaTerritorios = lista;
	}
	public LinkedList<Territorio> getListaTerritorios(){
		return listaTerritorios;
	}
	public Territorio buscaTerritorio( String nomeTerritorio ){
		for( Territorio territorio : listaTerritorios ){
			String nome = territorio.getNomeTerritorio() ;
			if( nome.equals(nomeTerritorio) )
				return territorio ;
		}
		return null ;
	}
	
	// --------------------------------------------------
	
	public void setListaCartaTerritorio(LinkedList<CartaTerritorio> listaCartaTerritorio) {
		this.listaCartaTerritorio = listaCartaTerritorio;
	}
	public LinkedList<CartaTerritorio> getListaCartaTerritorio() {
		return listaCartaTerritorio;
	}

	// --------------------------------------------------
	
	public void setListaCartaObjetivo(LinkedList<CartaObjetivo> listaCartaObjetivo) {
		this.listaCartaObjetivo = listaCartaObjetivo;
	}
	public LinkedList<CartaObjetivo> getListaCartaObjetivo() {
		return listaCartaObjetivo;
	}
	
	// --------------------------------------------------
	
	public Jogador getJogador(){
		return jogador;
	}
	
	// --------------------------------------------------
	
	public void AtualizaJogadoresCadastrados(LinkedList<Jogador> lista){
		this.listaJogadores = lista;
		for(Jogador j: lista){
			if(j.getApelidoJogador().equals(apelido)){
				jogador = j;
			}
		}
		controlador_tela_espera_jogo.AtualizaListaCadastrados(lista);
	}
	
	// --------------------------------------------------
	
	public void AtualizaJogadores(LinkedList<Jogador> lista){
		this.listaJogadores.clear();
		this.listaJogadores = lista;
		for(Jogador j : listaJogadores){
			if(j.getApelidoJogador().equals(apelido))
				jogador = j;
		}
		
		AtualizaTela_Jogo();
	}
	
	// --------------------------------------------------

	public void AtualizaTela_Jogo(){

		Atualiza_AbaMeusDados();
		
		Atualiza_AbaAtacar();
		
		Atualiza_AbaMeusTerritorios();
		
		Atualiza_AbaCartasTerritorio();
		
		Atualiza_AbaCartaObjetivo();
		
		Atualiza_AbaDeslocarExercito();
		
		Atualiza_AbaDistribuirExercito();
		
		controlador_tela_jogo.Atualiza_LigaDesligaBotoes();
		
	}
	public void Atualiza_AbaMeusDados(){
		controlador_tela_jogo.Atualiza_AbaMeusDados(
				jogador.getApelidoJogador(), 
				jogador.getExercitoJogador(),
				String.valueOf(jogador.getTerritoriosJogador().size()),
				String.valueOf(jogador.getCartasTerritorioJogador().size()),
				ConverterEstadoJogador(jogador.getEstadoJogador()));
	}
	public void Atualiza_AbaAtacar(){
		controlador_tela_jogo.Atualiza_AbaAtacar_LisgaDesligaBotoes();
	}
	public void Atualiza_AbaMeusTerritorios(){
		LinkedList<String> listaTerritorios = new LinkedList<String>();
		for(Territorio t: jogador.getTerritoriosJogador())
			listaTerritorios.add(t.getNomeTerritorio());
		controlador_tela_jogo.Atualiza_AbaMeusTerritorios(listaTerritorios);
	}
	public void Atualiza_AbaCartasTerritorio(){
		LinkedList<String> listaCartasTerritoios = new LinkedList<String>();
		for(CartaTerritorio t: jogador.getCartasTerritorioJogador())
			listaCartasTerritoios.add(t.getTerritorioCartaTerritorio().getNomeTerritorio());
		controlador_tela_jogo.Atualiza_AbaCartasTerritorio(listaCartasTerritoios);
	}
	public void Atualiza_AbaCartaObjetivo(){
		controlador_tela_jogo.Atualiza_AbaCartaObjetivo(
				String.valueOf(jogador.getObjetivoJogador().getIdCarta()), 
				jogador.getObjetivoJogador().getDescricaoObjetivo());
	}
	public void Atualiza_AbaDeslocarExercito(){
		LinkedList<String> listaTerritorios = new LinkedList<String>();
		for(Territorio t: jogador.getTerritoriosJogador())
			listaTerritorios.add(t.getNomeTerritorio());
		controlador_tela_jogo.Atualiza_AbaDeslocarExercito(listaTerritorios);
	}
	public void Atualiza_AbaDistribuirExercito(){
		LinkedList<String> listaCont = new LinkedList<String>();
		for(Territorio t: jogador.getTerritoriosJogador()){
			String a = t.getContinenteHaQuePertenceTerritorio().getNomeContinente();
			if(!listaCont.contains(a)){
				listaCont.add(a);
			}
		}
		controlador_tela_jogo.Atualiza_AbaDistribuirExercito_cont(listaCont);
	}
	
	// --------------------------------------------------
	
	public void MostrarRegrasJogo(){
		//Abre no navegador de internet o endereco com as regras do jogo.
		Desktop desk = java.awt.Desktop.getDesktop();
		try {
			desk.browse(new java.net.URI("http://pt.wikipedia.org/wiki/War"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(controlador_tela_conecta.getContainer(),
					"Erro - Nao foi possivel mostrar o link com as regras do jogo.", 
					"Erro", JOptionPane.ERROR_MESSAGE);
		} 
	}

	// --------------------------------------------------
	
	public void AtualizaTabuleiro(){
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.ATUALIZA_TABULEIRO);
		protocolo.addParametro(jogador);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_conecta.getContainer(),
					"Erro na operacao de atualizar tabuleiro.", 
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------

	public void Conecta(String ip, Integer porta){
		//Conecta o cliente com o servidor.
		try{
			
			socket = new Socket(ip, porta);
			
			//Liga a thread de escuta.
			Thread t = new Thread( new TarefaEscuta( socket ) ) ;
			t.start() ;
			
			Protocolo protocolo = new Protocolo(ComandoOperacao.CONECTA ) ;
			
			ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream() ) ;
			
			out.writeObject( protocolo ) ;
			
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_conecta.getContainer(),
					"Erro na operacao de conectar cliente.");
			return;
		}
		
		
	}
	
	// --------------------------------------------------
	
	public void Cadastra(String apelido, String cor){
		
		//Cadastra cliente.
		this.apelido = apelido;
		this.cor     = cor;
		
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.CADASTRAR);
		protocolo.addParametro(apelido);
		protocolo.addParametro(cor);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){	
			JOptionPane.showMessageDialog(controlador_tela_cadastra.getContainer(), 
					"Erro na operacao cadastrar cliente.",
					"Erro", JOptionPane.ERROR_MESSAGE);	
		}
	}

	// --------------------------------------------------

	public void SairEsperaJogo(){
		
		//mandar ComandoOperacao.SAIR_LISTA_ESPERA
		//mandar apelido.
		int resp = JOptionPane.showConfirmDialog(controlador_tela_espera_jogo.getContainer(),
				"Deseja sair da lista de espera?", "War", JOptionPane.YES_NO_OPTION);
		if(resp == JOptionPane.NO_OPTION)
			return;
		
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.SAIR_LISTA_ESPERA);
		protocolo.addParametro(apelido);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
	
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_espera_jogo.getContainer(), 
					"Erro ao sair da lista de espera do jogo no cliente.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------
	
	public void SolicitarInicioPartida(){
		
		//mandar SOLICITAR_INICIAR_PARTIDA,
		//mandar o apelido.
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.SOLICITAR_INICIAR_PARTIDA);
		protocolo.addParametro(apelido);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_espera_jogo.getContainer(), 
					"Erro ao solicitar inicio de partida no cliente.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------
	
	public void IniciarPartida(CondRet cond){
		
		//mandar ComandoOperacao.INICIAR_PARTIDA
		//mandar Ok ou NOK.
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.INICIAR_PARTIDA);
		protocolo.addParametro(cond);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_espera_jogo.getContainer(), 
					"Erro ao iniciar a partida no cliente.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------
	
	public void SairPartida(){
		
		//mandar ComandoOperacao.SAIR_PARTIDA
		//mandar apelido.
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.SAIR_PARTIDA);
		protocolo.addParametro(apelido);
		
		try{
		
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_espera_jogo.getContainer(), 
					"Erro ao sair da partida.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------
	
	public void Atacar(int qtdDados){
		
		//mandar ComandoOperacao.ATAQUE.
		//mandar o territorio de origem.
		//mandar o territorio de destino.
		//mandar o numero de dados de ataque
		
		ObjectOutputStream out = null;
		
		protocolo= new Protocolo(ComandoOperacao.EFETUAR_ATAQUE);
		protocolo.addParametro(paisAtaca.getNomeTerritorio());
		protocolo.addParametro(paisSerAtacado.getNomeTerritorio());
		protocolo.addParametro(qtdDados);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), 
					"Erro na operacao atacar do cliente.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------

	public void Atualizar_Tabuleiro(){
	
		ObjectOutputStream out = null;

		protocolo= new Protocolo(ComandoOperacao.ATUALIZA_TABULEIRO);
		protocolo.addParametro(jogador);
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), 
					"Erro na operacao de atualizar tabuleiro.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// --------------------------------------------------
	
	public void FinalizarJogada(){
		
		//mandar ComandoOperacao.FINALIZA_JOGADA
		//mandar apelido.
		//numero do objetivo
		ObjectOutputStream out = null;
		
		protocolo = new Protocolo(ComandoOperacao.FINALIZA_JOGADA);
		protocolo.addParametro(apelido);
		protocolo.addParametro(jogador.getObjetivoJogador().getIdCarta());
		
		try{
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeObject(protocolo);
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), 
					"Erro na operacao finalizar jogada do cliente.",
					"Erro", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	// --------------------------------------------------

	public void ConsultarTerritorio(int x, int y){

		for(Jogador j: listaJogadores){
			
			for(Territorio t: j.getTerritoriosJogador()){

				if(t.Pertence(x, y) == true){
					JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), 
							"Continente:         " + t.getContinenteHaQuePertenceTerritorio().getNomeContinente() +
							"\nTerritorio:           " + t.getNomeTerritorio() +
							"\nExercito:             " + t.getExercitoDominante() +
							"\nQuantidade:       " + t.getNumExercito());
					System.out.println(t.getNumExercito());
					return;
				}	
			}
		}
		JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), "Aqua!");
	}
	
	// --------------------------------------------------
	
	public String PaisAtaca(int x, int y){
		
		for(Territorio t: jogador.getTerritoriosJogador()){
			
			if(t.Pertence(x, y) == true){
				paisAtaca = t;
				QtdDadosAtacar(paisAtaca);
				return paisAtaca.getNomeTerritorio();
			}
		}
		
		JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), 
				"Atacar somente com os seus territorios.");
		return null;
	}
	public void QtdDadosAtacar(Territorio t){
		int qtdNumExercito = t.getNumExercito();
		LinkedList<String> s = new LinkedList<String>();
		if(qtdNumExercito >= 4){
			for(int i = 1 ; i < 4 ; i++)
				s.add(String.valueOf(i));
		}
		else if(qtdNumExercito == 3){
			for(int i = 1 ; i < 3 ; i++)
				s.add(String.valueOf(i));
		}
		else if(qtdNumExercito == 2)
			s.add("1");
		else
			s.add("0");
		controlador_tela_jogo.Atualiza_AbaAtacar_QtdDados(s);
		
	}
	
	// --------------------------------------------------

	public String PaisSerAtacado(int x, int y){
		
		paisSerAtacado = null;
		for(Jogador j: listaJogadores){

			for(Territorio t: j.getTerritoriosJogador()){

				if(t.Pertence(x, y) == true){
					paisSerAtacado = t;
					break;
				}
			}
		}
		if(paisSerAtacado == null){
			JOptionPane.showMessageDialog(controlador_tela_jogo.getContainer(), 
					"Nao pode atacar seu proprio territorio.");
			return null;
		}
			
		
		//verificar se ele nao é meu.
		for(Territorio t: jogador.getTerritoriosJogador()){
			
			if(t.getNomeTerritorio().equals(paisSerAtacado.getNomeTerritorio())){
				return null;
			}
			
		}
		
		//verifica se é fronteira.
		if(paisSerAtacado.getFronteirasTerritorio().contains(paisAtaca)){
			return paisSerAtacado.getNomeTerritorio();
		}
		
		return null;
	}
	
	// --------------------------------------------------
	
	public void Atualiza_AbaMeusTerriorios(String s){
		for(Territorio t: jogador.getTerritoriosJogador()){
			if(t.getNomeTerritorio().equals(s)){
				controlador_tela_jogo.Atualiza_AbaMeusTerriorios(
						t.getContinenteHaQuePertenceTerritorio().getNomeContinente(),
						t.getExercitoDominante(),
						String.valueOf(t.getNumExercito()));
			}
		}
	}
	
	// --------------------------------------------------
	
	public LinkedList<String> getListaFronteiras(String territorio){
		if(territorio == null)
			return null;
		LinkedList<String> lista = new LinkedList<String>();
		//procuta o territorio.
		Territorio terr = null;
		for(Territorio t: jogador.getTerritoriosJogador()){
			if(t.getNomeTerritorio().equals(territorio)){
				terr = t;
				break;
			}
		}
		//seleciona os territorios que fazem fronteiras e que
		//sao do mesmo jogador.
		for(Territorio t: terr.getFronteirasTerritorio()){
			if(jogador.getTerritoriosJogador().contains(t)){
				lista.add(t.getNomeTerritorio());
			}
		}
		
		return lista;
	}
	
	// --------------------------------------------------
	
	public LinkedList<String> getListaQuantidadeExercitos(String territorio){
		LinkedList<String> lista = new LinkedList<String>();
		//procuta o territorio.
		Territorio terr = null;
		for(Territorio t: jogador.getTerritoriosJogador()){
			if(t.getNomeTerritorio().equals(territorio)){
				terr = t;
				break;
			}
		}
		
		for(int i = 1; i <= terr.getNumExercito(); i++){
			if(terr.getNumExercito() == 1)
				lista.add(String.valueOf(0));
			else
				lista.add(String.valueOf(i-1));
		}
		
		return lista;
	}
	
	// --------------------------------------------------
	
	public void DeslocaExercito(String de, String para, Integer qtd){
		Territorio tde=null, tpara=null;
		//procura os territorios.
		for(Territorio t: jogador.getTerritoriosJogador()){
			if(t.getNomeTerritorio().equals(de))
				tde = t;
			else if(t.getNomeTerritorio().equals(para))
				tpara = t;
		}
		
		tde.setNumExercito(tde.getNumExercito() - qtd);
		tpara.setNumExercito(tpara.getNumExercito() + qtd);
		
		AtualizaTela_Jogo();
		
		Atualizar_Tabuleiro();
	}
	
	// --------------------------------------------------
	
	public void DistribuirExercito(String territorio, String qtdMundo, String qtdCont){
		
		Territorio t = jogador.buscaTerritorio(territorio);
		
		if(t != null){
			
			int qtdm = Integer.valueOf(qtdMundo);
			int qtdc  = Integer.valueOf(qtdCont);
			int soma = qtdm+qtdc;
			t.setNumExercito(t.getNumExercito()+soma);
			qtdExercitos -= qtdm;

			String continente = t.getContinenteHaQuePertenceTerritorio().getNomeContinente();
			setAddExercito(continente, -qtdc);	

			AtualizaTela_Jogo();
			
			controlador_tela_jogo.AtualizaLog("Adicionado " + soma + " exercitos ao territorio " + territorio + ".");
			
			Atualizar_Tabuleiro();
		}
		
	}
	
	// --------------------------------------------------
	
	public void zeraAddExercito(){
		for(int i = 0 ; i < 6 ; i++){
			addExercitos[i] = 0; 
		}
	}
	public void setAddExercito(String cont, int qtd){
		addExercitos[ConverterTerritorio(cont)] += qtd;
	}
	public int getAddExercito(String cont){
		return addExercitos[ConverterTerritorio(cont)];
	}
	public int ConverterTerritorio(String t){
		//0 - America do Sul, 1 - America do Norte, 2 - Africa, 3 - Asia, 4 - Oceania, 5 - Europa
		if(t.equals("America do Sul"))
			return 0;
		else if(t.equals("America do Norte"))
			return 1;
		else if(t.equals("Africa"))
			return 2;
		else if(t.equals("Asia"))
			return 3;
		else if(t.equals("Oceania"))
			return 4;
		else //if(t.equals("Europa"))
			return 5;
	}

	// --------------------------------------------------
	
	public int getQtdExercitos(){
		return qtdExercitos;
	}
	public void setQtdExercitos(int qtd){
		qtdExercitos += qtd;
	}
	
	// --------------------------------------------------
	
	public LinkedList<String> getListaTerritoriosJogador(String continente){
		LinkedList<String> listaTerritorio = new LinkedList<String>();
		for(Territorio t: jogador.getTerritoriosJogador()){
			Continente c = t.getContinenteHaQuePertenceTerritorio();
			if(c.getNomeContinente().equals(continente)){
				listaTerritorio.add(t.getNomeTerritorio());
			}
		}
		return listaTerritorio;
	}
	
	// --------------------------------------------------
	
	private String ConverterEstadoJogador(EstadoJogador e){
		if(e == EstadoJogador.CORRENTE)          return "Corrente";
		else if(e == EstadoJogador.JOGANDO)      return "Jogando";
		else if(e == EstadoJogador.CADASTRADO)   return "Cadastrado";
		else if(e == EstadoJogador.CONECTADO)    return "Conectado";
		else if(e == EstadoJogador.DESCONECTADO) return "Desconectado";
		else                                     return "Esperando";
	}
	
	// --------------------------------------------------
	
	public void AtualizaEstadoJogador(EstadoJogador e){
		
		jogador.setEstadoJogador(e);
		
		controlador_tela_jogo.AtualizaEstadoJogador(e);
		
		Atualiza_AbaMeusDados();
	}
	
	// --------------------------------------------------
	
	public void AbortarPrograma(){
		System.exit(3);
	}	
	
}
