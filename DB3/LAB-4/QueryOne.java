import java.sql.*;


public class QueryOne {
	static String query = "";
	public static ResultSet execute(Statement s) throws SQLException{
		return s.executeQuery(query);
	}
}
