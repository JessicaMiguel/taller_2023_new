package modeloDatos;

import util.Constantes;

/**
 * Clase que representa un Usuario de tipo Empleador.
 * <b>Todos los setters tienen como precondici�n, que su parametro es diferente de null. Rubro y tipo_persona son de los tipos esperados.</b>
 */
public class Empleador extends Usuario
{
	private String rubro;
	private String tipoPersona;

	public String getRubro()
	{
		return rubro;
	}

	public Empleador()
	{
		super();
	}

	public String getTipoPersona()
	{
		return tipoPersona;
	}

	public void setRubro(String rubro)
	{
	    this.rubro = rubro;
	}

	public void setTipoPersona(String tipoPersona)
	{
	    this.tipoPersona = tipoPersona;
	}

	
	/**
	 * <b>pre:</b> los parametros son diferentes de null, rubro y tipoPersona de los tipos esperados (contemplados en la clase Constantes)
	 * @param usserName
	 * @param password
	 * @param realName
	 * @param telefono
	 * @param rubro
	 * @param tipoPersona
	 */
	public Empleador(String usserName, String password, String realName, String telefono, String rubro,
			String tipoPersona)
	{
		super(usserName, password, telefono, realName);
		this.rubro = rubro;
		this.tipoPersona = tipoPersona;
	}
	
	
	/**
	 * Este m�todo se utiliza para calcular la comisi�n que un usuario debe recibir seg�n el tipo de puesto en un Ticket y su  puntaje  (ver detalles de calculo de comision). 
	 * <b>Pre: </b> El m�todo requiere un objeto Ticket v�lido como entrada para realizar los c�lculos de comisi�n. <br>
	 * 
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
