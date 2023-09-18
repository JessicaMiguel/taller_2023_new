package persistencia;

import java.util.ArrayList;
import java.util.HashMap;

import modeloDatos.Contratacion;
import modeloDatos.EmpleadoPretenso;
import modeloDatos.Empleador;
import modeloDatos.Usuario;

public class AgenciaDTO
{
    private HashMap<String, EmpleadoPretenso> empleados = new HashMap<String, EmpleadoPretenso>();
    private HashMap<String, Empleador> empleadores = new HashMap<String, Empleador>();
    private int v1;
    private int v2;
    private ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
    private boolean estadoContratacion = false;
    private HashMap<Usuario, Double> comisionesUsuarios = new HashMap<Usuario, Double>();
	
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
	public int getV1()
	{
		return v1;
	}
	public void setV1(int v1)
	{
		this.v1 = v1;
	}
	public int getV2()
	{
		return v2;
	}
	public void setV2(int v2)
	{
		this.v2 = v2;
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
	public HashMap<Usuario, Double> getComisionesUsuarios()
	{
		return comisionesUsuarios;
	}
	public void setComisionesUsuarios(HashMap<Usuario, Double> comisionesUsuarios)
	{
		this.comisionesUsuarios = comisionesUsuarios;
	}




}


