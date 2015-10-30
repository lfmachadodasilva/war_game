package GUI;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import comum.PapelDeParede;

public class Tela_Conecta extends JFrame 
{	
	private static final long serialVersionUID = -8177085984587316655L;
	public JTextField t_IpServidor;
	public JTextField t_PortaServidor;
	
	private JButton b_IniciarJogo;
	private JButton b_RegrasDoJogo;
	private JButton b_Sair;
	
	Controlador_Tela_Conecta controlador;
	
	private Container c;
	
	private String path;
	
	public Tela_Conecta(String p, Controlador_Tela_Conecta controlador)
	{
		super("WAR  -  Tela Inicial");
		
		path = p;
		
		setLayout(null);
		
		c = getContentPane();
		
		this.controlador = controlador;
		
		JLabel l_IpServidor = new JLabel("Ip: ");
		l_IpServidor.setBounds(15, 120, 20, 20);
		c.add(l_IpServidor);
		
		t_IpServidor = new JTextField();
		t_IpServidor.setBounds(32, 120, 120, 18);
		t_IpServidor.setText("localhost");
		c.add(t_IpServidor);
		
		JLabel l_PortaServidor = new JLabel("Porta: ");
		l_PortaServidor.setBounds(160, 120, 50, 20);
		c.add(l_PortaServidor);
		
		t_PortaServidor = new JTextField();
		t_PortaServidor.setBounds(198, 120, 50, 18);
		t_PortaServidor.setText("139");
		c.add(t_PortaServidor);
		
		/* Botao Iniciar Jogo */
		ImageIcon i_IniciarJogo = new ImageIcon(path+"Botoes/button_IniciarJogo.png"); //50% - 60%
		b_IniciarJogo           = new JButton(i_IniciarJogo);
		b_IniciarJogo.setBounds(200, 185, i_IniciarJogo.getIconWidth(), i_IniciarJogo.getIconHeight());
		b_IniciarJogo.addActionListener(new Evento());
		c.add(b_IniciarJogo);
		
		/* Botao Regras do Jogo */
		ImageIcon i_RegrasDoJogo = new ImageIcon(path+"Botoes/button_RegrasDoJogo.png");
		b_RegrasDoJogo           = new JButton(i_RegrasDoJogo);
		b_RegrasDoJogo.setBounds(100, 270, i_RegrasDoJogo.getIconWidth(), i_RegrasDoJogo.getIconHeight());
		b_RegrasDoJogo.addActionListener(new Evento());
		c.add(b_RegrasDoJogo);
		
		/* Botao Sair */
		ImageIcon i_Sair = new ImageIcon(path+"Botoes/button_Sair.png");
		b_Sair           = new JButton(i_Sair);
		b_Sair.setBounds(300, 270, i_Sair.getIconWidth(), i_Sair.getIconHeight());
		b_Sair.addActionListener(new Evento());
		c.add(b_Sair);
		
		/* Papel de parede */ 
		ImageIcon iPapelDeParede = new ImageIcon(path+"papelDeParede.jpg");
		PapelDeParede papelDeParede = new PapelDeParede(iPapelDeParede.getImage());
		c.add(papelDeParede);
		
		this.setResizable(false);
		this.setSize(iPapelDeParede.getIconWidth(), iPapelDeParede.getIconHeight());
		this.setResizable(false);
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
			if(e.getSource().equals(b_IniciarJogo)){
				if(t_IpServidor.getText().equals("") || t_PortaServidor.getText().equals("") )
					JOptionPane.showMessageDialog(c, "Necessario digitar o ip e porta do servidor.");
				else //Conecta cliente no servidor.
					controlador.Conecta(t_IpServidor.getText(), t_PortaServidor.getText());
			}
			else if(e.getSource().equals(b_RegrasDoJogo)){
				controlador.Regras();
			}
			else if(e.getSource().equals(b_Sair)){
				System.exit(EXIT_ON_CLOSE);
			}
		}
	}
}

