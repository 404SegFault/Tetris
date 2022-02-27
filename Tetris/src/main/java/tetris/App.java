package tetris;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;
import java.io.*;

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

	//private String[] colours = new String[]{"DarkBlue","Green","LightBlue","Orange","Purple","Red","Yellow"};
	private String[] colours = new String[]{"Blue","Green","Red"};

	private Piece moveablePiece;
	private Block virus;

	//private Piece piece;

	/////////////////////////////CREATING THE APP OBJECT//////////////////////////
    public void settings() {
        size(WIDTH, HEIGHT);
    }

	/**Loads the files inside the resources into the application, where it can be used in the game**/
    public void setup() {
        frameRate(FPS);

		// for (String colour : this.colours){
		// 	allSprites.put(colour, loadImage("Tiles/tile" + colour + ".png"));
		// }
		allSprites.put("test_sprite", loadImage("Tiles/checkerboard.png"));

		String[] pillFiles = new String[]{"Red_Red", "Blue_Blue", "Blue_Green", "Blue", "Green_Green", "Green_Red", "Green", "Red_Blue", "Red_Red", "Red"};

		for (String pillFile : pillFiles){
			allSprites.put(pillFile, loadImage("pills/" + pillFile + ".png"));
		}
		// for (int i = 0; i <  pillFiles.size(); i++){
		// 	allSprites.put(pillFiles, loadImage("pills/" + pillFiles + ".png"));
		// }
		allSprites.put("Blue_virus", loadImage("viruses/Blue_virus.png"));
		allSprites.put("Green_virus", loadImage("viruses/Green_virus.png"));
		allSprites.put("Red_virus", loadImage("viruses/Red_virus.png"));

		// config = loadJSONObject("config.json");
		// this.timeRemaining = config.getJSONArray("levels").getJSONObject(0).getInt("time"); maybe a level system
		
		Timer timer = new Timer();

		loadLevel("level1.txt");

		// this.virus = new Block(allSprites.get("Green_virus"), 320, 64, "Green");
		// this.allBlocks.add(virus);

		Piece newPiece = new Piece(allSprites, allSprites.get("Blue_Green"), 320, 64, "Blue", "Green");
		this.moveablePiece = newPiece;
		generateNewMoveable();
		//this.piece = new Piece(allSprites, allSprites.get("Red_Red"),320, 320, "Red", "Red");

		//this.allBlocks.add(block);

		this.font = createFont("PressStart2P-Regular.ttf", 20);
		this.textFont(this.font);
		fill(0); //makes the text black

		//the drop timer handles all the blocks falling
		this.dropTimer = new DropTimer(this);
		// uses the drop timer, (dropTimer, times it counts town, total rundown time)
		timer.schedule(dropTimer, 0, dropMilliseconds);
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
		for(Block pillHalf : moveablePiece.getBothHalves()){ 
			highestBlock = null;
			ArrayList<Block> sameXBlocks = new ArrayList<>();

			for(Block b : allBlocks){
				if(b.getXCoord() == pillHalf.getXCoord() && pillHalf.getYCoord() < b.getYCoord()){ sameXBlocks.add(b); }
			}

			if(sameXBlocks.size() != 0){
				highestBlock = sameXBlocks.get(0);
				for(Block b : sameXBlocks){
					if(b.getYCoord() < highestBlock.getYCoord()){ highestBlock = b; }
				}
			}
			
			// if it couldnt find a block that fits then the block should teleport straight to the bottom
	
			if (highestBlock == null){
				
				pillHalf.setCoord(pillHalf.getXCoord(), BOTTOM);
				System.out.println("no blocks below this one");
			}
			else {
				System.out.printf("%d, %d\n", pillHalf.getXCoord(), pillHalf.getYCoord());
				pillHalf.setCoord(pillHalf.getXCoord(), highestBlock.getYCoord() - App.GRIDSPACE);
				System.out.println("blocks below");
			}
			allBlocks.add(pillHalf);
		}

		setPiece();
	}

	public boolean blockStacked(){

		// goes through all the blocks 
		for(Block b : allBlocks){

			// checks if the block is stacked above it and that the 
			if(b.getYCoord() - 32 == moveablePiece.getYCoord() && b.getXCoord() == moveablePiece.getXCoord()){
				// if it matches 
				return true;
			}
			
		}
		return false;
	}

	/** Decrements the timer every second, moves enemies**/
	public void tick(){
		this.frameCount++;

		if (moveablePiece.checkVirusUnder(allBlocks) == true){
			hardDrop();
			//Insert pattern checkcode here
			checkForMatch();
			generateNewMoveable();
		}
	}


	private void checkForMatch(){

		//Checking bottom

		
		System.out.printf("Bottom Match -- %s : %d\n",moveablePiece.getLeftHalf().getColour(),matchBottom(moveablePiece.getLeftHalf()).size());
		System.out.printf("Left Match -- %s : %d\n",moveablePiece.getLeftHalf().getColour(),matchLeft(moveablePiece.getLeftHalf()).size());
		System.out.printf("Right Match -- %s : %d\n",moveablePiece.getLeftHalf().getColour(),matchRight(moveablePiece.getLeftHalf()).size());

		System.out.printf("Bottom Match -- %s : %d\n",moveablePiece.getRightHalf().getColour(),matchBottom(moveablePiece.getRightHalf()).size());
		System.out.printf("Left Match -- %s : %d\n",moveablePiece.getRightHalf().getColour(),matchLeft(moveablePiece.getRightHalf()).size());
		System.out.printf("Right Match -- %s : %d\n",moveablePiece.getRightHalf().getColour(),matchRight(moveablePiece.getRightHalf()).size());

	}

	private List<Block> matchBottom(Block node){

		List<Block> toBeRemoved = new ArrayList<Block>();
		toBeRemoved.add(node);

		for(Block b : allBlocks){
			if(node.getXCoord() == b.getXCoord() && node.getYCoord() == b.getYCoord() - App.GRIDSPACE){
				if(node.getColour() == b.getColour()){
					toBeRemoved.addAll(matchBottom(b));
				}
			}
		}
		return toBeRemoved;
	}	

	private List<Block> matchRight(Block node){

		List<Block> toBeRemoved = new ArrayList<Block>();
		toBeRemoved.add(node);

		for(Block b : allBlocks){
			if(node.getYCoord() == b.getYCoord() && node.getXCoord() == b.getXCoord() + App.GRIDSPACE){
				if(node.getColour() == b.getColour()){
					toBeRemoved.addAll(matchBottom(b));
				}
			}
		}
		return toBeRemoved;
	}	

	private List<Block> matchLeft(Block node){

		List<Block> toBeRemoved = new ArrayList<Block>();
		toBeRemoved.add(node);

		for(Block b : allBlocks){
			if(node.getYCoord() == b.getYCoord() && node.getXCoord() == b.getXCoord() - App.GRIDSPACE){
				if(node.getColour() == b.getColour()){
					toBeRemoved.addAll(matchBottom(b));
				}
			}
		}
		return toBeRemoved;
	}		


	// private void checkForMatch(){
	// 	int[] cursor = {LEFT, TOP};
	// 	int adjacentBlocks = 0;

	// 	ArrayList<int[]> coordsToRemove = new ArrayList<int[]>();

	// 	// while the y cursor is still not at the bottom
	// 	while (cursor[1] < BOTTOM){

	// 		// the x cursor goes as much right as possible before hitting the border
	// 		while (cursor[0] <= RIGHT) {
				
	// 			// needs to check the row it is currently on by giving the Y 
	// 			// FIXME POTENTIAL BUG BECAUSE IT MIGHT PASS A REFERECE TO IT AND FUCK UP THE POSITIONING INSIDE THE FUNCTION? 
	// 			int finalXPosition = runsTo(cursor);
				
	// 			adjacentBlocks = (finalXPosition - adjacentBlocks + 1) % App.GRIDSPACE;

	// 			if (adjacentBlocks >= 4){
	// 				for (int i = 0 ; i < adjacentBlocks; i++){
	// 					coordsToRemove.add(cursor);
	// 				}
	// 			}


	// 		}
		
	// 		cursor[0] = LEFT;
	// 		cursor[1] += App.GRIDSPACE;
	// 	}

	// 	for (int[] coord : coordsToRemove){
	// 		System.out.println(findBlock(coord).toString());
	// 		// FIXME there can probably 2 coordinates and it would try to remove something that doesnt exist 
	// 		allBlocks.remove(findBlock(coord));
	// 	}
	// } 

	private int runsTo(int[] cursor) {
		// FIXME NEED TO CHECK IF THIS POINTS TO THE VALUE INSIDE THE CURSOR OR IS A NEW VALUE ?
		// int xPosition = cursor[0];

		while (cursor[0] < RIGHT) {

			Block currentBlock = findBlock(cursor);
			Block blockToTheRight = findBlock(new int[]{cursor[0] + 32, cursor[1]});
			
			// finds the block it is currently on and matches the colour
			if (currentBlock.sameColourAs(blockToTheRight) == false) {
				return cursor[0];
			}

			cursor[0] += App.GRIDSPACE;
		}

		// if you cant find it then return the end of the screen
		return cursor[0];
	}

	private Block findBlock(int[] coords){
		// updates this object with the newest one
		for (Block block : allBlocks){
			if (Arrays.equals(block.getCoords(), coords)){
				return block;
			}
		}

		return null;
	}


	private void setPiece(){
		//sets the current block
		Block pieceLeftHalf = moveablePiece.getLeftHalf();
		Block pieceRightHalf = moveablePiece.getRightHalf();

		pieceLeftHalf.setBlock(); 
		pieceRightHalf.setBlock();
	}

	private void generateNewMoveable(){
		
		//

		// randomly chooses a colour
		Random rand = new Random();
		String colour1 = colours[rand.nextInt(3)];
		String colour2 = colours[rand.nextInt(3)];

		// gets a new block based on the colour

		String nextColour = colour1 + "_" + colour2;
		
		if(allSprites.get(nextColour) == null){
			nextColour = colour2 + "_"+ colour1;
		}
		
		Piece newPiece = new Piece(allSprites, allSprites.get(nextColour), 320, 64, colour1, colour2);
		moveablePiece = newPiece;
		
	}

	/** Goes through all the objects and draws them **/
    public void draw() {
		this.tick();
		this.background(loadImage("Tiles/checkerboard.png"));
		moveablePiece.draw(this);

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

		switch (keyCode){
			case PApplet.LEFT:
				moveablePiece.pieceLeftMove(allBlocks);
				break;
			
			case PApplet.RIGHT:
				moveablePiece.pieceRightMove(allBlocks);
				break;

			case PApplet.DOWN:
				moveablePiece.pieceDownMove();
				break;
			
			case PApplet.UP:
				System.out.println("clockwise");	
					moveablePiece.pieceCWRotation();
					break;

			case 88: //x for Clockwise	
				System.out.println("clockwise");	
				moveablePiece.pieceCWRotation();
				break;

			case 90: //z for AntiClockwise
				System.out.println("anticlockwise");
				moveablePiece.pieceCCWRotation();
				break;

			case ' ':
				hardDrop();
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

		/*
		randomly generating map:
		normal: an array of {" ", "R", "B", "G"}
		random generator from 0-4
		each index gets a randomly allocated ting
		*/


		char[][] map = new char[HEIGHT][WIDTH];
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
				if (symbol == 'B'){
					virus = new Block(this.allSprites.get("Blue_virus"), cursorX, cursorY, "Blue");
				} 
				if (symbol == 'G'){
					virus = new Block(this.allSprites.get("Green_virus"), cursorX, cursorY, "Green");
				} 
				if (symbol == 'R'){
					virus = new Block(this.allSprites.get("Red_virus"), cursorX, cursorY, "Red");
				} 

				//adds the virus to the drawing list and moves to the next position of where the sprite should spawn 
				if (virus != null){
					allBlocks.add(virus);
				}
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

	public Piece getMoveablePiece(){return this.moveablePiece;}
}