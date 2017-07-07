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

/**
 * 
 * @author Cvetan Georgiev
 * Jul 7, 2017
 */

public class MyTurtle {

	private JPanel controlPanel;
	private JPanel panel;

	private TurtleFrame myTFrame;
	private Turtle turtle;
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
	
	private MyTurtle(){
	}

	public static void main (String[] args) {
		MyTurtle myExample = new MyTurtle();
		myExample.prepareGUI();
	}

	private void resetTurtle() {
		turtle.home();
		myTFrame.getPlayground().clear();
	}
	
	private void drawSquares() {
		turtle.showTurtle();
		turtle.setPos(-100, 100);                 // Place joe to the Point(-100,100).
		turtle.setPenColor(Color.blue);
		turtle.pd();
		for (int i = 0; i < 4; i++) {
			turtle.rt(90).fd(200);                 // turn 90 degrees to the right, then 
												// move forward 200 pixels.
		}
		turtle.setPenColor(Color.red);           // set the pen color to red.
		turtle.pu();                             // lifts the pen off the canvas
		turtle.bk(50).lt(90).bk(50).rt(90).pd();
		for (int i = 0; i < 4; i++) {
			turtle.rt(90).fd(100);
		}
		shape = "";
	}
	
	private void drawTriangle() {
		turtle.showTurtle();
		turtle.setPenColor(Color.blue);
		turtle.pd();
		for (int i = 0; i < 4; i++) {
			
			for (int j = 0; j < 3; j++) {
				turtle.fd(200).rt(-120);                 // turn 90 degrees to the right, then 
				// move forward 200 pixels.
			}
			turtle.rt(-30);
			turtle.pu().fd(50);
			turtle.pd().fd(100);
			turtle.pu().bk(150);
			turtle.rt(120);
			turtle.pd();
			
		}
		
		shape = "";
	}
	
	private void drawHex() {
		turtle.showTurtle();
		turtle.setPos(-86, 50);
		turtle.setPenColor(Color.blue);
		turtle.pd();
		for (int i = 0; i < 6; i++) {
			turtle.rt(60).fd(100); 
		}
		
		shape = "";
	}
	
	private void drawStar() {
		turtle.showTurtle();
		turtle.setPenColor(Color.green);
		turtle.pd();
		for (int i = 0; i < 5; i++) {
			turtle.fd(200).rt(144); 
		}
		
		shape = "";
	}
	
	private void drawSpiral() {
		turtle.showTurtle();
		turtle.setPenColor(Color.blue);
		turtle.pd();
		for (int i = 0; i < 20; i++) {
			turtle.fd(10 + i*10).rt(60); 
		}
		
		shape = "";
	}
	
	private void drawSun() {
		turtle.showTurtle();
		turtle.setPenColor(Color.blue);
		turtle.pd();
		for (int i = 0; i < 36; i++) {
			turtle.fd(200).rt(170); 
		}
		
		shape = "";
	}
	
	private void drawSpiralTriangles() {
		turtle.showTurtle();
		turtle.setPenColor(Color.red);
		turtle.pd();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 21; j++) {
				turtle.rt(120).fd(20 + j*10); 
			}
			turtle.rt(120);
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
	        	if (turtle.isHidden()) {
	        		turtle.showTurtle();
				} else {
					turtle.hideTurtle();
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
		
		turtle = new Turtle(myTFrame, Color.green); // Create a green turtle in her 
		//own window.
		turtle.hideTurtle();
		turtle.setLineWidth(5);
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
