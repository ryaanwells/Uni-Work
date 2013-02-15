import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class BandInfoQuery {
	public static final String query = "SELECT name, country, webpage "
			+ "FROM band " + "WHERE bid=";

	/*
	 * This query fetches the band name, country and webpage for the band
	 * identified by the particular bid
	 */

	public static JPanel doQuery(Statement s, int bid)
			throws NoSuchBandException, SQLException {

		JLabel band, country, webpage;
		JPanel resultPanel;
		ResultSet result = s.executeQuery(query + "'" + bid + "'");

		// extract the relevant information from the result
		if (result.next()) {
			band = new JLabel("Band Name: " + result.getObject(1));
			country = new JLabel("Country: " + result.getObject(2));
			webpage = new JLabel("Webpage: " + result.getObject(3));
		} else {
			throw new NoSuchBandException(bid);
		}

		// set up the result panel
		resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(3, 1));
		resultPanel.add(band);
		resultPanel.add(country);
		resultPanel.add(webpage);

		return resultPanel;
	}
}
