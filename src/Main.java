import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

	public Main() {
		EventQueue.invokeLater(() -> {
			this.setTitle("Rossini's Revenge: Retrieving Relics");
			
			this.add(new MainPanel());
			
			this.setResizable(false);
			this.pack();
			
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
	}

	public static void main(String[] args) {
		new Main();
	}
}
