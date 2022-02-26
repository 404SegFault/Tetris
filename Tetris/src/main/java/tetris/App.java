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

    public static final int GRIDWIDTH = 8;
    public static final int GRIDHEIGHT = 16;


	public static final int TOP = 64;
	public static final int LEFT = 160;
	public static final int RIGHT = 448;
	public static final int BOTTOM = 544;

	private PFont font;
	private HashMap<String, PImage> allSprites = new HashMap<String, PImage>();
	private ArrayList<GameObject> allObjects;
	private JSONObject config;

	private ArrayList<Block> allBlocks = new ArrayList<>();
	private DropTimer dropTimer;
	private int dropMilliseconds = 2000;

	private String[] colours = new String[]{"DarkBlue","Green","LightBlue","Orange","Purple","Red","Yellow"};

	private Block moveableBlock;

	private Piece piece;

	/////////////////////////////CREATING THE APP OBJECT//////////////////////////
    public void settings() {
        size(WIDTH, HEIGHT);
    }

	/**Loads the files inside the resources into the application, where it can be used in the game**/
    public void setup() {
        frameRate(FPS);

		for (String colour : this.colours){
			allSprites.put(colour, loadImage("Tiles/tile" + colour + ".png"));
		}
		allSprites.put("test_sprite", loadImage("Tiles/checkerboard.png"));

		allSprites.put("Red_Red", loadImage("pills/Red_Red.png"));
		allSprites.put("Blue_Blue", loadImage("pills/Blue_Blue.png"));
		allSprites.put("Blue_Green", loadImage("pills/Blue_Green.png"));
		allSprites.put("Blue", loadImage("pills/Blue.png"));
		allSprites.put("Green_Green", loadImage("pills/Green_Green.png"));
		allSprites.put("Green_Red", loadImage("pills/Green_Red.png"));

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

		Block block = new Block(allSprites.get("DarkBlue"), 320, 64, "DarkBlue");
		this.moveableBlock = block;
		this.piece = new Piece(allSprites, allSprites.get("Red_Red"),320, 320, "Red", "Red");

		this.allBlocks.add(block);

		this.font = createFont("PressStart2P-Regular.ttf", 20);
		this.textFont(this.font);
		fill(0); //makes the text black
    }
	
	/////////////////////////METHODS USED WHEN RUNNING THE GAME////////////////////
	public void drawUI(){
		background(112, 123, 138);
	}
	// method to check if blocks are stacked on each other (on top)
	public void hardDrop(){

		Block highestBlock = null;
		int lowestY = -App.GRIDSPACE; //no block can ever be higher than -32

		// goes through all the blocks 
		for(Block b : allBlocks){

			// checks all blocks that have the same x coordinate, and is higher than the lowest block?
			if(b.getXCoord() == moveableBlock.getXCoord() && b.getYCoord() > lowestY && b.getSet()){
				highestBlock = b;
			}
			
		}

		// if it couldnt find a block that fits then the block should teleport straight to the bottom
		if (highestBlock == null){
			moveableBlock.setCoord(moveableBlock.getXCoord(), BOTTOM);
			System.out.println("no blocks below this one");
		}
		else {
			moveableBlock.setCoord(moveableBlock.getXCoord(), highestBlock.getYCoord() - App.GRIDSPACE);
			System.out.println("blocks below");
		}

		generateNewMoveable();
	}

	public boolean blockStacked(){

		// goes through all the blocks 
		for(Block b : allBlocks){

			// checks if the block is stacked above it and that the 
			if(b.getYCoord() - 32 == moveableBlock.getYCoord() && b.getXCoord() == moveableBlock.getXCoord()){
				// if it matches 
				return true;
			}
			
		}
		return false;
	}

	/** Decrements the timer every second, moves enemies**/
	public void tick(){
		this.frameCount++;

		if (moveableBlock.getYCoord() >= BOTTOM || blockStacked()){
			generateNewMoveable();
		}
	}

	private void generateNewMoveable(){
		//sets the current block
		moveableBlock.setBlock();

		// randomly chooses a colour
		Random rand = new Random();
		String colour = colours[rand.nextInt(7)];

		// gets a new block based on the colour
		Block block = new Block(allSprites.get(colour), 320, 64, colour);
		System.out.println(block.toString());
		moveableBlock = block;
		System.out.println(block.toString());
		
		allBlocks.add(block);
	}

	/** Goes through all the objects and draws them **/
    public void draw() {
		this.tick();
		this.background(loadImage("Tiles/checkerboard.png"));

		for (int i = 0; i < this.allBlocks.size(); i++){
			allBlocks.get(i).draw(this);
		}


		// this.piece.draw(this);
		//---------------------DRAWING THE OBJECTS------------------------
    }

	/** detects when a key is pressed and calls the player method for movement **/
	public void keyPressed(){

		// before it is allowed to move to the open space it needs to check all of the coordinates in the space where it wants to go
		// if there is a preexisting block in the new position it wont let it go there
		int[] newCoords = this.moveableBlock.getCoords().clone();

		switch (keyCode){
			case PApplet.LEFT:
				newCoords[0] -= App.GRIDSPACE;
				break;
				
			case PApplet.RIGHT:
				newCoords[0] += App.GRIDSPACE;
				break;

			case PApplet.DOWN:
				newCoords[1] += App.GRIDSPACE;
				break;

			case ' ':
				hardDrop();
		}

		// if it doesnt collide with any blocks then it can go into that position
		if (blockSideCollision(newCoords) == false && keyCode != ' '){
			this.moveableBlock.setCoord(newCoords);
		}
	}

	public boolean blockSideCollision(int[] coords){
		for (Block block : allBlocks){

			if (Arrays.equals(coords, block.getCoords()) || coords[0] == RIGHT || coords[0] == LEFT){
				return true;
			}
		}

		return false;
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

	public void loadLevel(String filepath){
		//App.GRIDSPACE * 2 offset from where the game map actually starts
		map = new char[HEIGHT][WIDTH];
		try {
			// -------------------------LOADING THE MAIN MAP--------------------------
			File f = new File(filepath);
			Scanner sc = new Scanner(f);
		
			int rowIndex = 0;
			// scans the entire map
			while (sc.hasNext()){
				String row =  sc.nextLine();
				char[] rowInCharacters = row.toCharArray(); 
				// sets the row to the characters
				map[rowIndex] = rowInCharacters;
				rowIndex ++;
			}
		}
		catch (FileNotFoundException e) { 
 			e.printStackTrace();
		}

		int cursorX = LEFT;
		int cursorY = TOP;

		//--------------------------------LOADING THE SPRITES FROM THE MAP------------------------------------
		
		for (char[] row : map){
			for (char symbol: row){
				Block virus = null;

				// spawns the appropriate sprite based on the symbol
				if (symbol == 'R'){
					virus = new Block(this.allSprites.get("Red_Virus"), cursorX, cursorY);
				} 
				if (symbol == 'B'){
					virus = new Block(this.allSprites.get("Blue_Virus"), cursorX, cursorY);
				} 
				if (symbol == 'G'){
					virus = new Block(this.allSprites.get("Green_Virus"), cursorX, cursorY);
				} 

				//adds the virus to the drawing list and moves to the next position of where the sprite should spawn 
				allBlocks.add(virus);
				cursorX += App.GRIDSPACE;
			}
			// resets the spawining thing to the left again and moves down the cursor
			cursorX = LEFT;
			cursorY += App.GRIDSPACE;
		}
	}

	/////////////////////////////GETTERS AND SETTERS//////////////////////////////////

    public static void main(String[] args) {
        PApplet.main("tetris.App");
    }

	public ArrayList<Block> getAllBlocks(){return this.allBlocks;}

	public Block getMoveableBlock(){return this.moveableBlock;}
}

