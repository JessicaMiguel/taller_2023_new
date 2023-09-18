package modeloDatos;

import util.Constantes;

public class Empleador extends Usuario
{
	private String rubro;
	private String tipo_persona;

	public String getRubro()
	{
		return rubro;
	}

	public Empleador()
	{
		super();
	}

	public String getTipo_persona()
	{
		return tipo_persona;
	}

	public Empleador(String usserName, String password, String realName, String telefono, String rubro,
			String tipo_persona)
	{
		super(usserName, password, telefono, realName);
		this.rubro = rubro;
		this.tipo_persona = tipo_persona;
	}

	@Override
	public double calculaComision(Ticket ticket)
	{
		double sueldo = ticket.getRemuneracion();
		if (this.rubro.equals(Constantes.COMERCIO_INTERNACIONAL))
			sueldo *= 0.8;
		else if (this.rubro.equals(Constantes.COMERCIO_LOCAL))
			sueldo *= 0.7;
		else if (this.rubro.equals(Constantes.SALUD))
			sueldo *= 0.6;

		// TODO Auto-generated method stub
		return sueldo;
	}

}
