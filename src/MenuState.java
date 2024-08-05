import java.awt.*;
import java.awt.image.*;

public class MenuState extends GameState {
	
	private BufferedImage bg;
	private BufferedImage diamond;
	
	private int currentOption = 0;
	private String[] options = {"START", "QUIT"};
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		bg = Content.MENUBG[0][0];
		diamond = Content.DIAMOND[0][0];
	}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, null);
		
		Content.drawString(g, options[0], 44, 90);
		Content.drawString(g, options[1], 48, 100);
		
		if(currentOption == 0) 
			g.drawImage(diamond, 25, 86, null);
		else if(currentOption == 1) 
			g.drawImage(diamond, 25, 96, null);
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1)
			currentOption++;
		if(Keys.isPressed(Keys.UP) && currentOption > 0)
			currentOption--;
		if(Keys.isPressed(Keys.ENTER))
			selectOption();
	}
	
	private void selectOption() {
		if(currentOption == 0)
			gsm.setState(gsm.nextIntertitle());
		if(currentOption == 1)
			System.exit(0);
	}
}
