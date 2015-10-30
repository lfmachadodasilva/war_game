package comum;
import java.awt.*;
import javax.swing.*;

public class PapelDeParede extends JPanel 
{
	private static final long serialVersionUID = 216818864149154304L;
	private Image img;

	public PapelDeParede(Image img)
	{
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize  (size);  
		setMaximumSize  (size);
		setSize         (size);
		setLayout       (null);
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(img, 0, 0, null);
	}

}