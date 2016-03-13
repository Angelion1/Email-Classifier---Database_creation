package email.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import email.bean.Email;

//class which performs the extraction of e-mails from the text files
//also contains a stopword remover function which removes the 
//stopwords from the data to reduce its size
public class Extracter {
	
	//the function recursively searches for text files and
	//then calls the extract function on each text file
	public ArrayList<Email> extractAll(String path) throws IOException{
		ArrayList<Email> arEmail = new ArrayList<Email>();
		File f = new File(path);
		if(f.isFile()){
			arEmail.add(extract(path));
		}
		else if(f.isDirectory()){
			
			File fa[] = f.listFiles();
			for(File ce : fa){
				if(ce.isDirectory()){
					arEmail.addAll(extractAll(ce.getAbsolutePath()));
				}
				else{
					arEmail.add(extract(ce.getAbsolutePath()));
				}
			}
		}
		else{
			IOException e = new IOException("Wrong path!!");
			throw e;
		}	
		return arEmail;
	}
	
	//this function searches the file for the different fields of info
	//and stores them in an Email object after removing stopwords
	public Email extract(String path){
		Email e1 = new Email();
		e1.setBody("body");
		e1.setFrom("from");
		e1.setSub("sub");
		e1.setTo("to");
		try {
			Scanner scan = new Scanner(new FileReader(path));
			while(scan.hasNext()){
				String token = scan.next();
				if("To:".equals(token)){
					e1.setTo(scan.next());
					continue;
				}
				else if("From:".equals(token)){
					e1.setFrom(scan.next());
					continue;
				}
				else if("Subject:".equals(token)){
					String s = scan.nextLine();
					s = stopWordsRemover(s);
					e1.setSub(s);
				}
			}
			scan.close();
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while(line != null){
				if("".equals(line)){
					
					line = br.readLine();
					String temp = line;
					while(temp != null){
						temp = br.readLine();
						line += temp;
					}
					line = line.substring(0, line.length()-4);
					line = stopWordsRemover(line);
					e1.setBody(line);
					line = null;
				}
				else
					line = br.readLine();
			}
			br.close();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return e1;
	}
	
	//the function removes stopwords from any string
	//it uses a textfile which has a list of stopwords in english language
	public String stopWordsRemover(String s){
		String res = "";
		Set<String> stopWordsSet = new HashSet<String>();
		ArrayList<String> wordsList = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(new FileReader("stopwordslist.txt"));
			while(scan.hasNext()){
				String token = scan.next();
				stopWordsSet.add(token);
			}
			scan.close();
			StringBuilder builder = new StringBuilder(s);
	        String[] words = builder.toString().split("\\s");
	        for (String word : words){
	            wordsList.add(word);
	        }
	        for(String word : wordsList){
	        	word = word.toLowerCase();
	        	if(!stopWordsSet.contains(word)){
	        		res += " " + word;
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
