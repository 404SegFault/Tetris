package tetris;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;

public class App extends PApplet {
	/////////////////////////////////ATTRIBUTES////////////////////////////////////
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;
    public static final int GRIDSPACE = 32;
    public static final int FPS = 60;

	private PFont font;

	private JSONObject config;

	
	/////////////////////////////CREATING THE APP OBJECT//////////////////////////
    public void settings() {
        size(WIDTH, HEIGHT);
    }

	/**Loads the files inside the resources into the application, where it can be used in the game**/
    public void setup() {
		HashMap<String, PImage> allSprites = new HashMap<>();

        frameRate(FPS);

		allSprites.put("darkBlueBlock", "Tiles/tileDarkBlue.png");
		allSprites.put("greenBlock", "Tiles/tileGreen.png");
		allSprites.put("lightBlueBlock", "Tiles/tileLightBlue.png");
		allSprites.put("orangeBlock", "Tiles/tileOrange.png");
		allSprites.put("purpleBlock", "Tiles/tilePurple.png");
		allSprites.put("redBlock", "Tiles/tileRed.png");
		allSprites.put("yellowBlock", "Tiles/tileYellow.png");
		
		this.font = createFont("PressStart2P-Regular.ttf", 20);
		this.textFont(this.font);
		fill(0); //makes the text black
    }
	
	/////////////////////////METHODS USED WHEN RUNNING THE GAME////////////////////
	public void drawUI(){
		background(112, 123, 138);

	}

	/** Decriments the timer every second, moves enemies**/
	public void tick(){
		this.frameCount++;

		// if a second passes
		if (frameCount % 60 == 0){
			// this.timeRemaining -= 1;
		}
	}

	/** Goes through all the objects and draws them **/
    public void draw() {
		this.tick();
		this.drawUI();

		//---------------------DRAWING THE OBJECTS------------------------
    }

	/** detects when a key is pressed and calls the player method for movement **/
	public void keyPressed(){  

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
}

