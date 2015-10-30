package operacao_servidor;

import java.net.Socket;
import java.util.HashMap;

import comum.ComandoOperacao;
import comum.Protocolo;

public class Observador implements IFObservador {
	
	// ------------------------------------------------
	
	private HashMap<ComandoOperacao, IFOperacao> operacoes ;
	
	// ------------------------------------------------
	
	public Observador(){
		
		operacoes = new HashMap<ComandoOperacao, IFOperacao>() ;
		
		//---
		operacoes.put(ComandoOperacao.INICIA_JOGADA,                new Operacao_IniciaJogada());
		operacoes.put(ComandoOperacao.INICIAR_PARTIDA,              new Operacao_IniciarPartida());
		operacoes.put(ComandoOperacao.NOTIFICA_DESTRUICAO_EXERCITO, new Operacao_NotificaDestruicaoExercito());
		//---
		
		//---
		operacoes.put(ComandoOperacao.RESPOSTA_CADASTRAR,                 new Operacao_RespostaCadastrar());
		operacoes.put(ComandoOperacao.RESPOSTA_CONEXAO,					  new Operacao_RespostaConecta()) ;
		operacoes.put(ComandoOperacao.RESPOSTA_DEFESA,                    new Operacao_RespostaDefesa());
		operacoes.put(ComandoOperacao.RESPOSTA_FINALIZA_JOGADA,           new Operacao_RespostaFinalizaJogada());
		operacoes.put(ComandoOperacao.RESPOSTA_SAIR_LISTA_ESPERA,         new Operacao_RespostaSairListaEspera());
		operacoes.put(ComandoOperacao.RESPOSTA_SOLICITAR_INICIAR_PARTIDA, new Operacao_RespostaSolicitarIniciarPartida());
		//---
		
		//---
		operacoes.put(ComandoOperacao.BROADCAST_CALLBACK_INICIO_PARTIDA,              new Operacao_BroadCastCallBackInicarPartida());
		operacoes.put(ComandoOperacao.BROADCAST_CONFIRMACAO_INICIO_PARTIDA,           new Operacao_IniciarPartida());
		operacoes.put(ComandoOperacao.BROADCAST_NEGACAO_INICIO_PARTIDA, 			  new Operacao_BroadCastNegacaoInicioPartida());
		operacoes.put(ComandoOperacao.BROADCAST_NOTIFICA_ATAQUE,                      new Operacao_BroadCastNotificaAtaque());
		operacoes.put(ComandoOperacao.BROADCAST_NOTIFICA_ATUALIZACAO_LISTA_JOGADORES, new Operacao_BroadCastNotificaAtualizacaoListaJogadores());
		operacoes.put(ComandoOperacao.BROADCAST_NOTIFICA_RESULTADO_ATAQUE,            new Operacao_BroadCastNotificaResultadoAtaque());
		operacoes.put(ComandoOperacao.BROADCAST_NOTIFICA_SAIDA_LISTA_ESPERA, 		  new Operacao_BroadCastNotificaSaidaListaEspera());
		operacoes.put(ComandoOperacao.BROADCAST_NOTIFICA_VENCEDOR,                    new Operacao_BroadCastNotificaVencedor());
		//---
		
	}
	
	// ------------------------------------------------
	
	public void notifica ( Protocolo protocolo , Socket socketCliente ){
		
		IFOperacao operacao = operacoes.get( protocolo.getComando() ) ;
		operacao.executaOperacao( socketCliente , protocolo ) ;
		
	}

}
