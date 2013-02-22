import javax.swing.*;
import java.sql.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.*;
import java.awt.image.*;

class ReleaseStats {
    private static final String query
	= "SELECT release.year, count(release.year) " +
			"FROM release " +
			"GROUP BY release.year";
	/*
	 * This query fetches the release year as Year, the number of releases as
	 * Num_Releases from the release table
	 * results are ordered by year
	 */

    public static JFreeChart createChart(Statement s) 
	throws SQLException {

	ResultSet results = s.executeQuery(query);

	String series = "Year";

	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	int year = 0;

	if(results.next()) {
	    /* get the first year in the database
	     * we can rely on this being the first year due to
	     * the order by in the query
	     */
	    year = results.getInt(1);
	    dataset.addValue(results.getInt(2), null, results.getString(1));
	}

	while(results.next()) {
	    // add any missing years between the last year
	    // and the new year we have just gained access to
	    for(int i=year + 1; i < results.getInt(1); i++) {
		dataset.addValue(0, null, "" + i);
	    }
		
	    dataset.addValue(results.getInt(2), null, results.getString(1));
	    year = results.getInt(1);
	}

	return ChartFactory.createVerticalBarChart("Releases per year",
						   "Year of release",
						   "Number of Releases",
						   dataset,
						   false,
						   false,
						   false);
    }

    public static void main(String args[]) {
	ConnManager con;
	try {
	    con = new ConnManager();

	    try {
		Statement s = con.getStatement();

		JFreeChart chart = createChart(s);

		// render the chart to a buffered image
		BufferedImage bi = chart.createBufferedImage(800,400);

		// add this buffered image into a label, this is the easiest
		// way to get the image into a frame
		JLabel l = new JLabel(new ImageIcon(bi));

		JFrame f = new JFrame("Release record in database");
		f.getContentPane().add(l);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	    } catch(SQLException e) {
		System.out.println("Error running query to create chart.");
		System.out.println(e.getMessage());
	    }
	} catch(SQLException e) {
	    System.out.println("Error connecting to database server");
	    System.out.println("Message: " + e.getMessage());
	    System.exit(1);
	} catch(ClassNotFoundException e) {
	    System.out.println("Could not find database drivers");
	    System.exit(1);
	}

    }
}
