package game;
import java.util.*;

public class NumberBox {
	
	private int number;
	private boolean combined;
	private boolean moved;
	Random rand= new Random();
	public NumberBox(){
		if(rand.nextInt(10)<2)
			number=4;
		else
			number=2;
		combined=false;
		setMoved(false);
	}
	
	public boolean getCombined(){
		return combined;
	}
	public int getNumber(){
		return number;
	}
	public void setNumber(int num){
		number=num;
		combined=true;
	}
	public void setCombined(boolean val){
		combined = val;
	}

	public boolean getMoved() {
		return moved;
	}

	public void setMoved(boolean val) {
		moved = val;
	}
}
