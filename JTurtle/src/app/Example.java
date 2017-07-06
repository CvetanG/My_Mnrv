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

	private TurtleFrame myTFrame;
	private Turtle joe;
	private String shape = "";
	
	public enum Shape {
	    Squares,
	    Triangle,
	    Hexagon,
	    Star,
	    Spiral,
	    Sun,
	    SpiralTriangles
	}
	
	private final Thread t;
	{
		t = new Thread(new Runnable() {
			public void run() {
				while (!Thread.interrupted()) {
					try {
						switch (shape) {
						case "Squares":
							drawSquares();
							break;
						case "Triangle":
							drawTriangle();
							break;
						case "Hexagon":
							drawHex();
							break;
						case "Star":
							drawStar();
							break;
						case "Spiral":
							drawSpiral();
							break;
						case "Sun":
							drawSun();
							break;
						case "SpiralTriangles":
							drawSpiralTriangles();
							break;

						default:
							break;
						}
							Thread.sleep(1000);
//							Thread.sleep(1 * 60 * 1000);
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

	private void resetTurtle() {
		joe.home();
		myTFrame.getPlayground().clear();
	}
	
	private void drawSquares() {
		joe.showTurtle();
		joe.setPos(-100, 100);                 // Place joe to the Point(-100,100).
		joe.setPenColor(Color.blue);
		joe.pd();
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
		shape = "";
	}
	
	private void drawTriangle() {
		joe.showTurtle();
		joe.setPenColor(Color.blue);
		joe.pd();
		for (int i = 0; i < 4; i++) {
			
			for (int j = 0; j < 3; j++) {
				joe.fd(200).rt(-120);                 // turn 90 degrees to the right, then 
				// move forward 200 pixels.
			}
			joe.rt(-30);
			joe.pu().fd(50);
			joe.pd().fd(100);
			joe.pu().bk(150);
			joe.rt(120);
			joe.pd();
			
		}
		
		shape = "";
	}
	
	private void drawHex() {
		joe.showTurtle();
		joe.setPos(-86, 50);
		joe.setPenColor(Color.blue);
		joe.pd();
		for (int i = 0; i < 6; i++) {
			joe.rt(60).fd(100); 
		}
		
		shape = "";
	}
	
	private void drawStar() {
		joe.showTurtle();
		joe.setPenColor(Color.green);
		joe.pd();
		for (int i = 0; i < 5; i++) {
			joe.fd(200).rt(144); 
		}
		
		shape = "";
	}
	
	private void drawSpiral() {
		joe.showTurtle();
		joe.setPenColor(Color.blue);
		joe.pd();
		for (int i = 0; i < 20; i++) {
			joe.fd(10 + i*10).rt(60); 
		}
		
		shape = "";
	}
	
	private void drawSun() {
		joe.showTurtle();
		joe.setPenColor(Color.blue);
		joe.pd();
		for (int i = 0; i < 36; i++) {
			joe.fd(200).rt(170); 
		}
		
		shape = "";
	}
	
	private void drawSpiralTriangles() {
		joe.showTurtle();
		joe.setPenColor(Color.red);
		joe.pd();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 21; j++) {
				joe.rt(120).fd(20 + j*10); 
			}
			joe.rt(120);
		}
		
		shape = "";
	}

	private void prepareGUI() {

		controlPanel = new JPanel();
		panel = new JPanel();

		GroupLayout layout = new GroupLayout(panel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JButton btn1 = new JButton("Rectangular");
		btn1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	shape = Shape.Squares.toString();
	        }
	    });

		JButton btn2 = new JButton("Reset");
		btn2.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	resetTurtle();
	        }
	    });

		JButton btn3 = new JButton("Hide / Show");
		btn3.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if (joe.isHidden()) {
	        		joe.showTurtle();
				} else {
					joe.hideTurtle();
				}
	        }
	    });

		JButton btn4 = new JButton("Triangle");
		btn4.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	shape = Shape.Triangle.toString();
	        }
	    });
		
		JButton btn5 = new JButton("Hexagon");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Shape.Hexagon.toString();
			}
		});
		
		JButton btn6 = new JButton("Star");
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Shape.Star.toString();
			}
		});
		
		JButton btn7 = new JButton("Spiral");
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Shape.Spiral.toString();
			}
		});
		
		JButton btn8 = new JButton("Sun");
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Shape.Sun.toString();
			}
		});
		
		JButton btn9 = new JButton("Spiral Triangles");
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shape = Shape.SpiralTriangles.toString();
			}
		});
		
		List<JButton> bList = new ArrayList<>();
		bList.add(btn1);
		bList.add(btn2);
		bList.add(btn3);
		bList.add(btn4);
		bList.add(btn5);
		bList.add(btn6);
		bList.add(btn7);
		bList.add(btn8);
		bList.add(btn9);
		
		Dimension d = new Dimension(150, 40);
		// to apply setting to All Buttons
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
		joe.setLineWidth(5);
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
