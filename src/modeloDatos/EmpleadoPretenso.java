package modeloDatos;

import util.Constantes;

/**
 * Clase que representa un usuario pretenso<br>
 * <b>Todos los setters tienen como precondicion , que su parametro es diferente de null</b><br>
 * <b>Invariante de clase</b><br>
 * El atributo apellido es diferente de null<br>
 * El atributo edad es siempre positivo.
 */
public class EmpleadoPretenso extends Cliente
{
	private String apellido;
	private int edad;

	/**
	 * <b>pre:</b> Los parametros String son distintos de null, la edad es mayor que cero.
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

	/**
	 * <b>Pre: </b>Edad es un valor positivo<br>
	 * @param edad edad el empleado pretenso (siempre positiva).
	 */
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
	 * Este metodo se utiliza para calcular la comision que un usuario debe recibir segun el tipo de puesto en un Ticket y su  puntaje (ver detalles de calculo de comision). 
	 * <b>Pre: </b> El metodo requiere un objeto Ticket valido como entrada para realizar los calculos de comision. (ver detalles) <br>
	 * @return double con el valor de la comision
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
