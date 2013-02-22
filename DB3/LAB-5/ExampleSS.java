import db3.SpreadSheet;

import java.io.IOException;
import java.sql.*;


/*

 Program to demonstrate usage of the SpreadSheet Class

 This program reads from file input.xls and works
 its way through the rows in the first sheet in the
 spreadsheet and prints information to the screen

 */

public class ExampleSS {
	static ConnManager CM;
	private static Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java ExampleSS inputFileName.xls");
			System.exit(1);
		} else {
			try {
				CM = new ConnManager();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			conn = CM.getConnection();

			try {
				ps = conn.prepareStatement("INSERT INTO ATTENDANCE VALUES(?,?,?,?)");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				SpreadSheet ss = new SpreadSheet(args[0], 0);
				int rows = ss.rows();
				for (int i = 1; i < rows; i++) {
					String col1 = ss.getRowCell(i, 0);
					String col2 = ss.getRowCell(i, 1);
					col2 = col2.trim();
					String col3 = ss.getRowCell(i, 2);
					col3 = col3.replace("/", "-");
					String col4 = ss.getRowCell(i, 3);
					System.out.println("Row " + i + ":\t" + col1 + "\t" + col2
							+ "\t" + col3 + "\t" + col4);
					
					if(col1==""){
						ps.setNull(1, java.sql.Types.INTEGER);
					} else{
						ps.setInt(1, Integer.parseInt(col1));
					}
					if(col2==""){
						ps.setNull(2,java.sql.Types.VARCHAR);
					} else {
						ps.setString(2, col2);
					}
					if(col3==""){
						ps.setNull(2,java.sql.Types.VARCHAR);
					} else {
						ps.setString(3, col3);
					}
					if(col4==""){
						ps.setNull(4, java.sql.Types.INTEGER);
					}else {
						ps.setInt(4, Integer.parseInt(col4));
					}
					try{
						rs = ps.executeQuery();
					} catch (SQLException sql){
						if(sql.getErrorCode()!=1){
							sql.printStackTrace();
						}
					}
				}

			} catch (Exception ee) {
				System.out
						.println("There was a problem reading the Spread Sheet");
				ee.printStackTrace();
			}
		}
		try {
			CM.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
