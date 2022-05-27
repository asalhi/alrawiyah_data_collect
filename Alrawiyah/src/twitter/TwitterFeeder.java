package twitter;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import support.IOFiles;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFeeder {

	private String twitter_consumer_key;
	private String twitter_consumer_secret;
	private String twitter_access_token;
	private String twitter_access_token_secret;
	private int general_counter;
	private TwitterStream twitterStream;

	private int counter;
    private String file_name;
    private boolean start;
    private int size_counter;

    public String getTwitter_consumer_key() {
		return twitter_consumer_key;
	}




	public void setTwitter_consumer_key(String twitter_consumer_key) {
		this.twitter_consumer_key = twitter_consumer_key;
	}




	public String getTwitter_consumer_secret() {
		return twitter_consumer_secret;
	}




	public void setTwitter_consumer_secret(String twitter_consumer_secret) {
		this.twitter_consumer_secret = twitter_consumer_secret;
	}




	public String getTwitter_access_token() {
		return twitter_access_token;
	}




	public void setTwitter_access_token(String twitter_access_token) {
		this.twitter_access_token = twitter_access_token;
	}




	public String getTwitter_access_token_secret() {
		return twitter_access_token_secret;
	}




	public void setTwitter_access_token_secret(String twitter_access_token_secret) {
		this.twitter_access_token_secret = twitter_access_token_secret;
	}




	public TwitterStream getTwitterStream() {
		return twitterStream;
	}




	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public int getGeneral_counter() {
		return general_counter;
	}

	public void setGeneral_counter(int general_counter) {
		this.general_counter = general_counter;
	}


	public TwitterFeeder(String twitter_consumer_key, String twitter_consumer_secret,
			String twitter_access_token, String twitter_access_token_secret) {
		super();


		this.setTwitter_consumer_key(twitter_consumer_key);
		this.setTwitter_consumer_secret(twitter_consumer_secret);
		this.setTwitter_access_token(twitter_access_token_secret);
		this.setTwitter_access_token_secret(twitter_access_token_secret);
		this.setGeneral_counter(0);
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setStreamBaseURL("https://stream.twitter.com/1.1/");
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(twitter_consumer_key);
		cb.setOAuthConsumerSecret(twitter_consumer_secret);
		cb.setOAuthAccessToken(twitter_access_token);
		cb.setOAuthAccessTokenSecret(twitter_access_token_secret);
		cb.setJSONStoreEnabled(true);
		cb.setTweetModeExtended(true);

		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		

	}



public void getTweetsTextFull(String folder_location, int split_value) {
	
    String [] arabicList = new String[] {"و","في","عن","الى","إلى","من","على","ثم","ان","إن","أن"};
  //  String [] arabicList = new String [] {"#فلسطين_قضيتي", "#فلسطين_ليست_قضيتي"};
    start = true;
    counter = 0;
    file_name = System.currentTimeMillis()+"";
  
    

	StatusListener listener = new StatusListener() {
		

	@Override
	public void onException(Exception arg0) {}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {}

	@Override
	public void onScrubGeo(long arg0, long arg1) {}
	
	@Override
	public void onStallWarning(StallWarning arg0) {}

	@Override
	public void onTrackLimitationNotice(int arg0) {}
	
	@Override
	public void onStatus(Status tweet) {
	
		if(size_counter == 1000) return;
		
		if(start) {
			System.out.println("[Alrawiyah - ArabicTweetsCollecter - Split by: "+split_value+"]: Saving to file:"+
				    folder_location+"/"+file_name);
			start = false;
		}
		if(counter == split_value) {
			file_name = System.currentTimeMillis()+"";
			size_counter++;

			
			counter = 0;
			System.out.println("[Alrawiyah - ArabicTweetsCollecter - Split by: "+split_value+"]: Saving to file:"+
				    folder_location+"/"+file_name +" Size Counter = :"+size_counter);
		}
		
		String tweet_txt = tweet.getText().replace("\"", " ").replaceAll("\\s+", " ");
		
		
				
				JSONObject tweet_j = new JSONObject();
				
	
				if(tweet.isRetweet()){
					

						String org_text = tweet.getRetweetedStatus().getText().replace("\"", " ").replaceAll("\\s+", " ");
						String org_retweet = tweet.getRetweetedStatus().getRetweetCount()+"";
						String org_fav = tweet.getRetweetedStatus().getFavoriteCount()+"";
						String t_id1 = tweet.getRetweetedStatus().getId()+"";
			 			String t_user_screenname1 = tweet.getRetweetedStatus().getUser().getScreenName();
			     		String t_url1 = "https://twitter.com/"+t_user_screenname1+"/status/"+t_id1;
						
					tweet_j.put("rt", "y");
					tweet_j.put("qt", "n");
					tweet_j.put("c", org_retweet);
					tweet_j.put("f", org_fav);
					tweet_j.put("t", org_text); //-----
					tweet_j.put("u", t_url1);

				
      		}
				else if(tweet.getQuotedStatusId()!=0) {
					
					
						String qouted_text = tweet.getQuotedStatus().getText().replace("\"", " ").replaceAll("\\s+", " ");
						String qouted_retweet = tweet.getQuotedStatus().getRetweetCount()+"";
						String qouted_fav = tweet.getQuotedStatus().getFavoriteCount()+"";

						String t_id2 = tweet.getQuotedStatus().getId()+"";
			 			String t_user_screenname2 = tweet.getQuotedStatus().getUser().getScreenName();
			     		String t_url2 = "https://twitter.com/"+t_user_screenname2+"/status/"+t_id2;

			     			
				    tweet_j.put("rt", "n");
					tweet_j.put("qt", "y");
					
					tweet_j.put("c", qouted_retweet);
					tweet_j.put("f", qouted_fav);
					
					tweet_j.put("t", qouted_text); //--------
					tweet_j.put("u", t_url2);

					
				}
			
				else {
					String t_fav = tweet.getFavoriteCount()+"";
					String t_retweets = tweet.getRetweetCount() +"";
	                String t_id = tweet.getId()+"";
	 				String t_user_screenname = tweet.getUser().getScreenName();
	     			String t_url = "https://twitter.com/"+t_user_screenname+"/status/"+t_id;


					
					tweet_j.put("rt", "n");
					tweet_j.put("qt", "n");
					
					tweet_j.put("c", t_retweets);
					tweet_j.put("f", t_fav);
					
					tweet_j.put("t", tweet_txt);
					tweet_j.put("u", t_url);

					
					
					
					
				}
			
				String tweet_json_string = StringEscapeUtils.unescapeJava(tweet_j.toString());
				
				IOFiles.saveTextFileAppend(tweet_json_string, folder_location+"/"+file_name+".txt");
				counter++;

      }; 
};

twitterStream.addListener(listener);

FilterQuery fq = new FilterQuery(0,null,arabicList);
        fq.language(new String[]{"ar"});
twitterStream.filter(fq);

}



	
}
