package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class PainelServidor{
	
	/*
	 * Variáveis de instância
	 */
	
	private JFrame frame ;
	
	private JPanel painel ;

	private JTextArea textAreaCliente ;
	private JLabel labelCliente ;
	
	private JTextArea textAreaLog;
	private JLabel labelLog ;

	private JButton button ;
	
	
	/*
	 * Construtores
	 */

	public PainelServidor( ControladorPainelServidor controlador ){
		
		// Configura o frame
		frame = new JFrame( "War - Servidor" ) ;
		frame.setResizable(false) ;
		frame.setIconImage(new ImageIcon("figuras/Tanque.png").getImage());
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		
		
		// Configura o painel
		ImageIcon icon = new ImageIcon("figuras/papelDeParede.jpg");
		painel = new PapelParede( icon.getImage() ) ;
		painel.setLayout(null) ;
		
		// Configura o label
		labelCliente = new JLabel("Jogadores conectados: ");
		labelCliente.setBounds(10, 110, 200, 25);
		painel.add(labelCliente) ;

		// Configura o textArea dos clientes conectados
		textAreaCliente = new JTextArea();
		textAreaCliente.setBounds(10, 140, 275, 200);
		textAreaCliente.setLineWrap(true);
		textAreaCliente.setWrapStyleWord(true);
		textAreaCliente.setEditable(false);
		textAreaCliente.setBackground(Color.black);
		textAreaCliente.setForeground(Color.white);
		
		JScrollPane spCliente = new JScrollPane(textAreaCliente);
		spCliente.setBounds(10, 140, 250, 200);
		painel.add(spCliente) ;
	
		// Configura o textArea dos Logs
		labelLog = new JLabel("Log: ");
		labelLog.setBounds(275, 110, 100, 25);
		painel.add(labelLog) ;

		textAreaLog = new JTextArea();
		textAreaLog.setBounds(275, 140, 275, 200);
		textAreaLog.setLineWrap(false);
		textAreaLog.setWrapStyleWord(true);
		textAreaLog.setEditable(false);
		textAreaLog.setBackground(Color.black);
		textAreaLog.setForeground(Color.white);
		
		JScrollPane spLog = new JScrollPane(textAreaLog);
		spLog.setBounds(275, 140, 275, 200);
		painel.add(spLog) ;

		// Configura o button
		button = new JButton("Desligar servidor");
		button.setBounds(10, 345, 540, 25);
		button.setBackground(Color.black) ;
		button.setForeground(Color.white) ;
		button.addActionListener(new Evento());
		painel.add(button) ;

		// Configura localização do frame
		Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize(); 
		frame.setLocation( ( tamanhoTela.width  - icon.getIconWidth() )/2 , ( tamanhoTela.height - icon.getIconHeight() )/2 );
		frame.setSize(icon.getIconWidth(), icon.getIconHeight()) ;
		frame.add(painel) ;
		
	}
	
	
	/*
	 * Métodos públicos
	 */
	
	public void setVisible( boolean cond ){
		frame.setVisible(cond) ;
	}
	
	public void escreveJogadoresConectados( String texto ){
		
		textAreaCliente.append( texto + "\n" ) ;
		
	}
	
	public void escreveLogServidor( String texto ){
		
		textAreaLog.append( texto + "\n" ) ;
		
	}
	
	
	/*
	 * Classe interna
	 */
	
	private class Evento implements ActionListener{

		public void actionPerformed( ActionEvent e ){
			
			int resp = JOptionPane.showConfirmDialog(frame, "Deseja realmente desligar o servidor?", "Desliga Servidor", JOptionPane.OK_CANCEL_OPTION) ;
			if( resp == JOptionPane.OK_OPTION )
				System.exit(0) ;
			
		}
		
	}
	
	
	/*
	 * Fim da Classe
	 */

}
