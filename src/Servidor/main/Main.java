package main;
import servidor.Servidor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Servidor servidor = Servidor.getServidor() ;
		
		servidor.carregaDadosServidor() ;
		servidor.abrePainelPorta() ;
		

	}

}
