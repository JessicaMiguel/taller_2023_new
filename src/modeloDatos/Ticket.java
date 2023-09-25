package modeloDatos;

import java.util.HashMap;

import modeloNegocio.Agencia;
import util.Constantes;

/**
 * clase que representa un ticket de empleo pretendido
 * <b>Todos los setters de tipo String tienen como precondicion , que su parametro es diferente de null y son del tipo esperado contemplado en la clase Constantes
 *. Remuneracion es mayor que cero. </b>
 */
public class Ticket
{

	private String locacion;
	private int remuneracion;
	private String jornada;
	private String puesto;
	private String experiencia;
	private String estudios;
	private static final HashMap<String, Integer> hashLocacion = new HashMap<String, Integer>();
	private static final HashMap<String, Integer> hashJornada = new HashMap<String, Integer>();
	private static final HashMap<String, Integer> hashPuesto = new HashMap<String, Integer>();
	private static final HashMap<String, Integer> hashExperiencia = new HashMap<String, Integer>();
	private static final HashMap<String, Integer> hashEstudios = new HashMap<String, Integer>();
	private static final double[][] matrizLocacion =
	{
			{ 1, -1, 1 },
			{ -1, 1, -1 },
			{ 1, -1, 1 } };
	private static final double[][] matrizJornada =
	{
			{ 1, -0.5, -1 },
			{ -0.5, 1, -0.5 },
			{ -1, 1, -0.5 } };
	private static final double[][] matrizPuesto =
	{
			{ 1, -0.5, -1 },
			{ -0.5, 1, -0.5 },
			{ -1, 1, -0.5 } };
	private static final double[][] matrizExperiencia =
	{
			{ 1, 1.5, 2 },
			{ -0.5, 1, 1.5 },
			{ -2, -1.5, 1 } };
	private static final double[][] matrizEstudios =
	{
			{ 1, 1.5, 2 },
			{ -0.5, 1, 1.5 },
			{ -2, -1.5, 1 } };

	private static final double[][] matrizRemuneracion =
	{
			{ 1, -0.5, -1 },
			{ 1, 1, -0.5 },
			{ 1, 1, 1 } };

	static
	{
		hashLocacion.put(Constantes.HOME_OFFICE, 0);
		hashLocacion.put(Constantes.PRESENCIAL, 1);
		hashLocacion.put(Constantes.INDISTINTO, 2);

		hashJornada.put(Constantes.JORNADA_MEDIA, 0);
		hashJornada.put(Constantes.JORNADA_COMPLETA, 1);
		hashJornada.put(Constantes.JORNADA_EXTENDIDA, 2);

		hashPuesto.put(Constantes.JUNIOR, 0);
		hashPuesto.put(Constantes.SENIOR, 1);
		hashPuesto.put(Constantes.MANAGMENT, 2);

		hashExperiencia.put(Constantes.EXP_NADA, 0);
		hashExperiencia.put(Constantes.EXP_MEDIA, 1);
		hashExperiencia.put(Constantes.EXP_MUCHA, 2);

		hashEstudios.put(Constantes.PRIMARIOS, 0);
		hashEstudios.put(Constantes.SECUNDARIOS, 1);
		hashEstudios.put(Constantes.TERCIARIOS, 2);

	}

	public Ticket()
	{
	}

	public Ticket(String locacion, int remuneracion, String jornada, String puesto, String experiencia, String estudios)
	{
		super();
		this.locacion = locacion;
		this.remuneracion = remuneracion;
		this.jornada = jornada;
		this.puesto = puesto;
		this.experiencia = experiencia;
		this.estudios = estudios;
	}

	public String getLocacion()
	{
		return locacion;
	}

	public void setLocacion(String locacion)
	{
		this.locacion = locacion;
	}

	public int getRemuneracion()
	{
		return remuneracion;
	}

	public void setRemuneracion(int remuneracion)
	{
		this.remuneracion = remuneracion;
	}

	public String getJornada()
	{
		return jornada;
	}

	public void setJornada(String jornada)
	{
		this.jornada = jornada;
	}

	public String getPuesto()
	{
		return puesto;
	}

	public void setPuesto(String puesto)
	{
		this.puesto = puesto;
	}

	public String getExperiencia()
	{
		return experiencia;
	}

	public void setExperiencia(String experiencia)
	{
		this.experiencia = experiencia;
	}

	public String getEstudios()
	{
		return estudios;
	}

	public void setEstudios(String estudios)
	{
		this.estudios = estudios;
	}

	@Override
	public String toString()
	{
		return "Ticket [locacion=" + locacion + ", remuneracion=" + remuneracion + ", jornada=" + jornada + ", puesto="
				+ puesto + ", experiencia=" + experiencia + ", estudios=" + estudios + "]";
	}

	/**
	 * Devuelve el valor de la comparacion  por Locacion del ticket actual con el pasado por parametro, (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	public double getComparacionLocacion(Ticket otro)
	{
		int i = Ticket.hashLocacion.get(this.locacion);
		int j = Ticket.hashLocacion.get(otro.locacion);
		return matrizLocacion[i][j];
	}

	/**
	 * Devuelve el valor de la comparacion  por Jornada del ticket actual con el pasado por parametro, (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	public double getComparacionJornada(Ticket otro)
	{
		int i = Ticket.hashJornada.get(this.jornada);
		int j = Ticket.hashJornada.get(otro.jornada);
		return matrizJornada[i][j];
	}

	/**
	 * Devuelve el valor de la comparacion  por Puesto del ticket actual con el pasado por parametro, (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	public double getComparacionPuesto(Ticket otro)
	{
		int i = Ticket.hashPuesto.get(this.puesto);
		int j = Ticket.hashPuesto.get(otro.puesto);
		return matrizPuesto[i][j];
	}

	/**
	 * Devuelve el valor de la comparacion  por nivel de estudios del ticket actual con el pasado por parametro, (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	public double getComparacionEstudios(Ticket otro)
	{
		int i = Ticket.hashEstudios.get(this.estudios);
		int j = Ticket.hashEstudios.get(otro.estudios);
		return matrizEstudios[i][j];

	}

	/**
	 * Devuelve el valor de la comparacion por Experiencia del ticket actual con el pasado por parametro, (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	public double getComparacionExperiencia(Ticket otro)
	{
		int i = Ticket.hashExperiencia.get(this.experiencia);
		int j = Ticket.hashExperiencia.get(otro.experiencia);
		return matrizExperiencia[i][j];

	}

	/**
	 * Devuelve el valor de la comparacion  por remuneracion del ticket actual con el pasado por parametro, (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	public double getComparacionRemuneracion(Ticket otro)
	{
		int i = 0, j = 0;
        if(this.remuneracion>Agencia.getInstance().getLimiteSuperior())
        	i=2;
        else if (this.remuneracion>Agencia.getInstance().getLimiteInferior())i=1;
        
        
        if(otro.remuneracion>Agencia.getInstance().getLimiteSuperior())
        	j=2;
 
		return matrizRemuneracion[i][j];
	}

	/**
	 * Devuelve el valor de la comparacion completa del ticket actual con el pasado por parametro, sumando la totalidad de las comparaciones parciales (ver detalles de calculo)
	 * <b>Pre:</b> el parametro otro es diferente de null
	 * @param otro ticket para comparar
	 * @return Valor del calculo de comparacion
	 */
	
	public double getComparacionTotal(Ticket otro)
	{
		double respuesta = 0;
		respuesta = this.getComparacionEstudios(otro) + this.getComparacionExperiencia(otro)
				 + this.getComparacionLocacion(otro) + this.getComparacionPuesto(otro)
				+ this.getComparacionRemuneracion(otro);
		return respuesta;
	}

}
