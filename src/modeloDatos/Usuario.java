package modeloDatos;
/**
 * Clase abstracta que representa los Usuarios. Tanto Administradores, Empleados Pretensos o Empleadores.
 * <b>Todos los setters tienen como precondicion , que su parametro es diferente de null</b>
 * <b>Invariante de clases:</b><br>
 * Los atributos usserName, password, telefono y realName son diferentes de null<br>
 */

public class Usuario
{
	private String usserName;
	private String password;
	private String telefono;
	private String realName;
	
	
	
	/**
	 * <b>pre:</b> Los parametros son distintos de null.
	 * @param usserName Nombre de usuario
	 * @param password  Contrasena
	 * @param realName  Nombre Real del usuario
	 * @param telefono  Telefono o celular del usuario
	 */
	public Usuario(String usserName, String password, String realName,String telefono)
	{
		super();
		this.usserName = usserName;
		this.password = password;
		this.telefono = telefono;
		this.realName = realName;
	}


	public Usuario() {}

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
	
	@Override
	public String toString()
	{
		return usserName + ", realName=" + realName ;
	}

}
