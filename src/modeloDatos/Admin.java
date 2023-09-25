package modeloDatos;

public class Admin extends Usuario
{
	public Admin()
	{
		this.setUsserName("admin");
		this.setRealName("Administrador");

	}

	@Override
	public String toString()
	{
		return "Administrador";
	}

	

}
