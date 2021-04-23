package dbutilities;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnectionFactory {
	
	public static Connection getdbconnection()
	{
		Connection con=null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		con=
		DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE","system","shashank");
		}
		catch(Exception e)
		{
		System.out.println("Connection Failed! Check console!"+e);	
		}
		if(con!=null)
		{
			System.out.println("..............................");
		}
		else
		{
			System.out.println("DB Connection Failed!");
		}
		return con;
	}


}
