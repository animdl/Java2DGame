import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class TileMap {
	
	private int x, y, xdest, ydest, speed;
	private boolean moving;
	
	private int xmin, ymin, xmax, ymax;
	
	private int[][] map;
	private int tileSize, numRows, numCols, width, height;
	
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	private int rowOffset, colOffset, numRowsToDraw, numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = MainPanel.HEIGHT / tileSize + 2;
		numColsToDraw = MainPanel.WIDTH / tileSize + 2;
		speed = 4;
	}
	
	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(s)));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = MainPanel.WIDTH - width;
			xmin = -width;
			xmax = 0;
			ymin = MainPanel.HEIGHT - height;
			ymin = -height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++)
					map[row][col] = Integer.parseInt(tokens[col]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTileSize() { 
		return tileSize; 
	}
	
	public int getx() { 
		return x;
	}
	
	public int gety() { 
		return y; 
	}
	
	public int getWidth() { 
		return width; 
	}
	
	public int getHeight() { 
		return height; 
	}
	
	public int getNumRows() { 
		return numRows; 
	}
	
	public int getNumCols() { 
		return numCols; 
	}
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public int getIndex(int row, int col) {
		return map[row][col];
	}
	
	public boolean isMoving() { 
		return moving; 
	}
	
	public void setTile(int row, int col, int index) {
		map[row][col] = index;
	}
	
	public void replace(int i1, int i2) {
		for(int row = 0; row < numRows; row++)
			for(int col = 0; col < numCols; col++)
				if(map[row][col] == i1) map[row][col] = i2;
	}
	
	public void setPosition(int x, int y) {
		xdest = x;
		ydest = y;
	}
	public void setPositionImmediately(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fixBounds() {
		if(x < xmin) 
			x = xmin;
		if(y < ymin) 
			y = ymin;
		if(x > xmax) 
			x = xmax;
		if(y > ymax) 
			y = ymax;
	}
	
	public void update() {
		if(x < xdest) {
			x += speed;
			if(x > xdest)
				x = xdest;
		}
		if(x > xdest) {
			x -= speed;
			if(x < xdest)
				x = xdest;
		}
		if(y < ydest) {
			y += speed;
			if(y > ydest)
				y = ydest;
		}
		if(y > ydest) {
			y -= speed;
			if(y < ydest)
				y = ydest;
		}
		
		fixBounds();
		
		colOffset = -this.x / tileSize;
		rowOffset = -this.y / tileSize;
		
		if(x != xdest || y != ydest) 
			moving = true;
		else moving = false;
		
	}
	
	public void draw(Graphics2D g) {
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) 
				break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) 
					break;
				if(map[row][col] == 0) 
					continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), x + col * tileSize, y + row * tileSize, null);
			}
		}
	}
}