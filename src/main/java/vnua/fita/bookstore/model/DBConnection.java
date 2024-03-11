package vnua.fita.bookstore.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	private static final String url = "jdbc:mysql://localhost:3306/bookstore";
    private static final String username = "root";
    private static final String password = "16072002";

	public static Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void closePreparedStatement(PreparedStatement ps) {
		try {
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement st) {
		try {
			st.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
