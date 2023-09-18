package modeloDatos;

import util.Constantes;

public class EmpleadoPretenso extends Usuario
{
	private String apellido;
	private int edad;

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
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString()
	{
		return super.toString() + "  apellido=" + apellido + ", edad=" + edad;
	}

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
		// TODO Auto-generated method stub
		return ticket.getRemuneracion() * descuento;
	}

}
