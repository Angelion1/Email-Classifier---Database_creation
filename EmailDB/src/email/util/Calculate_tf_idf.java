package email.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Calculate_tf_idf {

	HashMap<String, Float> tf_file1;
	HashMap<String, Float> tf_file2;
	HashMap<String, Float> idf_Map=new HashMap<String, Float>();
	ArrayList<File> allFiles = new ArrayList<File>();

	public HashMap<String,Float> Calculate_tf(File file){
		//function to calculate tf of a given file and return the hashmap of the tf of the word present

		HashMap<String, Float> tf_Map=new HashMap<String, Float>();
		HashMap <String,Float> freqMap = new HashMap<String,Float>(){
			@Override  //Override the get function of Hashmap to return default value as 0
			public Float get(Object key) {
				if(! containsKey(key))
					return (float)0;
				return super.get(key);
			}
		};

		try {
			float totalWord=0;
			Scanner srcRead= new Scanner(file);
			//Setting frequency Map
			while(srcRead.hasNext()){
				totalWord++;
				String str = srcRead.next();
				freqMap.put(str,(freqMap.get(str))+1);
				//				str = srcRead.next().toString();

			}
			//Setting tf Map
			for (String word: freqMap.keySet()){
				//tf=frequency/total no of word in document
				tf_Map.put(word,freqMap.get(word)/totalWord);   
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tf_Map;
	}


	public HashMap<String,Float> Calculate_idf(File file1,File file2,String folderPath){
		//Calculating idf of two file and return HashMap of idf
		listAllFile(folderPath);
		HashMap<File,HashMap<String, Float>> map_tf_file = new HashMap<File,HashMap<String, Float>>();
		for(File f : allFiles){
			map_tf_file.put(f,Calculate_tf(f));
		}
		for(File f : allFiles){
			HashMap<String, Float> curr_tf_file = new HashMap<String, Float>();
			curr_tf_file = map_tf_file.get(f);
			for(String word : curr_tf_file.keySet()){
				if(idf_Map.containsKey(word)){
					idf_Map.put(word,idf_Map.get(word)+1);
				}
				else{
					idf_Map.put(word,(float)1.0);
				}	
			}
		}

		//calculating idf
		for(String word : idf_Map.keySet()){
			//IDF(t) =1 + log_e(Total number of documents / Number of documents with term t in it).
			idf_Map.put(word,1+(float)Math.log((2/idf_Map.get(word))));
		}
		tf_file1= Calculate_tf(file1);
		tf_file2=Calculate_tf(file2);
		//Filling the missing term in tf of file 1 and 2 
		generalised_tf(tf_file1, idf_Map);
		generalised_tf(tf_file2, idf_Map);

		return idf_Map;
	}


	public float Calculate_CosineSimilarity(File file1,File file2,String folderPath){
		//calculate and return the cosine similarity between two file
		float cosineSimilarity=0;
		float dotProduct=0;
		float modFile1=0;
		float modFile2=0;
		HashMap<String, Float> idf = Calculate_idf(file1, file2,folderPath) ;
		HashMap<String, Float> tf_idf_file1 = new HashMap<String, Float>() ;
		HashMap<String, Float> tf_idf_file2 = new HashMap<String, Float>() ;

		//calculating tf*idf
		for(String word : tf_file1.keySet()){
			tf_idf_file1.put(word, tf_file1.get(word)*idf.get(word));
			tf_idf_file2.put(word, tf_file2.get(word)*idf.get(word));
		}

		
		
		/*for(String word : tf_idf_file1.keySet()){
			System.out.println(word +" idf = "+idf.get(word));
		}*/

		
		

		for(String word : tf_file1.keySet()){

			//Dot product (d1,d2) = d1[0] * d2[0] + d1[1] * d2[1] * … * d1[n] * d2[n]
			dotProduct+= tf_idf_file1.get(word)*tf_idf_file2.get(word);
			modFile1+=tf_idf_file1.get(word)*tf_idf_file1.get(word);
			modFile2+=tf_idf_file2.get(word)*tf_idf_file2.get(word);
		}

		//||d1|| = square root(d1[0]2 + d1[1]2 + ... + d1[n]2)
		modFile1= (float)Math.sqrt(modFile1);
		modFile2= (float)Math.sqrt(modFile2);

		//Cosine Similarity (d1, d2) =  Dot product(d1, d2) / ||d1|| * ||d2||
		cosineSimilarity=dotProduct/(modFile1*modFile2);

		return cosineSimilarity;
	}

	//this function generalised the tf of a particular file w.r.t to idf (add all missing term) 
	public HashMap<String, Float> generalised_tf(HashMap<String, Float> tf_file,HashMap<String, Float> idf_Map){
		for(String word : idf_Map.keySet())
		{			
			if(!tf_file.containsKey(word)){
				tf_file.put(word,(float)0.0);
			}
		}
		return tf_file;
	}

	//this function list all the file present in the folder passed as argument
	public void listAllFile(String folder)
	{
		try {
			final File f1 = new File(folder);
			if(!f1.exists())
			{
				return;
			}
			if(f1.isDirectory()){       
				File fa[] = f1.listFiles();
				for(File ce : fa){	
					if(ce.isDirectory())//if another file is present
					{
						listAllFile(ce.getPath());
					}
					else
					{
						allFiles.add(ce);
						//System.out.println(ce);//to show all the folder
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
