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

        System.out.printf("Old: %d , %d \n", rightHalf.getRotDegrees(), leftHalf.getRotDegrees());

        boolean validTurn = this.leftHalf.blockCWRotation();
        if(validTurn == true){ this.rightHalf.doRotate(1); }

        System.out.printf("After CW: %d , %d \n", rightHalf.getRotDegrees(), leftHalf.getRotDegrees());
    }

    public void pieceCCWRotation(){
        // if(Math.min(rightHalf.getXCoord(),leftHalf.getXCoord()) + App.GRIDSPACE <= 608 && pieceDir != 1){
        //     if(Math.max(rightHalf.getXCoord(),leftHalf.getXCoord()) - App.GRIDSPACE >= 0 && pieceDir != 3){
        //         
        //         pieceDir  = (pieceDir + 1) %4;

        //     }
        // }

        System.out.printf("Old: %d , %d \n", rightHalf.getRotDegrees(), leftHalf.getRotDegrees());

        boolean validTurn = this.rightHalf.blockCCWRotation();
        if(validTurn == true){ this.leftHalf.doRotate(-1); }

        System.out.printf("After CCW: %d , %d \n", rightHalf.getRotDegrees(), leftHalf.getRotDegrees());
        
    }

    public void pieceLeftMove(ArrayList<Block> allBlocks){

        for(Block pillHalf : this.getBothHalves()){
            for(Block b : allBlocks){
                if(b.getXCoord() == pillHalf.getXCoord() - 32 && b.getYCoord() == pillHalf.getYCoord()){ return; }
            }
        }

        if (this.leftHalf.getXCoord() > 192 && this.rightHalf.getXCoord() >= this.leftHalf.getXCoord() ){
                this.leftHalf.moveLeft();
                this.rightHalf.moveLeft();
        }
    }

    public void pieceRightMove(ArrayList<Block> allBlocks){

        for(Block pillHalf : this.getBothHalves()){
            for(Block b : allBlocks){
                if(b.getXCoord() == pillHalf.getXCoord() + 32 && b.getYCoord() == pillHalf.getYCoord()){ return; }
            }
        }

        if (this.rightHalf.getXCoord() < 416 && this.leftHalf.getXCoord() <= this.rightHalf.getXCoord()){
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

    public boolean checkVirusUnder(ArrayList<Block> allBlocks){
		for(Block pillHalf : this.getBothHalves()){
            if(pillHalf.getYCoord() + 32 == 576){ return true; }
        }

        for(Block pillHalf : this.getBothHalves()){
            for(Block b : allBlocks){
                if(b.getXCoord() == pillHalf.getXCoord() && b.getYCoord() == pillHalf.getYCoord() + 32){ return true; }
            }
        }
        return false;

    }
    
    public Block getLeftHalf(){ return this.leftHalf; }

    public Block getRightHalf() { return this.rightHalf; }

    public Block[] getBothHalves() { 
        Block[] blockList = new Block[]{ this.leftHalf, this.rightHalf };

        if(this.rightHalf.getCoords()[1] > this.leftHalf.getCoords()[1]){
            blockList[0] = this.rightHalf;
            blockList[1] = this.leftHalf;
        }

        return blockList;
    }
    
}