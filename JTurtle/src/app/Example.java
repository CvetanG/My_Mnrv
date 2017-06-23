package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.aplu.turtle.Playground;
import ch.aplu.turtle.Turtle;
import ch.aplu.turtle.TurtleContainer;
import ch.aplu.turtle.TurtleFrame;

public class Example {
	
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JPanel turtlePanel;
	private JLabel msglabel;
	
	private TurtleFrame myTFrame;
	
	private Playground myPlayGround;
	
	public Example(){
	      prepareGUI();
	   }
	
	public static void main (String[] args) {
		
		Example myExample = new Example();
	}
	
	public void prepareGUI() {
//		mainFrame = new JFrame("Java Swing Examples");
//		mainFrame.setSize(800, 800);
//		mainFrame.setVisible(true);
		
//		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		mainFrame.setLayout(new GridLayout(1, 2));
		
//		Container contentPane = frame.getContentPane();
		
//		contentPane.add(aComponent);
		
//		JButton closeButton  = new JButton("Close");
		
//		contentPane.add(closeButton);
		
//		frame.setBounds(50, 50, 300, 200);
//		frame.setLayout(new FlowLayout());
//		frame.pack();
		
		controlPanel = new JPanel();
	    /*controlPanel.setLayout(new FlowLayout());
	    
	    JButton okButton = new JButton("OK");        
	    JButton javaButton = new JButton("Submit");
	    JButton cancelButton = new JButton("Cancel");
	    
	    controlPanel.add(okButton);
	    controlPanel.add(javaButton);
	    controlPanel.add(cancelButton);*/
		
		JPanel panel = new JPanel();
	      
	      // panel.setBackground(Color.darkGray);
//	      panel.setSize(200,200);
	      GroupLayout layout = new GroupLayout(panel);
	      layout.setAutoCreateGaps(true);
	      layout.setAutoCreateContainerGaps(true);
	      
	      JButton btn1 = new JButton("Button 1");
	      JButton btn2 = new JButton("Button 2");
	      JButton btn3 = new JButton("Button 3");

	      layout.setHorizontalGroup(layout.createSequentialGroup()
	        .addGroup(layout.createSequentialGroup()
	        .addGroup(layout.createParallelGroup(
	        GroupLayout.Alignment.LEADING)
	        .addComponent(btn1)
	        .addComponent(btn2)
	        .addComponent(btn3))));
	   
	      layout.setVerticalGroup(layout.createSequentialGroup()
	         .addComponent(btn1)
	         .addComponent(btn2)
	         .addComponent(btn3));
	      
	      panel.setLayout(layout);
//	      controlPanel.add(panel);

//	    mainFrame.add(controlPanel);
		 
//		Dimension size = new Dimension(500, 800);
		
		
//		TurtleFrame myTFrame = new TurtleFrame("frame_01", myPlayGround);
		myTFrame = new TurtleFrame("MyTurtles", 1000, 800);
		myTFrame.setLayout(new GridLayout(1, 2));
//		myTFrame.setVisible(true);
//		Playground myPlayGround = new Playground(myTFrame, size);
//		myPlayGround = myTFrame.getPlayground();
//		mainFrame.add(myPlayGround);
//		mainFrame.add(myTFrame);
//		turtlePanel = myPlayGround.get
		
		myTFrame.add(controlPanel);
		myTFrame.add(panel);
		
//		TurtleContainer myPlayGround = new TurtleContainer(myPlayGround, size);
		
//		myTFrame.setLayout(new FlowLayout());
//		myPlayGround.add(closeButton);
		 
		    Turtle joe = new Turtle(myTFrame, Color.green); // Create a green turtle in her 
		                                          //own window.
		    joe.setPos(-100, 100);                 // Place joe to the Point(-100,100).
		    
		    for (int i=0; i < 4; i++) {
		      joe.rt(90).fd(200);                 // turn 90 degrees to the right, then 
		                                          // move forward 200 pixels.
		    }
		    joe.setPenColor(Color.red);           // set the pen color to red.
		    joe.pu();                             // lifts the pen off the canvas
		    joe.bk(50).lt(90).bk(50).rt(90).pd();
		    for (int i=0; i < 4; i++) {
		      joe.rt(90).fd(100);
		    }
		    
		    /*
		    Turtle anne = new Turtle(joe);        // Create a new Turtle (named anne)
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
