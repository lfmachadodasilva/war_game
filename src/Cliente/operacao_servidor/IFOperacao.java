package operacao_servidor;

import java.net.Socket;

import comum.Protocolo;

public interface IFOperacao {
	
	// ------------------------------------------------
	
	public void executaOperacao( Socket socketCliente, Protocolo protocolo) ;

	// ------------------------------------------------
	
}
