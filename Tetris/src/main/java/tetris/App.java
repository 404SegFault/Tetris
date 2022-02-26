package tetris;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class App extends PApplet {
	/////////////////////////////////ATTRIBUTES////////////////////////////////////
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;
    public static final int GRIDSPACE = 32;
    public static final int FPS = 60;
	private PFont font;

	private JSONObject config;

	private HashMap<String, PImage> allSprites = new HashMap<>();
	private ArrayList<Block> allBlocks = new ArrayList<>();
	private DropTimer dropTimer;
	private int dropMilliseconds = 2000;

	private String[] colours = new String[]{"DarkBlue","Green","LightBlue","Orange","Purple","Red","Yellow"};

	
	private Block moveableBlock;

	/////////////////////////////CREATING THE APP OBJECT//////////////////////////
    public void settings() {
        size(WIDTH, HEIGHT);
    }

	/**Loads the files inside the resources into the application, where it can be used in the game**/
    public void setup() {

        frameRate(FPS);

		allSprites.put("DarkBlue", loadImage("Tiles/tileDarkBlue.png"));
		allSprites.put("Green", loadImage("Tiles/tileGreen.png"));
		allSprites.put("LightBlue", loadImage("Tiles/tileLightBlue.png"));
		allSprites.put("Orange", loadImage("Tiles/tileOrange.png"));
		allSprites.put("Purple", loadImage("Tiles/tilePurple.png"));
		allSprites.put("Red", loadImage("Tiles/tileRed.png"));
		allSprites.put("Yellow", loadImage("Tiles/tileYellow.png"));

		config = loadJSONObject("config.json");
		// this.timeRemaining = config.getJSONArray("levels").getJSONObject(0).getInt("time"); maybe a level system
		
		Timer timer = new Timer();

		//the drop timer handles all the blocks falling
		this.dropTimer = new DropTimer(this);
		// uses the drop timer, (dropTimer, times it counts town, total rundown time)
		timer.schedule(dropTimer, 0, dropMilliseconds);

		Block block = new Block(allSprites.get("DarkBlue"), 320, 0, "DarkBlue");
		this.moveableBlock = block;
		this.allBlocks.add(block);

		this.font = createFont("PressStart2P-Regular.ttf", 20);
		this.textFont(this.font);
		fill(0); //makes the text black
    }
	
	/////////////////////////METHODS USED WHEN RUNNING THE GAME////////////////////
	/** Decriments the timer every second, moves enemies**/
	public void tick(){
		this.frameCount++;

		// if a second passes
		if (frameCount % 60 == 0){

		}

		// FIXME THIS IS SO FUCKING BAD, IT SHOULDNT HAVE TO CHECK EVERY TICK BUT IDK WHERE ELSE
		// goes through all the blocks
		// for (int i = 0; i < this.allBlocks.size(); i++){

		// 	// goes through all the blocks and checks whether it is set or not if it is not set then you can still move it
		// 	if (this.allBlocks.get(i).getSet() == false){
		// 		this.moveableBlock = this.allBlocks.get(i);
		// 	}
		// }

		if (moveableBlock.getYCoord() >= 608){
			moveableBlock.setBlock();
			Random rand = new Random();
			String colour = colours[rand.nextInt(7)];

			Block block = new Block(allSprites.get(colour), 320, 0, colour);
			this.moveableBlock = block;
			this.allBlocks.add(block);
		}
	}

	/** Goes through all the objects and draws them **/
    public void draw() {
		this.tick();
		this.background(112, 123, 138);

		for (int i = 0; i < this.allBlocks.size(); i++){
			allBlocks.get(i).draw(this);
		}

		//---------------------DRAWING THE OBJECTS------------------------
		
    }

	/** detects when a key is pressed and calls the player method for movement **/
	public void keyPressed(){
		switch (keyCode){
			case PApplet.LEFT:
				if (moveableBlock.getXCoord() >= 0){
					moveableBlock.moveLeft();
				}break;
				
			case PApplet.RIGHT:
				if (moveableBlock.getXCoord() <= 640){
					moveableBlock.moveRight();
				}break;

			case PApplet.DOWN:
				moveableBlock.moveDown();
				break;
				
		}
	}
	
	/** changes can move to true so that the player can move again**/
	public void keyReleased(){}

	/////////////////////////////////GAME SCREENS//////////////////////////////////////
	public void displayGameOver(){
		background(112, 123, 138);
		this.text("GAME OVER", WIDTH/2 - App.GRIDSPACE * 4 + 16, HEIGHT/2);
	}

	public void displayYouWin(){
		background(112, 123, 138);
		this.text("YOU WIN", WIDTH/2 - App.GRIDSPACE * 3 + 16, HEIGHT/2);
	}

	/////////////////////////////GETTERS AND SETTERS//////////////////////////////////

    public static void main(String[] args) {
        PApplet.main("tetris.App");
    }

	public ArrayList<Block> getAllBlocks(){return this.allBlocks;}
}

// generate random colour
// Random rand = new Random();

// String colour = this.colours[rand.nextInt(7)];

// Block block = new Block(allSprites.get(colour), 320, 0, colour);
// this.allBlocks.add(block);

// for (int i = 0; i < this.allBlocks.size(); i++){
// 		this.allBlocks.get(i).moveDown();
// 	}