package excepciones;

public class ImposibleCrearEmpleadorException extends Exception
{
    	private String usserName;
	private String password;
	private String telefono;
	private String realName;
	private String rubro;
	private String tipo_persona;
	public ImposibleCrearEmpleadorException(String message, String usserName, String password, String telefono,
		String realName, String rubro, String tipo_persona)
	{
	    super(message);
	    this.usserName = usserName;
	    this.password = password;
	    this.telefono = telefono;
	    this.realName = realName;
	    this.rubro = rubro;
	    this.tipo_persona = tipo_persona;
	}
	public String getUsserName()
	{
	    return usserName;
	}
	public void setUsserName(String usserName)
	{
	    this.usserName = usserName;
	}
	public String getPassword()
	{
	    return password;
	}
	public void setPassword(String password)
	{
	    this.password = password;
	}
	public String getTelefono()
	{
	    return telefono;
	}
	public void setTelefono(String telefono)
	{
	    this.telefono = telefono;
	}
	public String getRealName()
	{
	    return realName;
	}
	public void setRealName(String realName)
	{
	    this.realName = realName;
	}
	public String getRubro()
	{
	    return rubro;
	}
	public void setRubro(String rubro)
	{
	    this.rubro = rubro;
	}
	public String getTipo_persona()
	{
	    return tipo_persona;
	}
	public void setTipo_persona(String tipo_persona)
	{
	    this.tipo_persona = tipo_persona;
	}
	


}
