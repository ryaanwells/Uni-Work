import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.awt.Dimension;

class DiscographyQuery {
    private static final String queryStart
	= "SELECT release.title as Title, release.year as Year, release.type as Type, " +
			"release.rating as Rating, count(song.rid) as Song_Num " +
			"FROM release, song, band " +
			"WHERE song.RID=release.RID AND release.BID=band.BID " + 
			"AND release.BID = ";
	/*
	 * This query fetches the release title as Title, release year as Year,
	 * release type as Type, release rating as Rating, the number of titles in the release
	 * as Song Num
	 * from a join of song, release and band tables.
	 * results are ordered by year
	 * grouping is used to facilitate the count function
	 */
	    private static final String queryEnd
	= "GROUP BY release.title, release.year, release.type, release.rating, song.rid " +
	    "ORDER BY release.year";

    public static JComponent doQuery(Statement s, int bid) 
	throws SQLException {
	ResultSet result = s.executeQuery(queryStart + bid + " " + queryEnd);

	// fill the JTable with the results
	try {
	    JTable resultTable = ResultTable.createTableFromResultSet(result);
	    resultTable.setPreferredScrollableViewportSize(new Dimension(300,100));
	    return resultTable;
	} catch(ResultTable.NoResultException e) {
	    return new JLabel("No releases recorded in database");
	}
    }
}
