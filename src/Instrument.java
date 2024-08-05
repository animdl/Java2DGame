import java.awt.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class Instrument {
    private String name, description;
    private BufferedImage image;
    private ArrayList<Instrument> children;
    
    public Instrument(String name, String description) {
        this.name = name;
        this.description = description;
        try {
                Image img = ImageIO.read(getClass().getResourceAsStream("/res/"+name+".png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);
                image = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
                Graphics bGr = image.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();
            }
        catch(Exception e){
                System.out.println(name);
                e.printStackTrace();
            }
        children = new ArrayList<Instrument>();
    }
    
    public String getName() {return name;}
    public String getDescription() {return description;}
    public BufferedImage getImage() {return image;}
    public ArrayList<Instrument> getChildren() {return children;}
    
    public void add(Instrument i) {
        children.add(i);
    }
}