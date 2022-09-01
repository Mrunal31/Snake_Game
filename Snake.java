import javax.swing.*;

public class Snake extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Snake()
	{
		add(new Board());
		pack();
		
		setLocationRelativeTo(null);
		setTitle("Snake Game");
		setResizable(false);
	}
	public static void main(String[] args) {
		new Snake().setVisible(true);

	}

}
