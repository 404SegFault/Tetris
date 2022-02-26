package tetris;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import processing.data.JSONObject;

import java.util.*;

//New Name: FullPill
//Purpose: Movement (left and right), Rotation, setting the piece in place, once set creates individual half pill objects to be stored for "further stacking, cleaning once 4 in row, dropping if other half is cleared"

public class Piece extends GameObject{

    public Block leftHalf;
    public Block rightHalf;

    private int topX,topY,bottomX, bottomY;
    private int pieceDir;

    // private String direction[4] = {"Right", "Down","Left","Up"};


    /*
    red green
    
    object fullPill (sprite , x ,y , red , green){
        constructor

        super(sprite, x, y);

        halfPill half1 = new halfPill(sprite, x, y, color1);
        halfPill half2 = new halfPill(sprite, x, y, color2);



    };

    fullPill.rotation( dorotate , ccwRotation);
    movement();


    */
    
    public Piece(HashMap<String,PImage> allSprites, PImage sprite, int x, int y, String colour1, String colour2){

        super(sprite,x,y);

        this.leftHalf = new Block(allSprites.get(colour1), x-App.GRIDSPACE, y, colour1);
        this.leftHalf.setDegrees(180);
        this.rightHalf = new Block(allSprites.get(colour2), x, y, colour2);

        pieceDir = 0;

        //topLeftCorner = [min(block1X, block2X), min()

    }
    

    public void draw(App app){
        leftHalf.draw(app);
        rightHalf.draw(app);
    }

    public void pieceCWRotation(){
        // if(Math.min(rightHalf.getXCoord(),leftHalf.getXCoord()) + App.GRIDSPACE <= 608 && pieceDir != 3){
        //     if(Math.max(rightHalf.getXCoord(),leftHalf.getXCoord()) - App.GRIDSPACE >= 0 && pieceDir != 1){
        //         this.rightHalf.blockCWRotation();
        //         this.leftHalf.doRotate(1);
        //         pieceDir  = (pieceDir + 1) %4;
        //     }
        // }else if(){
            
        // }

        boolean validTurn = this.leftHalf.blockCWRotation();

        if(validTurn == true){ this.rightHalf.doRotate(1); }
    }

    public void pieceCCWRotation(){
        // if(Math.min(rightHalf.getXCoord(),leftHalf.getXCoord()) + App.GRIDSPACE <= 608 && pieceDir != 1){
        //     if(Math.max(rightHalf.getXCoord(),leftHalf.getXCoord()) - App.GRIDSPACE >= 0 && pieceDir != 3){
        //         
        //         pieceDir  = (pieceDir + 1) %4;

        //     }
        // }

        boolean validTurn = this.rightHalf.blockCCWRotation();
        if(validTurn == true){ this.leftHalf.doRotate(-1); }
        
    }

    public void pieceLeftMove(){
        if (this.leftHalf.getXCoord() > 0 && this.rightHalf.getXCoord() > 0 ){
                this.leftHalf.moveLeft();
                this.rightHalf.moveLeft();
        }
    }

    public void pieceRightMove(){
        if (this.rightHalf.getXCoord() < 608 && this.leftHalf.getXCoord() < 608){
                this.leftHalf.moveRight();
                this.rightHalf.moveRight();
        }
    }

    public void pieceDownMove(){
        if (this.rightHalf.getYCoord() < 544 && this.leftHalf.getYCoord() < 544){
                this.leftHalf.moveDown();
                this.rightHalf.moveDown();
        }
    }
    // red green
    // rand.generator(0:2)
    // array("red", "green" , "blue")
    // string color1+color2 (redgreen)
    // sprite 
    // Piece (sprite, coloru 1 , color 2)

    public void pieceGeneration(){
        
    }

    
}