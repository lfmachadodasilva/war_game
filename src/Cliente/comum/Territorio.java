package comum;

import java.io.Serializable;
import java.util.LinkedList;

public class Territorio implements Serializable {

	private static final long serialVersionUID = 6283201172112622574L;

	private String 					nomeTerritorio 						;
	
	private LinkedList<Territorio>	fronteirasTerritorio 				;
	
	private Continente 				continenteHaQuePertenceTerritorio	;
	
	private int						numExercito							;
	
	private Coordenadas             coordenadas							;
	
	private String                  exercitoDominante   				;
	
	// --------------------------------------------
	
	public Territorio( String nomeTerritorio , Continente continenteHaQuePertenceTerritorio){
		
		this.nomeTerritorio 					= nomeTerritorio					;
		
		this.continenteHaQuePertenceTerritorio 	= continenteHaQuePertenceTerritorio ;
		
		fronteirasTerritorio 					= new LinkedList<Territorio>() 	    ;
		
		numExercito								= 0 								;
		
		coordenadas								= new Coordenadas()					;
		
		exercitoDominante						= "Vazio"							;
		
	}
	
	// --------------------------------------------

	public boolean Pertence(int x, int y){
		if(x>=coordenadas.x1 && x<=coordenadas.x2 && x>=coordenadas.x3 && x<=coordenadas.x4){
			if(y>=coordenadas.y1 && y>=coordenadas.y2 && y<=coordenadas.y3 && y<=coordenadas.y4)
				return true;
		}
		return false;
	}
	
	// --------------------------------------------
	
	public void addCoordenadasX(int x1, int x2, int x3, int x4){
		coordenadas.x1 = x1;
		coordenadas.x2 = x2;
		coordenadas.x3 = x3;
		coordenadas.x4 = x4;
	}
	public void addCoordenadasY(int y1, int y2, int y3, int y4){
		coordenadas.y1 = y1;
		coordenadas.y2 = y2;
		coordenadas.y3 = y3;
		coordenadas.y4 = y4;
	}
	
	// --------------------------------------------
	
	public String getNomeTerritorio(){
		return nomeTerritorio ;
	}
	
	// --------------------------------------------
	
	public LinkedList<Territorio> getFronteirasTerritorio(){
		return fronteirasTerritorio ;
	}
	
	// --------------------------------------------
	
	public Continente getContinenteHaQuePertenceTerritorio(){
		return continenteHaQuePertenceTerritorio ;
	}
	
	// --------------------------------------------
	
	public void addFronteiraTerritorio( Territorio fronteiraTerritorio ){
		fronteirasTerritorio.add(fronteiraTerritorio) ;
	}

	// --------------------------------------------
	
	public void setNumExercito(int numExercito) {
		this.numExercito = numExercito;
	}
	
	public void addNumExercito( int numExercito ){
		this.numExercito+= numExercito ;
	}
	public int getNumExercito() {
		return numExercito;
	}
	
	// --------------------------------------------
	
	public void setExercitoDominante(String cor){
		this.exercitoDominante = cor;
	}
	public String getExercitoDominante(){
		return this.exercitoDominante;
	}
	
	// --------------------------------------------
	
	private class Coordenadas implements Serializable{
		
		private static final long serialVersionUID = -852971525348417771L;
		public int x1, x2, x3, x4;
		public int y1, y2, y3, y4;
	}
	
}
