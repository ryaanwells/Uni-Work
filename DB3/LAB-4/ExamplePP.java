import db3.PowerPoint;

import java.io.IOException;
import java.sql.*;

/*

 Program to demonstrate usage of the PowerPoint class.
 This class writes to file x.ppt

 It writes one slide with two text boxes on it

 */

public class ExamplePP {

	public static void main(String args[]) {
		ConnManager cm = null;
		if (args.length < 1) {
			System.out.println("Usage: java ExamplePP outputFileName.ppt");
			System.exit(1);
		} else {
			try{
				cm = new ConnManager();
				try{
					Statement s = cm.getStatement();
					ResultSet slideOne = QueryOne.execute(s);
					ResultSet slideTwo = s.executeQuery("");
					ResultSet slideThree = s.executeQuery("");
				} catch(Exception e){
					
				}
			} catch (SQLException e) {
				System.out.println("Error connecting to SQL Server");
				System.out.println("Message: " + e.getMessage());
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.out.println("Unable to find database drivers");
				System.out.println("Message: " + e.getMessage());
				System.exit(1);
			}

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
