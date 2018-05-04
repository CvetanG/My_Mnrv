package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.aplu.turtle.Turtle;
import ch.aplu.turtle.TurtleFrame;

@SuppressWarnings("serial")
public class Example2 extends TurtleFrame implements ActionListener{

	private Turtle joe;
	private JButton btn1;
	private boolean check;
	
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

	public Example2(){
		prepareGUI();
	}

	public static void main (String[] args) {
		Example2 myExample = new Example2();
	}
	
	public void drawRect() {
		
			joe.setPos(-100, 100);                 // Place joe to the Point(-100,100).
	
			joe.setPenColor(Color.blue);           // set the pen color to red.
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
	
	public void prepareGUI() {

		
		setLayout(new BorderLayout());
		btn1=new JButton();

		btn1.setPreferredSize(new Dimension(200,20));
		btn1.setText("ClickMe"); 
		btn1.addActionListener(this);

        add(btn1, BorderLayout.SOUTH);
        setSize(500,500);
		
		joe = new Turtle(this, Color.green); // Create a green turtle in her 
		//own window.
		joe.setLineWidth(2);
		setVisible(true);

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		check=true;
	}

}
