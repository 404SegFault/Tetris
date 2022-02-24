package tetris;
import java.util.*;

class DropTimer extends TimerTask{
	private GameObject block;
	public DropTimer(GameObject block){
		this.block = block;
	}

	/**every 0.2 seconds the next frame method within animation timer is called**/
	public void run(){block.toString();} //can be block.drop to move it down
}