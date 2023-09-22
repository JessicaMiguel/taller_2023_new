package modeloNegocio;

import modeloDatos.Usuario;



/**
 * Clase que representa un Usuario con su corespondiente puntaje obtenido en una busqueda laboral
 * El objeto es Comparable en forma descendente por su puntaje obtenido.
 */
public class UsuarioPuntaje implements Comparable
{
private double puntaje;
private Usuario usuario;



public UsuarioPuntaje() {}



/**
 * <b>Pre:</b> Usuario es diferente de null
 * @param puntaje
 * @param usuario
 */
public UsuarioPuntaje(double puntaje, Usuario usuario)
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
public Usuario getUsuario()
{
	return usuario;
}
/**
 * <b>Pre:</b> Usuario es diferente de null
 * @param usuario
 */
public void setUsuario(Usuario usuario)
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
