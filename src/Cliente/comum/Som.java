package comum;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JOptionPane;

public class Som 
{	
	static private Clip clip;
	public Som( String som , int qtd ) 
	{
		try{
			File soundFile = new File( som );
			AudioInputStream sound = AudioSystem.getAudioInputStream( soundFile ) ;
			DataLine.Info info = new DataLine.Info(Clip.class,sound.getFormat( ) ) ;
			clip = ( Clip )AudioSystem.getLine( info ) ;
			clip.close( );
			clip.open( sound ) ;
			clip.loop( qtd ) ;
			clip.flush();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Erro - som.");
			e.getStackTrace();
		}
	}
	public void stop( )
	{
		clip.stop( ) ;
		clip.close( ) ;
	}
	public void start( )
	{
		clip.start( ) ;
	}
	

}
