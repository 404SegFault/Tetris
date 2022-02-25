package tetris;

public class Block extends GameObject{

    String colour;

    public Block(PImage sprite, int x, int y, String colour){
        super(sprite, x, y);
        this.colour = colour;
    }
}