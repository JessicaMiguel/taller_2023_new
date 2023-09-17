package excepciones;

import modeloDatos.Usuario;


public class ContraException extends LoginException {
	private String pass;
	private Usuario usuario;

	public ContraException(String arg,Usuario usuario,String pass)
	{
		super(arg);
		this.usuario=usuario;
		this.pass=pass;
		// TODO Auto-generated constructor stub
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
	
	
	
	
}
