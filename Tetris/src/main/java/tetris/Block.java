package tetris;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;

public class Block extends GameObject{

    int rotDegrees;
    String colour;
	boolean set = false;

    public Block(PImage sprite, int x, int y, String colour){
        super(sprite, x, y);
        this.colour = colour;
        this.rotDegrees = 180 % 360;
    }

	public void moveDown(){
		if (set == false){
			this.coords[1] += App.GRIDSPACE;
		}
	}

    //Hruday testing rotation
    
    @Override
    public void draw(App app){
        int x = this.coords[0];
        int y = this.coords[1];
        System.out.println((int)Math.abs(Math.sin(this.rotDegrees * Math.PI / 180)));
        if(Math.abs(Math.sin(this.rotDegrees * Math.PI / 180)) == 1){ x += 16; } 
        else{ y += 16; }
        // Change the image mode so now we specify the image
        // position using the image center rather than top-left
        // corner
        app.imageMode(app.CENTER);
        // Draw the image centred about [x, y]
        app.image(this.sprite, x, y);
        // Now we want to rotate the image 'in position'
        app.pushMatrix(); // remember current drawing matrix)
        app.translate(x, y);
        app.rotate(app.radians(this.rotDegrees)); // rotate x degrees
        app.image(this.sprite, 0, 0);
        app.popMatrix(); // restore previous graphics matrix
        // Restore image mode back to default (optional)
        app.imageMode(app.CORNER);

    }
    
	public void moveRight(){this.coords[0] += App.GRIDSPACE;}
	public void moveLeft(){this.coords[0] -= App.GRIDSPACE;}

	public boolean getSet(){return this.set;}
	public String getColour() {return this.colour;} 
}