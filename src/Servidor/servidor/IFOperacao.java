package servidor;

import java.net.*;
import comum.*;

public interface IFOperacao {
	
	public void executaOperacao( Socket socketCliente , Protocolo protocolo ) ;

}
