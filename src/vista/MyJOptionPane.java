package vista;

import javax.swing.JOptionPane;

public class MyJOptionPane implements IOptionPane
{

	@Override
	public void ShowMessage(String mensaje)
	{
		JOptionPane.showMessageDialog(null, mensaje);
	}

}
