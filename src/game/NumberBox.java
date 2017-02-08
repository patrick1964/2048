package game;
import java.util.*;
/**
 * this class in the object pieces of the game.
 * 
 * @author patrick hanna
 */

public class NumberBox {
	
	private int number;
	private boolean combined;
	private boolean moved;
	Random rand= new Random();
	/**
	 *  Number box constructor
	 */
	public NumberBox(){
		if(rand.nextInt(10)<2)
			number=4;
		else
			number=2;
		combined=false;
		setMoved(false);
	}
	/**
	 * returns if this box has combined
	 */
	public boolean getCombined(){
		return combined;
	}
	/**
	 * returns this box's number
	 */
	public int getNumber(){
		return number;
	}
	/**
	 * sets this box's number
	 * @param num
	 */
	public void setNumber(int num){
		number=num;
		combined=true;
	}
	/**
	 * sets combine to a certain state
	 * @param val
	 */
	public void setCombined(boolean val){
		combined = val;
	}
	/**
	 * return if this box has moved
	 */
	public boolean getMoved() {
		return moved;
	}
	/**
	 * sets moved to a certain state
	 * @param val
	 */
	public void setMoved(boolean val) {
		moved = val;
	}
}
