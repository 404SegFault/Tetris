package tetris;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;


//New Name: HalfPill
//Purpose: further stacking, cleaning once 4 in row, dropping if other half is cleared

public class Block extends GameObject{

    int rotDegrees;
    String colour;
	boolean set = false;

    // private int right = 0;
    // private int down = 90;
    // private int left = 180;
    // private int up = 270;

    public Block(PImage sprite, int x, int y, String colour){
        super(sprite, x, y);
        this.colour = colour;
        this.rotDegrees = 0;
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
        // if(Math.abs(Math.sin(this.rotDegrees * Math.PI / 180)) == 1){ x += 16; } 
        // else{ y += 16; }
        // Change the image mode so now we specify the image
        // position using the image center rather than top-left
        // corner
        app.imageMode(app.CENTER);
        // Draw the image centred about [x, y]
        //app.image(this.sprite, x, y);
        // Now we want to rotate the image 'in position'
        app.pushMatrix(); // remember current drawing matrix)
        app.translate(x+16, y+16);
        app.rotate(app.radians(this.rotDegrees)); // rotate x degrees
        app.image(this.sprite, 0, 0);
        app.popMatrix(); // restore previous graphics matrix
        // Restore image mode back to default (optional)
        //app.imageMode(app.CORNER);

    }

    // 0 = right
    // 90 = down
    // 180 = left
    // 270 up

    public boolean blockCCWRotation(){
        //rotates along topblock
        
        int newBottomX = this.getCoords()[0];
        int newBottomY = this.getCoords()[1];
        int newDegrees = this.rotDegrees;
        
        switch(this.rotDegrees){
            case 90:
                newBottomX += App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;

                //System.out.printf("%d %d\n",newBottomX,newBottomY);
                newDegrees = 0;
                break;

            case 270:
                newBottomX -= App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 180;
                break;

            case 180:
                newBottomX += App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 90;
                break;

            case 0:
                newBottomX -= App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 270;
                break;
        }

        boolean validTurn = nextCorBoundaryCheck(newBottomX, newBottomY);

        if(validTurn == true){
            this.setCoord(newBottomX, newBottomY);
            this.setDegrees(newDegrees);
        }

        return validTurn;
    }

    public boolean blockCWRotation(){
        //rotates along topblock
        
        int newBottomX = this.getCoords()[0];
        int newBottomY = this.getCoords()[1];
        int newDegrees = this.rotDegrees;

        switch(this.rotDegrees){
            case 90:
                newBottomX -= App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 180;
                break;

            case 270:
                newBottomX += App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 0;
                break;

            case 180:
                newBottomX += App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 270;
                break;

            case 0:
                newBottomX -= App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                //System.out.printf("%d %d\n",newBottomX,newBottomY);

                newDegrees = 90;
                break;

        }

        boolean validTurn = nextCorBoundaryCheck(newBottomX, newBottomY);

        if(validTurn == true){
            this.setCoord(newBottomX, newBottomY);
            this.setDegrees(newDegrees);
        }

        return validTurn;
    }

    public boolean nextCorBoundaryCheck(int upcomingX, int upcomingY){
        if(upcomingX < 192 || upcomingX > 416){ return false; }
        if(upcomingY < 64 || upcomingY > 544){ return false; }
        return true;
    }

    public void doRotate(int direction){ 
        this.rotDegrees = (rotDegrees + direction * 90) % 360; 
        if(this.rotDegrees < 0){ this.rotDegrees = 360 + rotDegrees; }
    }
    
	public void moveRight(){this.coords[0] += App.GRIDSPACE;}
	public void moveLeft(){this.coords[0] -= App.GRIDSPACE;}
    public void setDegrees(int newDegrees){ this.rotDegrees = newDegrees; }

    public int getRotDegrees(){ return this.rotDegrees; }

	public boolean getSet(){return this.set;}
	public void setBlock(){this.set = true;}
	public String getColour() {return this.colour;}

	
}