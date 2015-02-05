package lib.cisco.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnectionManager {
	public static Properties commonProperties = null;
	
	private static String DB_DRIVERNAME = null;
	private static String DB_AUTOMATION_URL = null; 
	private static String DB_AUTOMATION_USERNAME = null; 
	private static String DB_AUTOMATION_PASSWORD = null; 
	private final static Logger LOGGER = Logger.getLogger(DBConnectionManager.class.getName());
    
	static {
		commonProperties = PropertiesFileReader.getInstance().readProperties("common.properties");
		DB_DRIVERNAME = commonProperties.getProperty("jdbc.driver.name").trim();
		DB_AUTOMATION_URL = commonProperties.getProperty("jdbc.driver.url").trim();
		DB_AUTOMATION_USERNAME = commonProperties.getProperty("db.username").trim();
		DB_AUTOMATION_PASSWORD = commonProperties.getProperty("db.password").trim();
		
	}


	public static Connection getConnection() throws Exception {
		Connection connection = null;
		if (null != commonProperties && !commonProperties.isEmpty()) {
			Class.forName(DB_DRIVERNAME);
			connection = DriverManager.getConnection(DB_AUTOMATION_URL,DB_AUTOMATION_USERNAME, DB_AUTOMATION_PASSWORD);
			System.out.println("Connection created successfully");
			return connection;
		}
		return null;
	}//End of Method. 
	public static void close(ResultSet rs) {

		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
				//e.printStackTrace();
				LOGGER.log(Level.SEVERE, "Exception :",e);
			}
	}

	public static void close(Statement statement) {

		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				LOGGER.log(Level.SEVERE, "Exception :",e);
			}
	}

	public static void close(PreparedStatement preStatement) {

		if (preStatement != null)
			try {
				preStatement.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				LOGGER.log(Level.SEVERE, "Exception :",e);
			}
	}

	public static void close(Connection connection) {

		try {
			if (connection != null)
				connection.close();
			System.out.println("Connection closed successfully");
		} catch (SQLException e) {
			//e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception :",e);
		}
	}
	
	public static void main(String[] a) throws Exception{
		getConnection();
		
	}

}//End of Class.
