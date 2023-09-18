package modeloDatos;

import java.util.HashMap;

import util.Constantes;

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

    public double getComparacionLocacion(Ticket otro)
    {
	int i = Ticket.hashLocacion.get(this.locacion);
	int j = Ticket.hashLocacion.get(otro.locacion);
	return matrizLocacion[i][j];
    }

    public double getComparacionJornada(Ticket otro)
    {
	int i = Ticket.hashJornada.get(this.jornada);
	int j = Ticket.hashJornada.get(otro.jornada);
	return matrizJornada[i][j];
    }

    public double getComparacionPuesto(Ticket otro)
    {
	int i = Ticket.hashPuesto.get(this.puesto);
	int j = Ticket.hashPuesto.get(otro.puesto);
	return matrizPuesto[i][j];
    }

    public double getComparacionEstudios(Ticket otro)
    {
	int i = Ticket.hashEstudios.get(this.estudios);
	int j = Ticket.hashEstudios.get(otro.estudios);
	return matrizEstudios[i][j];

    }

    public double getComparacionExperiencia(Ticket otro)
    {
	int i = Ticket.hashExperiencia.get(this.experiencia);
	int j = Ticket.hashExperiencia.get(otro.experiencia);
	return matrizExperiencia[i][j];

    }

    public double getComparacionRemuneracion(Ticket otro)
    {
	return 0;
    }

    public double getComparacionTotal(Ticket otro)
    {
	double respuesta = 0;
	respuesta = this.getComparacionEstudios(otro) + this.getComparacionExperiencia(otro)
		+ this.getComparacionJornada(otro) + this.getComparacionLocacion(otro) + this.getComparacionPuesto(otro)
		+ this.getComparacionRemuneracion(otro);
	return respuesta;
    }

   
    
    

}
