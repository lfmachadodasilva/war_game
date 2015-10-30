package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PainelPorta {
	
	/*
	 * Variáveis de instância
	 */

	private ControladorPainelPorta controlador ;
	
	private JFrame frame ;
	
	private JPanel panel ;
	
	private JTextField textField ;
	
	private JLabel label ;
	
	private JButton button ;
	
	
	/*
	 * Construtores
	 */
	
	public PainelPorta( ControladorPainelPorta controlador ){
		
		Toolkit kit = Toolkit.getDefaultToolkit();  
		Dimension tamanhoTela = kit.getScreenSize(); 
		
		this.controlador = controlador ;
		
		frame = new JFrame( "War - Servidor" ) ;
		frame.setBounds( ( tamanhoTela.width - 250 )/2 , (tamanhoTela.height - 150 )/2 , 320 , 150 ) ;
		frame.setResizable(false) ;
		frame.setIconImage(new ImageIcon("figuras/Tanque.png").getImage());
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		
		
		panel = new JPanel() ;
		panel.setLayout( null ) ;
		
		textField = new JTextField() ;
		textField.setBounds(100, 20, 150, 30) ;
		textField.setText("139");
		textField.addActionListener( new EventoBotao() );
		
		label = new JLabel("Porta: ") ;
		label.setBounds(50, 20, 150, 30) ;
		
		button = new JButton("Ligar Servidor") ;
		button.addActionListener( new EventoBotao() ) ;
		button.setBounds(50, 60, 200, 30) ;
		
		panel.add(label) ;
		panel.add(textField) ;
		panel.add(button) ;
		
		frame.add(panel) ;

	}
	
	
	/*
	 * Métodos públicos
	 */
	
	public void setVisible( boolean cond ){
		
		frame.setVisible(cond) ;
		
	}
	
	
	/*
	 * Classe interna
	 */
	
	public class EventoBotao implements ActionListener{

		public void actionPerformed( ActionEvent e ){

			String porta = textField.getText() ;

			if( porta.equals( "" ) ){
				
				JOptionPane.showMessageDialog( frame , "Escolha uma porta antes de ligar o servidor!" ) ;
				button.setFocusable( false ) ;
				
			}

			else{

				if( porta.contains( " " ) ){
					
					JOptionPane.showMessageDialog( frame , "A porta não pode conter campos em branco!" ) ;
					button.setFocusable( false ) ;
					
				}

				else{

					boolean resposta = controlador.trataConexao( porta ) ;

					if( resposta == true ){
						controlador.fechaPainelPorta() ;
					}

					else{
						
						JOptionPane.showMessageDialog( frame , "Esta porta já está sendo usada, tente outra." , "ERRO" , JOptionPane.ERROR_MESSAGE ) ;
						textField.setText( "" ) ;
						button.setFocusable( false );
						
					}

				}
				
			}
			
			button.setFocusable( true ) ;
			textField.setText( null ) ;

		}

		/*
		 * Fim da Classe
		 */

	}
	
	
	/*
	 * Fim da Classe
	 */
	
}
