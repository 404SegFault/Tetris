package tetris;

import java.util.ArrayList;
import java.util.Random;

public class Piece{

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

        int topX = 32; //someconstantidk can change
        int bottomX = topX;

        int topY = 32; //someconstantidk can change
        int bottomY = topY + 32; 

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
                newBottomY += 32;
                this.pieceDir = "right";

            case "up":
                newBottomX -= 32;
                newBottomY -= 32;
                this.pieceDir = "left";

            case "left":
                newBottomX += 32;
                newBottomY -= 32;
                this.pieceDir = "down";

            case "right":
                newBottomX -= 32;
                newBottomY += 32;
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
                newBottomY += 32;
                this.pieceDir = "left";

            case "up":
                newBottomX += 32;
                newBottomY -= 32;
                this.pieceDir = "right";

            case "left":
                newBottomX += 32;
                newBottomY += 32;
                this.pieceDir = "up";

            case "right":
                newBottomX -= 32;
                newBottomY -= 32;
                this.pieceDir = "down";
        }

        bottomBlock.setCoord(newBottomX, newBottomY);
    }

    
    public void pieceGeneration(){

    }

    
}