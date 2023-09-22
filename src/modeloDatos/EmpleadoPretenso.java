package modeloDatos;

import util.Constantes;

/**
 * Clase que representa un usuario pretenso
 * <b>Todos los setters tienen como precondici�n , que su parametro es diferente de null</b>
 */
public class EmpleadoPretenso extends Usuario
{
	private String apellido;
	private int edad;

	/**
	 * <b>pre:</b> Los par�metros String son distintos de null, la edad es mayor que cero.
	 * @param usserName
	 * @param password
	 * @param realName
	 * @param telefono
	 * @param apellido
	 * @param edad
	 */
	public EmpleadoPretenso(String usserName, String password, String realName, String telefono, String apellido,
			int edad)
	{
		super(usserName, password, realName, telefono);
		this.apellido = apellido;
		this.edad = edad;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public int getEdad()
	{
		return edad;
	}

	public void setEdad(int edad)
	{
		this.edad = edad;
	}

	public EmpleadoPretenso()
	{
		super();
	}

	@Override
	public String toString()
	{
		return super.toString() + "  apellido=" + apellido + ", edad=" + edad;
	}
	
	/**
	 * Este m�todo se utiliza para calcular la comisi�n que un usuario debe recibir seg�n el tipo de puesto en un Ticket y su  puntaje (ver detalles de calculo de comision). 
	 * <b>Pre: </b> El m�todo requiere un objeto Ticket v�lido como entrada para realizar los c�lculos de comisi�n. (ver detalles) <br>
	 * @return double con el valor de la comisi�n
	 * @param ticket objeto Ticket de donde se obtiene ell tipo de puesto junior, senior o management
	 */

	@Override
	public double calculaComision(Ticket ticket)
	{
		double descuento = 0;
		if (ticket.getPuesto().equals(Constantes.JUNIOR))
			descuento = .8;
		else if (ticket.getPuesto().equals(Constantes.SENIOR))
			descuento = .9;

		else if (ticket.getPuesto().equals(Constantes.MANAGMENT))
			descuento = 1;

		descuento -= 0.01 * this.getPuntaje();
		
		return ticket.getRemuneracion() * descuento;
	}

}
