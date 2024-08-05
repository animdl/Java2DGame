import java.awt.*;
import java.util.*;

public class PlayState extends GameState {
	
	private Player player;
	
	private TileMap tileMap;
	
	private ArrayList<Diamond> diamonds;
	
	private ArrayList<Item> items;
	
	private ArrayList<Sparkle> sparkles;
	
	private int xsector, ysector, sectorSize; 
	
	private Hud hud;
	
	private boolean blockInput, eventStart, eventFinish;
	private int eventTick;
	
	private ArrayList<Rectangle> boxes;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		diamonds = new ArrayList<Diamond>();
		sparkles = new ArrayList<Sparkle>();
		items = new ArrayList<Item>();

		tileMap = new TileMap(16);
		tileMap.loadTiles("/res/testtileset.gif");
		tileMap.loadMap("/res/testmap.map");
		
		player = new Player(tileMap);
		
		populateDiamonds();
		populateItems();
		
		player.setTilePosition(17, 17);
		player.setTotalDiamonds(diamonds.size());
		
		sectorSize = MainPanel.WIDTH;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);
		
		hud = new Hud(player, diamonds);
		
		boxes = new ArrayList<Rectangle>();
		eventStart = true;
		eventStart();
			
	}
	
	private void populateDiamonds() {
		
		Diamond d;
		
		d = new Diamond(tileMap);
		d.setTilePosition(20, 20);
		d.addChange(new int[] { 23, 19, 1 });
		d.addChange(new int[] { 23, 20, 1 });
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(12, 36);
		d.addChange(new int[] { 31, 17, 1 });
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(28, 4);
		d.addChange(new int[] {27, 7, 1});
		d.addChange(new int[] {28, 7, 1});
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(4, 34);
		d.addChange(new int[] { 31, 21, 1 });
		diamonds.add(d);
		
		d = new Diamond(tileMap);
		d.setTilePosition(28, 19);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(35, 26);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(38, 36);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(27, 28);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(20, 30);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(14, 25);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(4, 21);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(9, 14);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(4, 3);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(20, 14);
		diamonds.add(d);
		d = new Diamond(tileMap);
		d.setTilePosition(13, 20);
		diamonds.add(d);
		
	}
	
	private void populateItems() {
		
		Item item;
		
		item = new Item(tileMap);
		item.setType(Item.AXE);
		item.setTilePosition(26, 37);
		items.add(item);
		
		item = new Item(tileMap);
		item.setType(Item.BOAT);
		item.setTilePosition(12, 4);
		items.add(item);
		
	}
	
	public void update() {
		handleInput();
		
		if(eventStart) 
			eventStart();
		if(eventFinish) 
			eventFinish();
		
		if(player.numDiamonds() == player.getTotalDiamonds())
			eventFinish = blockInput = true;
		
		int oldxs = xsector;
		int oldys = ysector;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
		tileMap.update();
		
		if(tileMap.isMoving()) 
			return;
		
		player.update();
		
		for(int i = 0; i < diamonds.size(); i++) {
			Diamond d = diamonds.get(i);
			d.update();
			
			if(player.intersects(d)) {
				
				diamonds.remove(i);
				i--;
				
				player.collectedDiamond();
				
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(d.getx(), d.gety());
				sparkles.add(s);
				
				ArrayList<int[]> ali = d.getChanges();
				for(int[] j : ali)
					tileMap.setTile(j[0], j[1], j[2]);
				
			}
		}

		for(int i = 0; i < sparkles.size(); i++) {
			Sparkle s = sparkles.get(i);
			s.update();
			if(s.shouldRemove()) {
				sparkles.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if(player.intersects(item)) {
				items.remove(i);
				i--;
				item.collected(player);
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(item.getx(), item.gety());
				sparkles.add(s);
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		tileMap.draw(g);
		
		player.draw(g);
		
		for(Diamond d : diamonds)
			d.draw(g);
		
		for(Sparkle s : sparkles)
			s.draw(g);
		
		for(Item i : items)
			i.draw(g);
		
		hud.draw(g);
		
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < boxes.size(); i++)
			g.fill(boxes.get(i));
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(true);
			
		if(blockInput) 
			return;
		if(Keys.isDown(Keys.LEFT)) 
			player.setLeft();
		if(Keys.isDown(Keys.RIGHT)) 
			player.setRight();
		if(Keys.isDown(Keys.UP)) 
			player.setUp();
		if(Keys.isDown(Keys.DOWN)) 
			player.setDown();
		if(Keys.isPressed(Keys.SPACE)) 
			player.setAction();
	}
	
	//===============================================
	
	private void eventStart() {
		eventTick++;
		if(eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 9; i++)
				boxes.add(new Rectangle(0, i * 16, MainPanel.WIDTH, 16));
		}
		if(eventTick > 1 && eventTick < 32) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0)
					r.x -= 4;
				else r.x += 4;
			}
		}
		if(eventTick == 33) {
			boxes.clear();
			eventStart = false;
			eventTick = 0;
		}
	}
	
	private void eventFinish() {
		eventTick++;
		if(eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 9; i++) {
				if(i % 2 == 0) 
					boxes.add(new Rectangle(-128, i * 16, MainPanel.WIDTH, 16));
				else boxes.add(new Rectangle(128, i * 16, MainPanel.WIDTH, 16));
			}
		}
		if(eventTick > 1) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0)
					if(r.x < 0) r.x += 4;
				else if(r.x > 0) r.x -= 4;
			}
		}
		if(eventTick > 33) {
			if(player.numDiamonds() == 15) {
				Data.setTime(player.getTicks());
				gsm.setState(GameStateManager.GAMEOVER);
			}
		}
	}
	
}
