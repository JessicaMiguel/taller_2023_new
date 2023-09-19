package excepciones;

public class LimiteInferiorRemuneracionInvalidaException extends Exception
{
private int limiteInferiorActual;
private int limiteSuperiorActual;
private int limiteInferiorPretendido;




public LimiteInferiorRemuneracionInvalidaException(String mensaje,int limiteInferiorActual, int limiteSuperiorActual,
		int limiteInferiorPretendido)
{
	super(mensaje);
	this.limiteInferiorActual = limiteInferiorActual;
	this.limiteSuperiorActual = limiteSuperiorActual;
	this.limiteInferiorPretendido = limiteInferiorPretendido;
}



public int getLimiteInferiorActual()
{
	return limiteInferiorActual;
}
public int getLimiteSuperiorActual()
{
	return limiteSuperiorActual;
}
public int getLimiteInferiorPretendido()
{
	return limiteInferiorPretendido;
}


}
