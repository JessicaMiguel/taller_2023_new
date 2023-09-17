package modeloNegocio;

import modeloDatos.Usuario;



public class UsuarioPuntaje implements Comparable
{
private double puntaje;
private Usuario usuario;



public UsuarioPuntaje() {}



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
