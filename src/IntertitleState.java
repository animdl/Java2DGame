import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class IntertitleState extends GameState {
	
	private BufferedImage[] background;
	
        private int frame;
        private int ticks;
	
	private final int LENGTH = 240;
	
        private String title;
        
	public IntertitleState(GameStateManager gsm,int i) {
		super(gsm);
                switch(i) {
                    case 1: title = "Il barbiere\ndi Siviglia"; break;
                    case 2: title = " Duetto di\n due gatti"; break;
                    case 3: title = " La gazza\n   ladra"; break;
                    case 4: title = " Guglielmo\n   Tell"; break;
                }
	}
	
	public void init() {
                frame = 0;
                ticks = 0;
                background = new BufferedImage[6];
		try {
			for(int i = 0; i < background.length; i++)
                            background[i] = ImageIO.read(getClass().getResourceAsStream("/res/"+i+".png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		handleInput();
                if(ticks%10==0)
                    frame++;
                ticks++;
                if(frame>5)
                    frame=0;
		if(ticks > LENGTH){
			gsm.setState(gsm.nextLevel());
                }
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT2);
		g.drawImage(background[frame], 0, 0, MainPanel.WIDTH, MainPanel.HEIGHT2, null);
                String[] lines = title.split("\n");
                for (int i = 0; i<lines.length; i++)
                    Content.drawString(g,lines[i],20,58+i*20);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)){
			gsm.setState(gsm.nextLevel());
                }
	}
	
}