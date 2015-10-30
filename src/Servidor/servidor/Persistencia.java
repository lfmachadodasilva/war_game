/**
 * Classe que efetua a persistência de dados do servidor.
 * Para leitura de dados ser efetuada corretamente é necessário efetuar a seguinte ordem nas chamadas dos métodos:
 * leContinente -> leTerritorio -> leFronteira -> leCartaObjetivo -> leCartaTerritorio.
 */
package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import comum.CartaObjetivo;
import comum.CartaTerritorio;
import comum.Continente;
import comum.Territorio;

public class Persistencia {
	
	/*
	 * Variáveis de instância
	 */
	
	private BufferedReader in ;
	
	
	/*
	 * Métodos públicos
	 */
	
	public void leContinente( String caminho ){
		
		LinkedList<Continente> listaContinente = Servidor.getServidor().getListaContinentes() ;
		
		try{
			
			// Abre o fluxo de leitura com o arquivo
			in = new BufferedReader( new FileReader( new File( caminho ) ) ) ;
			
			String nomeContinente ;
			
			// Le o nome do continente no arquivo enquanto existir
			while( ( nomeContinente = in.readLine() ) != null ){
				
				// Le o número de territorios daquele continente
				String aux = in.readLine() ;
				int numTerritorios = Integer.parseInt( aux ) ;
				
				// Adiciona um novo continente a lista de continentes do servidor
				Continente continente = new Continente( nomeContinente , numTerritorios ) ;
				listaContinente.add( continente ) ;
				
			}
			
			// Fecha o fluxo de leitura com o arquivo
			in.close() ;
			
			Servidor.getServidor().escreveLogServidor("Persistencia/leContinente: Dados Carregados") ;
			
		}
		catch( Exception e ){
			
			Servidor.getServidor().escreveLogServidor("Erro Persistencia/leContinente: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}

	}
	
	public void leTerritorio( String caminho ){
		
		// Pega o servidor ( Padrao singleton )
		Servidor servidor = Servidor.getServidor() ;
		
		LinkedList<Territorio> listaTerritorio = servidor.getListaTerritorios() ;
		
		try{
			
			// Abre o fluxo de leitura com o arquivo
			in = new BufferedReader( new FileReader( new File( caminho ) ) ) ;
			
			String nomeTerritorio ;
			
			// Le o nome do territorio no arquivo enquanto existir
			while( ( nomeTerritorio = in.readLine() ) != null ){
				
				// Le o nome do continente no arquivo e pega o objeto na lista do servidor
				String nomeContinente = in.readLine() ;
				Continente continente = servidor.buscaContinente( nomeContinente ) ;
				if( continente == null ){
					System.out.println( "Erro leTerritorio: Continente não existe" ) ;
				}
				
				// Cria o territorio
				Territorio territorioAux = new Territorio( nomeTerritorio , continente ) ;
				
				// Adiciona o territorio a lista de territorios pertencentes ao continente
				continente.addTerritorioPertencenteContinente( territorioAux ) ;
				
				// Adiciona o territorio a lista de territorios do servidor
				listaTerritorio.add( territorioAux ) ;
				
			}
			
			// Fecha fluxo de leitura com o arquivo
			in.close() ;
			
			Servidor.getServidor().escreveLogServidor("Persistencia/leTerritorio: Dados Carregados") ;
			
		}
		catch( Exception e ){
			
			Servidor.getServidor().escreveLogServidor("Erro Persistencia/leTerritorio: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}
		
	}

	public void leFronteira( String caminho ){
		
		// Pega o servidor ( Padrao singleton )
		Servidor servidor = Servidor.getServidor() ;
		
		try{
			
			// Abre o fluxo de leitura com o arquivo
			in = new BufferedReader ( new FileReader ( new File( caminho ) ) ) ;
			
			String nomeTerritorio ;
			
			// Le o nome do territorio que possui fronteiras do arquivo enquanto existir
			while( ( nomeTerritorio = in.readLine() ) != null ){
				
				// Busca o objeto territorio da lista de territorios do servidor
				Territorio territorioAux = servidor.buscaTerritorio( nomeTerritorio ) ;
				if( territorioAux == null ){
					System.out.println( "Erro leFronteira: Territorio não existe." ) ;
				}
				
				// Le o número de fronteiras desse territorio
				String n = in.readLine() ;
				int numFronteiras = Integer.parseInt( n ) ;
				
				// Enquanto existir fronteiras
				for( int i = 0 ; i < numFronteiras ; i++ ){
					
					// Pega o nome da fronteira do arquivo
					String nomeFronteira = in.readLine() ;
					
					// Busca o objeto na lista de territorios do servidor
					Territorio fronteira = servidor.buscaTerritorio( nomeFronteira ) ;
					if( fronteira == null ){
						System.out.println( "Erro leFronteira: Fronteira não existe." ) ;
					}
					
					// Adiciona fronteira na lista de fronteiras do territorio 
					territorioAux.addFronteiraTerritorio( fronteira ) ;
					
				}
				
			}
			
			// Fecha o fluxo de leitura com o arquivo
			in.close() ;
			
			Servidor.getServidor().escreveLogServidor("Persistencia/leFronteira: Dados Carregados") ;
			
		}
		catch( Exception e ){
			
			Servidor.getServidor().escreveLogServidor("Erro Persistencia/leFronteira: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	public void leCartaObjetivo( String caminho ){
		
		// Pega a lista de cartas objetivos do servidor
		LinkedList<CartaObjetivo> listaCartaObjetivo = Servidor.getServidor().getListaCartaObjetivoNaoUsada() ;
		
		try{
			
			// Abre o fluxo de leitura com o arquivo
			in = new BufferedReader( new FileReader( new File( caminho ) ) ) ;
			
			String n ;
			
			// Le as cartas objetivos enquando existir
			while( ( n = in.readLine() ) != null ){
				
				// Pega os dados de cada carta objetivo
				int num = Integer.parseInt(n) ;
				String objetivo = in.readLine() ;
				
				// Adiciona a carta objetivo na lista do servidor
				CartaObjetivo cartaObjetivo = new CartaObjetivo( num , objetivo ) ;
				listaCartaObjetivo.add( cartaObjetivo ) ;
				
			}
			
			// Fecha fluxo de leitura com o arquivo
			in.close() ;
			
			Servidor.getServidor().escreveLogServidor("Persistencia/leCartaObjetivo: Dados Carregados") ;
		}
		catch( Exception e ){
			
			Servidor.getServidor().escreveLogServidor("Erro Persistencia/leCartaObjetivo: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	public void leCartaTerritorio( String caminho ){
		
		// Pega o servidor ( padrão Singleton )
		Servidor servidor = Servidor.getServidor() ;
		
		// Pega a lista de cartas territorios do servidor
		LinkedList<CartaTerritorio> listaCartaTerritorio = servidor.getListaCartaTerritorioNaoUsada() ;
		
		int num = 0 ;
		
		try{
			
			// Abre o fluxo de leitura com o arquivo
			in = new BufferedReader( new FileReader( new File( caminho ) ) ) ;
			
			String n ;
			
			// Le carta territorio enquanto existir
			while( ( n = in.readLine() ) != null ){
				
				// Pega os dados de cada carta
				num = Integer.parseInt(n) ;
				String territorio = in.readLine() ;
				String figura = in.readLine() ;
				
				// Busca o territorio na lista de territorios do servidor
				Territorio terr = servidor.buscaTerritorio( territorio ) ;
				if( terr == null ){
					System.out.println( "Erro leCartaTerritorio: territorio nao existe") ;
				}
				
				// Adiciona a carta territorio na lista de cartas territorios do servidor
				CartaTerritorio cartaTerritorio = new CartaTerritorio( num , figura , terr ) ;	
				listaCartaTerritorio.add( cartaTerritorio ) ;
				
			}
			
			// Adiciona as cartas curingas
			for( int i = 0 ; i < 2 ; i++ ){
				
				num++ ;
				CartaTerritorio carta = new CartaTerritorio( num , null , null ) ;
				carta.setCartaCuringa( true ) ;
				listaCartaTerritorio.addLast( carta ) ;
				
			}
			
			// Fecha o fluxo de leitura com o arquivo
			in.close();
			
			Servidor.getServidor().escreveLogServidor("Persistencia/leCartaTerritorio: Dados Carregados") ;
			
		}
		catch( Exception e ){
			
			Servidor.getServidor().escreveLogServidor("Erro Persistencia/leCartaTerritorio: " + e.getMessage() ) ;
			e.printStackTrace() ;
			
		}
		
	}
	
	// -------------------------------------------

	public void leCoordenadasTerritorio( String caminho ){
		
		try{

			// Abre o fluxo de leitura com o arquivo
			in = new BufferedReader( new FileReader( new File( caminho ) ) ) ;

			String n ;

			while( ( n = in.readLine() ) != null){

				Territorio t = Servidor.getServidor().buscaTerritorio(n);			
				if(t == null){
					System.out.println( "Erro leCoordenadaTerritorio: territorio nao existe") ;
					return;
				}
	
				t.addCoordenadasX(Integer.valueOf(in.readLine()),
						Integer.valueOf(in.readLine()), 
						Integer.valueOf(in.readLine()), 
						Integer.valueOf(in.readLine()));
				t.addCoordenadasY(Integer.valueOf(in.readLine()),
						Integer.valueOf(in.readLine()), 
						Integer.valueOf(in.readLine()), 
						Integer.valueOf(in.readLine()));
			}
			
			in.close();
		}
		catch( Exception e ){
			Servidor.getServidor().escreveLogServidor("Erro Persistencia/leTerritorioCoordenadas: " + e.getMessage() ) ;
			e.printStackTrace() ;
		}
			
			
			
	}
	
	// -------------------------------------------
	
	/*
	 * Fim da Classe
	 */
}
