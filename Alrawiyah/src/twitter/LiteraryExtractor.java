package twitter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import support.IOFiles;

public class LiteraryExtractor {


	public static String removeHarakat(String quote) {

		quote = quote.replace("َ", "");
		quote = quote.replace("ً", "");
		quote = quote.replace("ِ", "");
		quote = quote.replace("ٍ", "");
		quote = quote.replace("ُ", "");
		quote = quote.replace("ٌ", "");
		quote = quote.replace("ّ", "");
		quote = quote.replace("ْ", "");

		return quote.trim();
	}

	public static int countOccurrences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	public static String filterEnglish(String tweet) {

		tweet = tweet.replaceAll(
				"(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)", "");
		tweet = tweet.replaceAll("@(.*?) ", "");
		tweet = tweet.replaceAll(
				"(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)", "");
		tweet = tweet.replace("\"", "");
		tweet = tweet.replace("\\", "");
		tweet = tweet.replace("/", "");
		tweet = tweet.replace("_", "");
		tweet = tweet.replace("-", "");
		tweet = tweet.replace("<", "");
		tweet = tweet.replace(">", "");
		tweet = tweet.replace("•", "");
		tweet = tweet.replace("*", "");
		tweet = tweet.replace("~", "");
		tweet = tweet.replace("@", "");
		tweet = tweet.replace("⌣", "");
		tweet = tweet.replace("⌣", "");
		tweet = tweet.replace(")", "");
		tweet = tweet.replace("(", "");

		tweet = tweet.replaceAll("[a-zA-Z0-9]", "");

		tweet = tweet.replaceAll("\\s+$", " ").trim();

		return tweet;
	}

	public static int rankHarakat(String tweet) {
		int number = 0;

		String[] subTweets = tweet.split(" ");
		for (int i = 0; i < subTweets.length; i++) {

			if (howManyHarakat(subTweets[i]) > 0) {
				number++;
			}

		}
		return number;
	}

	public static int howManyHarakat(String tweet) {
		int howMany = 0;

		int fat7a = countOccurrences(tweet, 'َ');
		int tanweenFat7 = countOccurrences(tweet, 'ً');
		int kasra = countOccurrences(tweet, 'ِ');
		int tanweenKaser = countOccurrences(tweet, 'ٍ');
		int dameh = countOccurrences(tweet, 'ُ');
		int tanweenDam = countOccurrences(tweet, 'ٌ');
		int sokon = countOccurrences(tweet, 'ْ');
		int shadeh = countOccurrences(tweet, 'ّ');

		howMany = fat7a + tanweenFat7 + kasra + tanweenKaser + dameh
				+ tanweenDam + sokon + shadeh;

		return howMany;
	}

	public static void GetQuotes(String srcPath , String desPath, String logFile){

		System.out.println("Starting ...");

		Hashtable<String, Integer> possibleQuotes = new Hashtable<String, Integer>();
		ArrayList<String> FrequentQuotes = new ArrayList<String>();
		int numberOfQ=0;
		int howMany = 0;
		int numberOfTweets = 0;
		int N = 6;
		String files;
		File folder = null;
		try{
		folder = new File(srcPath);
		File[] listOfFiles = folder.listFiles();
		int counter = 0;
		int num = 1;
		int missed = 0;
		System.out.println("Reading Files ... ");
		
		String header= "tweet,interaction,url";
		IOFiles.saveTextFileAppend(header, desPath);
		for (int f = 0; f < listOfFiles.length; f++) {

		
			files = listOfFiles[f].getName();
		  try{
			String filePath = srcPath + files;
			System.out.println(filePath);
			String[] twiiData = IOFiles.readFileAsString(filePath).split("\n");
			String tweet ="";
			for (int i = 0; i < twiiData.length; i++) {

				try {
					String line = twiiData[i].trim();
					
					JSONObject js = new JSONObject(line);
					tweet = js.getString("t");
					int fav = Integer.parseInt(js.getString("f"));
					int re = Integer.parseInt(js.getString("c"));
					String urrl = js.getString("u");
					int score = fav+re;

					
					numberOfTweets++;
					
						tweet = filterEnglish(tweet);
						if ((howMany = rankHarakat(tweet)) >= N) {
							
							String org = tweet.replace(",", "") + ","+score+","+urrl;
						if(!FrequentQuotes.contains(tweet)){
							possibleQuotes.put(org, howMany);
							 FrequentQuotes.add(tweet);
						 }

						
					}
				} catch (Exception E) { missed++; }
			}
			counter++;

			  ArrayList<Entry<String,Integer>> myArrayList =new ArrayList<Entry<String, Integer>>(possibleQuotes.entrySet());
			  Collections.sort(myArrayList, new MyComparator());
		      Iterator<Entry<String,Integer>> itr =myArrayList.iterator();

		      while (itr.hasNext()) {
				Map.Entry<String, Integer> e = (Map.Entry<String, Integer>) itr
						.next();
				String key = (String) e.getKey();
				///key = key.replace(",", "");
				//int value = (Integer) e.getValue();

				IOFiles.saveTextFileAppend(key, desPath);
			
			}

			// quotes = new StringBuffer();
		    numberOfQ = possibleQuotes.size()+numberOfQ;
			possibleQuotes.clear();
			if (counter == 100) {
				System.out.println("Done with "+(num*100)+" files.");
				num++;
				counter = 0;
			}

		 }catch (Exception E) {IOFiles.saveTextFileAppend("Error 1 in GetQ: File read problem. In file: "+files,logFile);
		                       System.out.println("false");}

		}
		
		System.out.println("Phase two done");
		System.out.println("Number of Quotes: "+numberOfQ);
		System.out.println("Number of Tweets: "+numberOfTweets);
		System.out.println("Number of Missed Tweets: "+missed);
		} catch (Exception E) { IOFiles.saveTextFileAppend("Error 0 in GetQ: Folder read problem. In file: "+srcPath,logFile);
		                        System.out.println("false");}
		//System.out.println("exit(1)");

	}

	static class MyComparator implements Comparator<Object> {

		@SuppressWarnings("unchecked")
		public int compare(Object obj1, Object obj2) {
			int result = 0;
			Map.Entry<String, Integer> e1 = (Map.Entry<String, Integer>) obj1;
			Map.Entry<String, Integer> e2 = (Map.Entry<String, Integer>) obj2;
			Integer value1 = (Integer) e1.getValue();
			Integer value2 = (Integer) e2.getValue();
			if (value1.compareTo(value2) == 0) {
				String word1 = (String) e1.getKey();
				String word2 = (String) e2.getKey();
				result = word1.compareToIgnoreCase(word2);
			} else {
				result = value2.compareTo(value1);
			}
			return result;
		}
	}
}


