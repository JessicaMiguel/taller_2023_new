package excepciones;

public class ImposibleCrearEmpleadoException extends Exception
{
    	private String usserName;
	private String password;
	private String telefono;
	private String realName;
	private String apellido;
	private int edad;
	public ImposibleCrearEmpleadoException(String message, String usserName, String password, String telefono,
		String realName, String apellido, int edad)
	{
	    super(message);
	    this.usserName = usserName;
	    this.password = password;
	    this.telefono = telefono;
	    this.realName = realName;
	    this.apellido = apellido;
	    this.edad = edad;
	}
	public String getUsserName()
	{
	    return usserName;
	}
	public String getPassword()
	{
	    return password;
	}
	public String getTelefono()
	{
	    return telefono;
	}
	public String getRealName()
	{
	    return realName;
	}
	public String getApellido()
	{
	    return apellido;
	}
	public int getEdad()
	{
	    return edad;
	}
	
	

}
