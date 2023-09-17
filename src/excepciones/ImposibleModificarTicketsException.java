package excepciones;

import util.Mensajes;

public class ImposibleModificarTicketsException extends Exception
{

    public ImposibleModificarTicketsException()
    {
	super(Mensajes.ERROR_AGENCIA_EN_CONTRATACION.getValor());
	// TODO Auto-generated constructor stub
    }

}
