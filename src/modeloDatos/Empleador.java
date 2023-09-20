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
	 * Este m�todo se utiliza para calcular la comisi�n que un usuario debe recibir seg�n el tipo de puesto en un Ticket y su  puntaje. 
	 * 
	 * <b>Pre: </b> El m�todo requiere un objeto Ticket v�lido como entrada para realizar los c�lculos de comisi�n. <br>
	 * <b>Post: </b> Aplica un factor de descuento a la remuneraci�n en funci�n del rubro de la empresa del usuario. Los descuentos var�an seg�n el rubro: 0.8 para comercio internacional, 0.7 para comercio local y 0.6 para salud.  <br>
	 * @return double con el valor de la comisi�n
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
