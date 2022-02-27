package tetris;
import java.util.*;

class DropTimer extends TimerTask{

	App app;
	public DropTimer(App app) {
		this.app = app;
	}

	/** every certain number of seconds this method is called**/
	public void run(){
		app.getMoveablePiece().getLeftHalf().moveDown();
		app.getMoveablePiece().getRightHalf().moveDown();
	} 
}