package modeloDatos;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Clase abstracta que representa los Clientes, los cuales podran ser, Empleados Pretensos o Empleadores.<br>
 * Los atributos ticket y listaDePostulantes pueden ser null<br>
 * El atributo <b>puntaje</b> puede tomar cualquier valor numerico (tanto positivo, cero, o negativo)<br>
 * <b>Todos los setters tienen como precondicion , que su parametro es diferente de null</b><br>
 * 
 * 
 */
public abstract class Cliente extends Usuario
{
	
	private Ticket ticket = null;
	private ArrayList<ClientePuntaje> listaDePostulantes=null;
	private int puntaje;
	private Cliente candidato=null;


	
	public Cliente getCandidato()
	{
	    return candidato;
	}


	/**
	 * <b>pre:</b> candidato!= null, candidato es de un tipo de Usuario valido (EmpleadoPretenso para un Empleador y viceversa)
	 * @param candidato Candidato para realizar la contratacion
	 */
	public void setCandidato(Cliente candidato)
	{
	    this.candidato = candidato;
	}


	/**
	 * <b>pre:</b> Los parametros son distintos de null.
	 * @param usserName Nombre de usuario
	 * @param password  Contrasena
	 * @param realName  Nombre Real del usuario
	 * @param telefono  Telefono o celular del usuario
	 */
	public Cliente(String usserName, String password, String realName,String telefono)
	{
		super(usserName, password,realName,telefono);
	}


	


	


	public ArrayList<ClientePuntaje> getListaDePostulantes()
	{
	    return listaDePostulantes;
	}


	/**
	 * <b>pre:</b> listaDePostulantes es diferente de null, los postulantes son de tipo valido (EmpleadorPretensos para Empleadores y viceversa) 
	 * @param listaDePostulantes ArrayList ordenado por puntaje de los postulantes para realizar la contratacion
	 */
	public void setListaDePostulantes(ArrayList<ClientePuntaje> listaDePostulantes)
	{
	    this.listaDePostulantes = listaDePostulantes;
	}


	public Cliente() {}
	
	
	


	



	@Override
	public String toString()
	{
		return super.toString() +  " Puntaje: "+this.puntaje;
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


	public Ticket getTicket()
	{
		return ticket;
	}


	public void setTicket(Ticket ticket)
	{
		this.ticket = ticket;
	}
	
	
	
}
