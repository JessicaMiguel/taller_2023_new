package modeloDatos;

import java.util.ArrayList;

import modeloNegocio.UsuarioPuntaje;

public class EmpleadoPretenso extends Usuario
{
	private String apellido;
	private int edad;

	
	public EmpleadoPretenso(String usserName, String password,  String realName,String telefono, String apellido,
			int edad)
	{
		super(usserName, password, realName,telefono);
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
		return super.toString()+"  apellido=" + apellido + ", edad=" + edad;
	}

	@Override
	public double calculaComision(double sueldo)
	{
	    // TODO Auto-generated method stub
	    return 0;
	}

	

	
}
