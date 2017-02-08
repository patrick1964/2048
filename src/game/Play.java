package game;
/**
 * <h1>2048</h1>
 * 
 * this is a game in which the goal is
 * to combine exponents of 2 
 * in order to get to 2048
 * 
 * this class is main class of the game
 * 
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;
import java.awt.Color;

import javax.swing.JLabel;

public class Play implements KeyListener {
	private Random rand = new Random();
	private HashMap<Integer, NumberBox> components = new HashMap<Integer, NumberBox>();
	private HashMap<Integer, JLabel> labels;
	private JLabel scoreLabel;
	private int score = 0;
	public int edge;
	/**
	 * Constructor for class Play
	 * takes in the labels for the numbers, and the label for score
	 * @param labels
	 * @param score
	 */
	public Play(HashMap<Integer, JLabel> labels,JLabel score) {
		this.labels = labels;
		scoreLabel=score;
		edge= (int) Math.sqrt(labels.size());
		setUp();

	}
	/**
	 * sets up the board while adding 2 numbers to it
	 */
	public void setUp() {
		for (int i = 0; i < labels.size(); i++) {
			components.put(i, null);
		}
		generateNumber();
		generateNumber();
	}
	/**
	 * changes the color of the text in a given label
	 * @param loc the location of the label on the grid
	 */
	public void changeLabel(int loc){
		switch (components.get(loc).getNumber()){
			case 2:
				labels.get(loc).setForeground(Color.BLACK);break;
			case 4:
				labels.get(loc).setForeground(Color.BLUE);break;
			case 8:
				labels.get(loc).setForeground(Color.CYAN);break;
			case 16:
				labels.get(loc).setForeground(Color.DARK_GRAY);break;
			case 32:
				labels.get(loc).setForeground(Color.GRAY);break;
			case 64:
				labels.get(loc).setForeground(Color.LIGHT_GRAY);break;
			case 128:
				labels.get(loc).setForeground(Color.MAGENTA);break;
			case 256:
				labels.get(loc).setForeground(Color.ORANGE);break;
			case 512:
				labels.get(loc).setForeground(Color.PINK);break;
			case 1024:
				labels.get(loc).setForeground(Color.RED);break;
			case 2048:
				labels.get(loc).setForeground(Color.WHITE);break;
			case 4096:
				labels.get(loc).setForeground(Color.YELLOW);break;
		}labels.get(loc).setText(""+components.get(loc).getNumber());
	}
	/**
	* generates a numberBox at a random location on the grid
	* sets the text on label to the number of the numberBox.
	*/
	public void generateNumber() {
		int location = rand.nextInt(16);
		while (components.get(location) != null) {
			location = rand.nextInt(16);
		}
		components.replace(location, new NumberBox());
		changeLabel(location);
	}
	/**
	 * returns the score
	 * @return int
	 */
	public int getScore() {
		return score;
	}
	/**
	 * sets the score
	 * @param val
	 */
	public void setScore(int val) {
		score = val;
	}
	/**
	 * gets the game to act based on direction given
	 * then creates a number if anything has moved
	 * then resets the moved state for all number boxes
	 * @param dir
	 */
	public void act(char dir) {
		switch (dir){
			case 'u':
				for (int i = 0; i < edge; i++) {
					for (int j=0;j<edge; j++){
						int comp = firstItem(dir, j*edge+i);
						move(dir,comp,j*edge+i);
					}
				}
				break;
			case 'd':
				for (int i = 0; i < edge; i++) {
					for (int j=edge-1;j>=0; j--){
						int comp = firstItem(dir, j*edge+i);
						move(dir,comp,j*edge+i);
					}
				}
				break;
			case 'l':
				for (int i = 0; i < edge; i++) {
					for (int j=0;j<edge; j++){
						int comp = firstItem(dir, i*edge+j);
						move(dir,comp,i*edge+j);
					}
				}
				break;
			case 'r':
				for (int i = 0; i < edge; i++) {
					for (int j=edge-1;j>=0; j--){
						int comp = firstItem(dir, i*edge+j);
						move(dir,comp,i*edge+j);
					}
				}
				break;
		}
		if(moved())
			generateNumber();
		resetMoved();
	}
	/**
	 * resets the moved and combined state for all number boxes on the grid.
	 */
	private void resetMoved() {
		for(int i=0;i<components.size();i++){
			if(components.get(i)!=null){
				components.get(i).setCombined(false);
				components.get(i).setMoved(false);
			}
		}
		
	}
	/**
	 * moves an item based on a direction and the first location in said direction.
	 *@param dir the direction
	 *@param comp the first location in that direction
	 *@param i the location of the item to be moved.
	 *@throws String a message about the location of the issue
	 *@throws String[] the stackTrace of the thread to help locate the issue in other methods
	 */
	private void move(char dir, int comp, int i) {
		try {
			if (components.get(i) != null) {
				if (comp == i) {
				} else if (comp != i && components.get(comp) != null) {
					try {
						if (canCombine(comp,i)) {
							combine(comp,i);
						} else if (oppositeDirLoc(dir, comp) != i)
							moveToBehind(dir,comp,i);
					} catch (Exception e) {
						System.out.println("problem in the combine");
						System.out.println(e.toString());
					}
				} else if (comp != i && components.get(comp) == null) {
					try {
						moveTo(comp,i);
					} catch (Exception e) {
						System.out.println("problem in the moving without combining");
						System.out.println();
						System.out.println(e.toString());
					}
				} else if (comp == 666) {
					for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
						System.out.println(ste);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("problem in all of the movement.");
			System.out.println(e.toString());
		}
		
	}
	/**
	 * returns if anything moved
	 * @return boolean
	 */
	private boolean moved(){
		for(int i=0;i<components.size();i++){
			if(components.get(i)!=null){
				if(components.get(i).getMoved())
					return true;
			}
		}
		return false;
	}
	/**
	 * moves a single item from one location to another
	 * @param comp where to move to
	 * @param i where to move from
	 */
	private void moveTo(int comp, int i) {
		components.replace(comp, components.get(i));
		components.replace(i, null);
		changeLabel(comp);
		labels.get(i).setText("");
		components.get(comp).setMoved(true);
		
	}
	/**
	 * moves an item to behind a location in a certain direction
	 * @param dir the direction
	 * @param comp the location to be moved behind
	 * @param i the location of the item to be moved
	 */
	public void moveToBehind(char dir, int comp, int i) {
		components.replace(oppositeDirLoc(dir, comp), components.get(i));
		components.replace(i, null);
		changeLabel(oppositeDirLoc(dir, comp));
		labels.get(i).setText("");
		components.get(comp).setMoved(true);
		
	}
	/**
	 * determines if items can combine
	 * @param comp the location of the first item
	 * @param i the location of the second item
	 * @return boolean
	 */
	public boolean canCombine(int comp, int i) {
		if(components.get(comp).getNumber()==components.get(i).getNumber()){
			if(!components.get(comp).getCombined()&&!components.get(i).getCombined())
				return true;
		}
		return false;
	}
	/**
	 * determines the location of the first item in a direction for an item
	 * @param dir the direction
	 * @param loc the location of the item
	 * 
	 * @return int the location of the first item
	 * @return int 666 if there is any issue.
	 */
	public int firstItem(char dir, int loc) {
		switch (dir){
			case 'u':
				if(loc<edge){
					return loc;
				}else{
					int temp = nextLoc(dir, loc);
					while(temp>=edge){
						if(components.get(temp)==null)
							temp=nextLoc(dir,temp);
						else
							return temp;
					}
					return temp;
				}
			case 'd':
				if(loc>=labels.size()-edge){
					return loc;
				}else{
					int temp = nextLoc(dir, loc);
					while(temp<labels.size()-edge){
						if(components.get(temp)==null)
							temp=nextLoc(dir,temp);
						else
							return temp;
					}
					return temp;
				}
			case 'l':
				if(loc%edge==0){
					return loc;
				}else{
					int temp = nextLoc(dir, loc);
					while(temp%edge!=0){
						if(components.get(temp)==null)
							temp=nextLoc(dir,temp);
						else
							return temp;
					}
					return temp;
				}
			case 'r':
				if(loc%edge==3){
					return loc;
				}else{
					int temp = nextLoc(dir, loc);
					while(temp%edge!=3){
						if(components.get(temp)==null)
							temp=nextLoc(dir,temp);
						else
							return temp;
					}
					return temp;
				}
		}
		return 666;
	}
	/**
	 * determines if the location of an item is on the edge of the grid
	 * @param loc
	 * @return boolean
	 */
	public boolean onEdge(int loc){
		if(loc%edge==0||loc%edge==3||loc<edge||loc>=labels.size()-edge)
			return true;
		else
			return false;
	}
	/**
	 * determines the next location in a direction
	 * @param dir
	 * @param loc
	 * @return int
	 */
	public int nextLoc(char dir, int loc) {
		switch (dir) {
		case 'u':
			return loc - 4;
		case 'd':
			return loc + 4;
		case 'l':
			return loc - 1;
		case 'r':
			return loc + 1;
		}
		return 666;

	}
	/**
	 * determines the next location in the direction opposite to the one given
	 * @param dir
	 * @param loc
	 * @return int
	 */
	public int oppositeDirLoc(char dir, int loc) {
		switch (dir) {
		case 'u':
			return loc + 4;
		case 'd':
			return loc - 4;
		case 'l':
			return loc + 1;
		case 'r':
			return loc - 1;
		}
		return 666;
	}
	/**
	 * combines two components
	 * @param comp location of first component
	 * @param i location of the second component
	 */
	public void combine(int comp, int i){
		components.replace(comp, components.get(i));
		components.replace(i, null);
		components.get(comp).setNumber(components.get(comp).getNumber() * 2);
		changeLabel(comp);
		labels.get(i).setText("");
		score += components.get(comp).getNumber();
		scoreLabel.setText("Score: "+score);
		components.get(comp).setCombined(true);
		components.get(comp).setMoved(true);
	}
	/**
	 * clears the grid in preperation of a new game
	 */
	public void close() {
		for (int i = 0; i < labels.size(); i++) {
			components.put(i, null);
			labels.get(i).setText("");
			scoreLabel.setText("press p to start a new game");
		}
	}
	/**
	 * event handler that determines when a key is pressed and invokes act with the appropriate direction
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			act('u');
			break;
		case KeyEvent.VK_DOWN:
			act('d');
			break;
		case KeyEvent.VK_LEFT:
			act('l');
			break;
		case KeyEvent.VK_RIGHT:
			act('r');
			break;
		case KeyEvent.VK_C:
			close();
			break;
		case KeyEvent.VK_P:
			setUp();
			break;
		}
		
	}
	/**
	 * interface method
	 * does nothing
	 */
	public void keyTyped(KeyEvent e) {
	}
	/**
	 * interface method
	 * does nothing
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}