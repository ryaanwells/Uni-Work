import java.sql.*;
import javax.swing.*;
import java.util.*;

class ResultTable {
    /**
     * Creates a JTable to display the results returned in the
     * supplied result set.
     */
    public static JTable createTableFromResultSet(ResultSet result) 
	throws NoResultException, SQLException {

	ResultSetMetaData metaData = result.getMetaData();
	Vector columnNames = new Vector();
	Vector rows = new Vector();
	Vector currentRow;
	int numColumns = metaData.getColumnCount();

	if(numColumns == 0) {
	    throw new NoResultException();
	}

	// get all the column names
	for(int i=1; i <= metaData.getColumnCount(); i++) {
	    columnNames.add(metaData.getColumnName(i));
	}

	// get all the rows
	while(result.next()) {
	    currentRow = new Vector();
	    
	    // get all the column data within the current row
	    for(int i=1; i <= metaData.getColumnCount(); i++) {
		currentRow.add(result.getObject(i));
	    }
	    rows.add(currentRow);
	}

	// check that we actually got some data
	if(rows.size() == 0) {
	    throw new NoResultException();
	}
	
	// create our JTable from this data
	return new JTable(rows, columnNames);
    }

    public static class NoResultException extends Exception {
	public NoResultException() {
	    super("This query returned no results");
	}
    }
}
