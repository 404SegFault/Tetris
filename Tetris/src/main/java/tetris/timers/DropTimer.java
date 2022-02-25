package tetris;
import java.util.*;

class DropTimer extends TimerTask{

	App app;
	public DropTimer(App app) {
		this.app = app;
	}

	/** every certain number of seconds this method is called**/
	public void run(){
		ArrayList<Block> allBlocks = app.getAllBlocks();

		for (int i = 0; i < allBlocks.size(); i++){
			allBlocks.get(i).moveDown();
		}
	} 
}