	import java.sql.*;

class ConnManager {
    private Connection conn;
    
    private static final String connectionString
	= "jdbc:oracle:thin:L3_12_1002253W/1002253@crooked.dcs.gla.ac.uk:1521:L3";

    public ConnManager() throws SQLException, ClassNotFoundException {
	Class.forName("oracle.jdbc.driver.OracleDriver");
	
	initiateConnection();
    }

    private void initiateConnection() throws SQLException {
	conn = DriverManager.getConnection(connectionString);
    }

    public Statement getStatement() throws SQLException {
	if(conn == null) {
	    initiateConnection();
	}
	
	return conn.createStatement();
    }

    public void closeConnection() throws SQLException {
	conn.close();
	conn = null;
    }
    
    public Connection getConnection(){
    	return conn;
    }
}
