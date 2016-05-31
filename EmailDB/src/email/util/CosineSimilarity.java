package email.util;

import java.io.File;
import java.util.HashMap;

public class CosineSimilarity {
	public double getCosineSimilarity(String sourceFile1, String sourceFile2, String sourceFolder) {
		// String sourceFile1=("D:\\ExtractData\\temp.txt"); // path of file
		// String sourceFile2=("D:\\ExtractData\\temp2.txt");

		File f1 = new File(sourceFile1); // making file out of path
		File f2 = new File(sourceFile2);

		HashMap<String, Float> idf_Map = new HashMap<String, Float>();
		Calculate_tf_idf calc = new Calculate_tf_idf();

		double cs = calc.Calculate_CosineSimilarity(f1, f2, sourceFolder);

		// System.out.println(cs);
		
		//if cs = 1 => files are exactly same, so distance for clustering between files is zero
		return (1-cs);
	}
}
