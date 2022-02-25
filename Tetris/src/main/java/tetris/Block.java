package tetris;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;


public class Block extends GameObject{

    String colour;
	boolean set = false;

    public Block(PImage sprite, int x, int y, String colour){
        super(sprite, x, y);
        this.colour = colour;
    }

	public void moveDown(){
		if (set == false){
			this.coords[1] += App.GRIDSPACE;
		}
	}

	public boolean getSet(){return this.set;}
	public String getColour() {return this.colour;} 
}