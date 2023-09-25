package persistencia;

import java.util.ArrayList;
import java.util.HashMap;

import modeloDatos.Contratacion;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloDatos.Cliente;

public class AgenciaDTO
{
	private HashMap<String, EmpleadoPretenso> empleados = new HashMap<String, EmpleadoPretenso>();
	private HashMap<String, Empleador> empleadores = new HashMap<String, Empleador>();
	private int limiteInferior;
	private int limiteSuperior;
	private ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
	private boolean estadoContratacion = false;
	private HashMap<Cliente, Double> comisionesUsuarios = new HashMap<Cliente, Double>();

	public HashMap<String, EmpleadoPretenso> getEmpleados()
	{
		return empleados;
	}

	public void setEmpleados(HashMap<String, EmpleadoPretenso> empleados)
	{
		this.empleados = empleados;
	}

	public HashMap<String, Empleador> getEmpleadores()
	{
		return empleadores;
	}

	public void setEmpleadores(HashMap<String, Empleador> empleadores)
	{
		this.empleadores = empleadores;
	}

	

	public int getLimiteInferior()
	{
		return limiteInferior;
	}

	public void setLimiteInferior(int limiteInferior)
	{
		this.limiteInferior = limiteInferior;
	}

	public int getLimiteSuperior()
	{
		return limiteSuperior;
	}

	public void setLimiteSuperior(int limiteSuperior)
	{
		this.limiteSuperior = limiteSuperior;
	}

	public ArrayList<Contratacion> getContrataciones()
	{
		return contrataciones;
	}

	public void setContrataciones(ArrayList<Contratacion> contrataciones)
	{
		this.contrataciones = contrataciones;
	}

	public boolean isEstadoContratacion()
	{
		return estadoContratacion;
	}

	public void setEstadoContratacion(boolean estadoContratacion)
	{
		this.estadoContratacion = estadoContratacion;
	}

	public HashMap<Cliente, Double> getComisionesUsuarios()
	{
		return comisionesUsuarios;
	}

	public void setComisionesUsuarios(HashMap<Cliente, Double> comisionesUsuarios)
	{
		this.comisionesUsuarios = comisionesUsuarios;
	}

}
