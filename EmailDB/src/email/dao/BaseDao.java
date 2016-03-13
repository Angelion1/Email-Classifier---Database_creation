package email.dao;

import java.sql.Connection;
import java.sql.DriverManager;

//class to make connection to the database where the info of all the mails will be stored
public class BaseDao {
	
	public static Connection getCon(){
		Connection con = null;
		try {
			//specify the path where the database is located
			con = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Hari/Documents/Email.accdb");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}


public static void closeCon(Connection con){
	try {
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}