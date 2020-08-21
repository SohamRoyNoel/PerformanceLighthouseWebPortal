package connectionFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.reflect.Field;

public class Connections {
	public static Connection getConnection() {

		String ClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//		String IPAddr = "DESKTOP-LHLA0PA;";
//		String DBName="performancelighthouseserver";
		String DB_URL = "jdbc:sqlserver://pldatabase.database.windows.net:1433;database=performancelighthouseserver;user=performanceadmin@pldatabase;password=Noelle12;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;" ;
		Connection con=null;
		try {
			con = dbConnect (DB_URL,ClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Con s " + con);
		return con;
	}

	private static Connection dbConnect(String db_connect_string, String className) throws ClassNotFoundException
	{
		java.sql.Connection connection = null;
		String libpath = System.getProperty("java.library.path");
		String workingDirectory = System.getProperty("user.dir");
		String path = workingDirectory+"\\"+"src\\main\\webapp\\";

		libpath = path +libpath;
		System.setProperty("java.library.path",libpath);
		Field sysPathsField;
		
		try {
			sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			sysPathsField.setAccessible(true);
			sysPathsField.set(null, null);
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		}catch(SecurityException e1) {
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(className);
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
		try{
			connection = DriverManager.getConnection(db_connect_string);
			System.out.println("connected");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
