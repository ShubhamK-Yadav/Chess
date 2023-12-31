import javax.swing.JFrame;

public class GameLauncher extends JFrame {
	public GameLauncher() {
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Chess");
		this.add(new ChessGui());
		this.pack();
	}

	public static void main(String[] args) {
		new GameLauncher();
		ConsoleGui console = new ConsoleGui();
		console.run();
	}
}