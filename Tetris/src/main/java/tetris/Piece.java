package tetris;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;


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

                pieceDir = "down";
                break;

            case "right":
                newBottomX -= App.GRIDSPACE;
                newBottomY -= App.GRIDSPACE;
                System.out.printf("%d %d\n",newBottomX,newBottomY);

                pieceDir = "up";
                break;
        }

        bottomBlock.setCoord(newBottomX, newBottomY);
    }

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

    
}