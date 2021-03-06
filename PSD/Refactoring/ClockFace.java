import java.awt.*;
import java.util.*;

public class ClockFace extends Frame {
	Date D = new Date();

	void setDate(Date date) {
		D = date;
		repaint();
	}

	double P = Math.PI, A = P / 2, a, c, U = .05;
	int width, height, m, R;

	double E(int a, int u) {
		return (3 * P / 2 + 2 * P * a / u) % (2 * P);
	}

	void N(Graphics g, double q, double s) {
		g.fillPolygon(new int[] { H(s, q), H(U, q + A), H(U, q + 3 * A) },
				new int[] { J(s, q), J(U, q + A), J(U, q + 3 * A) }, 3);
	}

	public void paint(Graphics g) {
		Color C = SystemColor.control;
		g.setColor(C);
		g.fillRect(0, 0, width = size().width, height = size().height);
		width -= 52;
		height -= 52;
		R = Math.min(width / 2, height / 2);
		g.translate(width / 2 + 25, height / 2 + 36);
		g.setColor(C.darker());
		for (m = 0; m < 12; ++m) {
			a = E(m, 12);
			g.drawLine(H(.8), J(.8), H(.9), J(.9));
		}
		m = D.getMinutes();
		N(g, E(D.getHours() * 60 + m, 720), .5);
		N(g, E(m, 60), .8);
		N(g, E(D.getSeconds(), 60), .9);
	}

	int H(double y) {
		return (int) (R * y * Math.cos(a));
	}

	int H(double y, double q) {
		a = q;
		return H(y);
	}

	int J(double y) {
		return (int) (R * y * Math.sin(a));
	}

	int J(double y, double q) {
		a = q;
		return J(y);
	}

	public static void main(String[] _) throws Exception {
		ClockFace c = new ClockFace();
		c.resize(200, 200);
		c.show();
		for (;;) {
			c.setDate(new Date());
			Thread.sleep(200);
		}
	}
}
