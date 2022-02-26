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
	private HashMap<String, PImage> allSprites = new HashMap<String, PImage>();
	private ArrayList<GameObject> allObjects;
	private JSONObject config;

	private ArrayList<Block> allBlocks = new ArrayList<>();
	private DropTimer dropTimer;
	private int dropMilliseconds = 2000;

	private String[] colours = new String[]{"DarkBlue","Green","LightBlue","Orange","Purple","Red","Yellow"};

	private Piece newPill; //For testing purposes

	// private Block moveableBlock;
	// private Block notMoveBlock; //This is for testing

	//private Piece piece;


	/////////////////////////////CREATING THE APP OBJECT//////////////////////////
    public void settings() {
        size(WIDTH, HEIGHT);
    }

	/**Loads the files inside the resources into the application, where it can be used in the game**/
    public void setup() {
        frameRate(FPS);

		allSprites.put("test_sprite", loadImage("Tiles/checkerboard.png"));

		// allSprites.put("DarkBlue", loadImage("Tiles/tileDarkBlue.png"));
		// allSprites.put("Green", loadImage("Tiles/tileGreen.png"));
		// allSprites.put("LightBlue", loadImage("Tiles/tileLightBlue.png"));
		// allSprites.put("Orange", loadImage("Tiles/tileOrange.png"));
		// allSprites.put("Purple", loadImage("Tiles/tilePurple.png"));
		// allSprites.put("Red", loadImage("Tiles/tileRed.png"));
		// allSprites.put("Yellow", loadImage("Tiles/tileYellow.png"));

		allSprites.put("Red_Red", loadImage("pills/Red_Red.png"));
		allSprites.put("Blue_Blue", loadImage("pills/Blue_Blue.png")); 
		allSprites.put("Green_Green", loadImage("pills/Green_Green.png"));

		allSprites.put("Blue_Green", loadImage("pills/Blue_Green.png"));
		allSprites.put("Green_Red", loadImage("pills/Green_Red.png"));
		allSprites.put("Red_Blue", loadImage("pills/Red_Blue.png"));

		allSprites.put("Blue", loadImage("pills/Blue.png"));
		allSprites.put("Red", loadImage("pills/Red.png"));
		allSprites.put("Green", loadImage("pills/Green.png"));

		/*
		FUllpill color = color1 + "_" + color2;
		if(in array) => get sprite and generate piece
		color = color2 + "_"+ color1; => get sprite and generate

		drop, set
		once set x and y for both halfs, halfPill object for both halves

		B
		G B

		*/
	

		this.allSprites = allSprites;

		String[] pillFiles = new String[]{"Red_Red", "Blue_Blue", "Blue_Green", "Blue", "Green_Green", "Green_Red", "Green", "Red_Blue", "Red_Red", "Red"};

		// for (int i = 0; i <  pillFiles.size(); i++){
		// 	allSprites.put(pillFiles, loadImage("pills/" + pillFiles + ".png"));
		// }

		config = loadJSONObject("config.json");
		// this.timeRemaining = config.getJSONArray("levels").getJSONObject(0).getInt("time"); maybe a level system
		
		Timer timer = new Timer();

		//the drop timer handles all the blocks falling
		this.dropTimer = new DropTimer(this);
		// uses the drop timer, (dropTimer, times it counts town, total rundown time)
		timer.schedule(dropTimer, 0, dropMilliseconds);

		this.newPill = new Piece(allSprites, allSprites.get("Blue_Green"), 64, 32, "Blue", "Green");

		// Block newBlock = new Block(allSprites.get("Red"), 320, 32, "Red");
		// Block newBlock2 = new Block(allSprites.get("Blue"), 288, 32, "Red");
		// this.notMoveBlock = newBlock2;
		// this.notMoveBlock.setBlock();

		// this.notMoveBlock.doRotate(-2);

		// this.moveableBlock = newBlock;
		// this.moveableBlock.setBlock();
		// //this.piece = new Piece(allSprites, allSprites.get("Red_Red"),320, 320, "Red", "Red");

		// this.allBlocks.add(newBlock);
		// this.allBlocks.add(newBlock2);

		this.font = createFont("PressStart2P-Regular.ttf", 20);
		this.textFont(this.font);
		fill(0); //makes the text black
    }
	
	/////////////////////////METHODS USED WHEN RUNNING THE GAME////////////////////
	public void drawUI(){
		background(112, 123, 138);
		for(GameObject gameObject : this.allObjects){ gameObject.draw(this); }

	}

	/** Decriments the timer every second, moves enemies**/
	public void tick(){
		this.frameCount++;

		// if a second passes
		if (frameCount % 60 == 0){
			
		}

		// if (moveableBlock.getYCoord() >= 608){
		// 	moveableBlock.setBlock();
		// 	Random rand = new Random();
		// 	String colour = colours[rand.nextInt(7)];

		// 	Block block = new Block(allSprites.get(colour), 320+16, 0, colour);
		// 	this.moveableBlock = block;
		// 	this.allBlocks.add(block);
		// }
	}

	/** Goes through all the objects and draws them **/
    public void draw() {
		this.tick();
		this.background(this.allSprites.get("test_sprite"));

		for (int i = 0; i < this.allBlocks.size(); i++){
			allBlocks.get(i).draw(this);
		}
		newPill.draw(this);

		//---------------------DRAWING THE OBJECTS------------------------
    }

	/** detects when a key is pressed and calls the player method for movement **/
	public void keyPressed(){
		//System.out.println(keyCode);
		switch (keyCode){
			case PApplet.LEFT:
				newPill.pieceLeftMove();
				break;
				
			case PApplet.RIGHT:
				newPill.pieceRightMove();
				break;

			case PApplet.DOWN:
				newPill.pieceDownMove();
				break;
			
			case 88: //x for Clockwise	
				System.out.println("clockwise");	
				newPill.pieceCWRotation();
				break;

			case 90: //z for AntiClockwise
				System.out.println("anticlockwise");
				newPill.pieceCCWRotation();
				break;
		}
	}
	
	/** changes can move to true so that the player can move again**/
	public void keyReleased(){ 
	}

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

