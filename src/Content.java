import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class Content {
	
	public static BufferedImage[][] MENUBG = load("/res/menuscreen.gif", 128, 144);
	public static BufferedImage[][] BAR = load("/res/bar.gif", 128, 16);	
	public static BufferedImage[][] PLAYER = load("/res/playersprites.gif", 16, 16);
	
	public static BufferedImage[][] DIAMOND = load("/res/diamond.gif", 16, 16);
	public static BufferedImage[][] SPARKLE = load("/res/sparkle.gif", 16, 16);
	public static BufferedImage[][] ITEMS = load("/res/items.gif", 16, 16);

	public static BufferedImage[][] font = load("/res/font.gif", 8, 8); 
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++)
				for(int j = 0; j < width; j++)
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
			
			return ret;
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Loading Sprites.");
			System.exit(0);
		}
		return null;
	}
	
	public static void drawString(Graphics2D g, String s, int x, int y) {
		s = s.toUpperCase();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == 47) 
				c = 36; // slash
			if(c == 58) 
				c = 37; // colon
			if(c == 32) 
				c = 38; // space
			if(c >= 65 && c <= 90) 
				c -= 65; // letters
			if(c >= 48 && c <= 57) 
				c -= 22; // numbers
			int row = c / font[0].length;
			int col = c % font[0].length;
			g.drawImage(font[row][col], x + 8 * i, y, null);
		}
	}
	
}
