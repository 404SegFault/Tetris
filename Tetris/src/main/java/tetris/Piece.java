package tetris;

import java.util.ArrayList;

public class Piece{

    Block topBlock;
    Block bottomBlock;

    public Piece(String colour1, String colour2){
        
        String BlockOneName = colour1 + "Block";
        String BlockTwoName = colour2 + "Block";

        int topX = 32; //someconstantidk can change
        int bottomX = topX;

        int topY = 32; //someconstantidk can change
        int bottomY = topY + 32; 

        // this.topBlock = new Block(allSprites.get(BlockOneName), topX, topY);
        // this.bottomBlock = new Block(allSprites.get(BlockTwoName), bottomX, bottomY);

    }

}