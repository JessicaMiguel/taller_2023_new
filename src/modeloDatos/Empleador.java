package modeloDatos;

import java.util.Collection;

import vista.IVista;

public class Empleador extends Usuario
{
	private String rubro;
	private String tipo_persona;
	private double descuentoComision;

	public String getRubro()
	{
		return rubro;
	}

	
	public String getTipo_persona()
	{
		return tipo_persona;
	}

		public double getDescuentoComision()
	{
		return descuentoComision;
	}


		public Empleador(String usserName, String password, String realName,String telefono, String rubro,
				String tipo_persona)
		{
			super(usserName, password, telefono, realName);
			this.rubro = rubro;
			this.tipo_persona = tipo_persona;
		if(this.rubro.equals(IVista.COMERCIO_INTERNACIONAL))
			this.descuentoComision=0.8;
		else if (this.rubro.equals(IVista.COMERCIO_LOCAL))
			this.descuentoComision=0.7;
		else if (this.rubro.equals(IVista.SALUD))
			this.descuentoComision=0.6;
		}


		@Override
		public double calculaComision(double sueldo)
		{
		    // TODO Auto-generated method stub
		    return 0;
		}

	
}
