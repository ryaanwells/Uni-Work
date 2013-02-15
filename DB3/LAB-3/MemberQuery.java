import java.sql.*;
import javax.swing.*;
import java.util.Vector;
import java.awt.Dimension;

class MemberQuery {
    private static final String queryStart
	= "**** QUERY ***** ";
	/*
	 * This query fetches the member id (mid), name as Name, instrument played as Instrument
	 * from a join of the member, memberof and band tables
	 * where the endYear attribute of the band is null
	 * and the band is identified by the id (bid)
	 * results are ordered by member id (mid)
	 */
    private static final String queryEnd
	= "*** QUERY: order by clause here *** ";

    public static JComponent doQuery(Statement s, int bid) 
	throws SQLException {

	ResultSet result = s.executeQuery(queryStart + bid + " " + queryEnd);
	JTable t;
	Vector columnNames = new Vector();
	Vector rows = new Vector();
	Vector currentRow = null;
	String instruments = null;
	int lastMID;

	// fill the JTable with the results.
	ResultSetMetaData metaData = result.getMetaData();
	
	/* get the titles of the columns.
	 * we skip the first column since we don't want this in our
	 * results, it's just to aid our calculations below
	 */
	for(int i=2; i <= metaData.getColumnCount();i++) {
	    columnNames.add(metaData.getColumnName(i));
	}

	/* now we collect all the band members, and put the instruments
	 * for each band member together into one column
	 * since in the query we do an ORDER BY on member ID, we can
	 * be assured that all the rows for the same band member will
	 * be in consecutive order
	 */
	lastMID = -1;
	while(result.next()) {
	    if(result.getInt(1) == lastMID) {
		/* this is the same person as the last time, so we append 
		 * the instrument to their instument column.
		 */
		instruments += " / " + result.getString(3);
	    } else {
		/* this isn't the same person so we add the in progress 
		 * row and start fresh
		 */
		if(currentRow != null) {
		    currentRow.add(instruments);
		    rows.add(currentRow);
		}
		
		/* create the new row, and get the name and first instrument
		 * for this band member
		 */
		currentRow = new Vector();
		lastMID = result.getInt(1);
		currentRow.add(result.getString(2));
		instruments = result.getString(3);
	    }
	}

	/* it is possible that when we reach here there is still a row to
	 * be added to our final table, so we must do this now. This occurs
	 * when the currentRow only has one element (the name), since we
	 * have not added the instrument yet
	 */
	if(currentRow != null && currentRow.size() == 1) {
	    currentRow.add(instruments);
	    rows.add(currentRow);
	}

	/* it is also possible that the query returned no results, in which
	 * case rows will have size 0.
	 */
	if(rows.size() == 0) {
	    // return a label to notify the user
	    return new JLabel("No remaining members");
	} else {
	    JTable table = new JTable(rows, columnNames);
	    table.setPreferredScrollableViewportSize(new Dimension(300,100));
	    return table;
	}
    }
}
