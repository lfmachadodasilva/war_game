package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cliente.Cliente;

import comum.EstadoJogador;
import comum.PapelDeParede;

public class Tela_Jogo extends JFrame implements MouseListener {  
	
	private static final long serialVersionUID = 7960412449253600371L;
	
	private String path;
	
	private Container c;
	
	private JTabbedPane tab;
	
	private EstadoJogador estado;
	
	private JTextArea t_log;
	
	private Controlador_Tela_Jogo controlador;
	
	private boolean podeAtacar     = false;
	private boolean podeDeslocar   = false;
	private boolean podeDistribuir = false;
	
	private int qtdExercitosMundo = 0;
	private int qtdExercitosCont  = 0;
	
	//ABA MEUS DADOS.
	private JPanel     panel_meusDados;
	private JTextField t_meusDados_apelido;
	private JTextField t_meusDados_cor;
	private JTextField t_meusDados_qtdTerritorios;
	private JTextField t_meusDados_qtdCartasTerritorio;
	private JTextField t_meusDados_estado;
	private JButton    b_meusDados_SairPartida;
	//---
	
	//ABA ATACAR
	private JPanel     panel_atacar;
	private JTextField t_atacar_atacar;
	private JTextField t_atacar_serAtacado;
	private JButton    b_atacar_limparAtaque;
	private JButton    b_atacar_atacar;
	private JButton    b_atacar_fizalizarAtaque;
	private JComboBox  combo_atacar_qtdDados;
	//---
	
	//ABA DISTRIBUIR EXERCITOS
	private JPanel     panel_distribuirExercito;
	private JComboBox  combo_distribuirExercito_cont;
	private JComboBox  combo_distribuirExercito_terr;
	private JTextField t_distribuirExercito_mundo;
	private JTextField t_distribuirExercito_cont;
	private JButton    b_distribuirExercito_MenosCont;
	private JButton    b_distribuirExercito_MaisCont;
	private JButton    b_distribuirExercito_MaisMundo;
	private JButton    b_distribuirExercito_MenosMundo;
	private JButton    b_distribuirExercito_Distribuir;
	private JButton    b_distribuirExercito_Finalizar;
	//---
	
	//ABA DESLOCAR EXERCITOS
	private JPanel panel_deslocarExercito;
	private JComboBox combo_deslocarExercito_de;
	private JComboBox combo_deslocarExercito_para;
	private JComboBox combo_deslocarExercito_qtd;
	private JButton   b_deslocarExercito_finalizarJogada;
	//---
	
	//ABA MEUS TERRITORIOS
	private JPanel     panel_meusTerritorios;
	private JComboBox  combo_territorio; 
	private JTextField t_meusTerritorios_cont;
	private JTextField t_meusTerritorios_exer;
	private JTextField t_meusTerritorios_qtdExer;
	//---
	
	//ABA CARTAS TERRITORIO
	private JPanel    panel_cartaTerritorio;
	private JComboBox combo_cartaTerritorio;
	private JLabel    l_carta_territorio;
	private JButton   b_deslocarExercito_deslocar;
	//---
	
	//ABA CARTA OBJETIVO
	private JPanel    panel_objetivo;
	private JTextArea t_objectivo;
	private JLabel    l_carta_objetivo;
	//---
	
	// -----------------------------------
	
	public Tela_Jogo(String p, Controlador_Tela_Jogo controlador){
		
		super("War");
		
		path = p;

		setLayout(null);

		c = getContentPane();
		
		this.controlador = controlador;
		
		//Aba 1
		tab = new JTabbedPane();
		tab.setBounds(1, 538, 734, 135);
		c.add(tab);
		
		Criar_AbaMeusDados();
		tab.addTab("Meus Dados",          panel_meusDados);
		Criar_AbaAtacar();
		tab.addTab("Atacar",              panel_atacar);
		Criar_AbaDistribuirExercito();
		tab.addTab("Distribuir exercito", panel_distribuirExercito);
		Criar_AbaDeslocarExercito();
		tab.addTab("Deslocar exercito",   panel_deslocarExercito);
		Criar_AbaMeusTerritorios();
		tab.addTab("Meus Territorios",    panel_meusTerritorios);
		Criar_AbaCartaTerritorio();
		tab.addTab("Cartas Territorio",   panel_cartaTerritorio);
		Criar_AbaCartaObjetivo();
		tab.addTab("Carta Objetivo",      panel_objetivo);
		
		
		//Log
		JLabel log = new JLabel("Log do jogo:");
		log.setBounds(1, 736, 100, 18);
		c.add(log);
		
		JScrollPane sp;
		t_log = new JTextArea();
		t_log.setBackground(null);
		t_log.setLineWrap(true);
		t_log.setWrapStyleWord(true);
		t_log.setEditable(false);
		t_log.setBackground(Color.lightGray);
		t_log.setForeground(Color.WHITE);
		sp= new JScrollPane(t_log);
		sp.setBounds(736, 559, 278, 115);
		c.add(sp);
		t_log.setText("Vazio");
		
		//Tabuleiro
		ImageIcon i_tabuleiro = new ImageIcon(path+"Tabuleiro.jpg");
		PapelDeParede papelDeParede = new PapelDeParede(i_tabuleiro.getImage());
		c.add(papelDeParede);
		
		this.setBackground(null);
		this.addMouseListener(this);  
		this.setSize(i_tabuleiro.getIconWidth(), 700);
		this.setResizable(false);
		this.setIconImage(new ImageIcon(path+"Tanque.png").getImage());
		Toolkit kit = Toolkit.getDefaultToolkit();  
		Dimension tamanhoTela = kit.getScreenSize(); 
		this.setLocation((tamanhoTela.width-i_tabuleiro.getIconWidth())/2,(tamanhoTela.height-700)/2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// -----------------------------------
	
	private void Criar_AbaMeusDados(){
		
		panel_meusDados = new JPanel();
		panel_meusDados.setLayout(null);
		panel_meusDados.setBackground(null);
		
		JLabel l;
		
		l = new JLabel("Apelido: ");
		l.setBounds(10, 2, 100, 18);
		panel_meusDados.add(l);
		t_meusDados_apelido = new JTextField();
		t_meusDados_apelido.setBounds(10, 20, 200, 18);
		t_meusDados_apelido.setEditable(false);
		panel_meusDados.add(t_meusDados_apelido);
		
		l = new JLabel("Cor do exercito:");
		l.setBounds(10, 50, 100, 18);
		panel_meusDados.add(l);
		t_meusDados_cor = new JTextField();
		t_meusDados_cor.setEditable(false);
		t_meusDados_cor.setBounds(10, 68, 200, 18);
		panel_meusDados.add(t_meusDados_cor);
		
		l = new JLabel("Quantidade de territorios:");
		l.setBounds(250, 2, 200, 18);
		panel_meusDados.add(l);
		t_meusDados_qtdTerritorios = new JTextField();
		t_meusDados_qtdTerritorios.setEditable(false);
		t_meusDados_qtdTerritorios.setBounds(250, 20, 200, 18);
		panel_meusDados.add(t_meusDados_qtdTerritorios);
		
		l = new JLabel("Quantidade de cartas territorio:");
		l.setBounds(250, 50, 200, 18);
		panel_meusDados.add(l);
		t_meusDados_qtdCartasTerritorio = new JTextField();
		t_meusDados_qtdCartasTerritorio.setEditable(false);
		t_meusDados_qtdCartasTerritorio.setBounds(250, 68, 200, 18);
		panel_meusDados.add(t_meusDados_qtdCartasTerritorio);
		
		l = new JLabel("Estado:");
		l.setBounds(500, 2, 100, 18);
		panel_meusDados.add(l);
		t_meusDados_estado = new JTextField();
		t_meusDados_estado.setEditable(false);
		t_meusDados_estado.setBounds(500, 20, 200, 18);
		panel_meusDados.add(t_meusDados_estado);
		
		b_meusDados_SairPartida = new JButton("Sair da partida");
		b_meusDados_SairPartida.setBounds(535, 68, 130, 18);
		b_meusDados_SairPartida.addActionListener(new Evento());
		panel_meusDados.add(b_meusDados_SairPartida);	
	}
	public void Atualiza_AbaMeusDados(String apelido, String cor, String qtdTerritorios, String qtdCartasTerritorio, String estado){
		t_meusDados_apelido.setText(apelido);
		t_meusDados_cor.setText(cor);
		t_meusDados_qtdTerritorios.setText(qtdTerritorios);
		t_meusDados_qtdCartasTerritorio.setText(qtdCartasTerritorio);
		t_meusDados_estado.setText(estado);
	}
	
	// -----------------------------------
	
	private void Criar_AbaAtacar(){
		panel_atacar = new JPanel();
		panel_atacar.setLayout(null);
		panel_atacar.setBackground(null);
		
		JLabel l;
		
		l = new JLabel("País que vai atacar:");
		l.setBounds(10, 0, 200, 15);
		panel_atacar.add(l);
		t_atacar_atacar = new JTextField();
		t_atacar_atacar.setEditable(false);
		t_atacar_atacar.setBounds(10, 15, 400, 18);
		panel_atacar.add(t_atacar_atacar);

		l = new JLabel("País que vai ser atacado:");
		l.setBounds(10, 35, 200, 15);
		panel_atacar.add(l);
		t_atacar_serAtacado = new JTextField();
		t_atacar_serAtacado.setEditable(false);
		t_atacar_serAtacado.setBounds(10, 50, 400, 18);
		panel_atacar.add(t_atacar_serAtacado);
		
		l = new JLabel("Quantidade de dados:");
		l.setBounds(10, 68, 200, 18);
		panel_atacar.add(l);
		//String[] s = {"1","2","3"};
		combo_atacar_qtdDados = new JComboBox();
		combo_atacar_qtdDados.setBounds(10, 85, 50, 18);
		panel_atacar.add(combo_atacar_qtdDados);
		
		b_atacar_limparAtaque = new JButton("Limpar Ataque");
		b_atacar_limparAtaque.setBounds(500, 15, 130, 18);
		b_atacar_limparAtaque.addActionListener(new Evento());
		b_atacar_limparAtaque.setEnabled(false);
		panel_atacar.add(b_atacar_limparAtaque);
		
		b_atacar_atacar = new JButton("Atacar");
		b_atacar_atacar.setBounds(500, 45, 130, 18);
		b_atacar_atacar.addActionListener(new Evento());
		b_atacar_atacar.setEnabled(false);
		panel_atacar.add(b_atacar_atacar);
		
		b_atacar_fizalizarAtaque = new JButton("Finalizar ataque");
		b_atacar_fizalizarAtaque.setBounds(500, 75, 130, 18);
		b_atacar_fizalizarAtaque.addActionListener(new Evento());
		b_atacar_fizalizarAtaque.setEnabled(false);
		panel_atacar.add(b_atacar_fizalizarAtaque);	
	}
	public void Atualizar_AbaAtacar_LigaDesligaBotoes(){
		
		t_atacar_atacar.setText("");
		t_atacar_serAtacado.setText("");
		combo_atacar_qtdDados.removeAllItems();
		
		//Bloqueia aba de atacar.
		if(podeAtacar){
			b_atacar_atacar.setEnabled(true);
			b_atacar_fizalizarAtaque.setEnabled(true);
			b_atacar_limparAtaque.setEnabled(true);
		}
		//---
		//Desbloqueira aba de atacar.
		else{
			b_atacar_atacar.setEnabled(false);
			b_atacar_fizalizarAtaque.setEnabled(false);
			b_atacar_limparAtaque.setEnabled(false);
		}
		//---
		
	}
	public void Atualizar_AbaAtacar_QtdDados(LinkedList<String> s){
		combo_atacar_qtdDados.removeAllItems();
		for(String aux: s)
			combo_atacar_qtdDados.addItem(aux);
	}

	// -----------------------------------
	
	private void Criar_AbaMeusTerritorios(){
		panel_meusTerritorios = new JPanel();
		panel_meusTerritorios.setLayout(null);
		panel_meusTerritorios.setBackground(null);
		
		JLabel l;
		
		l = new JLabel("Territorios");
		l.setBounds(10, 0, 100, 18);
		panel_meusTerritorios.add(l);
		combo_territorio = new JComboBox();
		combo_territorio.setBounds(10, 15, 300, 20);
		combo_territorio.addActionListener(new Evento());
		panel_meusTerritorios.add(combo_territorio);
		
		l = new JLabel("Continente:");
		l.setBounds(370, 0, 100, 18);
		panel_meusTerritorios.add(l);
		t_meusTerritorios_cont = new JTextField();
		t_meusTerritorios_cont.setBounds(370, 15, 300, 18);
		t_meusTerritorios_cont.setEditable(false);
		panel_meusTerritorios.add(t_meusTerritorios_cont);
		
		l = new JLabel("Exercito dominante:");
		l.setBounds(370, 35, 200, 18);
		panel_meusTerritorios.add(l);
		t_meusTerritorios_exer = new JTextField();
		t_meusTerritorios_exer.setBounds(370, 50, 300, 18);
		t_meusTerritorios_exer.setEditable(false);
		panel_meusTerritorios.add(t_meusTerritorios_exer);
		
		l = new JLabel("Quantidade de exercitos:");
		l.setBounds(370, 70, 200, 18);
		panel_meusTerritorios.add(l);
		t_meusTerritorios_qtdExer = new JTextField();
		t_meusTerritorios_qtdExer.setBounds(370, 85, 300, 18);
		t_meusTerritorios_qtdExer.setEditable(false);
		panel_meusTerritorios.add(t_meusTerritorios_qtdExer);
	}
	public void Atualiza_AbaMeusTerritorios(LinkedList<String> listaTerritorios){
		combo_territorio.removeAllItems();
		for(String s: listaTerritorios)
			combo_territorio.addItem(s);
	}
	public void Atualiza_AbaMeusTerritorios(String continente, String exer, String qtdExer){
		t_meusTerritorios_cont.setText(continente);
		t_meusTerritorios_exer.setText(exer);
		t_meusTerritorios_qtdExer.setText(qtdExer);
	}
	
	// -----------------------------------
	
	private void Criar_AbaDistribuirExercito(){
		panel_distribuirExercito = new JPanel();
		panel_distribuirExercito.setLayout(null);
		panel_distribuirExercito.setBackground(null);
		
		JLabel l;
		
		l = new JLabel("Continente");
		l.setBounds(10, 0, 100, 18);
		panel_distribuirExercito.add(l);
		combo_distribuirExercito_cont = new JComboBox();
		combo_distribuirExercito_cont.setBounds(10, 15, 200, 20);
		combo_distribuirExercito_cont.addActionListener(new Evento());
		panel_distribuirExercito.add(combo_distribuirExercito_cont);
		//coloca tds os continentes do jogador tem exercito
		
		//lista com os territorios daquele continente.
		l = new JLabel("Territorios");
		l.setBounds(10, 40, 100, 18);
		panel_distribuirExercito.add(l);
		combo_distribuirExercito_terr = new JComboBox();
		combo_distribuirExercito_terr.setBounds(10, 55, 200, 20);
		combo_distribuirExercito_terr.addActionListener(new Evento());
		panel_distribuirExercito.add(combo_distribuirExercito_terr);
		
		b_distribuirExercito_Distribuir = new JButton("Distribuir");
		b_distribuirExercito_Distribuir.setBounds(520, 85, 130, 18);
		b_distribuirExercito_Distribuir.addActionListener(new Evento());
		panel_distribuirExercito.add(b_distribuirExercito_Distribuir);
		
		b_distribuirExercito_Finalizar = new JButton("Finalizar Distribuicao");
		b_distribuirExercito_Finalizar.setBounds(10, 85, 200, 18);
		b_distribuirExercito_Finalizar.addActionListener(new Evento());
		panel_distribuirExercito.add(b_distribuirExercito_Finalizar);
		
		//---
		l = new JLabel("Exercitos para distribuir no mundo:");
		l.setBounds(270, -30, 300, 100);
		panel_distribuirExercito.add(l);
		t_distribuirExercito_mundo = new JTextField();
		t_distribuirExercito_mundo.setBounds(575, 10, 25, 20);
		t_distribuirExercito_mundo.setEditable(false);
		panel_distribuirExercito.add(t_distribuirExercito_mundo);
		b_distribuirExercito_MenosMundo = new JButton(new ImageIcon(path+"Botoes/button_Menos.png"));
		b_distribuirExercito_MenosMundo.setBounds(520, 10, 33, 20);
		b_distribuirExercito_MenosMundo.addActionListener(new Evento());
		panel_distribuirExercito.add(b_distribuirExercito_MenosMundo);
		b_distribuirExercito_MaisMundo = new JButton(new ImageIcon(path+"Botoes/button_Mais.png"));
		b_distribuirExercito_MaisMundo.setBounds(620, 10, 33, 20);
		b_distribuirExercito_MaisMundo.addActionListener(new Evento());
		panel_distribuirExercito.add(b_distribuirExercito_MaisMundo);
		//---
		
		//---
		l = new JLabel("Exercitos para distribuir no continente:");
		l.setBounds(270, 15, 300, 100);
		panel_distribuirExercito.add(l);
		t_distribuirExercito_cont = new JTextField();
		t_distribuirExercito_cont.setBounds(575, 55, 25, 20);
		t_distribuirExercito_cont.setEditable(false);
		panel_distribuirExercito.add(t_distribuirExercito_cont);
		b_distribuirExercito_MenosCont = new JButton(new ImageIcon(path+"Botoes/button_Menos.png"));
		b_distribuirExercito_MenosCont.setBounds(520, 55, 33, 20);
		panel_distribuirExercito.add(b_distribuirExercito_MenosCont);
		b_distribuirExercito_MaisCont = new JButton(new ImageIcon(path+"Botoes/button_Mais.png"));
		b_distribuirExercito_MaisCont.setBounds(620, 55, 33, 20);
		panel_distribuirExercito.add(b_distribuirExercito_MaisCont);
		//---
	}
	public void Atualiza_AbaDistribuirExercito_cont(LinkedList<String> lista){
		combo_distribuirExercito_cont.removeAllItems();
		for(String s: lista)
			combo_distribuirExercito_cont.addItem(s);
	}
	public void Atualiza_AbaDistribuirExercito_qtdMundo(String qtd){
		qtdExercitosMundo = Integer.valueOf(qtd);
		t_distribuirExercito_mundo.setText(qtd);
	}
	public void Atualiza_AbaDistribuirExercito_qtdCont(String qtd){
		qtdExercitosCont = Integer.valueOf(qtd);
		t_distribuirExercito_cont.setText(qtd);
	}
	public void Atualiza_AbaDistribuirExercito_terr(LinkedList<String> lista){
		combo_distribuirExercito_terr.removeAllItems();
		for(String s: lista)
			combo_distribuirExercito_terr.addItem(s);
	}
	public void Atualiza_AbaDistribuirExercito_LigaDesligaBotoes(){
		if(podeDistribuir){
			b_distribuirExercito_Finalizar.setEnabled(true);
			b_distribuirExercito_Distribuir.setEnabled(true);
		}
		else{
			b_distribuirExercito_Finalizar.setEnabled(false);
			b_distribuirExercito_Distribuir.setEnabled(false);
		}
	}

	// -----------------------------------

	private void Criar_AbaDeslocarExercito(){
		panel_deslocarExercito = new JPanel();
		panel_deslocarExercito.setLayout(null);
		panel_deslocarExercito.setBackground(null);
		
		JLabel l;
		
		l = new JLabel("Deslocar exercito do pais:");
		l.setBounds(10, 0, 200, 18);
		panel_deslocarExercito.add(l);
		combo_deslocarExercito_de = new JComboBox();
		combo_deslocarExercito_de.setBounds(10, 15, 300, 20);
		combo_deslocarExercito_de.addActionListener(new Evento());
		panel_deslocarExercito.add(combo_deslocarExercito_de);
		
		l = new JLabel("Para:");
		l.setBounds(10, 40, 100, 18);
		panel_deslocarExercito.add(l);
		combo_deslocarExercito_para = new JComboBox();
		combo_deslocarExercito_para.setBounds(10, 55, 300, 20);
		combo_deslocarExercito_para.addActionListener(new Evento());
		panel_deslocarExercito.add(combo_deslocarExercito_para);
		
		l = new JLabel("Quantidade");
		l.setBounds(380, 20, 100, 18);
		panel_deslocarExercito.add(l);
		combo_deslocarExercito_qtd = new JComboBox();
		combo_deslocarExercito_qtd.setBounds(380, 35, 100, 20);
		combo_deslocarExercito_qtd.addActionListener(new Evento());
		panel_deslocarExercito.add(combo_deslocarExercito_qtd);
		
		b_deslocarExercito_deslocar = new JButton("Deslocar");
		b_deslocarExercito_deslocar.setBounds(550, 20, 130, 18);
		b_deslocarExercito_deslocar.addActionListener(new Evento());
		panel_deslocarExercito.add(b_deslocarExercito_deslocar);
		
		b_deslocarExercito_finalizarJogada = new JButton("Finalizar jogada");
		b_deslocarExercito_finalizarJogada.setBounds(550, 60, 130, 18);
		b_deslocarExercito_finalizarJogada.addActionListener(new Evento());
		panel_deslocarExercito.add(b_deslocarExercito_finalizarJogada);
	}
	public void Atualizar_AbaDeslocarExercito_LigaDesligaBotoes(){
		if(podeDeslocar){
			b_deslocarExercito_deslocar.setEnabled(true);
			b_deslocarExercito_finalizarJogada.setEnabled(true);
		}
		else{
			b_deslocarExercito_deslocar.setEnabled(false);
			b_deslocarExercito_finalizarJogada.setEnabled(false);
		}
	}
	public void Atualizar_AbaDeslocarExercito(LinkedList<String> listaTerritorios_de){
		combo_deslocarExercito_de.removeAllItems();
		combo_deslocarExercito_para.removeAllItems();
		combo_deslocarExercito_qtd.removeAllItems();
		for(String s: listaTerritorios_de)
			combo_deslocarExercito_de.addItem(s);
	}
	public void Atualizar_AbaDeslocarExercito(LinkedList<String> listaTerritorios_para, LinkedList<String> listaQtd){
		for(String s: listaTerritorios_para)
			combo_deslocarExercito_para.addItem(s);
		for(String s: listaQtd)
			combo_deslocarExercito_qtd.addItem(s);
	}

	// -----------------------------------
	
	private void Criar_AbaCartaTerritorio(){
		
		panel_cartaTerritorio = new JPanel();
		panel_cartaTerritorio.setLayout(null);
		panel_cartaTerritorio.setBackground(null);
		
		JLabel l_territorio = new JLabel("Carta territorio:");
		l_territorio.setBounds(10, 5, 100, 15);
		panel_cartaTerritorio.add(l_territorio);
		
		combo_cartaTerritorio = new JComboBox();
		combo_cartaTerritorio.setBounds(10, 20, 300, 20);
		combo_cartaTerritorio.addActionListener(new Evento());
		panel_cartaTerritorio.add(combo_cartaTerritorio);
		
		ImageIcon i_carta_territorio = new ImageIcon();
		l_carta_territorio = new JLabel(i_carta_territorio);
		l_carta_territorio.setBounds(500, 6, 63, 98);
		panel_cartaTerritorio.add(l_carta_territorio);
	}
	public void Atualiza_AbaCartaTerritorio(LinkedList<String> listaCartasTerritorio){
		combo_cartaTerritorio.removeAllItems();
		for(String s: listaCartasTerritorio){
			combo_cartaTerritorio.addItem(s);
			System.out.println(s);
		}
	}
	
	// -----------------------------------
	
	private void Criar_AbaCartaObjetivo(){
		panel_objetivo = new JPanel();
		panel_objetivo.setLayout(null);
		panel_objetivo.setBackground(null);
		
		JLabel l_territorio = new JLabel("Objetivo:");
		l_territorio.setBounds(10, 0, 100, 15);
		panel_objetivo.add(l_territorio);
		
		JScrollPane sp;
		t_objectivo = new JTextArea();
		t_objectivo.setLineWrap(true);
		t_objectivo.setWrapStyleWord(true);
		t_objectivo.setEditable(false);
		sp= new JScrollPane(t_objectivo);
		sp.setBounds(10, 15, 500, 90);
		panel_objetivo.add(sp);
		
		l_carta_objetivo = new JLabel();
		l_carta_objetivo.setBounds(600, 6, 62, 97);
		panel_objetivo.add(l_carta_objetivo);
	}
	public void Atualiza_AbaCartaObjetivo(String carta, String objetivo){
		t_objectivo.setText(objetivo);
		l_carta_objetivo.setIcon(new ImageIcon(path+"Objetivos/Objetivo"+carta+".png"));
	}
	
	// -----------------------------------

	private class Evento implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{	
			//ABA MEUS DADOS
			if(e.getSource().equals(b_meusDados_SairPartida)){
				Cliente.getCliente().SairPartida();
			}
			//---
			
			//ABA ATACAR
			else if(e.getSource().equals(b_atacar_atacar)){
				int i = Integer.valueOf((String)combo_atacar_qtdDados.getSelectedItem());
				if(i == 0)
					JOptionPane.showMessageDialog(c, "Nao pode atacar com apenas 1 exercito no territorio.");
				else
					controlador.Atacar(i);
				
				controlador.Atualiza_Tabuleiro();
			}
			else if(e.getSource().equals(b_atacar_limparAtaque)){
				t_atacar_serAtacado.setText("");
				t_atacar_atacar.setText("");
				combo_atacar_qtdDados.removeAll();
			}
			else if(e.getSource().equals(b_atacar_fizalizarAtaque)){
				controlador.Atualiza_Tabuleiro();
				tab.setSelectedComponent(panel_deslocarExercito);
				podeAtacar = false;
				podeDeslocar = true;
			}
			//---
			
			//ABA MEUS TERRITORIOS
			else if(e.getSource().equals(combo_territorio)){
				controlador.Atualiza_AbaMeusTerriorios((String)combo_territorio.getSelectedItem());
			}
			//---
			
			//ABA DESLOCAR EXERCITO
			else if(e.getSource().equals(b_deslocarExercito_finalizarJogada)){
				controlador.FinalizarJogada();
			}
			else if(e.getSource().equals(b_deslocarExercito_deslocar)){
				controlador.DeslocaExercito(
						(String)combo_deslocarExercito_de.getSelectedItem(),
						(String)combo_deslocarExercito_para.getSelectedItem(),
						(Integer)combo_deslocarExercito_qtd.getSelectedItem());
				combo_deslocarExercito_de.removeAllItems();
				combo_deslocarExercito_para.removeAllItems();
				combo_deslocarExercito_qtd.removeAllItems();
			}
			else if(e.getSource().equals(combo_deslocarExercito_de)){
				combo_deslocarExercito_para.removeAllItems();
				combo_deslocarExercito_qtd.removeAllItems();
				controlador.Atualiza_AbaDeslocarExercito((String)combo_deslocarExercito_de.getSelectedItem());
			}
			//---
			
			//ABA DISTRIBUIR EXERCITO
			else if(e.getSource().equals(combo_distribuirExercito_cont)){
				String t = (String)combo_distribuirExercito_cont.getSelectedItem();
				if(t != null){
					controlador.Atualiza_AbaDistribuirExercito_qtdMundo();
					controlador.Atualiza_AbaDistribuirExercito_terr(t);
					controlador.Atualiza_AbaDistribuirExercito_qtdCont(t);
				}
			}
			else if(e.getSource().equals(b_distribuirExercito_MaisMundo)){
				int qtd = Integer.valueOf(t_distribuirExercito_mundo.getText());
				if(qtd < qtdExercitosMundo)
					t_distribuirExercito_mundo.setText(String.valueOf(++qtd));
				System.out.println(t_distribuirExercito_mundo.getText());
			}
			else if(e.getSource().equals(b_distribuirExercito_MenosMundo)){
				int qtd = Integer.valueOf(t_distribuirExercito_mundo.getText());
				if(qtd > 0)
					t_distribuirExercito_mundo.setText(String.valueOf(--qtd));
			}
			else if(e.getSource().equals(b_distribuirExercito_MaisCont)){
				int qtd = Integer.valueOf(t_distribuirExercito_cont.getText());
				if(qtd < qtdExercitosCont)
					t_distribuirExercito_cont.setText(String.valueOf(++qtd));
			}
			else if(e.getSource().equals(b_distribuirExercito_MenosCont)){
				int qtd = Integer.valueOf(t_distribuirExercito_cont.getText());
				if(qtd > 0)
					t_distribuirExercito_cont.setText(String.valueOf(--qtd));
			}
			else if(e.getSource().equals(b_distribuirExercito_Distribuir)){

				String mundo = t_distribuirExercito_mundo.getText();
				String cont  = t_distribuirExercito_cont.getText();
				String t     = (String)combo_distribuirExercito_terr.getSelectedItem();
				
				qtdExercitosMundo -= Integer.valueOf(mundo);
				qtdExercitosCont  -= Integer.valueOf(cont);
				
				Atualiza_AbaDistribuirExercito_qtdMundo(String.valueOf(qtdExercitosMundo));
				Atualiza_AbaDistribuirExercito_qtdCont (String.valueOf(qtdExercitosCont));
				
				controlador.DistribuirExercito(t, mundo, cont);
				controlador.Atualiza_Tela_Jogo();

			}
			else if(e.getSource().equals(b_distribuirExercito_Finalizar)){
				tab.setSelectedComponent(panel_atacar);
				podeDistribuir = false;
				podeAtacar     = true;
				
				Atualiza_AbaDistribuirExercito_LigaDesligaBotoes();
				Atualizar_AbaAtacar_LigaDesligaBotoes();
			}
			//---
			
			//ABA CARTA TERRITORIO
			else if(e.getSource().equals(combo_cartaTerritorio)){
				String territorio = (String) combo_cartaTerritorio.getSelectedItem(); 
				System.out.println((String)combo_cartaTerritorio.getSelectedItem());
				String caminho = path+"Territorios/"+territorio+".png";
				l_carta_territorio.setIcon(new ImageIcon(caminho));
				l_carta_territorio.setVisible(true);
			}
			//---
		}
	}

	// -----------------------------------

	public void mouseClicked(MouseEvent e) {


		if(e.getButton() == MouseEvent.BUTTON3 && estado == EstadoJogador.CORRENTE){
			if(t_atacar_atacar.getText().equals("")){
				String p = controlador.PaisAtaca(e.getX(), e.getY());
				if(p != null)
					t_atacar_atacar.setText(p);
			}
			else if(t_atacar_serAtacado.getText().equals("")){
				String p = controlador.PaisSerAtacado(e.getX(), e.getY());
				if(p != null)
					t_atacar_serAtacado.setText(p);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON1)
			controlador.ConsultarTerritorio(e.getX(), e.getY());


	}

	// -----------------------------------
	
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	// -----------------------------------
	
	public void AtualizarCartaTerritorios(String[] a){
		combo_cartaTerritorio = new JComboBox(a);
		l_carta_territorio.setVisible(false);
	}
	
	// -----------------------------------
	
	public void AtualizaObjetivo(int id, String objetivo){
		String p = path+"Objetivos/Objetivo"+id+".png";
		l_carta_objetivo.setIcon(new ImageIcon(p));
		t_objectivo.setText(objetivo);
	}
	
	// -----------------------------------
	
	public void AtualizaLog(String s){
		t_log.setText(t_log.getText() + "\n" + s);
	}
	
	// -----------------------------------
	
	public void AtualizaEstadoJogador(EstadoJogador e){
		
		estado = e;
		
		if(e == EstadoJogador.CORRENTE)
			podeDistribuir = true;
		
		tab.setSelectedComponent(panel_distribuirExercito);
	
		controlador.Atualiza_Tela_Jogo();
	}
	
	// -----------------------------------
	
}
