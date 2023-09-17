package vista;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

public abstract class PanelAgencia extends JPanel implements ActionListener, KeyListener
{
protected ActionListener actionListener;

public PanelAgencia(ActionListener actionListener)
{
    super();
    this.actionListener = actionListener;
}


protected void setActionAndName(AbstractButton boton, String cadena)
{
	boton.setName(cadena);
	boton.setActionCommand(cadena);
}

public abstract void addActionListener(ActionListener actionListener);
}
