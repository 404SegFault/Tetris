package tetris;
import processing.core.PImage;

import java.util.*;
public abstract class GameObject {
	protected PImage sprite;
	protected int[] coords;
	protected boolean beRemoved = false;

	/**is used to create all GameObjects i.e. Flat, Wall, BrokenWall, Enemy and so on**/
	public GameObject(PImage sprite, int x, int y){
		this.sprite = sprite;
		this.coords = new int[]{x, y};
	}
	
	/**
	*calls tick and draws all the sprites that correspond to the hashmap
	* @param app the app where the explosion will be drawn
	**/
	public void draw(App app){
		app.image(this.sprite, this.coords[0], this.coords[1]);
	}

	/////////////////////////////////////////////GETTERS///////////////////////////////////////
	public int[] getCoords() {return this.coords;}
	public boolean shouldBeRemoved() {return this.beRemoved;}
	public String toString(){return this.getClass().getSimpleName() + " " + Arrays.toString(this.getCoords());}
	
	////////////////////////////////////////////SETTERS/////////////////////////////////////////
	public void removeObject() {this.beRemoved = true;}
	public void setCoord(int x, int y) {
		this.coords[0] = x;
		this.coords[1] = y;
	}
	
	public void setCoord(int[] newCoords){this.coords = newCoords;}
}