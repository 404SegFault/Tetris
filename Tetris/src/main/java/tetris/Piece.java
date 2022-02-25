package tetris;

<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.Random;
=======
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;
>>>>>>> Stashed changes

import java.util.*;

<<<<<<< Updated upstream
    Block topBlock;
    Block bottomBlock;
    String pieceDir;

    public Piece(String colour1, String colour2){

        String[] allColours = new String[]{"darkBlue", "green", "lightBlue", "orange", "purple", "red", "yellow"};
        Random random = new Random();

        String randomColour1 = allColours[(int)random.int(0,7).findFirst().getAsInt()];
        String randomColour2 = allColours[(int)random.int(0,7).findFirst().getAsInt()];

        String BlockOneName = colour1 + "Block";
        String BlockTwoName = colour2 + "Block";
=======

public class Piece extends GameObject{

    public Block topBlock;
    public Block bottomBlock;

    private int topX,topY,bottomX, bottomY;
    private String pieceDir;

    public Piece(HashMap<String,PImage> allSprites, PImage sprite, int x, int y, String colour1, String colour2){

        super(sprite,x,y);

        topX = App.GRIDSPACE;
        bottomX = topX;

        topY = App.GRIDSPACE;
        bottomY = topY + App.GRIDSPACE; 


        this.topBlock = new Block(allSprites.get(colour1), 320, 320, colour1);
        this.bottomBlock = new Block(allSprites.get(colour2), 320, 320, colour2);

        pieceDir = "down";        

    }

    public void pieceCCWRotation(){
        //rotates along topblock
        
        int newBottomX = this.topBlock.getCoords()[0];
        int newBottomY = this.topBlock.getCoords()[1];

        switch(pieceDir){
            case "down":
                newBottomX += App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);
                pieceDir = "right";
                break;

            case "up":
                newBottomX -= App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

                pieceDir = "left";
                break;

            case "left":
                newBottomX += App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);
>>>>>>> Stashed changes

                pieceDir = "down";
                break;

            case "right":
                newBottomX -= App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

<<<<<<< Updated upstream
        this.topBlock = new Block(allSprites.get(BlockOneName), topX, topY);
        this.bottomBlock = new Block(allSprites.get(BlockTwoName), bottomX, bottomY);

        this.topBlock.setDown(bottomBlock);
        this.pieceDir = "down";

    }

    public void pieceCCWRotation(){
        //rotates along topblock
        
        int newBottomX = this.topBlock.getCoords()[0];
        int newBottomY = this.topBlock.getCoords()[1];

        switch(this.blockDir){
            case "down":
                newBottomX += 32;
                newBottomY -= 32;
                this.pieceDir = "right";

            case "up":
                newBottomX -= 32;
                newBottomY += 32;
                this.pieceDir = "left";

            case "left":
                newBottomX += 32;
                newBottomY += 32;
                this.pieceDir = "down";

            case "right":
                newBottomX -= 32;
                newBottomY -= 32;
                this.pieceDir = "up";
        }

        bottomBlock.setCoord(newBottomX, newBottomY);
    }

    public void pieceCWRotation(){
        //rotates along topblock
        
        int newBottomX = this.topBlock.getCoords()[0];
        int newBottomY = this.topBlock.getCoords()[1];

        switch(this.blockDir){
            case "down":
                newBottomX -= 32;
                newBottomY -= 32;
                this.pieceDir = "left";

            case "up":
                newBottomX += 32;
                newBottomY += 32;
                this.pieceDir = "right";

            case "left":
                newBottomX += 32;
                newBottomY -= 32;
                this.pieceDir = "up";

            case "right":
                newBottomX -= 32;
                newBottomY += 32;
                this.pieceDir = "down";
        }

        bottomBlock.setCoord(newBottomX, newBottomY);
    }

    
    public void pieceGeneration(){
=======
                pieceDir = "up";
                break;
        }
>>>>>>> Stashed changes

        bottomBlock.setCoord(newBottomX, newBottomY);
    }

<<<<<<< Updated upstream
=======
    public void pieceCWRotation(){
        //rotates along topblock
        
        int newBottomX = this.topBlock.getCoords()[0];
        int newBottomY = this.topBlock.getCoords()[1];

        switch(pieceDir){
            case "down":
                newBottomX -= App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

                pieceDir = "left";
                break;

            case "up":
                newBottomX += App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

                pieceDir = "right";
                break;

            case "left":
                newBottomX += App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

                pieceDir = "up";
                break;

            case "right":
                newBottomX -= App.GRIDSPACE;
                newBottomY += App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

                pieceDir = "down";
                break;

        }

        bottomBlock.setCoord(newBottomX, newBottomY);
    }

    
    // public void pieceGeneration(){
        
    // }

>>>>>>> Stashed changes
    
}