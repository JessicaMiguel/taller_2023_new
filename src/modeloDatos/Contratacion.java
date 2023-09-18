package modeloDatos;

import java.util.GregorianCalendar;

import util.Constantes;

public class Contratacion
{
	private EmpleadoPretenso empleado;
	private Empleador empleador;
	private GregorianCalendar fecha;

	public Contratacion()
	{
	}

	public Contratacion( Empleador empleador,EmpleadoPretenso empleado)
	{
		super();
		this.empleado = empleado;
		this.empleador = empleador;
		this.fecha = new GregorianCalendar();
	}

	public EmpleadoPretenso getEmpleado()
	{
		return empleado;
	}

	public void setEmpleado(EmpleadoPretenso empleado)
	{
		this.empleado = empleado;
	}

	public Empleador getEmpleador()
	{
		return empleador;
	}

	public void setEmpleador(Empleador empleador)
	{
		this.empleador = empleador;
	}

	public GregorianCalendar getFecha()
	{
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha)
	{
		this.fecha = fecha;
	}

	@Override
	public String toString()
	{
		return "Empleado=" + empleado.getRealName() + ", empleador=" + empleador.getRealName() + ", fecha="
				+ Constantes.dateFormat.format(this.fecha.getTime());
	}

}
