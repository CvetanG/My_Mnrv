package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ch.aplu.turtle.Turtle;
import ch.aplu.turtle.TurtleFrame;

public class Example {

	private JPanel controlPanel;
	private JPanel panel;
	private Boolean check = false;

	private TurtleFrame myTFrame;
	private Turtle joe;
	
	private final Thread t;
	{
		t = new Thread(new Runnable() {
			public void run() {
				while (!Thread.interrupted()) {
					try {
						if (check) {
							drawRect();
							Thread.sleep(1000);
//							Thread.sleep(1 * 60 * 1000);
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}
		});
		t.setDaemon(true);
		t.setName("Draw");
		t.start();
	}
	
	private Example(){
	}

	public static void main (String[] args) {
		Example myExample = new Example();
		myExample.prepareGUI();
	}

	class CustomActionListenerDraw implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			check=true;
		}
	}

	class CustomActionListenerReset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			resetTurtle();
		}
	}

	class CustomActionListenerHide implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			joe.hideTurtle();
		}
	}

	class CustomActionListenerShow implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			joe.showTurtle();
		}
	}

	private void resetTurtle() {
		joe.home();
		myTFrame.getPlayground().clear();
	}
	
	private void drawRect() {
		joe.showTurtle();
		joe.setPos(-100, 100);                 // Place joe to the Point(-100,100).
		joe.setPenColor(Color.blue);
		for (int i = 0; i < 4; i++) {
			joe.rt(90).fd(200);                 // turn 90 degrees to the right, then 
												// move forward 200 pixels.
		}
		joe.setPenColor(Color.red);           // set the pen color to red.
		joe.pu();                             // lifts the pen off the canvas
		joe.bk(50).lt(90).bk(50).rt(90).pd();
		for (int i = 0; i < 4; i++) {
			joe.rt(90).fd(100);
		}
		check=false;
	}

	private void prepareGUI() {

		controlPanel = new JPanel();
		panel = new JPanel();

		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		Dimension d = new Dimension(150, 20);
		
		JButton btn1 = new JButton("Rectangular");
		btn1.addActionListener(new CustomActionListenerDraw());

		JButton btn2 = new JButton("Reset");
		btn2.addActionListener(new CustomActionListenerReset());

		JButton btn3 = new JButton("Hide");
		btn3.addActionListener(new CustomActionListenerHide());

		JButton btn4 = new JButton("Show");
		btn4.addActionListener(new CustomActionListenerShow());
		
		List<JButton> bList = new ArrayList<>();
		bList.add(btn1);
		bList.add(btn2);
		bList.add(btn3);
		bList.add(btn4);
		
		// to apply setting to Buttons
		for (JButton jButton : bList) {
			jButton.setMinimumSize(d);
		}
		
		GroupLayout.SequentialGroup bSeqGroup = layout.createSequentialGroup();
		GroupLayout.ParallelGroup bParGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
		
		for (JButton jButton : bList) {
			bParGroup.addComponent(jButton);
			bSeqGroup.addComponent(jButton);
		}
		
		layout.setHorizontalGroup(bParGroup);
		
		layout.setVerticalGroup(bSeqGroup);
		
		panel.setLayout(layout);
		controlPanel.add(panel);
		
		myTFrame = new TurtleFrame("MyTurtles", 1000, 800);
		
		myTFrame.add(controlPanel, BorderLayout.EAST);
		
		joe = new Turtle(myTFrame, Color.green); // Create a green turtle in her 
		//own window.
		joe.hideTurtle();
		joe.setLineWidth(3);
		myTFrame.setVisible(true);
		/*
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(10);
		borderLayout.setVgap(10);
		myTFrame.setLayout(borderLayout);
		 */

		//		Playground myPlayGround = new Playground(myTFrame, size);
		//		myPlayGround = myTFrame.getPlayground();
		//		mainFrame.add(myPlayGround);
		//		mainFrame.add(myTFrame);

		//		myTFrame.add(controlPanel);

		//		TurtleContainer myPlayGround = new TurtleContainer(myPlayGround, size);

		//		myTFrame.setLayout(new FlowLayout());
		//		myPlayGround.add(closeButton);

		//	}

		/*
		    Turtle anne = new Turtle(joe, Color.BLUE);        // Create a new Turtle (named anne)
		                                          // in the same window as joe
		    anne.speed(1000).fd(150).lt(90);      // sets the speed to 1000 pixels/sec,
		                                          // then do some moves.
		    anne.ht().fd(150).lt(90).stampTurtle(); // ht(): hide the turtle
		    anne.fd(300);
		    anne.st();                            // lets the turtle reappear on the 
		                                          // screen
		    anne.wrap();                          // Tells anne to wrap around the edges
		    anne.setPos(200,200);

		    for (int i=0; i < 4; i++) {
		      anne.rt(90).fd(400);
		    }

		    anne.reinit();                        // Resets anne to her standard settings,
		                                          // e.g. home position,facing north 

		    Turtle filly = new Turtle(joe, Color.yellow); // yellow Turtle in joe's
		                                          // Playground.
		    filly.setPos(75,75);
		    filly.setFillColor(Color.black);      // Sets the fill color to black.
		    filly.fill();                         // fills the region bounded by any 
		                                          // non-background colored pixel, 
							  // containing filly's position.
		    filly.setPos(175,175);
		    filly.setFillColor(Color.orange);
		    filly.fill();

		    Turtle texter = new Turtle(joe, Color.magenta);
		    texter.ht().label("Hello Turtle");
		 */
	}

}
