package modeloDatos;

/**
 * Clase que representa un Cliente con su corespondiente puntaje obtenido en una busqueda laboral
 * El objeto es Comparable en forma descendente por su puntaje obtenido.
 * Todos los setters tienen como precondicion , que su parametro es diferente de null
 * <b>Invariante de clase</b>cliente es distinto de null<br>
 */
public class ClientePuntaje implements Comparable
{
private double puntaje;
private Cliente cliente;



public ClientePuntaje() {}



/**
 * <b>Pre:</b> Usuario es diferente de null
 * @param puntaje
 * @param cliente
 */
public ClientePuntaje(double puntaje, Cliente cliente)
{
    super();
    this.puntaje = puntaje;
    this.cliente = cliente;
}



public double getPuntaje()
{
	return puntaje;
}
public void setPuntaje(double puntaje)
{
	this.puntaje = puntaje;
}
public Cliente getCliente()
{
	return cliente;
}
/**
 * <b>Pre:</b> Usuario es diferente de null
 * @param cliente
 */
public void setCliente(Cliente cliente)
{
	this.cliente = cliente;
}
@Override
public String toString()
{
	return "" + puntaje + " : " + cliente ;
}
@Override
public int compareTo(Object o)
{
	ClientePuntaje otro=(ClientePuntaje) o;
	
	return  (int) Math.signum((otro.puntaje-this.puntaje));
}



}
