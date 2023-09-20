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
	
	
	/**
	 * Este método se utiliza para calcular la comisión que un usuario debe recibir según el tipo de puesto en un Ticket y su  puntaje. 
	 * 
	 * <b>Pre: </b> El método requiere un objeto Ticket válido como entrada para realizar los cálculos de comisión. <br>
	 * <b>Post: </b> Aplica un factor de descuento a la remuneración en función del rubro de la empresa del usuario. Los descuentos varían según el rubro: 0.8 para comercio internacional, 0.7 para comercio local y 0.6 para salud.  <br>
	 * @return double con el valor de la comisión
	 * @param ticket objeto Ticket de donde se obtiene el rubro comercio internacional, comercio local o comercio local
	 */

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
