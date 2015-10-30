package servidor;

import java.net.Socket;

import comum.Protocolo;

public interface IFObservador {
	
	public void notifica( Protocolo protocolo , Socket socketCliente ) ;

}
