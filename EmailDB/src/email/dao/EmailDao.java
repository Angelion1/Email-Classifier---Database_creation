package email.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import email.bean.Email;

//SQL class which executes the SQL update to fill the database
//The database must contain a table named Email with four fields
//To, From, Sub, Body all of text type
public class EmailDao extends BaseDao {
	
	public boolean insert(Email e1) {
		boolean res = false;
		Connection con = null;
		try {
			con = BaseDao.getCon();
			String sql = "insert into Email " +
			" (To, From, Sub, Body) values " + " (?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			int i = 1;
			st.setString(i++, e1.getTo());
			st.setString(i++, e1.getFrom());
			st.setString(i++, e1.getSub());
			st.setString(i++, e1.getBody());
			int n = st.executeUpdate();
			res = n > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeCon(con);
		}
		return res;
	}

}
