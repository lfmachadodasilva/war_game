package gui;

import javax.swing.*;
import java.awt.*;

public class PapelParede extends JPanel{
	
	/*
	 * Variáveis de instância
	 */
	
	private static final long serialVersionUID = 216818864149154304L;
	
	private Image img;

	
	/*
	 * Construtores
	 */
	
	public PapelParede(Image img)
	{
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize  (size);  
		setMaximumSize  (size);
		setSize         (size);
		setLayout       (null);
	}

	
	/*
	 * Métodos públicos
	 */
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(img, 0, 0, null);
	}
	
	
	/*
	 * Fim da classe
	 */

}
