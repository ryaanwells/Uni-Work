import java.awt.*;
import java.util.*;

import static java.lang.Math.PI;

public class Clock extends Frame {
	
	private double  offset = PI / 2, U = .05;
	
	private int radius;
	
	private Date date = new Date();
	
	public void setDate(Date date) {
		this.date = date;
		repaint();
	}

	public void paint(Graphics g) {
		Color C = SystemColor.control;
		g.setColor(C);
		
		int width = getSize().width;
		int height = getSize().height;
		
		g.fillRect(0, 0, width , height);
		width -= 52;
		height -= 52;
		radius = Math.min(width / 2, height / 2);
		
		g.translate(width / 2 + 25, height / 2 + 36);
		g.setColor(C.darker());
		
		for (int i = 0; i < 12; ++i) {
			double angle = calculateAngle(i, 12);
			g.drawLine(
					calculateX(.8,angle),
					calculateY(.8,angle),
					calculateX(.9,angle),
					calculateY(.9,angle));
		}
		
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int hours = cal.get(Calendar.HOUR);
		int minutes = cal.get(Calendar.MINUTE);
		int seconds = cal.get(Calendar.SECOND);
		
		drawHand(g, calculateAngle(hours*60 + minutes, 720),.5);
		drawHand(g, calculateAngle(minutes, 60), .8);
		drawHand(g, calculateAngle(seconds, 60), .9);
	}
	
	private double calculateAngle(int numer, int denom) {
		return (3 * PI / 2 + 2 * PI * numer / denom) % (2 * PI);
	}

	private void drawHand(
			Graphics g, double angle, double length) {
		
		g.fillPolygon(
				new int[] {
						calculateX(length, angle),
						calculateX(U, angle + offset),
						calculateX(U, angle - offset)},
				new int[] {
						calculateY(length, angle),
						calculateY(U, angle + offset),
						calculateY(U, angle - offset)},
				3
				);
	}

	private int calculateX(double length, double angle) {
		return (int) (radius * length * Math.cos(angle));
	}

	private int calculateY(double length, double angle) {
		return (int) (radius * length * Math.sin(angle));
	}

	public static void main(String[] _) throws Exception {
		Clock c = new Clock();
		c.resize(200, 200);
		c.show();
		while(true) {
			c.setDate(new Date());
			Thread.sleep(200);
		}
	}
}
