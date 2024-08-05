import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GameOverState extends GameState {
	
	private BufferedImage background;
	
	private int rank;
	private long ticks;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		try {
                background = ImageIO.read(getClass().getResourceAsStream("/res/7.png"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
		ticks = Data.getTime();
		if(ticks < 3600) 
			rank = 1;
		else if(ticks < 5400) 
			rank = 2;
		else if(ticks < 7200) 
			rank = 3;
		else rank = 4;
                
                Data.setTime(0);
	}
	
	public void update() {handleInput();}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT2);
		g.drawImage(background, 0, 0, MainPanel.WIDTH, MainPanel.HEIGHT2, null);
		Content.drawString(g, "finish time", 20, 36);
		
		int minutes = (int) (ticks / 1800);
		int seconds = (int) ((ticks / 30) % 60);
		if(minutes < 10) {
			if(seconds < 10) 
				Content.drawString(g, "0" + minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, "0" + minutes + ":" + seconds, 44, 48);
		} else {
			if(seconds < 10) 
				Content.drawString(g, minutes + ":0" + seconds, 44, 48);
			else Content.drawString(g, minutes + ":" + seconds, 44, 48);
		}
		
		Content.drawString(g, "rank", 48, 66);
		if(rank == 1) 
			Content.drawString(g, " virtuoso", 20, 78);
		else if(rank == 2) 
			Content.drawString(g, " maestro", 24, 78);
		else if(rank == 3) 
			Content.drawString(g, "prodigy", 32, 78);
		else if(rank == 4) 
			Content.drawString(g, "beginner", 8, 78);
		
		Content.drawString(g, "press enter", 12, 110);
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER))
                    if(gsm.getLevel()<4)
                        gsm.setState(gsm.UPGRADE);
                    else
                        gsm.setState(gsm.MENU);
	}
	
}