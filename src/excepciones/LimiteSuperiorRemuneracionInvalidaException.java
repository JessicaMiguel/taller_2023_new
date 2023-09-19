package excepciones;

public class LimiteSuperiorRemuneracionInvalidaException extends Exception
{
private int limiteInferiorActual;
private int limiteSuperiorActual;
private int limiteSuperiorPretendido;




public LimiteSuperiorRemuneracionInvalidaException(String mensaje,int limiteInferiorActual, int limiteSuperiorActual,
		int limiteSuperiorPretendido)
{
	super(mensaje);
	this.limiteInferiorActual = limiteInferiorActual;
	this.limiteSuperiorActual = limiteSuperiorActual;
	this.limiteSuperiorPretendido = limiteSuperiorPretendido;
}



public int getLimiteInferiorActual()
{
	return limiteInferiorActual;
}
public int getLimiteSuperiorActual()
{
	return limiteSuperiorActual;
}



public int getLimiteSuperiorPretendido()
{
	return limiteSuperiorPretendido;
}


}
