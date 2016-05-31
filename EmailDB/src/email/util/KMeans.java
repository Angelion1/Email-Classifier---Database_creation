package email.util;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class KMeans {
	int k;
    int noOfItems;
    CosineSimilarity cs;
    ArrayList<String> dataItems;
    String sourceFolder;
    ArrayList<String> cz;
    ArrayList<String> oldCz;
    ArrayList<Double> row;
    ArrayList<ArrayList<String>> groups;
    
    public ArrayList<ArrayList<String>> getGroups() {
		return groups;
	}

	Scanner input;

    public KMeans(int k, int noOfItems, ArrayList<String> dataItems, String sourceFolder) {
        this.k = k;
        this.noOfItems = noOfItems;
        cs = new CosineSimilarity();
        this.dataItems = dataItems;
        this.sourceFolder = sourceFolder;
        cz = new ArrayList<>();
        oldCz = new ArrayList<>();
        row = new ArrayList<>();
        groups = new ArrayList<>();
        input = new Scanner(System.in);
    }
    
    public ArrayList<ArrayList<String>> getClusters(){
    	
        for (int i = 0; i < k; i++) {
            groups.add(new ArrayList<>());
        }

        for (int i = 0; i < noOfItems; i++) {
            //System.out.println("Enter Value for: " + (i + 1) + " item");
            //dataItems.add(input.nextInt());
            if (i < k) {
                cz.add(dataItems.get(i));
                System.out.println("C" + (i + 1) + " is " + cz.get(i));
            }
        }
        int iter = 1;
        do {
            for (String aItem : dataItems) {
                for (String c : cz) {
                	//distance metric, implement cosine similarity metric for e-mails
                    row.add(cs.getCosineSimilarity(c, aItem, sourceFolder));
                }
                groups.get(row.indexOf(Collections.min(row))).add(aItem);
                row.removeAll(row);
            }
            for (int i = 0; i < k; i++) {
                if (iter == 1) {
                    oldCz.add(cz.get(i));
                } else {
                    oldCz.set(i, cz.get(i));
                }
                if (!groups.get(i).isEmpty()) {
                    cz.set(i, average(groups.get(i)));
                }
            }
            if (!cz.equals(oldCz)) {
                for (int i = 0; i < groups.size(); i++) {
                    groups.get(i).removeAll(groups.get(i));
                }
            }
            iter++;
        } while (!cz.equals(oldCz));
        for (int i = 0; i < cz.size(); i++) {
            System.out.println("New C" + (i + 1) + " " + cz.get(i));
        }
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + (i + 1));
            System.out.println(groups.get(i).toString());
        }
        System.out.println("Number of Itrations: " + iter);
        
        return getGroups();
    }
    
    //implement most similar e-mail in a group logic here
    public String average(ArrayList<String> list) {
        double sum;
        HashMap<String, Double> sums = new HashMap<>(); 
        for (String file1 : list) {
        	sum = 0;
            for(String file2 : list){
        	sum = sum + cs.getCosineSimilarity(file1, file2, sourceFolder);
            }
            sum = sum / list.size();
            sums.put(file1, sum);
        }
        
        int n = sums.size();
        String result = list.get(0);
        double min = sums.get(list.get(0)),x;
        for(int i=0;i<n;i++){
        	x = sums.get(list.get(i));
        	if(x<min){
        		min = x;
        		result = list.get(i);
        	}
        }
        
        return result;
    }
}
