package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import comum.Jogador;
import comum.PapelDeParede;

public class Tela_Espera_Jogo extends JFrame {

	private static final long serialVersionUID = 3047422597769078339L;
	
	private Container c;
	
	private JTextArea t_conectados;
	
	private JButton b_iniciaJogo;
	private JButton b_sairListaEspera;
	
	private Controlador_Tela_Espera_Jogo controlador;
	
	private String path;
	
	protected Tela_Espera_Jogo(String p, Controlador_Tela_Espera_Jogo controlador){
		
		super("WAR - Espera Jogo");
		path = p;
		setLayout(null);		
		c = getContentPane();

		JScrollPane sp;
		
		this.controlador = controlador;

		t_conectados = new JTextArea();
		t_conectados.setBounds(10, 140, 250, 200);
		t_conectados.setLineWrap(true);
		t_conectados.setWrapStyleWord(true);
		t_conectados.setEditable(false);
		t_conectados.setBackground(Color.black);
		t_conectados.setForeground(Color.white);
		t_conectados.setText("Vazio");
		sp= new JScrollPane(t_conectados);
		sp.setBounds(10, 140, 250, 200);
		c.add(sp);
		
		ImageIcon i_IniciarJogo = new ImageIcon(path+"Botoes/button_IniciarJogo.png"); //50% - 60%
		b_iniciaJogo            = new JButton(i_IniciarJogo);
		b_iniciaJogo.setBounds(330, 170, i_IniciarJogo.getIconWidth(), i_IniciarJogo.getIconHeight());
		b_iniciaJogo.addActionListener(new Evento());
		c.add(b_iniciaJogo);
		
		ImageIcon i_sairListaEspera = new ImageIcon(path+"Botoes/button_ListaDeEsperaSair.png");
		b_sairListaEspera           = new JButton(i_sairListaEspera);
		b_sairListaEspera.setBounds(330, 250, i_sairListaEspera.getIconWidth(), i_sairListaEspera.getIconHeight());
		b_sairListaEspera.addActionListener(new Evento());
		c.add(b_sairListaEspera);

		/* Papel de parede */ 
		ImageIcon iPapelDeParede = new ImageIcon(path+"papelDeParede.jpg");
		PapelDeParede papelDeParede = new PapelDeParede(iPapelDeParede.getImage());
		c.add(papelDeParede);
		
		this.setSize(iPapelDeParede.getIconWidth(), iPapelDeParede.getIconHeight());
		this.setResizable(false);
		this.setIconImage(new ImageIcon(path+"Tanque.png").getImage());
		Toolkit kit = Toolkit.getDefaultToolkit();  
		Dimension tamanhoTela = kit.getScreenSize(); 
		this.setLocation((tamanhoTela.width-iPapelDeParede.getIconWidth())/2,(tamanhoTela.height-iPapelDeParede.getIconHeight())/2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// -------------------------------
	
	public void AtualizaCadastrados(LinkedList<Jogador> listaJogadores){
		t_conectados.setText("Vazio");
		for(Jogador j: listaJogadores){
			if(t_conectados.getText().equals("Vazio")){
				String s = j.getApelidoJogador() + "    -    " + j.getExercitoJogador();
				t_conectados.setText(s);
			}
			else
			{
				String s = "\n" + j.getApelidoJogador() + "    -    " + j.getExercitoJogador();
				t_conectados.setText(t_conectados.getText() + s);
			}
		}
	}
	
	// -------------------------------
	
	private class Evento implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{	
			if(e.getSource().equals(b_sairListaEspera)){
				controlador.SairListaEspera();
			}
			else if(e.getSource().equals(b_iniciaJogo)){
				controlador.SolicitarInicioPartida();
			}
		}
	}

}
