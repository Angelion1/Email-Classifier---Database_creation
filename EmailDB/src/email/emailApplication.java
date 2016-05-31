package email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//import org.hsqldb.lib.ArrayUtil;
import email.bean.Email;
import email.dao.EmailDao;
import email.util.Extracter;
import email.util.KMeans;
import email.util.Stemmer;

public class emailApplication {
	public static void main(String[] args) {

		ArrayList<String> ar = new ArrayList<>();
		String path = "D:/KmeansTrial";
		File f = new File(path);
		
		File fa[] = f.listFiles();
		for(File ce : fa){
				ar.add(ce.getAbsolutePath());	
		}
		
		
		KMeans km = new KMeans(2, 10, ar, path);
		ArrayList<ArrayList<String>> clusters = km.getClusters();
		
/*	
		EmailDao edao = new EmailDao();
		Email e1 = new Email();
		edao.insert(e1);
			
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

		  char[] w = {'e', 'a', 't', 'i', 'n', 'g'};
	      Stemmer s = new Stemmer();
	      for (int c = 0; c < 6; c++) s.add(w[c]);
	      s.stem();
	      String word = s.toString();
	      System.out.println(word);
	      	
*/	      
	        
	           
	     
	}
}
