package userInterface;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *  @src http://stackoverflow.com/questions/3727662/how-can-you-search-google-programmatically-java-api
 *
 */
public class NDCGInterface 
{
	 List<Integer> rankedList = new ArrayList<Integer>();
	 List<Integer> correctList = new ArrayList<Integer>();
	//list of search terms
	 static List<String> queryList = new ArrayList<String>();
	    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|csv" 
                + "|png|tiff?|mid|mp2|mp3|mp4"
                + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                + "|rm|smil|wmv|swf|wma|zip|rar|gz|xml|java|cpp))$");
	 
	 public static void main(String[] args)
	 {
		 queryList.add("crista lopes");
		 queryList.add("mondego");
		 queryList.add("software engineering");
		 queryList.add("computer games");
		 queryList.add("security");
		 queryList.add("REST");
		 queryList.add("student affairs");
		 queryList.add("graduate courses");
		 queryList.add("information retrieval");
		 queryList.add("machine learning");
		 
		 CalculateNDCG();
	 }
	 
	 static void CalculateNDCG()
	 {
		 try
		 {
			 List<List<String>> resultListOfGoogleSearch = GetListOfGoogleSearchResults();
			 List<List<String>> resultListOfOurSearch = GetListOfOurSearchResults();
			 double ndcgVal = 0;
			 for(int i = 0; i < 10; i++)
			 {
				ndcgVal = NDCGCalculator.compute(resultListOfOurSearch.get(i), resultListOfGoogleSearch.get(i), null);
				System.out.println("NDCG Value for " + queryList.get(i)+" " + ndcgVal);
			 }
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	 }
	 
	 static List<List<String>> GetListOfGoogleSearchResults()
	 {
		 List<List<String>> resultList = new ArrayList<List<String>>();
		 try
		 {
			 String google = "http://www.google.com/search?q=";
			 
			 //character encoding and user agent name
			 String charset = "UTF-8";
			 String userAgent = "NDCGCalculator_StudentSearchEngineProject_UCI";
			 String domain = "site:ics.uci.edu";
			 
			 for(String query : queryList)
			 {
				 query = query + " " + domain;
				 List<String> resultsForEachQuery = new ArrayList<String>();
 				 Elements links = Jsoup.connect(google + URLEncoder.encode(query, charset)).userAgent(userAgent).timeout(0).get().select("li.g>h3>a");
				 int linkCounter = 5;
 				 for (Element link : links) 
				 {
 					 if(linkCounter > 0)
 					 {
 					     String url = link.absUrl("href"); 
 					     url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

 					     if (shouldNotVisit(url)) 
 					     {
 					         continue; // Ads/news/non-html/https.
 					     }
 					     
 					     resultsForEachQuery.add(url);
 					     System.out.println("URL: " + url);
 					     linkCounter--;
 					 }
 					 else
 					 {
 						 break;
 					 }
 				  }
				resultList.add(resultsForEachQuery);
			}
			 return resultList;
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }

	 public static boolean shouldNotVisit(String url) { 
			
			return !url.startsWith("http") ||
 		 url.startsWith("https") || 
 		 FILTERS.matcher(url).matches()
					|| (url.contains("calendar"))
					//|| (url.contains("archive.ics.uci.edu"))
					|| (url.contains("drzaius.ics.uci.edu"))
					|| (url.contains("flamingo.ics.uci.edu"))
					|| (url.contains("fano.ics.uci.edu"))
					|| (url.contains("ironwood.ics.uci.edu"))
					|| (url.contains("duttgroup.ics.uci.edu"))
					|| (url.contains("wics.ics.uci.edu"))
					|| (url.contains("physics.uci.edu"))
					|| (url.contains("djp3-pc2.ics.uci.edu"))
					|| (url.contains("informatics.uci.edu"));
					//|| (url.contains("nile.ics.uci.edu"));
		}
	 
	 static List<List<String>> GetListOfOurSearchResults()
	 {
		 List<List<String>> resultList = new ArrayList<List<String>>();
		 try
		 {
			 QueryInterface queryInterface = new QueryInterface();
			 for(String query : queryList)
			 {
				 resultList.add(queryInterface.GetStringSearchResults(query));
			 }
			 return resultList;
		 }
		 
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 return null;
		 }
	 }
}
