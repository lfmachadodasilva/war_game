package servidor;

import java.net.Socket;
import java.util.HashMap;

import comum.*;

public class Observador implements IFObservador {
	
	private HashMap<ComandoOperacao, IFOperacao> operacoes ;
	
	public Observador(){
		
		operacoes = new HashMap<ComandoOperacao, IFOperacao>() ;
		
		operacoes.put( ComandoOperacao.EFETUAR_ATAQUE			 , new OperacaoAtaque() 				 ) ;
		operacoes.put( ComandoOperacao.ATUALIZA_CARTA_TERRITORIO , new OperacaoAtualizaCartaTerritorio() ) ;
		operacoes.put( ComandoOperacao.ATUALIZA_TABULEIRO		 , new OperacaoAtualizaTabuleiro() 		 ) ;
		operacoes.put( ComandoOperacao.CADASTRAR				 , new OperacaoCadastrar()				 ) ;
		operacoes.put( ComandoOperacao.CONECTA					 , new OperacaoConecta() 				 ) ;
		operacoes.put( ComandoOperacao.FINALIZA_JOGADA			 , new OperacaoFinalizaJogada() 		 ) ;
		operacoes.put( ComandoOperacao.INICIAR_PARTIDA			 , new OperacaoIniciarPartida() 		 ) ;
		operacoes.put( ComandoOperacao.SAIR_LISTA_ESPERA		 , new OperacaoSairListaEspera() 		 ) ;
		operacoes.put( ComandoOperacao.SAIR_PARTIDA				 , new OperacaoSairPartida() 			 ) ;
		operacoes.put( ComandoOperacao.SOLICITAR_INICIAR_PARTIDA , new OperacaoSolicitarIniciarPartida() ) ;

	}
	
	public void notifica ( Protocolo protocolo , Socket socketCliente ){
		
		IFOperacao operacao = operacoes.get( protocolo.getComando() ) ;
		operacao.executaOperacao( socketCliente , protocolo ) ;
		
	}

}
