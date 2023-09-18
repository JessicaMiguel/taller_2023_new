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

	@Override
	public double calculaComision(Ticket ticket)
	{
	    // TODO Auto-generated method stub
	    return 0;
	}

}
