package modeloDatos;

/**
 * Clase que representa un Usuario con su corespondiente puntaje obtenido en una busqueda laboral
 * El objeto es Comparable en forma descendente por su puntaje obtenido.
 * Todos los setters de tipo String tienen como precondicion , que su parametro es diferente de null y son del tipo esperado contemplado en la clase Constantes
 * <b>Invariante de clase</b>usuario es distinto de null<br>
 */
public class UsuarioPuntaje implements Comparable
{
private double puntaje;
private Cliente usuario;



public UsuarioPuntaje() {}



/**
 * <b>Pre:</b> Usuario es diferente de null
 * @param puntaje
 * @param usuario
 */
public UsuarioPuntaje(double puntaje, Cliente usuario)
{
    super();
    this.puntaje = puntaje;
    this.usuario = usuario;
}



public double getPuntaje()
{
	return puntaje;
}
public void setPuntaje(double puntaje)
{
	this.puntaje = puntaje;
}
public Cliente getUsuario()
{
	return usuario;
}
/**
 * <b>Pre:</b> Usuario es diferente de null
 * @param usuario
 */
public void setUsuario(Cliente usuario)
{
	this.usuario = usuario;
}
@Override
public String toString()
{
	return "" + puntaje + " : " + usuario ;
}
@Override
public int compareTo(Object o)
{
	UsuarioPuntaje otro=(UsuarioPuntaje) o;
	
	return  (int) Math.signum((otro.puntaje-this.puntaje));
}



}
