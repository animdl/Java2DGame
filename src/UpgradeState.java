import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class UpgradeState extends GameState {
	
	private BufferedImage background;
        private BufferedImage diamond;
        private Instrument instrument;
        
        private int currentOption = 0;
	
	public UpgradeState(GameStateManager gsm,Instrument i) {
		super(gsm);
                instrument = i;
	}
	
	public void init() {
		diamond = Content.DIAMOND[0][0];
                try {
                background = ImageIO.read(getClass().getResourceAsStream("/res/7.png"));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
	}
	
	public void update() {handleInput();}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MainPanel.WIDTH, MainPanel.HEIGHT2);
		g.drawImage(background, 0, 0, MainPanel.WIDTH, MainPanel.HEIGHT2, null);
		Content.drawString(g, "upgrade your", 15, 6);
                Content.drawString(g, "instrument", 25, 16);
                for(int i = 0; i < instrument.getChildren().size(); i++) {
                    g.drawImage(instrument.getChildren().get(i).getImage(),15,30+i*20,16,16,null);
                    Content.drawString(g,instrument.getChildren().get(i).getName(), 35,30+i*20);
                }
                if(currentOption == 0) 
			g.drawImage(diamond, 0, 30, null);
		else if(currentOption == 1) 
			g.drawImage(diamond, 0, 30+20, null);
                else if(currentOption == 2) 
			g.drawImage(diamond, 0, 30+40, null);
                else if(currentOption == 3) 
			g.drawImage(diamond, 0, 30+60, null);
                else if(currentOption == 4) 
			g.drawImage(diamond, 0, 30+80, null);
                else if(currentOption == 5) 
			g.drawImage(diamond, 0, 30+100, null);
	}
	
	public void handleInput() {
                if(Keys.isPressed(Keys.DOWN) && currentOption < instrument.getChildren().size() - 1)
			currentOption++;
		if(Keys.isPressed(Keys.UP) && currentOption > 0)
			currentOption--;
                if(Keys.isPressed(Keys.ENTER)){
                        gsm.setInstrument(instrument.getChildren().get(currentOption));
                        gsm.setState(gsm.nextIntertitle());
                }
	}
	
}