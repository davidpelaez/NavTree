package navtree;

import java.io.*;
import java.util.ArrayList;

public class JsonLoader {
	
	

	public static void main(String args[]) {
		loadJson();
	}

	public static String[] loadJson() {
		
		ArrayList<String> fileLines = new ArrayList<String>();
		String[] lines = null;
		 try{
			    // Open the file that is the first 
			    // command line parameter
			    FileInputStream fstream = new FileInputStream("data/myNavtree.json");
			    // Get the object of DataInputStream
			    DataInputStream in = new DataInputStream(fstream);
			        BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    String strLine;
			    //Read File Line By Line
			    while ((strLine = br.readLine()) != null)   {
			      // Print the content on the console
			      fileLines.add(strLine);
			    }
			    //Close the input stream
			    in.close();
			    lines = fileLines.toArray(new String[1]);
			    
			    }catch (Exception e){//Catch exception if any
			      System.err.println("Error: " + e.getMessage());
			    }
			    return lines;
	}

}
