package game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Grid {

	private JFrame frame;
	public HashMap<Integer,JLabel> labels= new HashMap<Integer,JLabel>();
	public JLabel scorePanel;
	public int score=0;
	public String instructions = "use the arrow keys to play press c to close and press p to restart";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grid window = new Grid();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Grid() {
		initialize();
		frame.addKeyListener(new Play(labels, scorePanel));
		frame.setFocusable(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.YELLOW);
		frame.getContentPane().setLayout(null);		
		
		//main panel where the numbers will show up and move within the grid.
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 500, 400);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(4, 4, 10, 10));
		makeGrid(panel);
		
		//this panel is for the scoreLabel.
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 400, 500, 55);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel scoreLabel = new JLabel(instructions);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scoreLabel.setBounds(0, 0, 500, 52);
		panel_1.add(scoreLabel);
		scorePanel=scoreLabel;
		frame.setBounds(100, 100, 517, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Create the labels
	 * @param p
	 */
	public void makeGrid(JPanel p){
		for(int i=0;i<16;i++){
				labels.put(i,new JLabel());
				labels.get(i).setFont(new Font("Tahoma", Font.PLAIN, 30));
				labels.get(i).setHorizontalAlignment(SwingConstants.CENTER);
				labels.get(i).setBackground(Color.GREEN);
				labels.get(i).setOpaque(true);
				p.add(labels.get(i));
		}
	}
	
}
