package modeloDatos;

import java.util.ArrayList;
import java.util.PriorityQueue;

import modeloNegocio.UsuarioPuntaje;

public abstract class Usuario
{
	private String usserName;
	private String password;
	private String telefono;
	private String realName;
	private Ticket ticket = null;
	private ArrayList<UsuarioPuntaje> listaDePostulantes=null;
	private int puntaje;
	private Usuario candidato=null;
	
	
	
	
	
	
	
	


	public Usuario getCandidato()
	{
	    return candidato;
	}


	public void setCandidato(Usuario candidato)
	{
	    this.candidato = candidato;
	}


	public Usuario(String usserName, String password, String realName,String telefono)
	{
		super();
		this.usserName = usserName;
		this.password = password;
		this.telefono = telefono;
		this.realName = realName;
	}


	


	


	public ArrayList<UsuarioPuntaje> getListaDePostulantes()
	{
	    return listaDePostulantes;
	}


	public void setListaDePostulantes(ArrayList<UsuarioPuntaje> listaDePostulantes)
	{
	    this.listaDePostulantes = listaDePostulantes;
	}


	public Usuario() {}
	
	
	


	


	public String getUsserName()
	{
		return usserName;
	}
	public void setUsserName(String usserName)
	{
		this.usserName = usserName;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getTelefono()
	{
		return telefono;
	}
	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}
	public Ticket getTicket()
	{
		return ticket;
	}
	public void setTicket(Ticket ticket)
	{
		this.ticket = ticket;
	}


	public String getRealName()
	{
		return realName;
	}


	public void setRealName(String realName)
	{
		this.realName = realName;
	}


	@Override
	public String toString()
	{
		return usserName + ", realName=" + realName + " Puntaje: "+this.puntaje;
	}


	public int getPuntaje()
	{
		return puntaje;
	}


	public void setPuntaje(int puntaje)
	{
		this.puntaje = puntaje;
	}


	
	public abstract double calculaComision(double sueldo);
	
	
	
}
