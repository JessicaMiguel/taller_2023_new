package modeloDatos;

import java.util.ArrayList;
import java.util.PriorityQueue;

import modeloNegocio.UsuarioPuntaje;

/**
 * Clas abstracta que representa los Usuarios. Tanto Administradores, Empleados Pretensos o EMpleadores.
 * <b>Todos los setters tienen como precondición , que su parametro es diferente de null</b>
 */
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


	/**
	 * <b>pre:</b> candidato!= null, candidato es de un tipo de Usuario válido (EmpleadoPretenso para un Empleador y viceversa)
	 * @param candidato Candidato para realizar la contratacion
	 */
	public void setCandidato(Usuario candidato)
	{
	    this.candidato = candidato;
	}


	/**
	 * <b>pre:</b> Los parámetros son distintos de null.
	 * @param usserName Nombre de usuario
	 * @param password  Contraseña
	 * @param realName  Nombre Real del usuario
	 * @param telefono  Telefono o celular del usuario
	 */
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


	/**
	 * <b>pre:</b> listaDePostulantes es diferente de null, los postulantes son de tipo valido (EmpleadorPretensos para Empleadores y viceversa) 
	 * @param listaDePostulantes ArrayList ordenado por puntaje de los postulantes para realizar la contratación
	 */
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


	
	public abstract double calculaComision(Ticket ticket);
	
	
	
}
