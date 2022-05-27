package control;

import twitter.LiteraryExtractor;

public class TwitterLiteraryExtractor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String srcPath = "<tweets folder>";
		
		// output file --> ready for translation phase.
		String desPath = "<output file>"; 
		
		String  logFile = "<error log file>"; // for errors logging
		LiteraryExtractor.GetQuotes(srcPath, desPath, logFile);
		
		
	}

}
