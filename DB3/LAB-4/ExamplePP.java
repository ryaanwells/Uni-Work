import db3.PowerPoint;

import java.io.IOException;

/*

 Program to demonstrate usage of the PowerPoint class.
 This class writes to file x.ppt

 It writes one slide with two text boxes on it

 */

public class ExamplePP {

	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("Usage: java ExamplePP outputFileName.ppt");
			System.exit(1);
		} else {

			try {
				PowerPoint p = new PowerPoint(args[0]);
				p.addSlide("slide1");
				p.addTextBox("text box1");
				p.addTextBox("text box2");
				p.writePresentation();
			} catch (IOException ee) {
				System.out
						.println("There was a problem creating the presentation");
				ee.printStackTrace();
			}
		}
	}

}
