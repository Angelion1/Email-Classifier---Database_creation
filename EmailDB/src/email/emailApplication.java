package email;

import java.util.ArrayList;

//import org.hsqldb.lib.ArrayUtil;
import email.bean.Email;
import email.dao.EmailDao;
import email.util.Extracter;

public class emailApplication {
	public static void main(String[] args) {
		Extracter ext = new Extracter();
		
		//setting path to where the text files are which contain the mails 
		String path = "C:/Users/Hari/Downloads/arnold-j/_sent_mail";
		
		try {
			//get the array of all the extracted mails from all the text files 
			ArrayList<Email> arEmail = ext.extractAll(path);
			
			//insert all the mails in the database
			EmailDao edao = new EmailDao();
			for(Email ce : arEmail){
				edao.insert(ce);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}