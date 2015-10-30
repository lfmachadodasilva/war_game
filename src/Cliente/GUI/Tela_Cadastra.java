package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import comum.PapelDeParede;

public class Tela_Cadastra extends JFrame 
{
	private static final long serialVersionUID = -3313152890596427305L;
	private JButton b_Azul;
	private JButton b_Vermelho;
	private JButton b_Preto;
	private JButton b_Branco;
	private JButton b_Verde;
	private JButton b_Amarelo;
	
	private String corExercito = "";
	
	private JButton b_ListaDeEsperaEntrar;
	private JButton b_CarregarJogo;
	private JButton b_Sair;
	
	private JTextField t_apelido;
	
	private String path;

	private JLabel l_Soldado;
	
	private Controlador_Tela_Cadastra controlador;
	
	private Container c;
	
	protected Tela_Cadastra (String p, Controlador_Tela_Cadastra controlador)
	{
		super("War  -  Cadastro");
		
		path = p;
		
		c = getContentPane();
		
		this.controlador = controlador;
		
		JLabel l_nick = new JLabel("Apelido: ");
		l_nick.setBounds(15 , 120 , 100 , 20);
		c.add(l_nick);
		
		t_apelido = new JTextField();
		t_apelido.setBounds(68 , 120 , 200 , 18);
		c.add(t_apelido); 

		JLabel l_CorDoExercito = new JLabel("Cor do Exercito: ");
		l_CorDoExercito.setBounds(30 , 155 , 120 , 20);
		c.add(l_CorDoExercito);
		
		ImageIcon i_Azul = new ImageIcon(path+"Botoes/button_Azul.png"); //20%
		b_Azul   = new JButton(i_Azul);
		b_Azul.setBounds(30 , 190 , i_Azul.getIconWidth() , i_Azul.getIconHeight());
		b_Azul.addActionListener(new Evento());
		c.add(b_Azul);
		
		ImageIcon i_Vermelho = new ImageIcon(path+"Botoes/button_Vermelho.png"); 
		b_Vermelho   = new JButton(i_Vermelho);
		b_Vermelho.setBounds(150 , 190 , i_Vermelho.getIconWidth() , i_Vermelho.getIconHeight());
		b_Vermelho.addActionListener(new Evento());
		c.add(b_Vermelho);
		
		ImageIcon i_Verde = new ImageIcon(path+"Botoes/button_Verde.png"); 
		b_Verde = new JButton(i_Verde);
		b_Verde.setBounds(30 , 240 , i_Verde.getIconWidth() , i_Verde.getIconHeight());
		b_Verde.addActionListener(new Evento());
		c.add(b_Verde);
		
		ImageIcon i_Amarelo = new ImageIcon(path+"Botoes/button_Amarelo.png"); 
		b_Amarelo = new JButton(i_Amarelo);
		b_Amarelo.setBounds(150 , 240 , i_Amarelo.getIconWidth() , i_Amarelo.getIconHeight());
		b_Amarelo.addActionListener(new Evento());
		c.add(b_Amarelo);
		
		ImageIcon i_Preto = new ImageIcon(path+"Botoes/button_Preto.png"); 
		b_Preto = new JButton(i_Preto);
		b_Preto.setBounds(30 , 290 , i_Preto.getIconWidth() , i_Preto.getIconHeight());
		b_Preto.addActionListener(new Evento());
		c.add(b_Preto);
		
		ImageIcon i_Branco = new ImageIcon(path+"Botoes/button_Branco.png"); 
		b_Branco = new JButton(i_Branco);
		b_Branco.setBounds(150 , 290 , i_Branco.getIconWidth() , i_Branco.getIconHeight());
		b_Branco.addActionListener(new Evento());
		c.add(b_Branco);
		
		ImageIcon i_Soldado = new ImageIcon(path+"Soldados/Soldado_Vermelho.png");
		l_Soldado = new JLabel(i_Soldado);
		l_Soldado.setBounds(350 , 160 , i_Soldado.getIconWidth() , i_Soldado.getIconHeight());
		l_Soldado.setVisible(false);
		c.add(l_Soldado);
			
		/* Botao Carregar Jogo */
		ImageIcon i_CarregarJogo = new ImageIcon(path+"Botoes/button_CarregarJogo.png");
		b_CarregarJogo           = new JButton(i_CarregarJogo);
		b_CarregarJogo.setBounds(380, 340, i_CarregarJogo.getIconWidth(), i_CarregarJogo.getIconHeight());
		b_CarregarJogo.addActionListener(new Evento());
		c.add(b_CarregarJogo);
		
		/* Botao Entrar na lista de espera */
		ImageIcon i_ListaDeEsperaEntrar = new ImageIcon(path+"Botoes/button_ListaDeEsperaEntrar.png"); 
		b_ListaDeEsperaEntrar = new JButton(i_ListaDeEsperaEntrar);
		b_ListaDeEsperaEntrar.setBounds(200 , 340 , i_ListaDeEsperaEntrar.getIconWidth() , i_ListaDeEsperaEntrar.getIconHeight());
		b_ListaDeEsperaEntrar.addActionListener(new Evento());
		c.add(b_ListaDeEsperaEntrar);
		
		/* Botao Sair */
		ImageIcon i_Sair = new ImageIcon(path+"Botoes/button_Sair.png");
		b_Sair           = new JButton(i_Sair);
		b_Sair.setBounds(20, 340, i_Sair.getIconWidth(), i_Sair.getIconHeight());
		b_Sair.addActionListener(new Evento());
		c.add(b_Sair);
		
		ImageIcon iPapelDeParede = new ImageIcon(path+"papelDeParede.jpg");
		PapelDeParede papelDeParede = new PapelDeParede(iPapelDeParede.getImage());
		c.add(papelDeParede);
		
		this.setResizable(false);
		this.setSize(iPapelDeParede.getIconWidth() , iPapelDeParede.getIconHeight());
		this.setIconImage(new ImageIcon(path+"Tanque.png").getImage());
		Toolkit kit = Toolkit.getDefaultToolkit();  
		Dimension tamanhoTela = kit.getScreenSize(); 
		this.setLocation((tamanhoTela.width-iPapelDeParede.getIconWidth())/2,(tamanhoTela.height-iPapelDeParede.getIconHeight())/2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private class Evento implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{	
			if(e.getSource().equals(b_ListaDeEsperaEntrar)){
	
				String apelido = t_apelido.getText();
				if(apelido.length() > 8){
					JOptionPane.showMessageDialog(c, "Tamanho do apelido muito grande.");
					return;
				}
				if(apelido.length() < 3){
					JOptionPane.showMessageDialog(c, "Tamanho do apelido muito pequeno.");
					return;
				}
				if(corExercito.equals("")){
					JOptionPane.showMessageDialog(c, "Favor escolher uma cor de exercito.");
					return;
				}
				//Cadastra cliente.//
				controlador.Cadastra(apelido, corExercito);
			}
			else if(e.getSource().equals(b_Sair)){
				System.exit(EXIT_ON_CLOSE);
			}
			else{
				l_Soldado.setVisible(true);
				if(e.getSource().equals(b_Preto)){
					l_Soldado.setIcon(new ImageIcon(path+"Soldados/Soldado_Preto.png"));    
					corExercito = "Preto";
				}
				else if(e.getSource().equals(b_Branco)){
					l_Soldado.setIcon(new ImageIcon(path+"Soldados/Soldado_Branco.png"));   
					corExercito = "Branco";
				}
				else if(e.getSource().equals(b_Amarelo)){
					l_Soldado.setIcon(new ImageIcon(path+"Soldados/Soldado_Amarelo.png"));  
					corExercito = "Amarelo";
				}
				else if(e.getSource().equals(b_Azul)){
					l_Soldado.setIcon(new ImageIcon(path+"Soldados/Soldado_Azul.png"));     
					corExercito = "Azul";
				}
				else if(e.getSource().equals(b_Vermelho)){
					l_Soldado.setIcon(new ImageIcon(path+"Soldados/Soldado_Vermelho.png")); 
					corExercito = "Vermelho";
				}
				else if(e.getSource().equals(b_Verde)){
					l_Soldado.setIcon(new ImageIcon(path+"Soldados/Soldado_Verde.png"));    
					corExercito = "Verde";
				}
			}
		}
	}
}
