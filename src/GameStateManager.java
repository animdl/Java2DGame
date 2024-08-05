import java.awt.*;


public class GameStateManager {
	
	private boolean paused;
	private PauseState pauseState;
	
	private GameState[] gameStates;
	private int currentState;
	private int previousState;
        private int level;
        private Instrument instrument;
	
	public static final int NUM_STATES = 13;
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
        public static final int PLAY2 = 3;
        public static final int PLAY3 = 4;
        public static final int PLAY4 = 5;
        public static final int INTERTITLE = 6;
        public static final int INTERTITLE_1 = 7;
        public static final int INTERTITLE_2 = 8;
        public static final int INTERTITLE_3 = 9;
        public static final int INTERTITLE_4 = 10;
        public static final int UPGRADE = 11;
	public static final int GAMEOVER = 12;
	
	public GameStateManager() {
		paused = false;
		pauseState = new PauseState(this);
		
		gameStates = new GameState[NUM_STATES];
		setState(INTRO);
		
                level = 0;
                
                Instrument pen = new Instrument("Composer's Pen","Does not come with ink");
                    Instrument baton = new Instrument("Conductor's Baton","A wavy stick"); pen.add(baton);
                        Instrument bow = new Instrument("Bow","Wiggles strings"); baton.add(bow);
                                Instrument violin = new Instrument("Violin","Gets the melody"); bow.add(violin);
                                Instrument viola = new Instrument("Viola","What is a viola"); bow.add(viola);
                                Instrument cello = new Instrument("Cello","Also gets the melody"); bow.add(cello);
                                Instrument bass = new Instrument("Bass","Rumbles in the distance"); bow.add(bass);

                            Instrument recorder = new Instrument("Recorder","Plays memes"); baton.add(recorder);
                                Instrument piccolo = new Instrument("Piccolo","A small flute"); recorder.add(piccolo);
                                Instrument flute = new Instrument("Flute","A big piccolo"); recorder.add(flute);
                                Instrument corAnglais = new Instrument("Cor Anglais","English horn in another language"); recorder.add(corAnglais);
                                Instrument oboe = new Instrument("Oboe","Sounds like a duck"); recorder.add(oboe);
                                Instrument clarinet = new Instrument("Clarinet","Squidward's clarinet"); recorder.add(clarinet);
                                Instrument bassoon = new Instrument("Bassoon","Also called a fagott in German"); recorder.add(bassoon);

                            Instrument bugle = new Instrument("Bugle","An old trumpet"); baton.add(bugle);
                                Instrument horn = new Instrument("French Horn","The swirly horn"); bugle.add(horn);
                                Instrument trumpet = new Instrument("Trumpet","A new trumpet"); bugle.add(trumpet);
                                Instrument trombone = new Instrument("Trombone","Comes with a slide"); bugle.add(trombone);
                                Instrument tuba = new Instrument("Tuba","Doesn't make farting noises"); bugle.add(tuba);

                            Instrument mallet = new Instrument("Mallet","Hits Things"); baton.add(mallet);
                                Instrument timpani = new Instrument("Timpani","Comes in a pack of four"); mallet.add(timpani);
                                Instrument triangle = new Instrument("Triangle","Three sides and angles"); mallet.add(triangle);
                                Instrument bassDrum = new Instrument("Bass Drum","A large drum"); mallet.add(bassDrum);
                                Instrument cymbals = new Instrument("Cymbals","Crashes the orchestra"); mallet.add(cymbals);
                instrument = pen;
	}
	
	public void setState(int state) {
		previousState = currentState;
		unloadState(previousState);
		currentState = state;
		
                
		switch(state) {
			case INTRO : {
				gameStates[state] = new IntroState(this);
				gameStates[state].init();
			} break;
			case MENU : {
				gameStates[state] = new MenuState(this);
				gameStates[state].init();
			} break;
			case PLAY : {
				gameStates[state] = new PlayState(this);
				gameStates[state].init();
			} break;
                        case PLAY2 : {
				gameStates[state] = new PlayState2(this);
				gameStates[state].init();
			} break;
                        case PLAY3 : {
				gameStates[state] = new PlayState3(this);
				gameStates[state].init();
			} break;
                        case PLAY4 : {
				gameStates[state] = new PlayState4(this);
				gameStates[state].init();
			} break;
                        case INTERTITLE_1 : {
				gameStates[state] = new IntertitleState(this,1);
				gameStates[state].init();
			} break;
                        case INTERTITLE_2 : {
				gameStates[state] = new IntertitleState(this,2);
				gameStates[state].init();
			} break;
                        case INTERTITLE_3 : {
				gameStates[state] = new IntertitleState(this,3);
				gameStates[state].init();
			} break;
                        case INTERTITLE_4 : {
				gameStates[state] = new IntertitleState(this,4);
				gameStates[state].init();
			} break;
                        case UPGRADE : {
				gameStates[state] = new UpgradeState(this,instrument);
				gameStates[state].init();
			} break;
			case GAMEOVER : {
				gameStates[state] = new GameOverState(this);
				gameStates[state].init();
			} break;
		}
	}
	
	public void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setPaused(boolean b) {
		paused = b;
	}
	
	public void update() {
		if(paused) {
			pauseState.update();
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	public void draw(Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}
	
        public int nextIntertitle() {
            level++;
            return INTERTITLE_1+level-1;
        }
        
        public int nextLevel() {
            return PLAY+level-1;
        }
        
        public int getLevel() {return level;}
        
        public void setInstrument(Instrument i) {instrument = i;}
}
