import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BandInfoViewer {

	private static final String bidQuery = "SELECT band.bid " + "FROM band "
			+ "WHERE band.name = ";

	/**
	 * Fetches the band id number corresponding to the band name supplied. If
	 * there is more than one band with this name, then the id of the first that
	 * the database supplies is returned. If no such band exists, -1 is
	 * returned.
	 */
	private static int getBID(Statement s, String bandName)
			throws SQLException, NoSuchBandException {

		ResultSet r = s.executeQuery(bidQuery + "'" + bandName + "'");

		if (r.next()) {
			return r.getInt(1);
		} else {
			throw new NoSuchBandException(bandName);
		}
	}

	public static void main(String args[]) {
		ConnManager cm = null;
		JFrame f;
		JPanel bandInfoPanel = null;
		JComponent memberInfo = null, discoInfo = null;
		int bandId;

		// check we have been supplied a band name
		if (args.length < 1) {
			System.out.println("Usage: java BandInfoViewer \"<band name>\"");
			System.exit(1);
		}

		try {
			// attempt to establish a connection
			cm = new ConnManager();

			try {
				Statement s = cm.getStatement();
				
				ResultSet result = s.executeQuery("SELECT name FROM band");
				System.out.println("List of bands to select from :");
				while(result.next()){
					System.out.println(result.getString(1));
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String band = "";
				try {
					band = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// get the band id for the following queries
				bandId = getBID(s, band);

				bandInfoPanel = BandInfoQuery.doQuery(s, bandId);
				memberInfo = MemberQuery.doQuery(s, bandId);
				discoInfo = DiscographyQuery.doQuery(s, bandId);
			} catch (SQLException e) {
				System.out.println("Error running queries");
				System.out.println("Message: " + e.getMessage());
				e.printStackTrace();
				System.exit(1);
			} catch (NoSuchBandException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}

			cm.closeConnection();
		} catch (SQLException e) {
			System.out.println("Error connecting to SQL Server");
			System.out.println("Message: " + e.getMessage());
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to find database drivers");
			System.out.println("Message: " + e.getMessage());
			System.exit(1);
		}

		// create the frame to display our results
		f = new JFrame("Information on " + args[0]);

		// create a box which will contain all the information
		Box b = Box.createVerticalBox();

		// create the band info panel, with a border that indicates
		// what it is
		Border blackLineBorder = BorderFactory.createLineBorder(Color.black);
		Border bandInfoBorder = BorderFactory.createTitledBorder(
				blackLineBorder, "Band Information");
		bandInfoPanel.setBorder(bandInfoBorder);
		b.add(bandInfoPanel);

		// create the member info panel, with a suitable border
		Border memberBorder = BorderFactory.createTitledBorder(blackLineBorder,
				"Current Members");
		JScrollPane memberInfoPane = new JScrollPane(memberInfo);
		memberInfoPane.setBorder(memberBorder);
		b.add(memberInfoPane);

		// create the discography panel, with a suitable border
		Border discoBorder = BorderFactory.createTitledBorder(blackLineBorder,
				"Discography");
		JScrollPane discoInfoPane = new JScrollPane(discoInfo);
		discoInfoPane.setBorder(discoBorder);
		b.add(discoInfoPane);

		// add our box to the pane, make the window visible
		f.getContentPane().add(b);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
